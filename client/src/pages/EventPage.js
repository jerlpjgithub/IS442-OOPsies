import { React, useEffect, useState} from "react";
import { Link, useLocation, useParams } from 'react-router-dom';
import {
    Layout,
    Image,
    Form,
    Input,
    Button,
    Checkbox,
    Row,
    Col,
    Typography,
    Divider,
    notification,
    Breadcrumb,
    theme,
} from "antd";
const { Content } = Layout;

const EventPage = () => {
    const {
        token: { colorBgContainer, borderRadiusLG },
    } = theme.useToken();
    
    const location = useLocation();
    const event = location.state ? location.state.event : null ;

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
                    <Breadcrumb.Item>
                        <Link to="/home">
                            Home
                        </Link>
                    </Breadcrumb.Item>
                    <Breadcrumb.Item>
                        <Link to="#">Event</Link>
                    </Breadcrumb.Item>
                </Breadcrumb>
                <div
                    style={{
                        background: colorBgContainer,
                        minHeight: 280,
                        padding: 24,
                        borderRadius: borderRadiusLG,
                    }}
                >
                    <Image
                        width={200}
                        src={event.image}
                    />
                    <Typography.Paragraph>
                        {event.description}
                    </Typography.Paragraph>
                </div>
            </Content>
        </Layout>
    );
}

export default EventPage;