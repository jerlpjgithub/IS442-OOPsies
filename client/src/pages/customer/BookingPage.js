import React, { useEffect, useState } from 'react'
import { useLocation } from 'react-router-dom'
import { Typography, Menu } from 'antd'
import {
  StopOutlined,
  HistoryOutlined,
  DashboardOutlined,
  EyeOutlined
} from '@ant-design/icons'

import { cancelBooking, retrieveBookingByUserId } from '../../utils/api'
import { BookingCard } from '../../components/navbar/components/customer/BookingCard'
import { TicketModal } from '../../components/navbar/components/customer/TicketModal'

const { Text } = Typography

const BookingPage = () => {
  const location = useLocation()
  const user_id = location.pathname.split('/')[2]

  const [selectedMenuItem, setSelectedMenuItem] = useState('all')
  const [selectedID, setSelectedID] = useState(null)

  const [loading, setLoading] = useState(false)
  const [bookings, setBookings] = useState([])

  const [filteredBookings, setFilteredBookings] = useState([])

  const items = [
    {
      key: 'all',
      label: 'All',
      icon: <EyeOutlined />
    },
    {
      key: 'upcoming',
      label: 'Upcoming',
      icon: <DashboardOutlined />
    },
    {
      key: 'past',
      label: 'Past',
      icon: <HistoryOutlined />
    },
    {
      key: 'cancelled',
      label: 'Cancelled',
      icon: <StopOutlined />
    }
  ]

  const fetchBookingData = async () => {
    setLoading(true)
    const bookings = await retrieveBookingByUserId(user_id)

    setBookings(bookings.data)
    setFilteredBookings(bookings.data)
    setLoading(false)
  }

  const handleRefund = async (bookingID) => {
    const res = await cancelBooking(bookingID)

    /* TODO: Work out a more elegant way to check */
    if (res.status === 200 && res.message === 'refund processed successfully') {
      fetchBookingData()
    }
  }

  const filterBookings = () => {
    switch (selectedMenuItem) {
      /*
        Display all bookings
      */
      case 'all':
        setFilteredBookings(bookings)
        break
      /*
        1. Ensure that the booking is not cancelled by either event manager or user
        2. The event date is later than today
      */
      case 'upcoming':
        const filterCondition = (booking) => {
          return (
            !booking.event.eventCancelled &&
            new Date(booking.event.dateTime) > new Date() &&
            booking.cancelDate === null
          )
        }

        const upcomingBookings = bookings.filter((booking) =>
          filterCondition(booking)
        )

        setFilteredBookings(upcomingBookings)
        break
      /*
        1. Disregard if the booking has been cancelled
        2. The event date is later than today
      */
      case 'past':
        const pastBookings = bookings.filter((booking) => {
          return new Date(booking.event.dateTime) < new Date()
        })

        setFilteredBookings(pastBookings)
        break
      /*
        1. Ensure that the booking is not cancelled by either event manager or user
        2. The event date is earlier than today
      */
      case 'cancelled':
        setFilteredBookings(
          bookings.filter(
            (booking) =>
              booking.cancelDate !== null || booking.event.eventCancelled
          )
        )
        break
      default:
        return bookings
    }
  }

  useEffect(() => {
    filterBookings()
  }, [selectedMenuItem])

  useEffect(() => {
    fetchBookingData()
  }, [])

  if (loading) return <div>Loading...</div> // Refactor this to a more elegant loading spinner

  return (
    <div
      style={{
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        height: '100%'
      }}
    >
      <div>
        <Text
          style={{ fontSize: '24px', fontWeight: 'bold', marginTop: '15px' }}
        >
          Manage My Bookings
        </Text>
        <div>
          <Menu
            onClick={(e) => {
              setSelectedMenuItem(e.key)
            }}
            selectedKeys={[selectedMenuItem]}
            mode="horizontal"
            items={items}
          />
        </div>
        <div style={{ width: '850px' }}>
          {filteredBookings.length > 0 ? (
            filteredBookings.map((booking) => (
              <BookingCard
                key={booking.id}
                booking={booking}
                setSelectedID={setSelectedID}
                handleRefund={handleRefund}
              />
            ))
          ) : (
            <div>No bookings found</div>
          )}
        </div>
        {selectedID !== null && (
          <TicketModal
            isModalOpen={selectedID !== null}
            bookingID={selectedID}
            onClose={setSelectedID}
          />
        )}
      </div>
    </div>
  )
}

export default BookingPage
