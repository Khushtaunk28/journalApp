import { myAxios } from "./helper";

export const signUp=(user)=>{
    return myAxios
    .post('/public/sign-up',user)
    .then((response)=>response.data )
};
export const logIn = (credentials) => {
    return myAxios
      .post('/public/login', credentials) // Pass the login credentials (username and password)
      .then((response) => response.data) // Extract data from the response
      .catch((error) => {
        console.error('Error during login:', error);
        throw error;
      });
  };
  