from flask import Flask, request, jsonify
from flask_cors import CORS
from analyse import perform_sentiment_analysis  # Import your perform_sentiment_analysis function

# Create a Flask app
app = Flask(__name__)
CORS(app)


# Define the route for sentiment analysis
@app.route('/analyze', methods=['POST'])
def analyze():
    try:
        # Get JSON data from the request
        data = request.json
        user_id = data.get("user_id")  # Extract the 'user_id' field

        if not user_id:
            return jsonify({"error": "User ID is required"}), 400

        # Perform sentiment analysis using the user_id
        result = perform_sentiment_analysis(user_id)

        # Return the result as JSON
        return jsonify(result), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 500


# Run the app
if __name__ == '__main__':
    app.run(port=5000)
