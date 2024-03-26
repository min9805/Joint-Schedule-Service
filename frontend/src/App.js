import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import Home from './pages/Home';
import Login from './pages/Login';
import Join from './pages/Join';
import User from './pages/User';
import About from './pages/About';
import Group from './pages/Group';
import GroupDetail from './pages/GroupDetail';
import Calendar from './pages/Calendar';
import CalendarDetail from './pages/CalendarDetail';
import CreateCalendar from './pages/CreateCalendar';
import LoginContextProvider from './contexts/LoginContextProvider';
import Admin from './pages/Admin';

export default function App() {
  return (
    <BrowserRouter>
      <LoginContextProvider>
        <Routes>
          <Route path="/" element={<Home />}></Route>
          <Route path="/login" element={<Login />}></Route>
          <Route path="/join" element={<Join />}></Route>
          <Route path="/user" element={<User />}></Route>
          <Route path="/about" element={<About />}></Route>
          <Route path="/admin" element={<Admin />}></Route>
          <Route path="/group" element={<Group />}></Route>
          <Route path="/group/:groupId" element={<GroupDetail />} />
          <Route path="/calendar" element={<Calendar />}></Route>
          <Route path="/calendar/:calendarId" element={<CalendarDetail />} />
          <Route path="/calendar/create" element={<CreateCalendar />}></Route>

        </Routes>
      </LoginContextProvider>
    </BrowserRouter>
  );
}
