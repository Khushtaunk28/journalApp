import { div, h1 } from 'framer-motion/client';
import React from 'react';
import { Link } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';


const SignOut = () => {
  const navigate = useNavigate();
  const handleSignOut = () => {
    // Clear the JWT token from localStorage
    localStorage.removeItem('jwt');
    // Redirect to the login page
    navigate('/log-in');
  };
  return (
    <div className='journal'>
       <button onClick={handleSignOut}>Sign Out</button>
    </div>
  );
};

export default SignOut;