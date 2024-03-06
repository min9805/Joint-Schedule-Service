import React, { useState, useEffect, useContext } from 'react';
import { LoginContext } from '../../contexts/LoginContextProvider'
import * as eventsApi from '../../apis/team';
import './GroupBox.css'

const Group = () => {
    const [teams, setTeams] = useState([]);
    const { isLogin, userInfo } = useContext(LoginContext)

    useEffect(() => {
        const fetchTeams = async () => {
            try {
                const response = await eventsApi.getTeams()
                // 로그인 상태인 경우에만 이벤트를 가져옴
                if (isLogin) {
                    console.log("fetchTeams");

                    const { data, status, headers } = response;

                    console.log(status);
                    console.log(headers);
                    //     const convertedData = data.data
                    //     // .map(team => ({
                    //     //     ...team,

                    //     // }));

                    setTeams(data.data);
                }
            } catch (error) {
                console.error('Error fetching teams:', error);
            }
        };

        fetchTeams(); // 이벤트 가져오기 함수 호출
    }, []); // isLogin 상태가 변경될 때마다 실행


    const createGroup = () => {
        // 새로운 그룹을 생성하는 로직을 여기에 구현할 수 있습니다.
        // 이는 모달 표시 또는 새 페이지로 이동하는 등의 방식으로 구현될 수 있습니다.
        console.log('새로운 그룹을 생성합니다...');
    };

    const handleGroupClick = (groupId) => {
        console.log(groupId);
        // 박스를 클릭할 때 해당 그룹의 groupId를 URL 쿼리로 전달합니다.
    };

    return (
        <>
            <div className="container">
                <div className="group-container">
                    {teams.map((group, index) => (
                        <div key={index} className="group-box" onClick={() => handleGroupClick(group.groupId)}>
                            {/* 그룹 정보 표시 */}
                            <h3>{group.name}</h3>
                            <p>{`그룹 ID: ${group.teamId}`}</p>
                        </div>
                    ))}
                    <div className="group-box create-group" onClick={createGroup}>
                        <span className="plus-icon">+</span>
                        <p className="create-group-text">그룹 생성</p>
                    </div>
                </div>
            </div>
        </>
    );
};

export default Group;
