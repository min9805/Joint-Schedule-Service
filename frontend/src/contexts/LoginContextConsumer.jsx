import React, { useContext, useEffect, useState } from 'react'
import { LoginContext } from './LoginContextProvider'
import BasicScheduler from '../components/Scheduler/BasicScheduler'
import DemoScheduler from '../components/Scheduler/DemoScheduler'
import { EVENTS } from '../assets/defaultEvents.ts';
import * as eventsApi from '../apis/events';

const LoginContextConsumer = () => {
  const { isLogin, userInfo } = useContext(LoginContext)
  const [events, setEvents] = useState([]);

  useEffect(() => {
    const fetchEvents = async () => {
      try {
        // 로그인 상태인 경우에만 이벤트를 가져옴
        if (isLogin) {
          const response = await eventsApi.getEvents()
          const { data, status, headers } = response;

          const convertedData = data.data.map(event => ({
            ...event,
            start: new Date(event.start),
            end: new Date(event.end)
          }));

          console.log("convertedData", convertedData);

          setEvents(convertedData);
        }
      } catch (error) {
        console.error('Error fetching events:', error);
      }
    };

    fetchEvents(); // 이벤트 가져오기 함수 호출
  }, [isLogin]); // isLogin 상태가 변경될 때마다 실행

  return (
    <div>
      {isLogin ?
        <div>
          <h1>{<span style={{ fontFamily: 'Arial', color: 'blue' }}>{userInfo.name}</span>}'s scheduler</h1>
          <hr />
          <BasicScheduler events={events}></BasicScheduler>
        </div>
        :
        <div>
          <h1>Demo Data</h1>
          <hr />
          <h3>Log in and try it</h3>
          <br></br>
          <DemoScheduler events={EVENTS}></DemoScheduler>
        </div>
      }
    </div>
  )
}

export default LoginContextConsumer