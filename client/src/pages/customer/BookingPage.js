import React, { useState } from 'react'
import { BookingCard } from '../../components/navbar/components/customer/BookingCard';
import { TicketModal } from '../../components/navbar/components/customer/TicketModal';

const BookingPage = () => {
  /* Boiler plate codes and thought processes */

  /* Based on the user ID, retrieves all of the available bookings [Endpoint] */
  /*
    [
      {id: 1, bookingDate: '01/01/2024', eventDate: '05/03/2024', eventName: 'Taylor Swift - My Era concert', numOfTickets: 4, venue: 'Marina Bay Sands'},
      ...
    ]
  */
  /* This is to set the selected booking ID */
  const [selectedID, setSelectedID] = useState(null);

  return (
    <>
      <div>Booking History Page</div>
      <div style={{ width: "850px"}}>
        <BookingCard setSelectedID={setSelectedID}/>
        <BookingCard setSelectedID={setSelectedID}/>
      </div>
      <TicketModal
        isModalOpen={selectedID !== null}
        onClose={setSelectedID}
      />
    </>
  )
}

export default BookingPage;