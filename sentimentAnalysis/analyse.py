# %%
import nltk
import pandas as pd
import numpy as np
import re
# import matplotlib.pyplot as plt
from nltk.corpus import twitter_samples, stopwords
from nltk.tokenize import TweetTokenizer
#from wordcloud import WordCloud
from nltk.stem import PorterStemmer
from sklearn.feature_extraction.text import TfidfVectorizer




# %%
nltk.download('twitter_samples')
nltk.download('stopwords')
# Load positive and negative tweets
positive_tweets = twitter_samples.strings('positive_tweets.json')
negative_tweets = twitter_samples.strings('negative_tweets.json')


# %%
data = pd.DataFrame({
    'tweet': positive_tweets + negative_tweets,
    'label': [1] * len(positive_tweets) + [0] * len(negative_tweets)
})

# %%
print("\nSample Tweets:\n", data.head())
print("\nLabel Distribution:\n", data['label'].value_counts())


# %%
from nltk.stem import WordNetLemmatizer
stop_words = set(stopwords.words('english'))
tokenizer = TweetTokenizer(preserve_case=False, strip_handles=True, reduce_len=True)
lemmatizer = WordNetLemmatizer()

# %%
# Function to clean tweets
def preprocess_tweet(tweet):
# Remove URLs and mentions
    tweet = re.sub(r'http\S+|www\S+|@\S+', '', tweet)  
    tokens = tokenizer.tokenize(tweet)
    clean_tokens = [lemmatizer.lemmatize(word) for word in tokens if word not in stop_words and word.isalpha()]
    return ' '.join(clean_tokens)

# %%
positive_cleaned = [preprocess_tweet(tweet) for tweet in positive_tweets]


# %%
import nltk
nltk.download('wordnet')

# %%
negative_cleaned = [preprocess_tweet(tweet) for tweet in negative_tweets]

# %%
# Create cleaned dataset
cleaned_data = pd.DataFrame({
    'tweet': positive_cleaned + negative_cleaned,
    'label': [1] * len(positive_cleaned) + [0] * len(negative_cleaned)
})

print("\nCleaned Tweets Sample:\n", cleaned_data.head())


# %%
# Feature Extraction: TF-IDF
vectorizer = TfidfVectorizer(max_features=5000)
X = vectorizer.fit_transform(cleaned_data['tweet']).toarray()
y = cleaned_data['label']


# %%
# Train-Test Split
from sklearn.model_selection import train_test_split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# %%
# Logistic Regression
from sklearn.metrics import  accuracy_score
from sklearn.linear_model import LogisticRegression
lr_model = LogisticRegression()
lr_model.fit(X_train, y_train)
y_pred_lr = lr_model.predict(X_test)
print("\nLogistic Regression Accuracy:", accuracy_score(y_test, y_pred_lr))


# %%
# Function to predict user input sentiment
def predict_sentiment(input_text, model, vectorizer):
    cleaned_text = preprocess_tweet(input_text)
    text_features = vectorizer.transform([cleaned_text])
    prediction = model.predict(text_features)[0]
    return "Positive" if prediction == 1 else "Negative"

# %%
from pymongo import MongoClient
# MongoDB Connection
client = MongoClient("mongodb+srv://ktaunk28:qwerty123@cluster0.dim29.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0")  # Local MongoDB instance
db = client["journaldb"]  # Replace 'sentiment_db' with your database name
collection = db["users"]  # Replace 'input_tweets' with your collection name

# %%
from pymongo import MongoClient
from bson.objectid import ObjectId

# Connect to MongoDB
client = MongoClient("mongodb+srv://ktaunk28:qwerty123@cluster0.dim29.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0")  # Replace with your MongoDB URI if needed
db = client['journaldb']  # Connect to the journaldb database
users_collection = db['users']  # Access the users collection
journal_entries_collection = db['journal_Entries']  # Access the journal_Entries collection


# Ensure user exists and has journal entries
def fetch_user_document(user_id):

    try:
        # Validate the user ID format
        if not ObjectId.is_valid(user_id):
            raise ValueError("Invalid user ID format")

        user_object_id = ObjectId(user_id)
        user_document = users_collection.find_one({"_id": user_object_id})

        if not user_document:
            raise ValueError("User not found")
        return user_document
    except Exception as e:
        print(f"Error fetching user: {e}")
        return None
    
def fetch_journal_entries(journal_entries_refs):
    """Fetch all journal entries for the given DBRef list."""
    journal_entries = []
    for ref in journal_entries_refs:
        entry_id = ref.id  # Get the ObjectId of the journal entry
        entry_document = journal_entries_collection.find_one({"_id": entry_id})
        if entry_document:
            journal_entries.append(entry_document)
    return journal_entries


    # Process and analyze each journal entry
def analyze_journal_entry(entry_text):
    """Analyze the sentiment of a single journal entry."""
    try:
        cleaned_entry = preprocess_tweet(entry_text)  # Preprocess the text
        features = vectorizer.transform([cleaned_entry])  # Vectorize the text
        prediction = lr_model.predict(features)[0]  # Predict sentiment
        return "Positive" if prediction == 1 else "Negative"
    except Exception as e:
            print(f"Error analyzing journal entry: {e}")
            return "Error"

def perform_sentiment_analysis(user_id):
    """Fetch a user, analyze their journal entries, and save results."""
    try:
        # Fetch the user document
        user_document = fetch_user_document(user_id)
        if not user_document or "journalEntries" not in user_document:
            print("User not found or no journal entries available")
            return

        # Fetch journal entries
        journal_entries_refs = user_document["journalEntries"]  # Array of DBRef objects
        journal_entries = fetch_journal_entries(journal_entries_refs)

        # Perform sentiment analysis
        analysis_results = []
        for entry in journal_entries:
            if "text" in entry:  # Assuming each journal entry has a "text" field
                sentiment = analyze_journal_entry(entry["text"])
                analysis_results.append({
                    "entry_id": str(entry["_id"]),  # Convert ObjectId to string for JSON serialization
                    "sentiment": sentiment
                })

        # Save analysis results to the user's document
        users_collection.update_one(
            {"_id": ObjectId(user_id)},
            {"$set": {"journal_Analysis": analysis_results}}  # Add a new field for analysis results
        )
        print("Sentiment analysis results saved to MongoDB.")
    except Exception as e:
        print(f"Error performing sentiment analysis: {e}")


# %%
# Apply analysis to the content field of all journal entries
results = []

# Loop through journal entries and analyze their 'content' field

for entry in fetch_journal_entries(journal_entries_refs):
    if "content" in entry:  # Ensure the 'content' field exists
        sentiment = analyze_journal_entry(entry["content"])  # Pass only the 'content' field
        results.append({
            "entry_id": entry["_id"],  # Include the unique ID of the journal entry
            "content": entry["content"],  # Include the original content for reference
            "sentiment": sentiment       # Include the sentiment analysis result
        })
    else:
        print(f"Entry with ID {entry['_id']} has no 'content' field.")  # Log missing 'content' fields

print("Analysis Results:", results)


# %% [markdown]
# 

# %%
# Update the journal entries with sentiment analysis results
for result in results:
    db["journal_Entries"].update_one(
        {"_id": result["entry_id"]},
        {"$set": {"sentiment": result["sentiment"]}}
    )


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%


# %%



