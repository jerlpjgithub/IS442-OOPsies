import React, { useState, useEffect } from 'react';
import { Layout, Breadcrumb, Row, Card, Col, Input, Select } from 'antd';
import { Pagination } from 'antd';
import { Link } from 'react-router-dom';
const { Content } = Layout;
const { Meta } = Card;
const { Option } = Select;

const HomePage = () => {
    const [events, setEvents] = useState([
        {
            title: 'Event 1',
            description: 'This is the description for event 1.',
            image: 'https://media.licdn.com/dms/image/C5103AQHCHy2M0HoXsg/profile-displayphoto-shrink_800_800/0/1541057709013?e=1715817600&v=beta&t=d_N4Ov_IOjfKa1DYDpirAnI5F4JYczriY10CE7v-xoU'
        },
        {
            title: 'Event 2',
            description: 'This is the description for event 2.',
            image: 'https://media.licdn.com/dms/image/D5603AQFJU9Q4E453fQ/profile-displayphoto-shrink_800_800/0/1661907635137?e=1715817600&v=beta&t=SdmRwWOgFgiET0ezV1IIeCdZKPC_wrvd-vOqqnDKilw'
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
    const [category, setCategory] = useState('');

    const handleCategoryChange = (value) => {
        setCategory(value);
    };


    useEffect(() => {
        setEvents(events);
    }, [events]);

    // Get current events
    const indexOfLastEvent = currentPage * eventsPerPage;
    const indexOfFirstEvent = indexOfLastEvent - eventsPerPage;
    const currentEvents = events.slice(indexOfFirstEvent, indexOfLastEvent);

    // Change page
    const paginate = (page) => {
        setCurrentPage(page);
    };

    const handleSearch = (event) => {
        setSearchTerm(event.target.value);
    };


    return (
        <Layout>
            <Content style={{ padding: '0 48px' }}>
                <Breadcrumb style={{ margin: '16px 0' }}>
                    <Breadcrumb.Item>Home</Breadcrumb.Item>
                </Breadcrumb>
                <div style={{ background: '#fff', minHeight: 280, padding: 24 }}>
                    <Input.Search
                        placeholder="Search for events"
                        onChange={handleSearch}
                        style={{ marginBottom: '20px' }}
                    />
                    
                    {/* Might not want this if we don't have a category for Events */}
                    
                    {/* <Select
                        placeholder="Select a category"
                        onChange={handleCategoryChange}
                        style={{ width: '100%', marginBottom: '20px' }}
                    >
                        <Option value="category1">Category 1</Option>
                        <Option value="category2">Category 2</Option>
                        <Option value="category3">Category 3</Option>
                    </Select> */}
                    <Row gutter={16}>
                        {currentEvents.map((event) => (
                            <Col xs={24} sm={12} md={8} lg={6} key={event.title}>
                                {/* <Link to={`/event/${event.id}`}> */}
                                <Card
                                    hoverable
                                    style={{ width: '100%', marginBottom: '20px' }}
                                    cover={<img alt={event.title} src={event.image} />}
                                >
                                    <Meta title={event.title} description={event.description} />
                                </Card>
                                {/* </Link> */}
                            </Col>
                        ))}
                    </Row>
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
