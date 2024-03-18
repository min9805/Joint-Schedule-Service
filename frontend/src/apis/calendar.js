import api from './api';


//공유 일정 조회
export const getCalendars = () => api.get(`/api/calendars`)

//공유 일정 생성
export const createCalendar = (data) => api.post(`/api/calendars/create`, data)

//공유 일정 이벤트 조회
export const getCalendarEvents = (data) => api.post(`/api/calendars/events`, data)

//공유 일정 이벤트 조회 by ID
export const getCalendarEventsById = (calendarId) => api.get(`/api/calendars/events/${calendarId}`)

//공유 일정 구독
export const subscribeCalendar = (data) => api.post(`/api/subscribe/subscribe`, data)

//공유 일정 구독 취소
export const unsubscribeCalendar = (data) => api.post(`/api/subscribe/unsubscribe`, data)

