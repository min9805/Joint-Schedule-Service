import api from './api';


//이벤트 조회
export const getEvents = () => api.get(`/api/event/data`)