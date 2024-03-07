import React, { useState, useEffect } from 'react';
import Header from '../components/Header/Header';
import GroupBox from '../components/Group/GroupBox';

const Group = () => {
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
