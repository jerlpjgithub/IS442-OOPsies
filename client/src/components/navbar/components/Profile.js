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
    switch(e.key) {
      case "0":
        navigate(`/profile/${authUser.id}`);
        break;
      case "1":
        navigate(`/booking/${authUser.id}`);
        break;
      case "2":
        await logout();
        navigate('/login');
        break;
      default:
        console.log("Invalid option")
        break;
    }
  };

  /* NOTE: You will want to conditionally render different profile based on the user profile type */
  const profileMenu = (
    <Menu onClick={handleMenuClick}>
      <Menu.Item key="0">My Profile</Menu.Item>
      <Menu.Item key="1">My Bookings</Menu.Item>
      <Menu.Item key="2">Logout</Menu.Item>
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
