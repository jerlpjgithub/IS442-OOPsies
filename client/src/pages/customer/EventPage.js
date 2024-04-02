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
    InputNumber,
    Divider,
    notification
} from "antd";
import { images } from '../imageloader';
import axios from 'axios';

const { Content } = Layout;
const { Title } = Typography;



// TODO: Figure out what details we want from the event to show 

const EventPage = () => {

    const user = JSON.parse(localStorage.getItem('authUser'));
    const userId = user.id;

    const {
        token: { colorBgContainer, borderRadiusLG },
    } = theme.useToken();

    // get event
    // const { event_id } = useParams();
    // const [event, setEvent] = useState(null);

    const [event, setEvent] = useState(
        {
            event_id: 1,
            name: 'Jerome Lim Small PP',
            description:
                "Join us for an intimate and informative session with Jerome Lim, a renowned expert in the field of small PowerPoint (PP) presentations. In this event, Jerome will share his insights and best practices for creating impactful and concise PP slides for small audiences. Whether you're a beginner looking to enhance your presentation skills or a seasoned presenter seeking new ideas, this event promises to be both enlightening and inspiring. Don't miss this opportunity to learn from Jerome and take your small PP presentations to the next level!",
            eventCancelled: false,
            capacity: 100,
            location: "Jerome's house",
            dateTime: Date(),
            ticketPrice: 10

        });


    // useEffect(() => {
    //     const fetchEvent = async () => {
    //         try {
    //             const response = await axios.get(`http://localhost:8080/event/get/${event_id}`);
    //             setEvent(response.data);
    //         } catch (error) {
    //             console.error(error);
    //         }
    //     };

    //     fetchEvent();
    // }, [event_id]);

    // to handle bookings
    const [isModalVisible, setIsModalVisible] = useState(false);

    const [userDetails, setUserDetails] = useState({ accountBalance: 100 });

    useEffect(() => {
        const fetchStatus = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/user/${userId}`, { withCredentials: true });
                setUserDetails(response.data);
            } catch (error) {
                console.log(error);
            }
        };

        fetchStatus();
    }, [userId]);


    const showModal = () => {
        setIsModalVisible(true);
    };

    const handleBooking = async () => {
        setIsModalVisible(false);

        try {
            const response = await axios.post(`http://localhost:8080/booking/create/${userId}`, {
                event_id: event.id,
                numTickets: numTickets
            });

            notification.success({
                message: "Purchase Successful",
                description: `You have successfully purchased tickets to ${event.name} .`,
              });
            ;
        } catch (error) {
            console.error(error);
            notification.error({
                message: "Purchase Unsuccessful",
                description: `Your attempt to purchase tickets to ${event.name} was unsuccessful.`,
              });
        }
    };

    const handleCancel = () => {
        setIsModalVisible(false);
    };

    const [numTickets, setNumTickets] = useState(1);

    const calculateTotalPrice = (numTickets, ticketPrice) => {
        return numTickets * ticketPrice;
    };

    return (
        <Layout>
            <Content
                style={{
                    padding: '0 48px'
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
                        borderRadius: borderRadiusLG,
                        background: colorBgContainer,
                    }}
                >
                    <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', background: colorBgContainer }}>
                        <Image
                            style={{ borderRadius: 10, marginTop: 10 }}
                            width={800}
                            height={300}
                            src={images[Math.floor(Math.random() * images.length)]}
                        />
                        <Title style={{ fontSize: '60px' }}>
                            {event.name}
                        </Title>
                        <div style={{ width: '1000px' }}>
                            <Typography.Paragraph>
                                <p>
                                    {event.description}
                                </p>
                                <hr />
                                <p>
                                    Location: {event.location}
                                    <p>
                                        Date: {event.dateTime}
                                    </p>
                                    Tickets left: {event.capacity}
                                    <p>
                                        Ticket price: {event.ticketPrice}
                                    </p>
                                </p>
                            </Typography.Paragraph>
                        </div>
                    </div>
                    <div style={{ display: 'flex', justifyContent: 'center', margin: '20px' }}>
                        <Button type="primary" onClick={showModal}>
                            Buy Tickets
                        </Button>
                        <Modal title="Booking details" visible={isModalVisible} onCancel={handleCancel} footer={[
                            <Button key="back" onClick={handleCancel}>
                                Return
                            </Button>,
                            <Button
                                key="submit"
                                type="primary"
                                onClick={handleBooking}
                                disabled={userDetails.accountBalance - calculateTotalPrice(numTickets, event.ticketPrice) < 0}
                            >
                                Confirm Purchase
                            </Button>,
                        ]}>
                            <Typography.Title level={2}>
                                You are buying tickets to {event.name}
                            </Typography.Title>
                            Number of Tickets:  <InputNumber min={1} max={5} value={numTickets} onChange={setNumTickets} />
                            <Divider />
                            <Typography.Title level={3}>
                                Payment details:
                            </Typography.Title>
                            <p style={{ display: 'flex', justifyContent: 'space-between' }}>
                                <span>Your account balance:</span>
                                <span>${userDetails.accountBalance}</span>
                            </p>
                            <p style={{ display: 'flex', justifyContent: 'space-between' }}>
                                <span>Total price:</span>
                                <span>${calculateTotalPrice(numTickets, event.ticketPrice)}</span>
                            </p>
                            <p style={{ display: 'flex', justifyContent: 'space-between' }}>
                                <span>Remaining balance:</span>
                                <span style={{ color: userDetails.accountBalance - calculateTotalPrice(numTickets, event.ticketPrice) < 0 ? 'red' : 'black' }}>
                                    ${userDetails.accountBalance - calculateTotalPrice(numTickets, event.ticketPrice)}
                                </span>
                            </p>
                            {userDetails.accountBalance - calculateTotalPrice(numTickets, event.ticketPrice) < 0 &&
                                <p>Insufficient balance! Go work harder.</p>
                            }
                        </Modal>
                    </div>
                </div>
            </Content>
        </Layout >
    );
}

export default EventPage;