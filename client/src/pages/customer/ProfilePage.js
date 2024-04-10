import React, { useState, useEffect } from "react";
import {
  Form,
  Input,
  Button,
  Row,
  Col,
  Typography,
  Card,
  notification,
  Select,
} from "antd";
import { getUserData, updateUser } from "../../utils/api";
import { useParams, useNavigate } from "react-router-dom";

const { Title } = Typography;
const { Option } = Select;

const ProfilePage = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const [userInfo, setUserInfo] = useState({
    id: "",
    email: "",
    firstName: "",
    lastName: "",
    accountBalance: 0,
    emailVerified: false,
    roles: [],
  });
  const roles = ['ROLE_USER', 'ROLE_MANAGER', 'ROLE_OFFICER'];
  const [form] = Form.useForm();
  const [isRoleEditable, setIsRoleEditable] = useState(false);

  useEffect(() => {
    // Check if user has ROLE_MANAGER or ROLE_OFFICER role
    try {
      const authUser = JSON.parse(localStorage.getItem("authUser"));
      const roles = authUser?.roles || [];
      if (roles.includes("ROLE_MANAGER")) {
        setIsRoleEditable(true);
      }
    } catch (error) {
      console.error("Error parsing authUser from localStorage", error);
    }

    const fetchUserData = async () => {
      try {
        if (id) {
          const data = await getUserData(id);
          if (data) {
            setUserInfo({
              id: data.id,
              email: data.email,
              firstName: data.firstName,
              lastName: data.lastName,
              accountBalance: data.accountBalance,
              emailVerified: data.emailVerified,
              roles: data.roles.map((role) => role.name),
            });
            form.setFieldsValue({
              email: data.email,
              firstName: data.firstName,
              lastName: data.lastName,
              roles: data.roles.map((role) => role.name),
            });
          }
        }
      } catch (error) {
        console.error(error);
        navigate("/errorpage");
      }
    };

    fetchUserData();
  }, [form, id, navigate]);

  const onFinish = async (values) => {
    try {
      await updateUser(userInfo.id, {
        ...values,
        roles: values.roles ? [values.roles] : userInfo.roles,
      });
      setUserInfo({ ...userInfo, ...values, roles: values.roles ? [values.roles] : userInfo.roles });
      notification.success({
        message: "Update Successful",
        description: "Your profile has been updated successfully.",
      });
    } catch (error) {
      console.error(error);
      notification.error({
        message: "Update Failed",
        description: "An error occurred while updating your profile.",
      });
    }
  };

  return (
    <Row justify="center" style={{ marginTop: "50px", marginBottom: "50px" }}>
      <Col xs={24} sm={12} md={10} lg={8}>
        <Card bordered={false}>
          <Title level={2}>Profile</Title>
          <Form
            form={form}
            name="profile"
            onFinish={onFinish}
            layout="vertical"
          >
            <Form.Item label="ID">{userInfo.id}</Form.Item>
            <Form.Item label="Email">{userInfo.email}</Form.Item>
            <Form.Item label="Account Balance">
              ${userInfo.accountBalance}
            </Form.Item>
            <Form.Item
              label="First Name"
              name="firstName"
              rules={[
                { required: true, message: "Please input your first name!" },
              ]}
            >
              <Input />
            </Form.Item>
            <Form.Item
              label="Last Name"
              name="lastName"
              rules={[
                { required: true, message: "Please input your last name!" },
              ]}
            >
              <Input />
            </Form.Item>
            <Form.Item
              label="Roles"
              name="roles"
            >
              <Select
                disabled={!isRoleEditable}
                placeholder="Select a role"
              >

                {roles.map((role) => (
                  <Option key={role} value={role}>
                    {role}
                  </Option>
                ))}
              </Select>
            </Form.Item>
            <Form.Item>
              <Button type="primary" htmlType="submit">
                Submit Changes
              </Button>
            </Form.Item>
          </Form>
        </Card>
      </Col>
    </Row>
  );
};

export default ProfilePage;
