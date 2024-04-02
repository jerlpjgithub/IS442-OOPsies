import React, { useState, useEffect } from 'react';
import { Layout, Breadcrumb, Button, Card, List, Input, Select, Typography, Carousel } from 'antd';
import { Pagination } from 'antd';
import { Link } from 'react-router-dom';
import { images } from '../imageloader';

const { Content } = Layout;
const { Meta } = Card;
const { Title } = Typography;

// TO-DO: Add more information about events

export const EventsPage = () => {

    // Currently using placeholder data
    // Once mockdata are up, uncomment the bottom

    // const [events, setEvents] = useState([]);

    // useEffect(() => {
    //     const fetchEvents = async () => {
    //         const user = localStorage.getItem('authUser');
    //         const userId = user.id;
    //         try {
    //             const response = await axios.get(`http://localhost:8080/event/get/all/${userId}`);
    //             setEvents(response.data);
    //         } catch (error) {
    //             console.error(error);
    //         }
    //     };
    
    //     fetchEvents();
    // }, []); 

    const [events, setEvents] = useState([
        {
            title: 'Jerome Lim Small PP',
            description: 'This is the description for event 1.',
            eventCancelled: false,
            capacity: 100
        },
        {
            title: 'Event 2',
            description: 'This is the description for event 2.',
            eventCancelled: false,
            capacity: 100
        },
        {
            title: 'Event 3',
            description: 'This is the description for event 2.',
            eventCancelled: false,
            capacity: 100
        },
        {
            title: 'Event 4',
            description: 'This is the description for event 2.',
            eventCancelled: false,
            capacity: 100
        },
        {
            title: 'Event 5',
            description: 'This is the description for event 2.',
            eventCancelled: false,
            capacity: 100
        },
        {
            title: 'Event 6',
            description: 'This is the description for event 2.',
            eventCancelled: true,
            capacity: 100
        }
    ]);

    const [currentPage, setCurrentPage] = useState(1);
    const [eventsPerPage] = useState(5);
    const [searchTerm, setSearchTerm] = useState('');

    // Doing validation to ensure cancelled or events that are over do not get rendered
    const filteredEvents = events.filter(event => !event.eventCancelled || new Date(event.date) > new Date());

    const paginate = (page) => {
        setCurrentPage(page);
    };

    const handleSearch = (event) => {
        setSearchTerm(event.target.value);
    };

    return (
        <Layout style={{ height: '100vh', width: '100%', display: 'flex', flexDirection: 'column' }}>
            <Content style={{
                padding: '0 48px',
                flexGrow: 1,
                overflow: 'auto',
            }}>
                <Breadcrumb style={{ margin: '16px 0' }}>
                    <Breadcrumb.Item><Link to="/home">
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
                        dataSource={filteredEvents}
                        renderItem={event => (
                            <List.Item
                                style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-end' }}
                                actions={[
                                    <Link to={`/event/${event.id}`}>
                                        <Button type="primary">View Details</Button>
                                    </Link>
                                ]}
                            >
                                <List.Item.Meta
                                    avatar={<img alt={event.title} src={images[Math.floor(Math.random() * images.length)]} style={{ width: '200px', height: '100px', objectFit: 'cover' }} />}
                                    title={<Link to={`/event/${event.id}`}>{event.title}</Link>}
                                    description={
                                        <>
                                            <p>{event.description}</p>
                                            {/* Add more info about the event! */}
                                            <p>Date: {event.date ? new Date(event.dateTime).toLocaleDateString() : "Undated"}</p>
                                            <p>Tickets left: {event.capacity} </p>
                                        </>
                                    }
                                />
                            </List.Item>
                        )}
                    />

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


