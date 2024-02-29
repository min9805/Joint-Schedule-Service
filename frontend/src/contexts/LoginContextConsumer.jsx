import React, { useContext } from 'react'
import { LoginContext } from './LoginContextProvider'
import BasicScheduler from '../components/Scheduler/BasicScheduler'
import { EVENTS } from '../assets/defaultEvents.ts';

const LoginContextConsumer = () => {
  const { isLogin } = useContext(LoginContext)

  return (
    <div>
      {isLogin ?
        <p>
          logined
        </p>
        :
        <div>
          <h1>Demo Data</h1>
          <hr />
          <h3>Log in and try it</h3>
          <br></br>
          <BasicScheduler events={EVENTS}></BasicScheduler>
        </div>
      }
    </div>
  )
}

export default LoginContextConsumer