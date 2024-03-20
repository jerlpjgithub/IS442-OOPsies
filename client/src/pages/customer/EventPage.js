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
import axios from 'axios';

const { Content } = Layout;
const { Title } = Typography;

// TODO: Figure out what details we want from the event to show 

const EventPage = () => {
    const {
        token: { colorBgContainer, borderRadiusLG },
    } = theme.useToken();

    // get event
    // const { event_id } = useParams();
    // const [event, setEvent] = useState(null);
    
    const [event, setEvent] = useState([
        {
            event_id: 1,
            title: 'Jerome Lim Small PP',
            description: 'This is the description for event 1.',
            eventCancelled: false,
            capacity: 100,
        }]);


    // useEffect(() => {
    //     const fetchEvent = async () => {
    //         try {
    //             const response = await axios.get(`http://localhost:3000/event/get/${event_id}`);
    //             setEvent(response.data);
    //         } catch (error) {
    //             console.error(error);
    //         }
    //     };

    //     fetchEvent();
    // }, [event_id]);

    // to handle bookings
    const [isModalVisible, setIsModalVisible] = useState(false);

    const showModal = () => {
        setIsModalVisible(true);
    };

    const handleBooking = async () => {
        setIsModalVisible(false);

        const user = localStorage.getItem('authUser');
        const userId = user.id;

        try {
            const response = await axios.post(`http://localhost:3000/booking/create/${userId}`, {
                event_id: event.id,
                numTickets: numTickets
            });

            alert('Booking was successful');
        } catch (error) {
            console.error(error);
            alert('Booking was unsuccessful');
        }
    };

    const handleCancel = () => {
        setIsModalVisible(false);
    };

    const [numTickets, setNumTickets] = useState(1);

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
                            <p>
                                {event.description}
                            </p>
                            <p>
                                Location: {event.location}
                                Date: {event.dateTime}
                                Tickets left: {event.capacity}
                                Ticket Price: {event.ticketPrice}

                            </p>
                        </Typography.Paragraph>
                    </div>
                    <div style={{ alignSelf: 'flex-end', margin: '20px' }}>
                        <Button type="primary" onClick={showModal}>
                            Buy Tickets
                        </Button>
                        <Modal title="Buy Tickets" visible={isModalVisible} onOk={handleBooking} onCancel={handleCancel}>
                            Number of Tickets:  <InputNumber min={1} value={numTickets} onChange={setNumTickets} />
                        </Modal>
                    </div>
                </div>
            </Content>
        </Layout >
    );
}

export default EventPage;