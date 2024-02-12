import React from 'react';
import {
  Layout,
  Form,
  Input,
  Button,
  Checkbox,
  Row,
  Col,
  Typography,
  Divider,
} from 'antd';
import { MailOutlined, LockOutlined, GoogleOutlined } from '@ant-design/icons';
import login_background from "../assets/login-background.jpg";


const LoginPage = () => {
  const onFinish = (values) => {
    console.log('Success:', values);
    // Implement your login logic here
  };

  const onFinishFailed = (errorInfo) => {
    console.log('Failed:', errorInfo);
  };

  const { Content } = Layout;

  return (
    <Row style={{ height: '100vh' }} align="middle" justify="center">
      <Col xs={24} sm={24} md={12} lg={8} style={{ height: '100%', display: "flex", alignItems: "center" }}>
        <Layout style={{ background: "white", margin: '0 60px' }}>
          <Content>
            <Typography.Title level={1} style={{ textAlign: 'center', marginTop: "0" }}>Sign in</Typography.Title>
            <Typography.Paragraph style={{ textAlign: "center", color: "#808080" }}>Welcome back to Oopsies Ticketing. Please enter your details to login.</Typography.Paragraph>
            <Form
              name="basic"
              initialValues={{ remember: true }}
              onFinish={onFinish}
              onFinishFailed={onFinishFailed}
              autoComplete="off"
              layout="vertical"
              requiredMark={false}
            >
              <Form.Item
                name="email"
                label="Email"
                rules={[{ required: true, message: 'Please input your email!' }]}
              >
                <Input placeholder="Email" prefix={<MailOutlined />} />
              </Form.Item>

              <Form.Item
                name="password"
                label="Password"
                rules={[{ required: true, message: 'Please input your password!' }]}
              >
                <Input.Password placeholder="Password" prefix={<LockOutlined />} />
              </Form.Item>

              <Divider>
                <Typography.Paragraph style={{ color: "#808080", margin: "0" }}>OR</Typography.Paragraph>
              </Divider>

              <Button 
              icon={<GoogleOutlined />} 
              type="outline"
              style={{ borderColor: "#d9d9d9", display: 'block', width: '100%', marginBottom: "20px"}}
              >
                <Typography.Text style={{ color: "#808080" }}>Continue with Google</Typography.Text>
              </Button>


              <Row justify="space-between">
                <Col>
                  <Form.Item name="remember" valuePropName="checked">
                    <Checkbox>Remember me</Checkbox>
                  </Form.Item>
                </Col>
                <Col style={{ marginTop: "10px" }}>
                  <a href="#">Forgot password?</a>
                </Col>
              </Row>

              <Form.Item>
                <Button type="primary" htmlType="submit">
                  Log in
                </Button>
              </Form.Item>
            </Form>
            <Typography.Paragraph style={{ textAlign: "center", color: "#808080" }}>Don't have an account? <a href="#">Sign up now</a></Typography.Paragraph>
          </Content>
        </Layout>
      </Col>

      <Col xs={0} sm={0} md={12} lg={16}>
        <img src={login_background} style={{ width: '100%', height: '100vh', objectFit: 'cover' }} />
      </Col>
    </Row>
  );
};

export default LoginPage;
