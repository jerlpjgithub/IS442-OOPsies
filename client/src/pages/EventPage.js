import React from "react";
import {
    Layout,
    Content,
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

const EventPage = () => {
    return (
        <Layout>
            <Content
                style={{
                    padding: '0 48px',
                }}
            >
                <Breadcrumb
                    style={{
                        margin: '16px 0',
                    }}
                >
                    <Breadcrumb.Item>Home</Breadcrumb.Item>
                    <Breadcrumb.Item>List</Breadcrumb.Item>
                    <Breadcrumb.Item>App</Breadcrumb.Item>
                </Breadcrumb>
                <div
                    style={{
                        background: colorBgContainer,
                        minHeight: 280,
                        padding: 24,
                        borderRadius: borderRadiusLG,
                    }}
                >
                    Content
                </div>
            </Content>
        </Layout>
    );
}

export default EventPage;