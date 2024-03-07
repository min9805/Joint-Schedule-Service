import React, { useState, useEffect, useContext } from 'react';
import { useNavigate } from 'react-router-dom'
import { LoginContext } from '../../contexts/LoginContextProvider'
import * as teamApi from '../../apis/team';
import './GroupBox.css'
import * as Swal from '../../apis/alert';


const Group = () => {
    const navigate = useNavigate();

    const [teams, setTeams] = useState([]);
    const { isLogin, userInfo } = useContext(LoginContext)

    useEffect(() => {
        const fetchTeams = async () => {
            try {
                const response = await teamApi.getTeams()
                // 로그인 상태인 경우에만 이벤트를 가져옴
                if (isLogin) {
                    console.log("fetchTeams");
                    const { data, status, headers } = response;
                    console.log(data.data);
                    setTeams(data.data);
                }
            } catch (error) {
                console.error('Error fetching teams:', error);
            }
        };

        fetchTeams(); // 이벤트 가져오기 함수 호출
    }, []); // isLogin 상태가 변경될 때마다 실행

    const createGroup = () => {
        Swal.confirmGroupCreationWithNameInput(`그룹 생성`, `그룹 이름을 입력해주세요.`, submitGroup)
    };


    const submitGroup = async (name) => {
        try {
            console.log("create Team")
            let response;
            response = await teamApi.createTeam(name);
            const { data, status, headers } = response;

            var prevData = {
                name: data.data.name,
                temaId: data.data.teamId
            }
            setTeams(prevGroups => [...prevGroups, prevData]); // setGroups는 현재 그룹 상태를 업데이트하는 함수

        } catch (error) {
            // 에러가 발생한 경우 처리합니다.
            console.error("Error:", error);
            console.error(error.response.data)
            // 에러를 throw하여 호출자에게 전달합니다.
            throw error;
        }

    };

    const handleGroupClick = (groupId) => {
        navigate("/group/" + groupId)
        // 박스를 클릭할 때 해당 그룹의 groupId를 URL 쿼리로 전달합니다.
    };

    return (
        <>
            <div className="container">
                <div className="group-container">
                    {teams.map((group, index) => (
                        <div key={index} className="group-box" onClick={() => handleGroupClick(group.teamId)}>
                            {/* 그룹 정보 표시 */}
                            <h3>{group.groupName}</h3>
                            <br></br>
                            <h5>{group.memberName}</h5>
                            <h6>{group.subName}</h6>
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
