import React from "react";
import { useAuth } from "../context/AuthContext.js";
import { useNavigate, Link } from "react-router-dom";
import { GoogleLogin } from "@react-oauth/google";
import axios from 'axios';

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
  notification,
} from "antd";
import { MailOutlined, LockOutlined } from "@ant-design/icons";
import login_background from "../assets/login-background.jpg";

const LoginPage = () => {
  const { login, googleLogin } = useAuth();

  const navigate = useNavigate();

  const onFinish = async (values) => {
    // Implement your login logic here
    try {
      const response = await login(values["email"], values["password"]);
      console.log(response);

      if (
        response &&
        response.data.id &&
        response.data.email &&
        response.data.roles
      ) {
        // Login successful, redirect to home page
        navigate("/home");
      } else {
        // Login unsuccessful
        notification.error({
          message: "Login Failed",
          description: "Please check your email and password.",
          placement: "bottom",
        });
      }
    } catch (error) {
      notification.error({
        message: "Login Failed",
        description: "Please check your email and password.",
        placement: "bottom",
      });
    }
  };

  const onFinishFailed = (errorInfo) => {
    console.log("Failed:", errorInfo);
  };

  const { Content } = Layout;

  const handleGoogleLogin = async (credentialResponse) => {
    try {
      const response = await googleLogin(credentialResponse);
      console.log(response);

      if (
        response &&
        response.data.id &&
        response.data.email &&
        response.data.roles
      ) {
        // Login successful, redirect to home page
        navigate("/home");
      } else {
        // Login unsuccessful
        notification.error({
          message: "Login Failed",
          description: "Please check your email and password.",
          placement: "bottom",
        });
      }
    } catch (error) {
      notification.error({
        message: "Login Failed",
        description: "Please check your email and password.",
        placement: "bottom",
      });
    }
  }

  const handleGoogleLoginFailure = () => {
    console.log('Login Failed');
  };

  return (
    <Row style={{ height: "100vh" }} align="middle" justify="center">
      <Col
        xs={24}
        sm={24}
        md={12}
        lg={8}
        style={{ height: "100%", display: "flex", alignItems: "center" }}
      >
        <Layout style={{ background: "white", margin: "0 60px" }}>
          <Content>
            <Typography.Title
              level={1}
              style={{ textAlign: "center", marginTop: "0" }}
            >
              Sign in
            </Typography.Title>
            <Typography.Paragraph
              style={{ textAlign: "center", color: "#808080" }}
            >
              Welcome back to Oopsies Ticketing. Please enter your details to
              login.
            </Typography.Paragraph>
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
                rules={[
                  { required: true, message: "Please input your email!" },
                ]}
              >
                <Input placeholder="Email" prefix={<MailOutlined />} />
              </Form.Item>
              <Form.Item
                name="password"
                label="Password"
                rules={[
                  { required: true, message: "Please input your password!" },
                ]}
              >
                <Input.Password
                  placeholder="Password"
                  prefix={<LockOutlined />}
                />
              </Form.Item>
              <Divider>
                <Typography.Paragraph style={{ color: "#808080", margin: "0" }}>
                  OR
                </Typography.Paragraph>
              </Divider>

              <Row justify="center">
                <Col>
                  <GoogleLogin
                    className="google-login-button"
                    onSuccess={handleGoogleLogin}
                    onError={handleGoogleLoginFailure}
                  />
                </Col>
              </Row>

              <Row justify="space-between">
                <Col>
                  <Form.Item name="remember" style={{ marginTop: '20px' }} valuePropName="checked">
                    <Checkbox>Remember me</Checkbox>
                  </Form.Item>
                </Col>
                <Col style={{ marginTop: "25px" }}>
                  <a href="#">Forgot password?</a>
                </Col>
              </Row>

              <Form.Item>
                <Button type="primary" htmlType="submit">
                  Log in
                </Button>
              </Form.Item>
            </Form>
            <Typography.Paragraph
              style={{ textAlign: "center", color: "#808080" }}
            >
              Don't have an account? <Link to="/register">Sign up now</Link>
            </Typography.Paragraph>
          </Content>
        </Layout>
      </Col>

      <Col xs={0} sm={0} md={12} lg={16}>
        <img
          src={login_background}
          style={{ width: "100%", height: "100vh", objectFit: "cover" }}
        />
      </Col>
    </Row>
  );
};

export default LoginPage;
