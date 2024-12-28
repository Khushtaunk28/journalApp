import { nav } from 'framer-motion/client'
import React from 'react'
import { Link } from 'react-router-dom'
import {HashLink} from "react-router-hash-link"

const Header = () => {
  return (
    <nav className='navHeader'>
        <h1>Mood Memo</h1>
        <main>
            <HashLink to={"/#home"}>Home</HashLink>
            <Link to={"/log-in"}>LogIn</Link>
            <Link to={"/sign-up"}>Create-Account</Link>
            <HashLink to={"/#about"}>About</HashLink>
        </main>
    </nav>
  )
}

export default Header