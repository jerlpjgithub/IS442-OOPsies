import { React, useEffect, useState } from "react";
import { Link, useParams } from 'react-router-dom';
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
    notification,
    Row,
    Col,
    Card,
    Statistic,
} from "antd";
import { images } from '../imageloader';
import { getEvent, createBooking, getUserData } from "../../utils/api";
import { parseToReadableDate, parseToReadableTime } from '../../utils/methods';

const { Content } = Layout;
const { Title, Paragraph } = Typography;

const EventPage = () => {
    const user = JSON.parse(localStorage.getItem('authUser'));
    const userId = user.id;

    const {
        token: { colorBgContainer, borderRadiusLG },
    } = theme.useToken();

    const { id } = useParams();
    const [event, setEvent] = useState(null);

    useEffect(() => {
        const fetchEvent = async () => {
            try {
                const response = await getEvent(id);
                setEvent(response);
            } catch (error) {
                console.error('Error fetching event:', error);
            }
        };

        fetchEvent();
    }, [id]);

    // to handle bookings
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [userDetails, setUserDetails] = useState({ accountBalance: 0 });

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const response = await getUserData(userId);
                console.log(response)
                setUserDetails(response);
            }
            catch (error) {
                console.error('Error fetching user', error);
            }
        }
        fetchUser();
    }, [userId]);

    const showModal = () => {
        setIsModalVisible(true);
    };

    const handleBooking = async () => {
        setIsModalVisible(false);

        try {
            await createBooking(userId, id, numTickets);

            notification.success({
                message: "Purchase Successful",
                description: `You have successfully purchased tickets to ${event.eventName}.`,
            });
        } catch (error) {
            console.error('Error creating booking:', error);
            notification.error({
                message: "Purchase Unsuccessful",
                description: `Your attempt to purchase tickets to ${event.eventName} was unsuccessful.`,
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

    if (!event) {
        return <div>Loading...</div>;
    }

    return (
        <Layout style={{ height: '100vh' }}>
            <Content style={{
                padding: '0 48px',
                flexGrow: 1,
                overflow: 'auto',
            }}>
                <Breadcrumb style={{ margin: '16px 0' }}>
                    <Breadcrumb.Item><Link to="/home">Home</Link></Breadcrumb.Item>
                    <Breadcrumb.Item><Link to="/events">Events</Link></Breadcrumb.Item>
                    <Breadcrumb.Item>{event.eventName}</Breadcrumb.Item>
                </Breadcrumb>
                <Col style={{ height: '90vh', background: colorBgContainer, padding: '24px', borderRadius: borderRadiusLG }}>
                    <Row gutter={[16, 16]} style={{ height: '100%' }}>
                        <Col xs={24} sm={24} md={12} >
                            <Image
                                src={images[Math.floor(Math.random() * images.length)]}
                                alt={event.eventName}
                                style={{ borderRadius: borderRadiusLG, height: "430px", width: "650px" }}
                            />
                        </Col>
                        <Col xs={24} sm={24} md={12}>
                            <Card
                                title={<Title level={2}>{event.eventName}</Title>}
                                style={{ borderRadius: borderRadiusLG }}
                            >
                                <Paragraph>
                                    <strong>Location:</strong> {event.venue}
                                </Paragraph>
                                <Paragraph>
                                    <strong>Date:</strong> {parseToReadableDate(event.dateTime)}
                                </Paragraph>
                                <Paragraph>
                                    <strong>Time:</strong> {parseToReadableTime(event.dateTime)}
                                </Paragraph>
                                <Col xs={12}>
                                    <Statistic title="Tickets Left" value={event.capacity} />
                                </Col>
                                <Col xs={12}>
                                    <Statistic
                                        title="Ticket Price"
                                        value={event.ticketPrice}
                                        prefix="$"
                                    />
                                </Col>
                                <Col xs={12}>
                                    <div style={{ fontSize: '20px' }}>
                                        <Statistic
                                            title="Cancellation Fee"
                                            value={event.cancellationFee}
                                            prefix="$"
                                            formatter={(value) => <div style={{ fontSize: '10px' }}>{value}</div>}
                                        />
                                    </div>
                                </Col>
                                <div style={{ marginTop: '24px', textAlign: 'center' }}>
                                    <Button type="primary" size="large" onClick={showModal}>
                                        Buy Tickets
                                    </Button>
                                </div>
                            </Card>
                        </Col>
                    </Row>
                </Col>
                <Modal
                    title="Booking Details"
                    visible={isModalVisible}
                    onCancel={handleCancel}
                    footer={[
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
                    ]}
                >
                    <Typography.Title level={4}>
                        You are buying tickets to {event.eventName}
                    </Typography.Title>
                    <div style={{ marginBottom: '16px' }}>
                        Number of Tickets:{' '}
                        <InputNumber
                            min={1}
                            max={5}
                            value={numTickets}
                            onChange={setNumTickets}
                        />
                    </div>
                    <Divider />
                    <Typography.Title level={5}>Payment Details</Typography.Title>
                    <Row gutter={[16, 16]}>
                        <Col xs={12}>Your Account Balance:</Col>
                        <Col xs={12} style={{ textAlign: 'right' }}>
                            ${userDetails.accountBalance}
                        </Col>
                    </Row>
                    <Row gutter={[16, 16]}>
                        <Col xs={12}>Total Price:</Col>
                        <Col xs={12} style={{ textAlign: 'right' }}>
                            ${calculateTotalPrice(numTickets, event.ticketPrice)}
                        </Col>
                    </Row>
                    <Row gutter={[16, 16]}>
                        <Col xs={12}>Remaining Balance:</Col>
                        <Col
                            xs={12}
                            style={{
                                textAlign: 'right',
                                color:
                                    userDetails.accountBalance -
                                        calculateTotalPrice(numTickets, event.ticketPrice) <
                                        0
                                        ? 'red'
                                        : 'inherit',
                            }}
                        >
                            ${userDetails.accountBalance - calculateTotalPrice(numTickets, event.ticketPrice)}
                        </Col>
                    </Row>
                    {userDetails.accountBalance - calculateTotalPrice(numTickets, event.ticketPrice) < 0 && (
                        <div style={{ marginTop: '16px', color: 'red' }}>
                            Insufficient balance! Please choose fewer tickets or add funds to your account.
                        </div>
                    )}
                </Modal>
            </Content>
        </Layout>
    );
};

export default EventPage;