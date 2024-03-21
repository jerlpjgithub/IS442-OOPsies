import React from 'react'
import { BookingCard } from '../../components/navbar/components/customer/BookingCard';

const BookingPage = () => {
  /* Boiler plate codes and thought processes */

  /* Based on the user ID, retrieves all of the available bookings [Endpoint] */
  /*
    [
      {id: 1, bookingDate: '01/01/2024', eventDate: '05/03/2024', eventName: 'Taylor Swift - My Era concert', numOfTickets: 4, venue: 'Marina Bay Sands'},
      ...
    ]
  */

  return (
    <>
      <div>Booking History Page</div>
      <div style={{ width: "850px"}}>
        <BookingCard />
        <BookingCard />
        <BookingCard />
        <BookingCard />
        <BookingCard />
      </div>
    </>
  )
}

export default BookingPage;