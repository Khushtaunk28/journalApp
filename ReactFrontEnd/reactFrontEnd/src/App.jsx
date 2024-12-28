import Signup from "./components/signup";
import LogIn from "./components/login";
import Footer from "./components/Footer";
import Header from "./components/Header";
import Home from "./components/Home";
import Contact from "./components/Contact";
import SignOut from "./components/JournalEntry";
import {BrowserRouter as Router ,Route,Routes} from "react-router-dom"
import { ToastContainer } from "react-toastify";
import React from 'react'
import "./styles/signup.scss"
import "./styles/App.scss"
import "./styles/Header.scss"
import "./styles/Home.scss"
import "./styles/Footer.scss"
import "./styles/Contact.scss"
import "./styles/Login.scss"

const App = () => {
  return (
    <>
    <Router>
    <Header/>
    <ToastContainer/>
      <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="/sign-up" element={<Signup/>}/>
        <Route path="/log-in" element={<LogIn/>}/>
        <Route path="/contact-us" element={<Contact/>}/>
        <Route path="/journal" element={<SignOut/>}/>
      </Routes>
      <Footer/>
    </Router>
    </>
  )
}

export default App