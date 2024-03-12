import api from './api';

// 로그인
export const login = (loginId, password) => {
    const data = {
        loginId: loginId,
        password: password
    };

    return api.post('/api/member/login', data);
};

// 사용자 정보
export const info = () => api.get(`/api/member/info`)

// 회원 가입 
//TODO
export const join = (data) => api.post(`/users`, data)

// 회원 정보 수정
export const update = (data) => api.put(`/api/member/info`, data)

// 회원 탈퇴
//TODO
export const remove = (userId) => api.delete(`/users/${userId}`)

//회원 조회
export const findMember = (data) => api.post(`/api/member/info`, data)
