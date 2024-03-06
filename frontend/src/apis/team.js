import api from './api';


//팀 조회
export const getTeams = () => api.get(`/api/team/`)

//이벤트 생성
export const createTeam = (event) => api.post(`/api/event/create`, event)

//이벤트 수정
export const updateEvent = (event) => api.post(`/api/event/update`, event)

//이벤트 삭제
export const deleteEvent = (eventId) => api.post(`/api/event/delete`, eventId)