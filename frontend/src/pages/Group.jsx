import React, { useState, useEffect } from 'react';
import Header from '../components/Header/Header';
import GroupBox from '../components/Group/GroupBox';

const Group = () => {
  const [groups, setGroups] = useState([]);

  useEffect(() => {
    async function fetchGroups() {
      try {
        const groupList = [
          { groupId: 100, name: '그룹 1' },
          { groupId: 101, name: '그룹 2' },
          { groupId: 102, name: '그룹 3' },
          { groupId: 103, name: '그룹 4' },
          { groupId: 104, name: '그룹 5' },
          { groupId: 105, name: '그룹 6' },
          { groupId: 106, name: '그룹 7' }
        ];
        setGroups(groupList);
      } catch (error) {
        console.error('그룹을 불러오는 중 오류가 발생했습니다:', error);
      }
    }

    fetchGroups();
  }, []);

  const createGroup = () => {
    // 새로운 그룹을 생성하는 로직을 여기에 구현할 수 있습니다.
    // 이는 모달 표시 또는 새 페이지로 이동하는 등의 방식으로 구현될 수 있습니다.
    console.log('새로운 그룹을 생성합니다...');
  };

  const handleGroupClick = (groupId) => {
    // 박스를 클릭할 때 해당 그룹의 groupId를 URL 쿼리로 전달합니다.
    console.log(groupId);
  };

  return (
    <>
      <Header />
      <div className="container">
        <GroupBox></GroupBox>
      </div>
    </>
  );
};

export default Group;
