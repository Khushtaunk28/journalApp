import Signup from "./components/signup";
import Header from "./components/Header";
import Home from "./components/Home";
import {BrowserRouter as Router ,Route,Routes} from "react-router-dom"
import React from 'react'
import "./styles/signup.scss"
import "./styles/App.scss"

const App = () => {
  return (
    <>
    <Router>
      <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="/sign-up" element={<Signup/>}/>
      </Routes>
    </Router>
    </>
  )
}

export default App