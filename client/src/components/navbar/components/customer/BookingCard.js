import React from 'react'
import { Card, Typography, Row, Col, Button } from 'antd';
import { InfoCircleOutlined, StopOutlined } from '@ant-design/icons';

const { Text } = Typography;

export const BookingCard = (props) => {
  const { setSelectedID } = props;

  const dummyData = {
    id: 1,
    bookingDate: '01/01/2024',
    eventDate: '12 Jan 2024',
    eventTime: '6:30PM',
    eventName: 'Taylor Swift - My Era concert',
    numOfTickets: 4,
    venue: 'Marina Bay Sands'
  }

  return (
    <>
      <Card
        id={dummyData.id}
        bodyStyle={{ padding: 0 }} // Add this line to remove the padding
      >
        <Row>
          <Col span={4} style={{ backgroundColor: "#1677ff", display: "flex", flexDirection: "column", alignItems: "center", justifyContent: "center", textAlign: "center", padding: "0 10 0 10"}}>
            <Text style={{ color: "white", fontSize: "32px", fontWeight: "bold"}}>{dummyData.eventDate}</Text>
            <Text style={{ color: "white", fontSize: "20px", fontWeight: "bold"}}>{dummyData.eventTime}</Text>
          </Col>
          <Col span={20} style={{ display: "flex", flexDirection: "column", justifyContent: "center" }}>
            <div style={{ display: "flex", flexDirection: "column", alignItems: "center", textAlign: "center", padding: "0 10 0 10" }}>
              <Text style={{ fontSize: "32px", fontWeight: "bold", textAlign: "center" }} strong>{dummyData.eventName}</Text>
              <Text style={{ fontSize: "20px" }} type="secondary">{dummyData.venue}</Text>
              <Text type="secondary">Booked on - {dummyData.bookingDate}</Text>
              <Text type="secondary">Number of tickets: {dummyData.numOfTickets}</Text>
              <div id="action-tab" style={{ display: "flex", width: "100%", paddingTop: "10px"}}>
                <Button icon={<InfoCircleOutlined />} block style={{ borderRadius: 0 }} onClick={() => setSelectedID(dummyData.id)}> View More </Button>
                <Button icon={<StopOutlined />} type="primary" block danger style={{ borderRadius: 0 }}> Cancel booking </Button>
              </div>
            </div>
          </Col>
        </Row>
      </Card>
    </>
      )
}
