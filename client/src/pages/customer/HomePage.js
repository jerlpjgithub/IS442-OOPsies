import React, { useState, useEffect } from 'react';
import { Layout, Breadcrumb, Row, Card, Col, Typography, Carousel, Button, Image } from 'antd';
import { LeftOutlined, RightOutlined } from '@ant-design/icons';
import { Link } from 'react-router-dom';
import { images } from '../imageloader';
import { getAllEvents } from '../../utils/api';
import { parseToReadableDate, parseToReadableTime } from '../../utils/methods';

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

const HomePage = () => {
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

    const filteredEvents = events.filter(event =>
        (!event.eventCancelled && new Date(event.dateTime) > new Date()));

    return (
        <Layout style={{ height: '105vh', width: '100%', display: 'flex', flexDirection: 'column' }}>
            <Content style={{ padding: '0 48px' }}>
                <Breadcrumb style={{ margin: '16px 0' }}>
                    <Breadcrumb.Item>Home</Breadcrumb.Item>
                </Breadcrumb>
                <div style={{ background: '#fff', minHeight: 280, padding: 24 }}>
                    <div style={{ padding: 30, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                        <Title level={1} style={{ fontSize: '70px', textAlign: 'center' }}>
                            Welcome to OOPsies Ticketing!
                        </Title>
                        <Typography.Paragraph style={{ fontSize: '20px', textAlign: 'center' }}>
                            Find and participate in your favourite events.
                        </Typography.Paragraph>
                    </div>
                    <div style={{ overflow: 'auto', width: '100%' }}>
                        <Carousel {...settings} autoplay>
                            {chunk(filteredEvents, 4).map((eventChunk, index) => (
                                <div key={index}>
                                    <Row gutter={16}>
                                        {eventChunk.map((event) => (
                                            <Col xs={24} sm={12} md={8} lg={6} key={event.id} style={{ padding: '20px' }}>
                                                <Link to={`/event/${event.id}`}>
                                                    <Card
                                                        hoverable
                                                        style={{ width: '100%', marginBottom: '20px' }}
                                                        cover={<img alt={event.name} src={images[Math.floor(Math.random() * images.length)]}
                                                            style={{ width: '100%', height: '200px', objectFit: 'cover' }}
                                                        />}
                                                    >
                                                        <Meta
                                                            title={event.eventName}
                                                            description={<div style={{
                                                                whiteSpace: 'nowrap',
                                                                overflow: 'hidden',
                                                                textOverflow: 'ellipsis'
                                                            }}>
                                                                <div>{event.venue} 
                                                                <br/> Tickets left: {event.capacity}</div>
                                                                <div>{parseToReadableDate(event.dateTime)}, {parseToReadableTime(event.dateTime)}</div>
                                                            </div>}
                                                        />
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

export default HomePage;