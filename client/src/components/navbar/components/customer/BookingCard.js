import React from 'react'
import { Card, Typography, Row, Col, Button } from 'antd';
import { InfoCircleOutlined, StopOutlined } from '@ant-design/icons';

import { parseToReadableDate, parseToReadableTime } from '../../../../utils/methods';

const { Text } = Typography;

export const BookingCard = (props) => {
  const { setSelectedID, key, booking } = props;

  const { bookingID, bookingDate, numTickets, cancelDate, event } = booking;
  const { eventName, dateTime, venue, ticketPrice, eventCancelled } = event;

  // Disregard if the event was cancelled by the organiser or the user
  const isCancelled = !!cancelDate || eventCancelled;

  return (
    <>
      <Card
        id={key}
        bodyStyle={{ padding: 0 }} // Add this line to remove the padding
      >
        <Row>
          <Col span={4} style={{ backgroundColor: "#1677ff", display: "flex", flexDirection: "column", alignItems: "center", justifyContent: "center", textAlign: "center", padding: "0 10 0 10"}}>
            <Text style={{ color: "white", fontSize: "32px", fontWeight: "bold"}}>{parseToReadableDate(dateTime)}</Text>
            <Text style={{ color: "white", fontSize: "20px", fontWeight: "bold"}}>{parseToReadableTime(dateTime)}</Text>
          </Col>
          <Col span={20} style={{ display: "flex", flexDirection: "column", justifyContent: "center" }}>
            <div style={{ display: "flex", flexDirection: "column", alignItems: "center", textAlign: "center", padding: "0 10 0 10" }}>
              <Text style={{ fontSize: "32px", fontWeight: "bold", textAlign: "center" }} strong>{eventName}</Text>
              <Text style={{ fontSize: "20px" }} type="secondary">{venue}</Text>
              <Text type="secondary">Booked on - {parseToReadableDate(bookingDate)}</Text>
              <Text type="secondary">Number of tickets: {numTickets}</Text>
              <div id="action-tab" style={{ display: "flex", width: "100%", paddingTop: "10px"}}>
                <Button icon={<InfoCircleOutlined />} block style={{ borderRadius: 0 }} onClick={() => setSelectedID(bookingID)}> View More </Button>
                <Button icon={<StopOutlined />} type="primary" block danger style={{ borderRadius: 0 }}> Cancel booking </Button>
              </div>
            </div>
          </Col>
        </Row>
      </Card>
    </>
      )
}
