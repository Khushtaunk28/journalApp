import React from 'react'
import { useState } from 'react'
import { signUp } from '../services/user-service';
import { toast } from 'react-toastify'; 
const Signup = () => {
  const [username,setusername]=useState("");
  const [password,setpassword]=useState("");
  const handleClick=(e)=>{
    e.preventDefault()
    const user={username,password}
    console.log(user);
    signUp(user).then((resp)=>{
    console.log(resp)
    console.log("success log")
    toast.success("User registered successfully")
    setpassword:" "
    setusername:" "
  })
  .catch((error)=>{
  console.log(error)
  console.log("Error")
  toast.error("There is some error")
  })
}
  return (
    <div className='signup-form'>
        <main>
            <h1>Sign up Page</h1>
        <form action="sign-up" className='formcss'>
          <div>
          <label htmlFor="username">Username:</label>
          <input type="text" name="username" id="username" placeholder='Enter your name' 
          value={username}
          onChange={(e)=>setusername(e.target.value)}/>
          </div>
          <div><label htmlFor="pass">Password:</label >
          <input type="password" placeholder='********'
          value={password}
          onChange={(e)=>setpassword(e.target.value)} />
          </div>
            <button type='Submit'
            onClick={handleClick}>Sign-Up</button>
        </form>
        </main>
    </div>
  )
}
export default Signup;