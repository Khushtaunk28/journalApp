import axios from 'axios';
import React, { useState } from 'react';
import {toast} from 'react-toastify'

const Contact = () => {
    const [formData, setFormData] = useState({
        name: '',
        email: '',
        query: '',
    });

    // Handle form field changes
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8081/public/contact-us', formData, {
                headers: {
                    'Content-Type': 'application/json',
                },
                
            });
            toast.success("Query Recieved")
            console.log(response.data); // Optional: Handle response
        } catch (error) {
            toast.error("Unable to recieve query at this time")
            console.log(error);
        }
    };

    return (
        <div className='contact-form'>
            <main>
                <h1>Contact Us</h1>
                <form action="contact" onSubmit={handleSubmit}>
                    <div>
                        <label htmlFor="name">Name</label>
                        <input
                            type="text"
                            id="name"
                            name="name"
                            value={formData.name}
                            onChange={handleChange}
                        />
                    </div>
                    <div>
                        <label htmlFor="email">Email</label>
                        <input
                            type="email"
                            id="email"
                            name="email"
                            value={formData.email}
                            onChange={handleChange}
                        />
                    </div>
                    <div>
                        <label htmlFor="query">Query</label>
                        <input
                            type="text"
                            id="query"
                            name="query"
                            value={formData.query}
                            onChange={handleChange}
                        />
                    </div>
                    <button type="submit">Send</button>
                </form>
            </main>
        </div>
    );
};

export default Contact;
