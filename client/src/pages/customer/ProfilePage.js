import React, { useState, useEffect } from 'react';
import { Form, Input, Button, Row, Col, Typography, Card, notification } from 'antd';
import { getUserData, updateUser } from '../../utils/api';
import { useParams, useNavigate } from 'react-router-dom';

const { Title } = Typography;

const ProfilePage = () => {
  const navigate = useNavigate();

  const { id } = useParams();
  const [userInfo, setUserInfo] = useState({
    id: '',
    email: '',
    firstName: '',
    lastName: '',
    accountBalance: 0,
    emailVerified: false,
    roles: []
  });

  const [form] = Form.useForm();

  useEffect(() => {
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
              roles: data.roles.map(role => role.name).join(', ')
            });
            form.setFieldsValue({
              email: data.email,
              firstName: data.firstName,
              lastName: data.lastName,
            });
          }
        }
      } catch (error) {
        console.error(error);
        navigate('/errorpage');
      }
    };

    fetchUserData();
  }, [form, id, navigate]);

  const onFinish = async (values) => {
    // console.log(values);
    try {
      await updateUser(userInfo.id, values);
      setUserInfo({ ...userInfo, ...values });
      notification.success({
        message: 'Update Successful',
        description: 'Your profile has been updated successfully.',
      });
    } catch (error) {
      console.error(error);
      notification.error({
        message: 'Update Failed',
        description: 'An error occurred while updating your profile.',
      });
    }
  };

  return (
    <Row justify="center" style={{ marginTop: '50px', marginBottom: '50px' }}>
      <Col xs={24} sm={12} md={10} lg={8}>
        <Card bordered={false}>
          <Title level={2}>Profile</Title>
          <p><strong>ID:</strong> {userInfo.id}</p>
          <p><strong>Email:</strong> {userInfo.email}</p>
          <p><strong>Account Balance:</strong> ${userInfo.accountBalance}</p>
          <p><strong>Roles:</strong> {userInfo.roles}</p>
          <Form
            form={form}
            name="profile"
            onFinish={onFinish}
            layout="vertical"
          >
            <Form.Item
              label="First Name"
              name="firstName"
              rules={[{ required: true, message: 'Please input your first name!' }]}
            >
              <Input />
            </Form.Item>
            <Form.Item
              label="Last Name"
              name="lastName"
              rules={[{ required: true, message: 'Please input your last name!' }]}
            >
              <Input />
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
