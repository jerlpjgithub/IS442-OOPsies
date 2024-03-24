import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Avatar, Dropdown, Menu } from 'antd';
import { UserOutlined } from '@ant-design/icons';

import { useAuth } from '../../../context/AuthContext.js';

const keyToMenuMap = {
  0: 'My Profile',
  1: 'My Bookings',
  2: 'Logout'
};

const ProfileToMenuMap = {
  ROLE_USER: [0, 1, 2],
  ROLE_MANAGER: [0, 2],
  ROLE_OFFICER: [0, 2]
};

const Profile = () => {
  const navigate = useNavigate();
  const { logout } = useAuth();
  const authUser = JSON.parse(localStorage.getItem('authUser'));

  const handleMenuClick = async (e) => {
    switch (e.key) {
      case '0':
        navigate(`/profile/${authUser.id}`);
        break;
      case '1':
        navigate(`/booking/${authUser.id}`);
        break;
      case '2':
        await logout();
        navigate('/login');
        break;
      default:
        console.log('Invalid option');
        break;
    }
  };

  /* NOTE: You will want to conditionally render different profile based on the user profile type */
  const profileMenu = (
    <Menu onClick={handleMenuClick}>
      {ProfileToMenuMap[authUser.roles[0]].map((key) => (
        <Menu.Item key={key}>{keyToMenuMap[key]}</Menu.Item>
      ))}
    </Menu>
  );

  return (
    <Dropdown overlay={profileMenu} trigger={['click']}>
      <a onClick={(e) => e.preventDefault()}>
        <Avatar icon={<UserOutlined />} />
      </a>
    </Dropdown>
  );
};

export default Profile;
