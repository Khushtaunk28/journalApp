import React, { useState, useEffect } from "react";
import axios from "axios";
import SignOut from "./JournalEntry";
import GetSentiment from "./Sentiment.jsx";
import { Link } from "react-router-dom";

const JournalPage = () => {
  const [journals, setJournals] = useState([]); // State to store journal entries
  const [newJournal, setNewJournal] = useState({ title: "", content: "" }); // State for new journal input
  const [editJournal, setEditJournal] = useState(null); // State to store the journal being edited
  const [updatedContent, setUpdatedContent] = useState({ title: "", content: "" }); // State for updated journal content
  const [weather,setweather]=useState(null);
  // Fetch all journal entries for the logged-in user on component mount
  useEffect(() => {
    // Function to fetch journal entries
    const fetchJournals = async () => {
      const token = localStorage.getItem("jwt");
      console.log(token);
      if (!token) {
        setError("No JWT token found.");
        return;
      }
      try {
        const response = await fetch("http://localhost:8081/journal", {
          method: "GET",
          headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        });
    
        if (!response.ok) {
          throw new Error("Failed to fetch journals");
        }
        const data = await response.json();
        console.log(data); // Check the structure of the response
        setJournals(data); // Set the response data
      } catch (error) {
        console.error("Error fetching journals:", error.message);
      }
    };
  
    fetchJournals();
  }, []);



  // Handle creating a new journal entry
  const handleCreateJournal = async () => {
    if (!newJournal.title.trim() || !newJournal.content.trim()) return;
    const token = localStorage.getItem("jwt"); // Retrieve token from localStorage
if (!token) {
  console.error("JWT token is missing.");
  return;
}
try {
  const response = await axios.post(
    "http://localhost:8081/journal",
    newJournal,
    {
      headers: {
        Authorization: `Bearer ${token}`, // Include JWT in Authorization header
        "Content-Type": "application/json",
      },
    }
  );
  setJournals([...journals, response.data]); // Add the new journal to the state
  setNewJournal({ title: "", content: "" }); // Clear the input fields
} catch (error) {
  console.error("Error creating journal entry:", error.response?.data || error.message);
}
  }

  // Handle deleting a journal entry
  const handleDeleteJournal = async (id) => {
    console.log("_id to delete:", id);  // Log the _id value being passed
    const token = localStorage.getItem("jwt");
    if (!token) {
      console.error("JWT token is missing.");
      return;
    }
    const idString=String(id);
    try {
      await axios.delete(`http://localhost:8081/journal/id/${idString}`, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",

        },
      });
      console.log("delete success");
      setJournals(journals.filter((journal) => journal.id !== id));
    } catch (error) {
      console.error("Error deleting journal entry:", error.response?.data || error.message);
    }
  };
  
  // Handle updating a journal entry
  const handleUpdateJournal = async () => {
    if (!updatedContent.title.trim() || !updatedContent.content.trim() || !editJournal) return;
  
    const token = localStorage.getItem("jwt");
    if (!token) {
      console.error("JWT token is missing.");
      return;
    }
    console.log(editJournal.id);
    try {
      const response = await axios.put(
        `http://localhost:8081/journal/id/${String(editJournal.id)}`, // URL
        updatedContent, // Data (body)
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        } // Config (headers)
      );
  
      setJournals(
        journals.map((journal) =>
          journal.id === editJournal.id ? response.data : journal
        )
      );
  
      setEditJournal(null); // Reset the editJournal state
      setUpdatedContent({ title: "", content: "" }); // Reset updated content
    } catch (error) {
      console.error("Error updating journal entry:", error);
    }
  };

  //fetch weather of the city
  useEffect(() => {
    const fetchWeather = async () => {
      console.log("Fetching weather..."); // Debug log
      const token = localStorage.getItem("jwt");
      try {
        const response = await axios.get("http://localhost:8081/user",{
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        console.log("Weather API response:", response.data); // Debug log
        setweather(response.data); // Store weather data in state
      } catch (error) {
        console.error("Error fetching weather:", error); // Debug log
      }
    };
  
    fetchWeather();
  }, []);
  
  
  


  return (
    <div className="maindiv">
      <SignOut/>
      <div className="analyzeSentiment">
    <Link to={"/analyze-sentiment"}><button >Analyze Sentiment</button></Link>
    </div>
      
       <div className="weather">
        <h1 >Hello Mr. s{weather}
        </h1>
      </div>
      <h1 className="head">My Journal Entries</h1>

      {/* Display journal entries */}
      <div >
        {journals.map((journal) => (
          <div className="displayjournal"
            key={journal._id}
          >
            {editJournal && editJournal.id === journal.id ? (
              <div >
                <input
                  type="text"
                  value={updatedContent.title}
                  onChange={(e) => setUpdatedContent({ ...updatedContent, title: e.target.value })}
                  placeholder={journal.title}
                />
                <textarea
                  value={updatedContent.content}
                  onChange={(e) => setUpdatedContent({ ...updatedContent, content: e.target.value })}
                  rows="10s" placeholder={journal.content}
                ></textarea>
                <button onClick={handleUpdateJournal}>Save</button>
                <button onClick={() => setEditJournal(null)}>Cancel</button>
              </div>
            ) : (
              <div>
               {/* <h4>{journal.id}</h4> */}
                <h3>{journal.title}</h3>
                <p>{journal.content}</p>
                <small>Date: {new Date(journal.date).toLocaleString()}</small>
                <div>
                  <button onClick={() => setEditJournal(journal)}>Edit</button>
                  <button onClick={() => handleDeleteJournal(journal.id)}>Delete</button>
                </div>
              </div>
            )}
          </div>
        ))}
      </div>

      {/* Create new journal entry */}
      <div style={{ marginTop: "20px" }}>
        <input
          type="text"
          value={newJournal.title}
          onChange={(e) => setNewJournal({ ...newJournal, title: e.target.value })}
          placeholder="Enter title"
          style={{ width: "100%", marginBottom: "10px" }}
        />
        <textarea
          value={newJournal.content}
          onChange={(e) => setNewJournal({ ...newJournal, content: e.target.value })}
          rows="3"
          style={{ width: "100%" }}
          placeholder="Write your new journal entry here..."
        ></textarea>
        <button onClick={handleCreateJournal} style={{ marginTop: "10px" }}>
          Add Journal Entry
        </button>
        

      </div>
     
    </div>
  );
};

export default JournalPage;
