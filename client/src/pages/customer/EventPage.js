import { React, useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import {
  Layout,
  Image,
  Button,
  Typography,
  Breadcrumb,
  theme,
  Modal,
  InputNumber,
  Divider,
  notification,
  Row,
  Col,
  Card,
  Alert
} from 'antd'
import {
  CalendarOutlined,
  ClockCircleOutlined,
  PushpinOutlined,
  UserOutlined
} from '@ant-design/icons'
import { images } from '../imageloader'
import { getEvent, createBooking, getUserData } from '../../utils/api'
import { parseToReadableDate, parseToReadableTime } from '../../utils/methods'

const { Content } = Layout
const { Title, Paragraph } = Typography
const { Meta } = Card

const EventPage = () => {
  const user = JSON.parse(localStorage.getItem('authUser'))
  const userId = user.id

  const {
    token: { colorBgContainer, borderRadiusLG }
  } = theme.useToken()

  const { id } = useParams()
  const [event, setEvent] = useState(null)

  useEffect(() => {
    const fetchEvent = async () => {
      try {
        const response = await getEvent(id)
        setEvent(response)
      } catch (error) {
        console.error('Error fetching event:', error)
      }
    }

    fetchEvent()
  }, [id])

  // to handle bookings
  const [isModalVisible, setIsModalVisible] = useState(false)
  const [userDetails, setUserDetails] = useState({ accountBalance: 0 })

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const response = await getUserData(userId)
        console.log(response)
        setUserDetails(response)
      } catch (error) {
        console.error('Error fetching user', error)
      }
    }
    fetchUser()
  }, [userId])

  const showModal = () => {
    setIsModalVisible(true)
  }

  const handleBooking = async () => {
    setIsModalVisible(false)

    try {
      await createBooking(userId, id, numTickets)

      notification.success({
        message: 'Purchase Successful',
        description: `You have successfully purchased tickets to ${event.eventName}.`
      })
    } catch (error) {
      console.error('Error creating booking:', error)
      notification.error({
        message: 'Purchase Unsuccessful',
        description: `Your attempt to purchase tickets to ${event.eventName} was unsuccessful.`
      })
    }
  }

  const handleCancel = () => {
    setIsModalVisible(false)
  }

  const [numTickets, setNumTickets] = useState(1)

  const calculateTotalPrice = (numTickets, ticketPrice) => {
    return numTickets * ticketPrice
  }

  if (!event) {
    return <div>Loading...</div>
  }

  return (
    <Layout style={{ height: '100vh' }}>
      <Content
        style={{
          padding: '0 48px',
          flexGrow: 1,
          overflow: 'auto'
        }}
      >
        <Breadcrumb style={{ margin: '16px 0' }}>
          <Breadcrumb.Item>
            <Link to="/home">Home</Link>
          </Breadcrumb.Item>
          <Breadcrumb.Item>
            <Link to="/events">Events</Link>
          </Breadcrumb.Item>
          <Breadcrumb.Item>{event.eventName}</Breadcrumb.Item>
        </Breadcrumb>
        <div
          style={{
            height: '100vh',
            background: colorBgContainer,
            padding: '24px',
            borderRadius: borderRadiusLG,
            display: 'flex',
            justifyContent: 'center'
          }}
        >
          <Card
            style={{ width: 1000 }}
            cover={
              <Image
                src={images[Math.floor(Math.random() * images.length)]}
                alt={event.eventName}
                style={{
                  borderRadius: borderRadiusLG,
                  height: '450px'
                }}
              />
            }
          >
            <Meta
              title={event.eventName}
              description="Event created with The Oopsies"
            />
            {/* Event Details */}
            <div style={{ display: 'flex', gap: '25px', paddingTop: '15px' }}>
              <div style={{ display: 'flex', gap: '10px' }}>
                <div>
                  <CalendarOutlined style={{ fontSize: '16px' }} />
                </div>
                <div>{parseToReadableDate(event.dateTime)}</div>
              </div>
              <div style={{ display: 'flex', gap: '10px' }}>
                <div>
                  <ClockCircleOutlined style={{ fontSize: '16px' }} />
                </div>
                <div>{parseToReadableTime(event.dateTime)}</div>
              </div>
              <div style={{ display: 'flex', gap: '10px' }}>
                <div>
                  <PushpinOutlined style={{ fontSize: '16px' }} />
                </div>
                <div>{event.venue}</div>
              </div>
              <div style={{ display: 'flex', gap: '10px' }}>
                <div>
                  <UserOutlined style={{ fontSize: '16px' }} />
                </div>
                <div>{event.capacity}</div>
              </div>
            </div>
            {/* Event */}
            <div style={{ paddingTop: '25px' }}>
              <Alert
                message={
                  <div
                    style={{
                      fontSize: '14px',
                      fontWeight: '300',
                      marginLeft: '5px'
                    }}
                  >
                    Please be aware that a cancellation fee of{' '}
                    <strong>${event.cancellationFee}</strong> per ticket will be
                    applied should you decide to cancel your event booking after
                    purchase. Additionally, cancellations will not be accepted
                    within <u>48 hours</u> prior to the event.
                  </div>
                }
                showIcon
              />
            </div>
            <div style={{ paddingTop: '25px' }}>
              <div>
                <strong>Admission Rules:</strong>
              </div>
              <div
                style={{
                  fontSize: '14px',
                  fontWeight: '200'
                }}
              >
                Tickets must be presented for entry, either in printed form or
                on a mobile device, and late arrivals may result in denied entry
                or limited access to the event. Re-entry may not be permitted
                once you leave the event premises, and the resale of tickets is
                strictly prohibited. The event organizer reserves the right to
                refuse admission or remove attendees for misconduct or violation
                of event rules, and event details, including performers and
                schedule, are subject to change without notice. Additionally,
                please be aware that children under a certain age may require a
                separate ticket or be admitted free of charge, depending on the
                event's policy. Thank you for your understanding and
                cooperation.
              </div>
            </div>
            <div style={{ paddingTop: '25px' }}>
              <Button type="primary" block onClick={showModal}>
                Buy Now - $ {event.ticketPrice}
              </Button>
            </div>
          </Card>
        </div>
        <Modal
          title="Booking Details"
          visible={isModalVisible}
          onCancel={handleCancel}
          footer={[
            <Button key="back" onClick={handleCancel}>
              Return
            </Button>,
            <Button
              key="submit"
              type="primary"
              onClick={handleBooking}
              disabled={
                userDetails.accountBalance -
                  calculateTotalPrice(numTickets, event.ticketPrice) <
                0
              }
            >
              Confirm Purchase
            </Button>
          ]}
        >
          <Typography.Title level={4}>
            You are buying tickets to {event.eventName}
          </Typography.Title>
          <div style={{ marginBottom: '16px' }}>
            Number of Tickets:{' '}
            <InputNumber
              min={1}
              max={5}
              value={numTickets}
              onChange={setNumTickets}
            />
          </div>
          <br />
          Please understand that you are only able to purchase up to 5 tickets.
          <Divider />
          <Typography.Title level={5}>Payment Details</Typography.Title>
          <Row gutter={[16, 16]}>
            <Col xs={12}>Your Account Balance:</Col>
            <Col xs={12} style={{ textAlign: 'right' }}>
              ${userDetails.accountBalance}
            </Col>
          </Row>
          <Row gutter={[16, 16]}>
            <Col xs={12}>Total Price:</Col>
            <Col xs={12} style={{ textAlign: 'right' }}>
              ${calculateTotalPrice(numTickets, event.ticketPrice)}
            </Col>
          </Row>
          <Row gutter={[16, 16]}>
            <Col xs={12}>Remaining Balance:</Col>
            <Col
              xs={12}
              style={{
                textAlign: 'right',
                color:
                  userDetails.accountBalance -
                    calculateTotalPrice(numTickets, event.ticketPrice) <
                  0
                    ? 'red'
                    : 'inherit'
              }}
            >
              $
              {userDetails.accountBalance -
                calculateTotalPrice(numTickets, event.ticketPrice)}
            </Col>
          </Row>
          {userDetails.accountBalance -
            calculateTotalPrice(numTickets, event.ticketPrice) <
            0 && (
            <div style={{ marginTop: '16px', color: 'red' }}>
              Insufficient balance! Please choose fewer tickets or add funds to
              your account.
            </div>
          )}
        </Modal>
      </Content>
    </Layout>
  )
}

export default EventPage
