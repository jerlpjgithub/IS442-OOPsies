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
  Statistic,
  Tooltip,
  Input
} from 'antd'
import { images } from '../imageloader'
import {
  getEvent,
  validateTicket,
  redeemTicket,
  createOnsiteBooking
} from '../../utils/api'
import { parseToReadableDate, parseToReadableTime } from '../../utils/methods'

const { Content } = Layout
const { Title, Paragraph } = Typography

export const EventPage = () => {
  const user = JSON.parse(localStorage.getItem('authUser'))
  const userId = user.id
  const [isValidateModalVisible, setIsValidateModalVisible] = useState(false)
  const [isRedeemModalVisible, setIsRedeemModalVisible] = useState(false)
  const [isModalVisible, setIsModalVisible] = useState(false)
  const [ticketId, setTicketId] = useState(0)
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

  const showValidateModal = (name) => {
    setIsValidateModalVisible(true)
  }

  const showRedeemModal = (name) => {
    setIsRedeemModalVisible(true)
  }

  const handleRedeemOk = () => {
    try {
      redeemTicket(ticketId)

      console.log(ticketId)
      notification.success({
        message: 'Redeem Successful',
        description: `You have successfully redeemed the ticket.`
      })
    } catch (error) {
      notification.error({
        message: 'Redeem Unsuccessful',
        description: `Your attempt to redeem the ticket was unsuccessful.`
      })
      throw error
    }
    setIsRedeemModalVisible(false)
  }

  const handleValidateOk = () => {
    try {
      validateTicket(ticketId)

      console.log(ticketId)
      notification.success({
        message: 'Validate Successful',
        description: `You have successfully validated the ticket.`
      })
    } catch (error) {
      notification.error({
        message: 'Validate Unsuccessful',
        description: `Your attempt to validate the ticket was unsuccessful.`
      })
      throw error
    }
    setIsValidateModalVisible(false)
  }

  const handleValidateCancel = () => {
    setIsValidateModalVisible(false)
  }

  const handleRedeemCancel = () => {
    setIsRedeemModalVisible(false)
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
        <Col
          style={{
            height: '90vh',
            background: colorBgContainer,
            padding: '24px',
            borderRadius: borderRadiusLG
          }}
        >
          <Row gutter={[16, 16]} style={{ height: '100%' }}>
            <Col xs={24} sm={24} md={12}>
              <Image
                src={images[Math.floor(Math.random() * images.length)]}
                alt={event.eventName}
                style={{
                  borderRadius: borderRadiusLG,
                  height: '430px',
                  width: '650px'
                }}
              />
            </Col>
            <Col xs={24} sm={24} md={12}>
              <Card
                title={<Title level={2}>{event.eventName}</Title>}
                style={{ borderRadius: borderRadiusLG }}
              >
                <Paragraph>
                  <strong>Location:</strong> {event.venue}
                </Paragraph>
                <Paragraph>
                  <strong>Date:</strong> {parseToReadableDate(event.dateTime)}
                </Paragraph>
                <Paragraph>
                  <strong>Time:</strong> {parseToReadableTime(event.dateTime)}
                </Paragraph>
                <Col xs={12}>
                  <Statistic title="Tickets Left" value={event.capacity} />
                </Col>
                <Col xs={12}>
                  <Statistic
                    title="Ticket Price"
                    value={event.ticketPrice}
                    prefix="$"
                  />
                </Col>
                <Col xs={12}>
                  <div style={{ fontSize: '20px' }}>
                    <Statistic
                      title="Cancellation Fee"
                      value={event.cancellationFee}
                      prefix="$"
                      formatter={(value) => (
                        <div style={{ fontSize: '10px' }}>{value}</div>
                      )}
                    />
                  </div>
                </Col>
                <div
                  style={{
                    marginTop: '24px',
                    display: 'flex',
                    justifyContent: 'center'
                  }}
                >
                  <Row gutter={16}>
                    <Col>
                      <Button type="primary" onClick={showValidateModal}>
                        Validate Ticket
                      </Button>
                    </Col>
                    <Col>
                      <Button type="primary" onClick={showRedeemModal}>
                        Redeem Ticket
                      </Button>
                    </Col>
                    <Col>
                      <Button type="primary" onClick={showModal}>
                        Buy Ticket
                      </Button>
                    </Col>
                  </Row>
                </div>
                <Modal
                  title="Validate Ticket"
                  visible={isValidateModalVisible}
                  onOk={handleValidateOk}
                  onCancel={handleValidateCancel}
                >
                  <Typography.Title level={4}>
                    You are validating a ticket to {event.eventName}.
                  </Typography.Title>
                  <Divider />
                  <Typography.Title level={5}>Ticket Details</Typography.Title>
                  Ticket ID:{' '}
                  <InputNumber
                    value={ticketId}
                    onChange={setTicketId}
                    style={{ width: '70%' }}
                  />
                  <p>Are you sure you want to validate the ticket?</p>
                </Modal>
                <Modal
                  title="Redeem Ticket"
                  visible={isRedeemModalVisible}
                  onOk={handleRedeemOk}
                  onCancel={handleRedeemCancel}
                >
                  <Typography.Title level={4}>
                    You are helping to redeem a ticket to {event.eventName}.
                  </Typography.Title>
                  <Divider />
                  <Typography.Title level={5}>Ticket Details</Typography.Title>
                  Ticket ID:{' '}
                  <InputNumber
                    value={ticketId}
                    onChange={setTicketId}
                    style={{ width: '70%' }}
                  />
                  <p>Are you sure you want to redeem the ticket?</p>
                </Modal>
              </Card>
            </Col>
          </Row>
        </Col>
        <Modal
          title="Booking Details"
          visible={isModalVisible}
          onCancel={handleCancel}
          footer={[
            <Button key="back" onClick={handleCancel}>
              Return
            </Button>,
            <Button key="submit" type="primary" onClick={handleBooking}>
              Confirm Purchase
            </Button>
          ]}
        >
          <Typography.Title level={4}>
            You are helping a potential buyer to buy tickets to{' '}
            {event.eventName}!
          </Typography.Title>
          <div style={{ marginBottom: '16px' }}>
            Buyer's email
            <Input
              value={buyerEmail}
              onChange={(event) => setBuyerEmail(event.target.value)}
            />
          </div>
          <div>
            Number of Tickets:{' '}
            <InputNumber
              min={1}
              max={5}
              value={numTickets}
              onChange={setNumTickets}
            />
          </div>
          <br />
          Please understand that you are only able to purchase up to 5 tickets
          for each customer.
          <Divider />
          <Typography.Title level={5}>Payment Details</Typography.Title>
          <Row gutter={[16, 16]}>
            <Col xs={12}>Total Price:</Col>
            <Col xs={12} style={{ textAlign: 'right' }}>
              ${calculateTotalPrice(numTickets, event.ticketPrice)}
            </Col>
          </Row>
          <Tooltip>
            <strong>Remember to get the customer to pay first!</strong>
          </Tooltip>
        </Modal>
      </Content>
    </Layout>
  )
}
