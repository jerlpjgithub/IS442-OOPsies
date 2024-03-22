import React from 'react'
import { Card, Row, Col, Typography, Tag } from 'antd';
import { parseToReadableDate, parseToReadableTime } from '../../../../utils/methods';

export const TicketCard = (props) => {
  const dummyData = {
    id: 12345,
    eventName: 'Taylor Swift - My Era concert',
    eventDate: '12 Jan 2024',
    eventTime: '6:30PM',
    venue: 'Marina Bay Sands',
    price: '$70.00',
    isRedeemed: true,
  }

  const { Text } = Typography;
  const { key, ticket } = props;

  const { ticketPrice, ticketId, eventName, eventVenue, eventDatetime, redeemed, isValid } = ticket;

  return (
    <>
      <Card
        key={key}
        style={{ width: 500 }}
        bodyStyle={{ padding: 0 }} // Add this line to remove the padding
      >
        <Row>
          <Col span={2} style={{ backgroundColor: "grey", writingMode: 'vertical-rl', transform: 'scale(-1)', display: "flex", justifyContent: "space-evenly", padding: "10px" }}>
            <Text style={{ fontSize: "12px", fontWeight: "bold", textAlign: "center" }} strong>Ticket Number:</Text>
            <Text style={{ fontSize: "12px", fontWeight: "bold", textAlign: "center" }} strong>{ticketId}</Text>
          </Col>

          {/* Event Name and Date */}
          <Col span={20} style={{ display: "flex", flexDirection: "column", alignItems: "center", justifyContent: "space-evenly", textAlign: "center", padding: "0 10 0 10", borderRight: "1px lightgray solid" }}>
              <Text style={{ fontSize: "16px", fontWeight: "bold", textAlign: "center" }} strong>{eventName}</Text>
              <Text style={{ fontSize: "14px" }} type="secondary">{eventVenue}</Text>
              <div style={{ padding: "5px 10px 5px 10px", backgroundColor: "lightgray"}}>
                <Text style={{ fontSize: "12px" }} type="secondary">{parseToReadableDate(eventDatetime)}, {parseToReadableTime(eventDatetime)} </Text>
              </div>
          </Col>
          {/* Venue and Price and status*/}
          <Col span={2}>
            <div style={{ writingMode: 'vertical-rl', textOrientation: 'mixed', transform: 'scale(-1)' }}>
              <div style={{ display: "flex", gap: "10px", padding: "10px" }}>
                <Text style={{ fontSize: "12px", fontWeight: "bold", textAlign: "center" }} strong>${ticketPrice.toFixed(2)}</Text>
                <Text style={{ fontSize: "12px", fontWeight: "bold", textAlign: "center" }} strong>
                  { <Tag color={dummyData.redeemed ? "red" : "green "}> { redeemed ? "Redeemed" : "Valid "} </Tag> }
                </Text>
              </div>
            </div>
          </Col>
        </Row>
      </Card>
    </>
  )
}
