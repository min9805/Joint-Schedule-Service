import React from 'react'
import Header from '../components/Header/Header'
import LoginContextConsumer from '../contexts/LoginContextConsumer'
import BasicScheduler from '../components/Scheduler/BasicScheduler'
import { EVENTS } from '../assets/defaultEvents.ts';

const Home = () => {
  return (
    <>
      <Header />
      <div className="container">
        <LoginContextConsumer />
      </div>
    </>
  )
}

export default Home