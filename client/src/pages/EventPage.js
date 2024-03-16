import { React, useEffect, useState } from "react";
import { Link } from 'react-router-dom';
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
    // const [event, setEvent] = useState({ image: 'https://media.licdn.com/dms/image/D5603AQFKx8V3hq-wjA/profile-displayphoto-shrink_800_800/0/1677954667456?e=1715817600&v=beta&t=hb5AzhD6vw4itXDE78sjKygXGLLv3qJQM8ZuJvJtBYE', description: 'Kukujiao' });
    const event = { image: 'https://media.licdn.com/dms/image/D5603AQFKx8V3hq-wjA/profile-displayphoto-shrink_800_800/0/1677954667456?e=1715817600&v=beta&t=hb5AzhD6vw4itXDE78sjKygXGLLv3qJQM8ZuJvJtBYE', description: 'Kukujiao' }
    // useEffect(() => {
    //     // Fetch your event data here and update the event state
    //     // This is just a placeholder, replace it with your actual code
    //     fetchEventData().then(data => setEvent(data));
    //   }, []);

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