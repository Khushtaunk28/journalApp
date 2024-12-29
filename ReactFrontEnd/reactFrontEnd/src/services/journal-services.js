import { myAxios } from "./helper";

// Function to fetch all journal entries
export const getJournal = () => {
  return myAxios
    .get('/journal') // Replace with the appropriate endpoint
    .then((response) => {
      // Handle the response, returning the data
      console.log("Fetched journals:", response.data);
      return response.data; // Assuming the response contains the array of journal entries
    })
    .catch((error) => {
      // Handle errors
      console.error("Error fetching journals:", error);
      throw error; // Re-throw the error to allow calling functions to handle it
    });
};
