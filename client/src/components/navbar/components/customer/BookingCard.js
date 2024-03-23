import React from 'react'
import { Card, Typography, Row, Col, Button, Popconfirm } from 'antd'
import { InfoCircleOutlined, StopOutlined } from '@ant-design/icons'

import {
  parseToReadableDate,
  parseToReadableTime
} from '../../../../utils/methods'

const { Text } = Typography

const isEventCancellable = (dateTime) => {
  const currentDate = new Date()
  const eventDate = new Date(dateTime)

  const timeDifference = eventDate.getTime() - currentDate.getTime()
  const hoursDifference = timeDifference / (1000 * 60 * 60)

  return hoursDifference >= 48
}

export const BookingCard = (props) => {
  const { setSelectedID, key, booking, handleRefund } = props

  const { bookingID, bookingDate, numTickets, cancelDate, event } = booking
  const { eventName, dateTime, venue, eventCancelled, cancellationFee } = event

  console.log(booking)
  // Disregard if the event was cancelled by the organiser or the user
  const isCancelled = !!cancelDate || eventCancelled
  const disableCancelBtn = isCancelled || !isEventCancellable(dateTime)

  return (
    <>
      <Card
        id={key}
        bodyStyle={{ padding: 0 }} // Add this line to remove the padding
        style={{ marginBottom: '15px' }}
      >
        <Row>
          <Col
            span={4}
            style={{
              backgroundColor: '#1677ff',
              display: 'flex',
              flexDirection: 'column',
              justifyContent: 'center',
              textAlign: 'center',
              padding: '0 2px 0 2px'
            }}
          >
            <Text
              style={{
                color: 'white',
                fontSize: '28px',
                fontWeight: 'bold'
              }}
            >
              {parseToReadableDate(dateTime)}
            </Text>
            <Text
              style={{
                color: 'white',
                fontSize: '20px',
                fontWeight: 'bold'
              }}
            >
              {parseToReadableTime(dateTime)}
            </Text>
          </Col>
          <Col
            span={20}
            style={{
              display: 'flex',
              flexDirection: 'column',
              justifyContent: 'center'
            }}
          >
            <div
              style={{
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                textAlign: 'center',
                padding: '0 10 0 10'
              }}
            >
              <Text
                style={{
                  fontSize: '32px',
                  fontWeight: 'bold',
                  textAlign: 'center'
                }}
                strong
              >
                {eventName}
              </Text>
              <Text style={{ fontSize: '20px' }} type="secondary">
                {venue}
              </Text>
              <Text type="secondary">
                Booked on - {parseToReadableDate(bookingDate)}
              </Text>
              <Text type="secondary">Number of tickets: {numTickets}</Text>
              <div
                id="action-tab"
                style={{
                  display: 'flex',
                  width: '100%',
                  paddingTop: '10px'
                }}
              >
                <Button
                  disabled={isCancelled}
                  icon={<InfoCircleOutlined />}
                  block
                  style={{ borderRadius: 0 }}
                  onClick={() => setSelectedID(bookingID)}
                >
                  {' '}
                  View More{' '}
                </Button>
                <Popconfirm
                  title="Cancel booking?"
                  description={
                    <div style={{ width: "250px"}}>
                      Are you sure you want to cancel booking? By doing so, you
                      will incur a cancellation fee of ${cancellationFee || 0}.
                    </div>
                  }
                  onConfirm={() => handleRefund(bookingID)}
                  okText="Confirm"
                  cancelText="Cancel"
                >
                  <Button
                    disabled={disableCancelBtn}
                    icon={<StopOutlined />}
                    type="primary"
                    block
                    danger
                    style={{ borderRadius: 0 }}
                  >
                    Cancel booking
                  </Button>
                </Popconfirm>
              </div>
              {isCancelled && (
                <div
                  style={{
                    position: 'absolute',
                    top: '50%',
                    left: '50%',
                    transform: 'translate(-50%, -50%)',
                    fontSize: '80px',
                    fontWeight: 'bold',
                    color: 'red'
                  }}
                >
                  INVALID
                </div>
              )}
            </div>
          </Col>
        </Row>
      </Card>
    </>
  )
}
