import React from "react";
import { useAuth } from "../../context/AuthContext.js";
import { useNavigate } from "react-router-dom";

import {
  Layout,
  Form,
  Input,
  Button,
  Row,
  Col,
  Typography,
  notification,
} from "antd";
import { MailOutlined, LockOutlined } from "@ant-design/icons";
import login_background from "../../assets/login-background.jpg";

const RegisterPage = () => {
  const { register } = useAuth();
  const navigate = useNavigate();

  const onFinish = async (values) => {
    console.log("Success:", values);
    // Implement your signup logic here
    try {
      const { email, password, firstName, lastName } = values;
      const response = await register(email, password, firstName, lastName);
      if (response.status == 200) {
        notification.success({
          message: "Registration Successful",
          placement: "bottom",
        });
        setTimeout(() => {
            navigate('/login');
        }, 2000);
      }
    } catch (error) {
      notification.error({
        message: "Registration Failed",
        placement: "bottom",
      });
    }
  };

  const onFinishFailed = (errorInfo) => {
    console.log("Failed:", errorInfo);
  };

  const { Content } = Layout;
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
              Sign Up
            </Typography.Title>
            <Typography.Paragraph
              style={{ textAlign: "center", color: "#808080" }}
            >
              Welcome back to Oopsies Ticketing. Please enter your details to
              signup.
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
              <Row gutter={16}>
                {" "}
                {/* Adjust gutter value as needed for spacing */}
                <Col span={12}>
                  <Form.Item
                    name="firstName"
                    label="First Name"
                    rules={[
                      {
                        required: true,
                        message: "Please input your first name!",
                      },
                    ]}
                  >
                    <Input placeholder="First Name" />
                  </Form.Item>
                </Col>
                <Col span={12}>
                  <Form.Item
                    name="lastName"
                    label="Last Name"
                    rules={[
                      {
                        required: true,
                        message: "Please input your last name!",
                      },
                    ]}
                  >
                    <Input placeholder="Last Name" />
                  </Form.Item>
                </Col>
              </Row>
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

              <Form.Item
                name="confirmPassword"
                label="Confirm Password"
                dependencies={["password"]}
                rules={[
                  {
                    required: true,
                    message: "Please confirm your password!",
                  },
                  ({ getFieldValue }) => ({
                    validator(_, value) {
                      if (!value || getFieldValue("password") === value) {
                        return Promise.resolve();
                      }
                      return Promise.reject(
                        new Error(
                          "The two passwords that you entered do not match!"
                        )
                      );
                    },
                  }),
                ]}
              >
                <Input.Password
                  placeholder="Confirm Password"
                  prefix={<LockOutlined />}
                />
              </Form.Item>

              <Form.Item>
                <Button type="primary" htmlType="submit">
                  Sign Up
                </Button>
              </Form.Item>
            </Form>
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

export default RegisterPage;
