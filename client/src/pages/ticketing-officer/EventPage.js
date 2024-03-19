import { React, useEffect, useState } from "react";
import { Link, useLocation, useParams } from 'react-router-dom';
import {
    Layout,
    Image,
    Button,
    Typography,
    Breadcrumb,
    theme,
    Modal,
    InputNumber
} from "antd";
import { images } from '../imageloader';

const { Content } = Layout;
const { Title } = Typography;

// TODO: Add in the functionalities that a ticketing officer may require

const EventPage = () => {
    const {
        token: { colorBgContainer, borderRadiusLG },
    } = theme.useToken();

    // get event
    const { event_id } = useParams(); 
    const [ event, setEvent ] = useState(null); 

    useEffect(() => {
        const fetchEvent = async () => {
            const response = await fetch(`http://localhost:3000/event/get/${event_id}`); 
            const data = await response.json();
            setEvent(data);
        };

        fetchEvent();
    }, [event_id]);

    // to handle bookings
    const [isModalVisible, setIsModalVisible] = useState(false);

    const showModal = () => {
        setIsModalVisible(true);
    };

    const handleCancel = () => {
        setIsModalVisible(false);
    };

    return (
        <Layout style={{ height: '100vh', width: '100%', display: 'flex', flexDirection: 'column' }}>
            <Content
                style={{
                    padding: '0 48px',
                    flexGrow: 1, 
                    overflow: 'auto', 
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
                        display: 'flex',
                        flexDirection: 'column',
                        justifyContent: 'space-between',
                        height: '100%', // adjust as needed
                        borderRadius: borderRadiusLG,
                        background: colorBgContainer,
                    }}
                >
                    <div>
                        <Image
                            width={200}
                            src={images[Math.floor(Math.random() * images.length)]}
                        />
                        <Title>
                            {event.name}
                        </Title>
                        <Typography.Paragraph>
                            {event.description}
                        </Typography.Paragraph>
                    </div>
                    <div style={{ alignSelf: 'flex-end', margin: '20px' }}>
                        <Button type="primary" onClick={showModal}>
                            Buy Tickets
                        </Button>

                    </div>
                </div>
            </Content>
        </Layout >
    );
}

export default EventPage;