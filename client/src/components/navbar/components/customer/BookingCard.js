import React from 'react'
import { Card, Typography, Space } from 'antd';

const { Text } = Typography;

export const BookingCard = () => {
  const dummyData = {
    id: 1,
    bookingDate: '01/01/2024',
    eventDate: '05/03/2024',
    eventName: 'Taylor Swift - My Era concert',
    numOfTickets: 4,
    venue: 'Marina Bay Sands'
  }

  return (
    <>
      <Card
        id={dummyData.id}
        onClick={() => { console.log('Card clicked') }}
        >
          <div>
            {/* This is event related */}
            <Space direction="vertical">
              <Text strong>{dummyData.eventName}</Text>
              <Text type="secondary">{dummyData.eventDate}</Text>
              <Text type="secondary">{dummyData.venue}</Text>
              <Text type="secondary">Booked on - {dummyData.bookingDate}</Text>
              <Text type="secondary">Number of tickets: {dummyData.numOfTickets}</Text>
            </Space>
          </div>
      </Card>
    </>

  )
}
