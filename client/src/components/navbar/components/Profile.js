import React from 'react';
import { Avatar, Dropdown, Menu } from 'antd';
import { UserOutlined } from '@ant-design/icons';

const ProfileMenu = (
  <Menu>
    <Menu.Item key="0">
      Profile
    </Menu.Item>
    <Menu.Item key="1">
      Logout
    </Menu.Item>
  </Menu>
);

const Profile = () => (
  <Dropdown overlay={ProfileMenu} trigger={['click']}>
    <Avatar icon={<UserOutlined />} />
  </Dropdown>
);

export default Profile;