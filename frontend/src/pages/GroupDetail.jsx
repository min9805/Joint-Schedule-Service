import { useParams } from 'react-router-dom';
import React, { useState, useEffect } from 'react';
import Header from '../components/Header/Header';
import GroupBox from '../components/Group/GroupBox';
import GroupScheduler from '../components/Scheduler/GroupScheduler';
import * as teamsApi from '../apis/team';
import * as Swal from '../apis/alert'

const GroupDetail = () => {
  const { groupId } = useParams();
  const [events, setEvents] = useState([]);
  const [resources, setResources] = useState([]);
  const [loading, setLoading] = useState(true); // 로딩 상태 추가

  useEffect(() => {
    console.log("Group Details");
    const fetchData = async () => {
      try {
        const [eventsResponse, resourcesResponse] = await Promise.all([
          teamsApi.getTeamEvents(groupId),
          teamsApi.getTeamMembers(groupId)
        ]);
        const { data: eventsData } = eventsResponse;
        const { data: resourcesData } = resourcesResponse;

        const convertedEvents = eventsData.data.map(event => ({
          ...event,
          start: new Date(event.start),
          end: new Date(event.end)
        }));
        setEvents(convertedEvents);

        console.log("team Events: ", convertedEvents);
        console.log("resource Data", resourcesData);

        setResources(resourcesData.data);
        setLoading(false); // 데이터 로딩이 완료되면 로딩 상태 변경
      } catch (error) {
        console.error('Error fetching data:', error);
        setLoading(false); // 에러가 발생해도 로딩 상태 변경
      }
    };

    fetchData();
  }, [groupId]); // groupId가 변경될 때마다 실행

  const addGroupMember = async (data) => {
    data["groupId"] = Number(groupId);
    console.log(data);
    try {
      var response = await teamsApi.addTeamMember(data)
      console.log(response)
      Swal.alert(`멤버 추가 성공`, `확인`, "success", () => {
        window.location.reload();
      })
    } catch (error) {
      Swal.alert(`멤버 추가 실패`, error.response.data.data.message, "error", () => {
      })
    }




  };
  const addMemberSwal = () => {
    Swal.confirmGroupMemberAdditionWithNameInput(groupId, addGroupMember)
  };

  if (loading) {
    return <div>Loading...</div>; // 로딩 중일 때 로딩 상태 표시
  }

  return (
    <>
      <Header />
      <div className="container" style={{ position: "relative" }}>
        <h2 className="login-title" style={{ display: "inline-block" }}>Group ID: {groupId}</h2>
        <div style={{ position: "absolute", top: 0, right: 0 }}>
          <button className="btn btn--form" style={{ padding: "10px", backgroundColor: "#f48982", color: "#fdf2e9", borderRadius: "9px", border: "none", cursor: "pointer" }} onClick={addMemberSwal}>그룹원 추가</button>
        </div>
      </div>
      <GroupScheduler EVENTS={events} RESOURCES={resources} ></GroupScheduler>
    </>
  );
};

export default GroupDetail;
