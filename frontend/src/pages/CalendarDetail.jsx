import React, { useState, useEffect } from 'react';
import { useParams, useLocation, useNavigate } from 'react-router-dom';
import Header from '../components/Header/Header';
import * as CalendarAPI from '../apis/calendar';
import BasicScheduler from '../components/Scheduler/BasicScheduler';

const CalendarDetail = () => {
  const location = useLocation();
  const navigate = useNavigate()

  const { calendarId } = useParams();
  const [events, setEvents] = useState()
  const [loading, setLoading] = useState(true); // 로딩 상태 추가

  const preData = location.state.data;

  const getEvents = async () => {
    try {
      console.log(calendarId);
      const response = await CalendarAPI.getCalendarEventsById(calendarId)
      const data = response.data.data
      console.log(`Calendars`);
      console.log(data);

      const convertedEvents = data.map(event => ({
        ...event,
        start: new Date(event.start),
        end: new Date(event.end),
        editable: false,
        deletable: false,
        draggable: false,
        color: preData.color
      }));
      console.log(convertedEvents);
      setEvents(convertedEvents);
      setLoading(false); // 데이터 로딩이 완료되면 로딩 상태 변경

    } catch (error) {
      console.error('Error fetching data:', error);
      setLoading(false); // 에러가 발생해도 로딩 상태 변경
    }
  }

  const subscribe = async () => {
    console.log(preData);
    var temp = {
      "id": preData.calendarId, "alias": "group", "color": preData.color
    }
    await CalendarAPI.subscribeCalendar(temp);
    navigate("/")
  }



  useEffect(() => {

    getEvents()
  }, [])

  if (loading) {
    return <div>Loading...</div>; // 로딩 중일 때 로딩 상태 표시
  }

  return (
    <>
      <Header />
      <div className="container" style={{ position: "relative" }}>
        <h2 className="login-title" style={{ display: "inline-block" }}>Group ID: {calendarId}</h2>
        <div style={{ position: "absolute", top: 0, right: 0 }}>
          <button className="btn btn--form" style={{ padding: "10px", backgroundColor: "#f48982", color: "#fdf2e9", borderRadius: "9px", border: "none", cursor: "pointer" }} onClick={subscribe}>일정 구독</button>
        </div>
      </div>
      <div style={{ width: "800px", margin: "auto" }}>
        <BasicScheduler events={events}></BasicScheduler>
      </div>
    </>
  );
};

export default CalendarDetail
