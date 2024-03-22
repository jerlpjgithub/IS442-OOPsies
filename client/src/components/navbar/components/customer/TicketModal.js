import React from 'react'
import { Modal } from 'antd';
import { TicketCard } from './TicketCard';

export const TicketModal = (props) => {
  /*
    Boilerplate code for TicketModal
    1. Receives the booking ID from the BookingCard
    2. Fetches the ticket details associated to each booking
    3. Displays the ticket details in a modal
  */
  const { isModalOpen, onClose } = props;

  const handleClose = () => {
    console.log("triggered");
    onClose(null);
  }

  return (
    <Modal
      title="Your E-tickets"
      open={isModalOpen}
      onCancel={handleClose}
      width={550}
      footer={null}
    >
      <TicketCard />
      <TicketCard />
      <TicketCard />
      <TicketCard />
    </Modal>
  )
}
