import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom'
import Header from '../components/Header/Header';
import * as CalendarAPI from '../apis/calendar';

const Calendar = () => {
  const navigate = useNavigate();

  const [calendars, setcalendars] = useState()
  const [loading, setLoading] = useState(true); // 로딩 상태 추가


  const getCalendars = async () => {
    try {

      const response = await CalendarAPI.getCalendars()
      const data = response.data.data
      console.log(`Calendars`);
      console.log(data);
      setcalendars(data)
      setLoading(false); // 데이터 로딩이 완료되면 로딩 상태 변경

    } catch (error) {
      console.error('Error fetching data:', error);
      setLoading(false); // 에러가 발생해도 로딩 상태 변경
    }
  }


  const handleCalendarClick = (item) => {
    navigate("/calendar/" + item.calendarId, { state: { data: item } })
    // 박스를 클릭할 때 해당 그룹의 groupId를 URL 쿼리로 전달합니다.
  };

  const createCalendar = () => {
    navigate("/calendar/create")
  }

  useEffect(() => {

    getCalendars()
  }, [])

  if (loading) {
    return <div>Loading...</div>; // 로딩 중일 때 로딩 상태 표시
  }

  return (
    <>
      <Header />
      <div className="container">
        <div style={{ position: "relative" }}>
          <button className="btn btn--form" style={{ padding: "10px", backgroundColor: "#f48982", color: "#fdf2e9", borderRadius: "9px", border: "none", cursor: "pointer", marginBottom: "10px" }} onClick={createCalendar}>구독 일정 생성</button>
        </div>
      </div>
      <div className="container">
        <div className="group-container">
          {calendars.map((item, index) => (
            <div key={index} className="group-box" onClick={() => handleCalendarClick(item)}>
              {/* 캘린더 정보 표시 */}
              <h3>{item.name}</h3>
              <br />
              <h5>{item.description}</h5>
            </div>
          ))}
        </div>
      </div>


    </>
  );
};

export default Calendar
