import React, { useState, useEffect } from 'react'
import {
  Layout,
  Breadcrumb,
  Button,
  List,
  Input,
  Modal,
  Typography,
  notification,
  Divider,
  InputNumber,
  Alert,
  Tooltip,
  Pagination
} from 'antd'
import { InfoCircleOutlined } from '@ant-design/icons'
import { Link } from 'react-router-dom'
import { images } from '../imageloader'
import { getAllEvents, validateAndRedeemTicket } from '../../utils/api'
import { parseToReadableDate, parseToReadableTime } from '../../utils/methods'

const { Content } = Layout

export const EventsPage = () => {
  const [events, setEvents] = useState([])

  useEffect(() => {
    const fetchEvents = async () => {
      try {
        const response = await getAllEvents()
        setEvents(response)
      } catch (error) {
        console.error('Error fetching events:', error)
      }
    }

    fetchEvents()
  }, [])

  const [currentPage, setCurrentPage] = useState(1)
  const [eventsPerPage] = useState(5)
  const [searchTerm, setSearchTerm] = useState('')
  const [isValidateModalVisible, setIsValidateModalVisible] = useState(false)
  const [eventName, setEventName] = useState('')
  const [ticketId, setTicketId] = useState(0)

  // Doing validation to ensure cancelled or events that are over do not get rendered
  const filteredEvents = events.filter(
    (event) =>
      (!event.eventCancelled && new Date(event.dateTime) > new Date()) &&
      event.eventName.toLowerCase().includes(searchTerm.toLowerCase())
  )

  const indexOfLastEvent = currentPage * eventsPerPage
  const indexOfFirstEvent = indexOfLastEvent - eventsPerPage
  const currentEvents = filteredEvents.slice(
    indexOfFirstEvent,
    indexOfLastEvent
  )

  const paginate = (page) => {
    setCurrentPage(page)
  }

  const handleSearch = (event) => {
    setSearchTerm(event.target.value)
    setCurrentPage(1)
  }

  const showValidateModal = (name) => {
    setEventName(name)
    setIsValidateModalVisible(true)
  }

  const handleValidateOk = async () => {
    try {
      const response = await validateAndRedeemTicket(ticketId)

      const { data, message } = response

      if (data) {
        notification.success({
          message: 'Redemption Successful',
          description: 'The ticket is valid and successfully redeemed.'
        })
      } else {
        notification.warning({
          message: 'Redemption Unsuccessful',
          description: message
        })
      }
    } catch (error) {
      notification.error({
        message:
          'We might have run into an error while redeeming the ticket. Please try again later!',
        description: error.message
      })
    }
    setIsValidateModalVisible(false)
  }

  const handleValidateCancel = () => {
    setIsValidateModalVisible(false)
  }

  return (
    <Layout
      style={{
        height: '100vh',
        width: '100%',
        display: 'flex',
        flexDirection: 'column'
      }}
    >
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
          <Breadcrumb.Item>Events</Breadcrumb.Item>
        </Breadcrumb>
        <div style={{ background: '#fff', minHeight: 280, padding: 24 }}>
          <Input.Search
            placeholder="Search for events"
            onChange={handleSearch}
            style={{ marginBottom: '20px' }}
          />
          <List
            itemLayout="horizontal"
            dataSource={currentEvents}
            renderItem={(event) => (
              <List.Item
                style={{
                  display: 'flex',
                  justifyContent: 'space-between',
                  alignItems: 'flex-end'
                }}
                actions={[
                  <Button
                    type="primary"
                    onClick={() => {
                      showValidateModal(event.eventName)
                    }}
                  >
                    Redeem Ticket
                  </Button>,
                  <Link to={`/ticketing-officer/event/${event.id}`}>
                    <Button type="primary">Onsite Purchase</Button>
                  </Link>
                ]}
              >
                <List.Item.Meta
                  avatar={
                    <img
                      alt={event.eventName}
                      src={images[Math.floor(Math.random() * images.length)]}
                      style={{
                        width: '200px',
                        height: '100px',
                        objectFit: 'cover'
                      }}
                    />
                  }
                  title={
                    <Link to={`/event/${event.id}`}>{event.eventName}</Link>
                  }
                  description={
                    <>
                      <div>
                        Date:{' '}
                        {event.dateTime
                          ? parseToReadableDate(event.dateTime)
                          : 'Undated'}
                      </div>
                      <div>
                        This event starts at{' '}
                        {parseToReadableTime(event.dateTime)}.
                      </div>
                      <div>
                        {event.capacity} tickets left. Tickets are $
                        {event.ticketPrice}.{' '}
                      </div>
                    </>
                  }
                />
              </List.Item>
            )}
          />

          <Modal
            title="Redeem Ticket"
            visible={isValidateModalVisible}
            onCancel={handleValidateCancel}
            footer={
              <>
                <Divider />
                <Button block type="primary" onClick={handleValidateOk}>
                  Redeem Ticket{' '}
                </Button>
              </>
            }
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
                  You are currently helping to redeem a ticket for{' '}
                  <strong>{eventName}</strong>. Please ensure that the details
                  on the ticket are correct.
                </div>
              }
              type="info"
              showIcon
              style={{ margin: '8px 0px 16px 0px' }}
            />
            <Alert
              message={
                <div
                  style={{
                    fontSize: '14px',
                    fontWeight: '300'
                  }}
                >
                  1. <strong>No Re-Entry:</strong> "Once you exit the venue,
                  re-entry may not be permitted. Please plan accordingly and
                  ensure you have all necessary items with you.
                  <br /> <br /> 2. <strong>Security Screening:</strong> "Please
                  be advised that all attendees are subject to security
                  screening upon entry. Your cooperation is appreciated. <br />
                  <br />
                  3. <strong>Photography and Recording:</strong> "Unauthorized
                  photography, videography, or audio recording may not be
                  permitted during the event. Please respect the artist's rights
                  and refrain from such activities."
                </div>
              }
              type="warning"
              style={{ margin: '8px 0px 16px 0px' }}
            />
            <Divider />
            <Typography.Title level={5}>
              Ticket Details{' '}
              <Tooltip
                placement="top"
                title="The ticket ID can be found on the ticket itself or the ticket confirmation email."
              >
                <InfoCircleOutlined />
              </Tooltip>
            </Typography.Title>
            Ticket ID:{' '}
            <InputNumber
              value={ticketId}
              onChange={setTicketId}
              style={{ width: '70%' }}
            />
          </Modal>

          <Pagination
            current={currentPage}
            total={filteredEvents.length}
            pageSize={eventsPerPage}
            onChange={paginate}
            style={{ marginTop: '20px', textAlign: 'center' }}
          />
        </div>
      </Content>
    </Layout>
  )
}
