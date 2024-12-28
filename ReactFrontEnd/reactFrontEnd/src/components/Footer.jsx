import { footer } from 'framer-motion/client'
import React from 'react'

const Footer = () => {
  return (
    <footer>
        <div>
            <h1>Mood-memo</h1>
            <p>@All rights reserved</p>
        </div>

        <div>
            <h5>Follow us ON</h5>
            <div>
                <a href="https://www.youtube.com/watch?v=b50zSyLiCYQ&t=1321s" target={"blank"}>Youtube</a>
                <a href="https://www.youtube.com/watch?v=b50zSyLiCYQ&t=1321s" target={"_blank"}>Instagram</a>
                <a href="https://github.com/Khushtaunk28" target={"_blank"}>Github</a>
            </div>
        </div>

    </footer>
  )
}

export default Footer