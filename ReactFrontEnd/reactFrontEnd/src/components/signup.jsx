import React from 'react'
const Signup = () => {
  return (
    <div className='signup-form'>
        <main>
            <h1>Sign up Page</h1>
        <form action="sign-up" className='formcss'>
          <div>
          <label htmlFor="username">Username:</label>
          <input type="text" name="username" id="username" placeholder='Enter your name' />
          </div>
          <div><label htmlFor="pass">Password:</label>
          <input type="password" placeholder='********' />
          </div>
            <button type='Submit'>Sign-Up</button>
        </form>
        </main>
    </div>
  )
}
export default Signup