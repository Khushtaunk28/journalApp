import React from 'react'
import vg from "../web-pics/journal-svgrepo-com.svg"



const Home = () => {
  return (
    <>
    <div className='home' id='home'>
      <main>
        <h1>Welcome To Mood-Memo</h1>
        <p>A journal-App for your daily-need</p>
      </main>
    </div>

    <div className='home2'>
      <img src={vg} alt="graphics" />
   <div>
    <p>"Stay organized with our Journal App! Built using Spring Boot, this powerful backend solution allows users to securely store their data, with admin access for efficient management. Featuring integrated weather updates and automated email services, it's a seamless experience designed for productivity. Start journaling smarter today!"</p>
    </div>
    </div>
    </>
  )
}

export default Home