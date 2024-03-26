import React from 'react'
import Header from '../components/Header/Header'
import LoginContextConsumer from '../contexts/LoginContextConsumer'

const About = () => {
  return (
    <>
      <Header />
      <div style={{ "margin": "auto", "width": "900px" }}>
        <h1>About</h1>
        <hr />
        <h2>소개 페이지</h2>
        <LoginContextConsumer />
      </div>
    </>
  )
}

export default About