import React from "react";
import { Avatar, Dropdown, Menu } from "antd";
import { UserOutlined } from "@ant-design/icons";
import { useAuth } from "../../../context/AuthContext.js";
import { useNavigate } from "react-router-dom";

const Profile = () => {
  const navigate = useNavigate();
  const { logout } = useAuth();
  const authUser = JSON.parse(localStorage.getItem("authUser"));

  const handleMenuClick = async (e) => {
    if (e.key === "1") {
      await logout();
      navigate('/login');
    } else if (e.key === "0") {
      navigate(`/profile/${authUser.id}`);
    }
  };

  const profileMenu = (
    <Menu onClick={handleMenuClick}>
      <Menu.Item key="0">Profile</Menu.Item>
      <Menu.Item key="1">Logout</Menu.Item>
    </Menu>
  );

  return (
    <Dropdown overlay={profileMenu} trigger={["click"]}>
      <a onClick={(e) => e.preventDefault()}>
        <Avatar icon={<UserOutlined />} />
      </a>
    </Dropdown>
  );
};

export default Profile;
