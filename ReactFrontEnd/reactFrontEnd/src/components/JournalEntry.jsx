import { div } from 'framer-motion/client';
import React from 'react';
import { useNavigate } from 'react-router-dom';

const journalEntry=()=>{
    <div>make your journal Entry</div>
}
const fetchjournalEntry=()=>{
    <div>fetch the entries</div>
}

const SignOut = () => {
  const navigate = useNavigate();
  const handleSignOut = () => {
    // Clear the JWT token from localStorage
    localStorage.removeItem('jwt');
    // Redirect to the login page
    navigate('/log-in');
  };
  return (
    <button onClick={handleSignOut}>Sign Out</button>
  );
};

export default SignOut;