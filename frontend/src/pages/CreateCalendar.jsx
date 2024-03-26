import React, { useState, useEffect } from 'react';
import Header from '../components/Header/Header';
import FormScheduler from '../components/Scheduler/FormScheduler';

const CreateCalendar = () => {
  const [calendarName, setCalendarName] = useState("");
  const [description, setDescription] = useState("");
  const [events, setEvents] = useState([]);

  const handleCreateCalendar = () => {
    console.log("Calendar Name:", calendarName);
    console.log("Description:", description);
    console.log("Events:", events);
  };

  return (
    <>
      <Header />
      <br></br>
      <div style={{
        width: "800px",
        margin: "auto",
        padding: "36px 48px 48px 48px",
        backgroundColor: "#f2efee",
        borderRadius: "11px",
        boxShadow: "0 2.4rem 4.8rem rgba(0, 0, 0, 0.15)"
      }}>
        <div style={{
          display: "grid",
          gridTemplateColumns: "1fr",
          rowGap: "16px"
        }}>
          <label htmlFor="calendarName" style={{ display: "block", marginBottom: "8px" }}>calendarName</label>
          <input type="text"
            id='calendarName'
            placeholder='calendarName'
            name='calendarName'
            autoComplete='calendarName'
            required
            value={calendarName}
            onChange={(e) => setCalendarName(e.target.value)}
            style={{ width: "100%", padding: "1.2rem", borderRadius: "9px", border: "none", marginBottom: "12px" }}
          />
        </div>

        <div style={{
          display: "grid",
          gridTemplateColumns: "1fr",
          rowGap: "16px"
        }}>
          <label htmlFor="description" style={{ display: "block", marginBottom: "8px" }}>description</label>
          <textarea
            id='description'
            placeholder='description'
            name='description'
            autoComplete='description'
            required
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            style={{ width: "100%", padding: "1.2rem", borderRadius: "9px", border: "none", marginBottom: "12px" }}
          ></textarea>
        </div>
        <FormScheduler
          events={events}
          onEventsChange={(newEvents) => setEvents(newEvents)}
        ></FormScheduler>

        <div style={{ marginTop: "15px", position: "relative", display: "flex", justifyContent: "flex-end" }}>
          <button className="btn btn--form" style={{ padding: "10px", backgroundColor: "#f48982", color: "#fdf2e9", borderRadius: "9px", border: "none", cursor: "pointer", marginBottom: "10px" }} onClick={handleCreateCalendar}>저장</button>
        </div>

      </div>
    </>
  );
};

export default CreateCalendar;