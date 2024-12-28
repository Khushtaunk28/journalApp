import { div, main } from 'framer-motion/client'
import React from 'react'

const Contact = () => {
  return (
    <div className='contact-form'>
        <main>
            <h1>Contact Us</h1>
            <form action="contact">
                <div>
                    <label htmlFor="name">Name</label>
                    <input type="text" />
                </div>
                <div>
                    <label htmlFor="email">Email</label>
                    <input type="email" />
                </div>
                <div>
                    <label htmlFor="query">Query</label>
                    <input type="text" />
                </div>
                <button type='Submit'>Send</button>
            </form>
        </main>
    </div>
  )
}

export default Contact