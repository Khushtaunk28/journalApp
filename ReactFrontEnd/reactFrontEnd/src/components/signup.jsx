import React from 'react'
import { useState } from 'react'
const Signup = () => {
  const [username,setusername]=useState("");
  const [password,setpassword]=useState("");
  const handleClick=(e)=>{
    e.preventDefault()
    const user={username,password}
    console.log(user)
    fetch("http://localhost:8080/user/sign-up",{
      method: "POST",
      headers :{"Content-Type":"application/json"},
      body:JSON.stringify(user)
    }).then(()=>{
      console.log("New user signed up")
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
          onChange={(e)=>setusername(e.target.value)} />
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
const LogIn=()=>{

}
export {Signup,LogIn}