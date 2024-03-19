import React, { useState, useEffect } from 'react';
import { Layout, Breadcrumb, Button, Card, List, Input, Select, Typography, Carousel } from 'antd';
import { Pagination } from 'antd';
import { Link } from 'react-router-dom';
import { images } from '../imageloader';

const { Content } = Layout;
const { Meta } = Card;
const { Title } = Typography;

// TO-DO: Add more information about events

const HomePage = () => {

    // Currently using placeholder data
    // Once mockdata are up, uncomment the bottom

    // const [events, setEvents] = useState([]);

    // useEffect(() => {
    //     const fetchEvents = async () => {
    //         const response = await fetch('http://localhost:3000/event/get/all');
    //         const data = await response.json();
    //         setEvents(data);
    //     };

    //     fetchEvents();
    // }, []); 

    const [events, setEvents] = useState([
        {
            title: 'Jerome Lim Small PP',
            description: 'This is the description for event 1.',
        },
        {
            title: 'Event 2',
            description: 'This is the description for event 2.',
        },
        {
            title: 'Event 2',
            description: 'This is the description for event 2.',
        },
        {
            title: 'Event 2',
            description: 'This is the description for event 2.',
        }
    ]);
    const [currentPage, setCurrentPage] = useState(1);
    const [eventsPerPage] = useState(6); // Set the number of events per page
    const [searchTerm, setSearchTerm] = useState('');

    useEffect(() => {
        setEvents(events);
    }, [events]);

    // Get current events
    const indexOfLastEvent = currentPage * eventsPerPage;
    const indexOfFirstEvent = indexOfLastEvent - eventsPerPage;
    const currentEvents = events.slice(indexOfFirstEvent, indexOfLastEvent);

    // Currently, there are some issues with the pagination. If I go to second page then
    // back to first, the second page items would still stay.

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
                        dataSource={currentEvents}
                        renderItem={event => (
                            <List.Item
                                actions={[
                                    <Link to={`/event/${event.id}`}>
                                        <Button type="primary">View Details</Button>
                                    </Link>
                                ]}
                            >
                                <List.Item.Meta
                                    avatar={<img alt={event.title} src={images[Math.floor(Math.random() * images.length)]}  style={{ width: '200px', height: '100px', objectFit: 'cover' }}  />}
                                    title={<Link to={`/event/${event.id}`}>{event.title}</Link>}
                                    description={
                                        <>
                                            <p>{event.description}</p>
                                            {/* Add more info about the event! */}
                                            <p>Date: {new Date(event.date).toLocaleDateString()}</p>
                                        </>
                                    }
                                />
                            </List.Item>
                        )}
                    />

                    <Pagination
                        current={currentPage}
                        total={events.length}
                        pageSize={eventsPerPage}
                        onChange={paginate}
                        style={{ marginTop: '20px', textAlign: 'center' }}
                    />
                </div>
            </Content>
        </Layout>
    );
};

export default HomePage;
