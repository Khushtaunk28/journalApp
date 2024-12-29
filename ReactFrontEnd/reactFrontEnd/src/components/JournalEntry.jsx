import { div, h1 } from 'framer-motion/client';
import React from 'react';
import { Link } from 'react-router-dom';
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
    <div className='journal'>
       <h1>Welcome to your Daily Journal</h1>
       <Link to={"/journal-page"}>Create a new Entry</Link>








       <button onClick={handleSignOut}>Sign Out</button>
    </div>
  );
};

export default SignOut;