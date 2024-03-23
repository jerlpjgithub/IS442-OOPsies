import React, { useState, useEffect } from 'react'
import { Modal, Empty } from 'antd'

import { TicketCard } from './TicketCard'
import { retrieveTicketByBookingId } from '../../../../utils/api'

export const TicketModal = (props) => {
  /*
    Boilerplate code for TicketModal
    1. Receives the booking ID from the BookingCard
    2. Fetches the ticket details associated to each booking
    3. Displays the ticket details in a modal
  */
  const { isModalOpen, onClose, bookingID } = props
  const [tickets, setTickets] = useState([])
  const [loading, setLoading] = useState(false)

  const handleClose = () => {
    console.log('triggered')
    onClose(null)
  }

  useEffect(() => {
    setLoading(true)
    const fetchTicketData = async () => {
      const tickets = await retrieveTicketByBookingId(bookingID)

      setTickets(tickets.data)
      setLoading(false)
    }

    fetchTicketData()
  }, [])

  return (
    <Modal
      title="Your E-tickets"
      open={isModalOpen}
      onCancel={handleClose}
      width={600}
      footer={null}
    >
      {loading ? (
        <div>Loading...</div>
      ) : (
        <div style={{ overflowY: 'auto', height: '100%', maxHeight: '50vh' }}>
          {tickets.length > 0 ? (
            tickets.map((ticket) => (
              <TicketCard key={ticket.id} ticket={ticket} />
            ))
          ) : (
            <Empty description="No tickets found" />
          )}
        </div>
      )}
    </Modal>
  )
}
