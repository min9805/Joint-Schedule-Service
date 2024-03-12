import { useParams } from 'react-router-dom';
import React, { useState, useEffect } from 'react';
import Header from '../components/Header/Header';
import GroupBox from '../components/Group/GroupBox';
import GroupScheduler from '../components/Scheduler/GroupScheduler';
import * as teamsApi from '../apis/team';
import * as Swal from '../apis/alert'

const EVENTS = [
  {
    event_id: 1,
    title: "Event 1",
    start: new Date(new Date(new Date().setHours(9)).setMinutes(30)),
    end: new Date(new Date(new Date().setHours(10)).setMinutes(30)),
    admin_id: 1
  },
  {
    event_id: 2,
    title: "Event 2",
    start: new Date(new Date(new Date().setHours(10)).setMinutes(0)),
    end: new Date(new Date(new Date().setHours(11)).setMinutes(0)),
    admin_id: 2
  },
  {
    event_id: 3,
    title: "Event 3",
    start: new Date(
      new Date(new Date(new Date().setHours(9)).setMinutes(0)).setDate(
        new Date().getDate() - 1
      )
    ),
    end: new Date(new Date(new Date().setHours(10)).setMinutes(0)),
    admin_id: 1
  },
  {
    event_id: 4,
    title: "Event 4",
    start: new Date(
      new Date(new Date(new Date().setHours(9)).setMinutes(0)).setDate(
        new Date().getDate() - 2
      )
    ),
    end: new Date(
      new Date(new Date(new Date().setHours(10)).setMinutes(0)).setDate(
        new Date().getDate() - 2
      )
    ),
    admin_id: 2
  },
  {
    event_id: 5,
    title: "Event 5",
    start: new Date(
      new Date(new Date(new Date().setHours(10)).setMinutes(0)).setDate(
        new Date().getDate() - 2
      )
    ),
    end: new Date(
      new Date(new Date(new Date().setHours(11)).setMinutes(0)).setDate(
        new Date().getDate() + 10
      )
    ),
    admin_id: 4
  },
  {
    event_id: 6,
    title: "Event 6",
    start: new Date(new Date(new Date().setHours(11)).setMinutes(0)),
    end: new Date(new Date(new Date().setHours(12)).setMinutes(0)),
    admin_id: 2
  },
  {
    event_id: 7,
    title: "Event 7",
    start: new Date(
      new Date(new Date(new Date().setHours(11)).setMinutes(0)).setDate(
        new Date().getDate() - 1
      )
    ),
    end: new Date(
      new Date(new Date(new Date().setHours(12)).setMinutes(0)).setDate(
        new Date().getDate() - 1
      )
    ),
    admin_id: 3
  },
  {
    event_id: 8,
    title: "Event 8",
    start: new Date(
      new Date(new Date(new Date().setHours(13)).setMinutes(0)).setDate(
        new Date().getDate() - 1
      )
    ),
    end: new Date(
      new Date(new Date(new Date().setHours(14)).setMinutes(0)).setDate(
        new Date().getDate() - 1
      )
    ),
    admin_id: 4
  },
  {
    event_id: 9,
    title: "Event 11",
    start: new Date(
      new Date(new Date(new Date().setHours(13)).setMinutes(0)).setDate(
        new Date().getDate() + 1
      )
    ),
    end: new Date(
      new Date(new Date(new Date().setHours(15)).setMinutes(30)).setDate(
        new Date().getDate() + 1
      )
    ),
    admin_id: 1
  },
  {
    event_id: 10,
    title: "Event 9",
    start: new Date(
      new Date(new Date(new Date().setHours(15)).setMinutes(0)).setDate(
        new Date().getDate() + 1
      )
    ),
    end: new Date(
      new Date(new Date(new Date().setHours(16)).setMinutes(30)).setDate(
        new Date().getDate() + 1
      )
    ),
    admin_id: 2
  },
  {
    event_id: 11,
    title: "Event 10",
    start: new Date(
      new Date(new Date(new Date().setHours(11)).setMinutes(0)).setDate(
        new Date().getDate() - 1
      )
    ),
    end: new Date(
      new Date(new Date(new Date().setHours(15)).setMinutes(0)).setDate(
        new Date().getDate() - 1
      )
    ),
    admin_id: 1
  }
];
const RESOURCES = [
  {
    admin_id: 1,
    title: "John",
    avatar: "https://picsum.photos/200/300",
    color: "#ab2d2d"
  },
  {
    admin_id: 2,
    title: "Sarah",
    avatar: "https://picsum.photos/200/300",
    color: "#58ab2d"
  },
  {
    admin_id: 3,
    title: "Joseph",
    avatar: "https://picsum.photos/200/300",
    color: "#a001a2"
  },
  {
    admin_id: 4,
    title: "Mera",
    avatar: "https://picsum.photos/200/300",
    color: "#08c5bd"
  }
];



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
