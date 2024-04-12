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
  Tooltip,
  Input,
  Alert
} from 'antd'
import {
  CalendarOutlined,
  ClockCircleOutlined,
  PushpinOutlined,
  UserOutlined,
  InfoCircleOutlined
} from '@ant-design/icons'
import { images } from '../imageloader'
import {
  getEvent,
  createOnsiteBooking
} from '../../utils/api'
import { parseToReadableDate, parseToReadableTime } from '../../utils/methods'

const { Content } = Layout
const { Meta } = Card

export const EventPage = () => {
  const [isModalVisible, setIsModalVisible] = useState(false)
  const [numTickets, setNumTickets] = useState(0)
  const [buyerEmail, setBuyerEmail] = useState('')

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

  const showModal = () => {
    setIsModalVisible(true)
  }

  const handleBooking = async () => {
    setIsModalVisible(false)

    try {
      await createOnsiteBooking(id, numTickets, buyerEmail)
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
            <Link to="/ticketing-officer/home">Home</Link>
          </Breadcrumb.Item>
          <Breadcrumb.Item>
            <Link to="/ticketing-officer/events">Events</Link>
          </Breadcrumb.Item>
          <Breadcrumb.Item>{event.eventName}</Breadcrumb.Item>
        </Breadcrumb>
        <div
          style={{
            height: '135%',
            padding: '24px',
            background: colorBgContainer,
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
              title={
                <div>
                  <Alert
                    message={
                      <div
                        style={{
                          fontSize: '14px',
                          fontWeight: '300',
                          marginLeft: '5px'
                        }}
                      >
                        <strong>
                          You are purchasing tickets on behalf of a customer.
                          Please ensure tickets are only released upon payment
                          confirmation.
                        </strong>
                      </div>
                    }
                    type="error"
                    showIcon
                    style={{ marginBottom: '16px' }}
                  />
                  <div>{event.eventName}</div>
                </div>
              }
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
            <Tooltip title={new Date(event.dateTime) < new Date() ? "This event has already ended.": ""}>
                <Button type="primary" block onClick={showModal} disabled={new Date(event.dateTime) < new Date()}>
                  Buy Now - $ {event.ticketPrice}
                </Button>
            </Tooltip>
            </div>
          </Card>
        </div>
        <Modal
          title="Booking Details"
          visible={isModalVisible}
          onCancel={handleCancel}
          footer={[
            <Button key="submit" type="primary" onClick={handleBooking} block>
              Release Tickets
            </Button>
          ]}
        >
          <Alert
            message={
              <div
                style={{
                  fontSize: '14px',
                  fontWeight: '300',
                  marginLeft: '5px'
                }}
              >
                Please note that each customer is limited to purchasing a
                maximum of 5 tickets.
              </div>
            }
            type="info"
            showIcon
          />
          <div style={{ marginBottom: '16px' }}>
            <div
              style={{
                display: 'flex',
                alignContent: 'center',
                gap: '4px',
                padding: '5px 0px 5px 0px'
              }}
            >
              Buyer's Email:
              <Tooltip
                placement="top"
                title="Please note that the provided email address will be used for receiving the e-ticket and confirmation."
              >
                <InfoCircleOutlined />
              </Tooltip>
            </div>
            <Input
              value={buyerEmail}
              onChange={(event) => setBuyerEmail(event.target.value)}
            />
          </div>
          <div style={{ paddingTop: '15px' }}>
            Number of Tickets:{' '}
            <InputNumber
              min={1}
              max={5}
              value={numTickets}
              onChange={setNumTickets}
            />
          </div>
          <Divider style={{ margin: '30px 0px 15px 0px' }} />
          <Row gutter={[16, 16]}>
            <Col xs={12}>Total Price:</Col>
            <Col xs={12} style={{ textAlign: 'right' }}>
              ${calculateTotalPrice(numTickets, event.ticketPrice)}
            </Col>
          </Row>
          <Divider style={{ margin: '15px 0px 15px 0px' }} />
          <Alert
            message={
              <div
                style={{
                  fontSize: '14px',
                  fontWeight: '300',
                  marginLeft: '5px'
                }}
              >
                Please note that you are helping a potential buyer to purchase.
                Therefore, ensure that the buyer has paid before releasing the
                ticket.
              </div>
            }
            type="warning"
            showIcon
            style={{ margin: '8px 0px 16px 0px' }}
          />
        </Modal>
      </Content>
    </Layout>
  )
}