import React, { useState } from 'react';
import { Form, Input, Button, Row, Col, Typography, Card } from 'antd';

const { Title } = Typography;

const ProfilePage = () => {
  const user = JSON.parse(localStorage.getItem("authUser"));
  const [userInfo, setUserInfo] = useState({
    id: user?.id,
    email: user?.email,
    name: 'John Doe',
    balance: '100.00'
  });

  const [form] = Form.useForm();
  // TO-DO: Need end points to retrieve user information, only allow if same user.
  // TO-DO: 
  const onFinish = (values) => {
    console.log('Success:', values);
    // Here, integrate your API to update the user information
    setUserInfo({ ...userInfo, ...values });
    // Show success notification or handle the state accordingly
  };

  return (
    <Row justify="center" style={{ marginTop: '50px', marginBottom: '50px' }}>
      <Col xs={24} sm={12} md={10} lg={8}>
        <Card bordered={false}>
          <Title level={2}>Profile</Title>
          <p><strong>ID:</strong> {userInfo.id}</p>
          <p><strong>Account Balance:</strong> ${userInfo.balance}</p>
          <Form
            form={form}
            name="profile"
            initialValues={{
              email: userInfo.email,
              name: userInfo.name,
            }}
            onFinish={onFinish}
            layout="vertical"
          >
            <Form.Item
              label="Email"
              name="email"
              rules={[{ required: true, message: 'Please input your email!', type: 'email' }]}
            >
              <Input />
            </Form.Item>
            <Form.Item
              label="Name"
              name="name"
              rules={[{ required: true, message: 'Please input your name!' }]}
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
