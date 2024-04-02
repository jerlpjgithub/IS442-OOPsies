import React, { useState, useEffect } from 'react';
import { Layout, Breadcrumb, Button, Card, List, Input, Modal, Typography, notification, Divider, InputNumber } from 'antd';
import { Pagination } from 'antd';
import { Link } from 'react-router-dom';
import { images } from '../imageloader';
import { getAllEvents, validateTicket } from '../../utils/api';
import { parseToReadableDate, parseToReadableTime } from '../../utils/methods';

const { Content } = Layout;
const { Meta } = Card;
const { Title } = Typography;


export const EventsPage = () => {

    const [events, setEvents] = useState([]);

    useEffect(() => {
        const fetchEvents = async () => {
            try {
                const response = await getAllEvents();
                setEvents(response);
            } catch (error) {
                console.error('Error fetching events:', error);
            }
        };

        fetchEvents();
    }, []);

    const [currentPage, setCurrentPage] = useState(1);
    const [eventsPerPage] = useState(5);
    const [searchTerm, setSearchTerm] = useState('');
    const [isValidateModalVisible, setIsValidateModalVisible] = useState(false);
    const [eventName, setEventName] = useState('');
    const [ticketId, setTicketId] = useState(0);

    // Doing validation to ensure cancelled or events that are over do not get rendered
    const filteredEvents = events.filter(event =>
        (!event.eventCancelled || new Date(event.date) > new Date()) &&
        event.eventName.toLowerCase().includes(searchTerm.toLowerCase())
    );

    const indexOfLastEvent = currentPage * eventsPerPage;
    const indexOfFirstEvent = indexOfLastEvent - eventsPerPage;
    const currentEvents = filteredEvents.slice(indexOfFirstEvent, indexOfLastEvent);

    const paginate = (page) => {
        setCurrentPage(page);
    };

    const handleSearch = (event) => {
        setSearchTerm(event.target.value);
        setCurrentPage(1);
    };

    const showValidateModal = (name) => {
        setEventName(name);
        setIsValidateModalVisible(true);
    };

    const handleValidateOk = () => {
        try{
            validateTicket(ticketId);

            console.log(ticketId);
            notification.success({
                message: "Validate Successful",
                description: `You have successfully validated the ticket.`,
            });
        } catch (error){
            notification.error({
                message: "Validate Unsuccessful",
                description: `Your attempt to validate the ticket was unsuccessful.`,
            });
            throw error;
        }
        setIsValidateModalVisible(false);
    };

    const handleValidateCancel = () => {
        setIsValidateModalVisible(false);
    };

    return (
        <Layout style={{ height: '100vh', width: '100%', display: 'flex', flexDirection: 'column' }}>
            <Content style={{
                padding: '0 48px',
                flexGrow: 1,
                overflow: 'auto',
            }}>
                <Breadcrumb style={{ margin: '16px 0' }}>
                    <Breadcrumb.Item><Link to="/ticketing-officer/home">
                        Home
                    </Link></Breadcrumb.Item>
                    <Breadcrumb.Item>Events</Breadcrumb.Item>
                </Breadcrumb>
                <div style={{ background: '#fff', minHeight: 280, padding: 24 }}>
                    <Input.Search
                        placeholder="Search for events"
                        onChange={handleSearch}
                        style={{ marginBottom: '20px' }}
                    />
                    <List
                        itemLayout="horizontal"
                        dataSource={currentEvents}
                        renderItem={event => (
                            <List.Item
                                style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-end' }}
                                actions={[
                                    <Button type="primary" onClick={()=>{showValidateModal(event.eventName)}}>Validate Ticket</Button>,
                                    <Link to={`/ticketing-officer/event/${event.id}`}>
                                        <Button type="primary">View Details</Button>
                                    </Link>
                                ]}
                            >
                                <List.Item.Meta
                                    avatar={<img alt={event.eventName} src={images[Math.floor(Math.random() * images.length)]} style={{ width: '200px', height: '100px', objectFit: 'cover' }} />}
                                    title={<Link to={`/event/${event.id}`}>{event.eventName}</Link>}
                                    description={
                                        <>
                                            <div>Date: {event.dateTime ? parseToReadableDate(event.dateTime) : "Undated"}</div>
                                            <div>This event starts at {parseToReadableTime(event.dateTime)}.</div>
                                            <div>{event.capacity} tickets left. Tickets are ${event.ticketPrice}. </div>
                                        </>
                                    }
                                />
                            </List.Item>
                        )}
                    />
                    <Modal
                        title="Validate Ticket"
                        visible={isValidateModalVisible}
                        onOk={handleValidateOk}
                        onCancel={handleValidateCancel}
                    >
                        <Typography.Title level={4}>
                            You are validating a ticket to {eventName}.
                        </Typography.Title>
                        <Divider />
                        <Typography.Title level={5}>Ticket Details</Typography.Title>
                        Ticket ID: {' '}
                        <InputNumber
                            value={ticketId}
                            onChange={setTicketId}
                            style={{ width: '70%' }}
                        />  
                        <p>Are you sure you want to validate the ticket?</p>
                    </Modal>

                    <Pagination
                        current={currentPage}
                        total={filteredEvents.length}
                        pageSize={eventsPerPage}
                        onChange={paginate}
                        style={{ marginTop: '20px', textAlign: 'center' }}
                    />
                </div>
            </Content>
        </Layout>
    );
};


