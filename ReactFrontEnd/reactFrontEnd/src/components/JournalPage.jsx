import React, { useState, useEffect } from "react";
import axios from "axios";

const JournalPage = () => {
  const [journals, setJournals] = useState([]); // State to store journal entries
  const [newJournal, setNewJournal] = useState({ title: "", content: "" }); // State for new journal input
  const [editJournal, setEditJournal] = useState(null); // State to store the journal being edited
  const [updatedContent, setUpdatedContent] = useState({ title: "", content: "" }); // State for updated journal content

  // Fetch all journal entries for the logged-in user on component mount
  useEffect(() => {
    // Function to fetch journal entries
    const fetchJournals = async () => {
      try {
        const token = localStorage.getItem("jwt"); // Retrieve JWT token from localStorage

        // Check if token exists
        if (!token) {
          console.log("No JWT token found. Please log in.");
          return;
        }
        // Make the API request with the token in the Authorization header
        const response = await axios.get("http://localhost:8081/journal", {
          headers: {
            "Authorization": `Bearer ${token}`, // Attach the token to the header
          },
        });

        // Set the fetched journals data
        setJournals(response.data);
      } catch (err) {
        console.log("Error fetching journal entries: " + err.message);
      }
    };

    fetchJournals();
  }, []);

  // Handle creating a new journal entry
  const handleCreateJournal = async () => {
    if (!newJournal.title.trim() || !newJournal.content.trim()) return;
    try {
      const response = await axios.post("/journal", newJournal); // Send title and content
      setJournals([...journals, response.data]); // Add the new journal to the state
      setNewJournal({ title: "", content: "" }); // Clear the input fields
    } catch (error) {
      console.error("Error creating journal entry:", error);
    }
  };

  // Handle deleting a journal entry
  const handleDeleteJournal = async (_id) => {
    try {
      await axios.delete(`/journal/id/${_id}`);
      setJournals(journals.filter((journal) => journal._id !== _id));
    } catch (error) {
      console.error("Error deleting journal entry:", error);
    }
  };

  // Handle updating a journal entry
  const handleUpdateJournal = async () => {
    if (!updatedContent.title.trim() || !updatedContent.content.trim() || !editJournal) return;
    try {
      const response = await axios.put(`/journal/id/${editJournal._id}`, updatedContent);
      setJournals(
        journals.map((journal) =>
          journal._id === editJournal._id ? response.data : journal
        )
      );
      setEditJournal(null);
      setUpdatedContent({ title: "", content: "" });
    } catch (error) {
      console.error("Error updating journal entry:", error);
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h1>My Journal Entries</h1>

      {/* Display journal entries */}
      <div>
        {journals.map((journal) => (
          <div
            key={journal._id}
            style={{
              border: "1px solid #ccc",
              borderRadius: "5px",
              padding: "10px",
              marginBottom: "10px",
            }}
          >
            {editJournal && editJournal._id === journal._id ? (
              <div>
                <input
                  type="text"
                  value={updatedContent.title}
                  onChange={(e) => setUpdatedContent({ ...updatedContent, title: e.target.value })}
                  placeholder="Edit title"
                  style={{ width: "100%", marginBottom: "10px" }}
                />
                <textarea
                  value={updatedContent.content}
                  onChange={(e) => setUpdatedContent({ ...updatedContent, content: e.target.value })}
                  rows="3"
                  style={{ width: "100%" }}
                ></textarea>
                <button onClick={handleUpdateJournal}>Save</button>
                <button onClick={() => setEditJournal(null)}>Cancel</button>
              </div>
            ) : (
              <div>
                <h3>{journal.title}</h3>
                <p>{journal.content}</p>
                <small>Date: {new Date(journal.date).toLocaleString()}</small>
                <div>
                  <button onClick={() => setEditJournal(journal)}>Edit</button>
                  <button onClick={() => handleDeleteJournal(journal._id)}>Delete</button>
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
