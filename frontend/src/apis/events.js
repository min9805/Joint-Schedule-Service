import api from './api';


//이벤트 조회
export const getEvents = () => api.get(`/api/event/`)

//이벤트 생성
export const createEvent = (event) => api.post(`/api/event/create`, event)

//이벤트 수정
export const updateEvent = (event) => api.post(`/api/event/update`, event)

//이벤트 삭제
export const deleteEvent = (eventId) => api.post(`/api/event/delete`, eventId)

//이벤트 조회
export const getTeams = () => api.get(`/api/team/`)