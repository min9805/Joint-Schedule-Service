import api from './api';


//팀 조회
export const getTeams = () => api.get(`/api/team/`)

//팀 생성
export const createTeam = (team) => api.post(`/api/team/create`, team)

//팀 멤버 조회
export const getTeamMembers = (teamId) => api.get(`/api/team/members/${teamId}`);

//팀 이벤트 조회
export const getTeamEvents = (teamId) => api.get(`/api/team/events/${teamId}`);


//이벤트 수정
export const updateEvent = (event) => api.post(`/api/event/update`, event)

//이벤트 삭제
export const deleteEvent = (eventId) => api.post(`/api/event/delete`, eventId)