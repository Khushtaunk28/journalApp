import React from 'react'
const Signup = () => {
  return (
    <div className='signup-form'>
        <main>
            <h1>Sign up Page</h1>
        </main>
        <form action="sign-up" className='formcss'>
            <label htmlFor="username">Username:</label>
            <input type="text" name="username" id="username" placeholder='Enter your name' />
            <label htmlFor="pass">Password</label>
            <input type="password" placeholder='********' />
            <button >Submit</button>
        </form>
    </div>
  )
}
export default Signup