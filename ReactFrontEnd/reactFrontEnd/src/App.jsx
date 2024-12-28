import {Signup,LogIn} from "./components/signup";
import Footer from "./components/Footer";
import Header from "./components/Header";
import Home from "./components/Home";
import Contact from "./components/Contact";
import {BrowserRouter as Router ,Route,Routes} from "react-router-dom"
import React from 'react'
import "./styles/signup.scss"
import "./styles/App.scss"
import "./styles/Header.scss"
import "./styles/Home.scss"
import "./styles/Footer.scss"
import "./styles/Contact.scss"

const App = () => {
  return (
    <>
    
    <Router>
    <Header/>
      <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="/sign-up" element={<Signup/>}/>
        <Route path="/log-in" element={<LogIn/>}/>
        <Route path="/contact-us" element={<Contact/>}/>
      </Routes>
      <Footer/>
    </Router>
    </>
  )
}

export default App