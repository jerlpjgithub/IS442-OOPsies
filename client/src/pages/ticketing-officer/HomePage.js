import React, { useState, useEffect } from 'react';
import { Layout, Breadcrumb, Row, Card, Col, Input, Select, Typography, Carousel, Button, Image } from 'antd';
import { LeftOutlined, RightOutlined } from '@ant-design/icons';
import { Pagination } from 'antd';
import { Link } from 'react-router-dom';
import { images } from '../imageloader';
import Logo from '../../assets/oopsies-logo.svg';

const { Content } = Layout;
const { Meta } = Card;
const { Title } = Typography;
const settings = {
    nextArrow: <Button shape="default" icon={<RightOutlined style={{ fontSize: '20px', color: 'black' }} />} style={{ backgroundColor: 'rgba(0, 0, 0, 0.5)' }} />,
    prevArrow: <Button shape="default" icon={<LeftOutlined style={{ fontSize: '20px', color: 'black' }} />} style={{ backgroundColor: 'rgba(0, 0, 0, 0.5)' }} />,
    autoplaySpeed: 2000,
};

function chunk(array, size) {
    const chunked = [];
    let index = 0;

    while (index < array.length) {
        chunked.push(array.slice(index, size + index));
        index += size;
    }

    return chunked;
}

export const HomePage = () => {

    // Currently using placeholder data
    // Once mockdata are up, uncomment the bottom

    // const [events, setEvents] = useState([]);

    // useEffect(() => {
    //     const fetchEvents = async () => {
    //         const user = localStorage.getItem('authUser');
    //         const userId = user.id;
    //         try {
    //             const response = await axios.get(`http://localhost:3000/event/get/all/${userId}`);
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
            image: 'https://media.licdn.com/dms/image/C5103AQHCHy2M0HoXsg/profile-displayphoto-shrink_800_800/0/1541057709013?e=1715817600&v=beta&t=d_N4Ov_IOjfKa1DYDpirAnI5F4JYczriY10CE7v-xoU'
        },
        {
            title: 'Event 2',
            description: 'This is the description for event 2.',
            image: 'https://media.licdn.com/dms/image/D5603AQFJU9Q4E453fQ/profile-displayphoto-shrink_800_800/0/1661907635137?e=1715817600&v=beta&t=SdmRwWOgFgiET0ezV1IIeCdZKPC_wrvd-vOqqnDKilw'
        },
        {
            title: 'Event 3',
            description: 'This is the description for event 2.',
        },
        {
            title: 'Event 4',
            description: 'This is the description for event 2.',
        },
        {
            title: 'Event 5',
            description: 'This is the description for event 2.',
        },
        {
            title: 'Event 6',
            description: 'This is the description for event 2.',
        }
    ]);
    const [currentPage, setCurrentPage] = useState(1);
    const [eventsPerPage] = useState(6); // Set the number of events per page
    const [searchTerm, setSearchTerm] = useState('');
    
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
            <Content style={{ padding: '0 48px' }}>
                <Breadcrumb style={{ margin: '16px 0' }}>
                    <Breadcrumb.Item>Home</Breadcrumb.Item>
                </Breadcrumb>
                <div style={{ background: '#fff', minHeight: 280, padding: 24 }}>
                    <div style={{padding:30, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                        <Title level={1} style={{ fontSize:'70px', textAlign: 'center' }}>
                            Welcome to OOPsies Ticketing!
                        </Title>
                        <Typography.Paragraph style={{ fontSize: '20px', textAlign: 'center' }}>
                            Validate, verify, and issue tickets for your events.
                        </Typography.Paragraph>
                    </div>
                    <div style={{ overflow: 'auto', width: '100%' }}>
                    <Carousel {...settings} autoplay>
                        {chunk(currentEvents, 4).map((events, index) => (
                            <div key={index}>
                                <Row gutter={16}>
                                    {events.map((event, index) => (
                                        <Col xs={24} sm={12} md={8} lg={6} key={index} style={{ padding: '20px'}}>
                                            <Link to={{ pathname: `/event/${index}` }}>
                                                <Card
                                                    hoverable
                                                    style={{ width: '100%', marginBottom: '20px'}}
                                                    cover={<img alt={event.title} src={images[Math.floor(Math.random() * images.length)]}
                                                        style={{ width: '100%', height: '200px', objectFit: 'cover' }}
                                                    />}
                                                >
                                                    <Meta title={event.title} description={event.description} />
                                                </Card>
                                            </Link>
                                        </Col>
                                    ))}
                                </Row>
                            </div>
                        ))}
                    </Carousel>
                    </div>
                </div>
            </Content>
        </Layout>
    );
};

