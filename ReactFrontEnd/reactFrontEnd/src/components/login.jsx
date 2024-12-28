import React from 'react'
import { useState } from 'react';
import { Navigate, useNavigate } from 'react-router-dom';
import {toast} from "react-toastify"
import { logIn } from '../services/user-service';


const LogIn = () => {
  const [username, setusername] = useState("");
  const [password, setpassword] = useState("");
  const navigate = useNavigate();

  const handleLogIn = async (e) => {
    e.preventDefault();
    try {
      const credentials = { username, password };
      const response = await logIn(credentials);
      console.log('Login successful:', response);
      localStorage.setItem('jwt', response.token); // Save JWT token
      toast.success('Login successful!');
      navigate('/journal')
    } catch (error) {
      if (error.response) {
        // Backend returned an error response
        if (error.response.status === 401) {
          toast.error('Invalid credentials!');
        } else if (error.response.status === 404) {
          toast.error('Login endpoint not found!');
        } else {
          toast.error('Something went wrong on the server!');
        }
      } else {
        // Network or other errors
        toast.error('Unable to connect to the server!');
      }
      console.error('Login failed:', error);
    }
  };

  
  return (
    <div className='login-form'>
      <main>
        <h1>Login To Your Journal</h1>
        <form action="formlogin">
          <div>
            <label htmlFor="username">Username</label>
            <input type="text" name="username" id="username" placeholder='Enter your name'
             value={username}
             onChange={(e)=>setusername(e.target.value)}/>
          </div>
          <div>
            <label htmlFor="password">Password</label>
            <input type="password" name="password" id="password" placeholder='*****'
            value={password}
            onChange={(e)=>setpassword(e.target.value)}/>
          </div>
          <button className='login-button' onClick={handleLogIn}
          >Login</button>
        </form>
      </main>
    </div>
  )
};  
const ProtectedRoute = ({ children }) => {
  const token = localStorage.getItem('jwt');
  if (!token) {
    // If no token is found, redirect to the login page
    return <Navigate to="/login" />;
  }
  // If the token exists, render the children (the protected route, in this case)
  return children;
};

export  {LogIn,ProtectedRoute};