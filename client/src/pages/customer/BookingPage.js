import React, { useEffect, useState } from 'react'
import { useLocation } from "react-router-dom";

import { cancelBooking, retrieveBookingByUserId } from '../../utils/api';
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
  const location = useLocation();
  const user_id = location.pathname.split("/")[2];

  const [selectedID, setSelectedID] = useState(null);
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(false);

  const fetchBookingData = async () => {
    setLoading(true);
    const bookings = await retrieveBookingByUserId(user_id);

    setBookings(bookings.data);
    setLoading(false);
  };

  const handleRefund = async (bookingID) => {
    const res = await cancelBooking(bookingID);

    /* TODO: Work out a more elegant way to check */
    if(res.status === 200 && res.message === "refund processed successfully") {
      fetchBookingData();
    }
  };

  useEffect(() => {
    fetchBookingData();
  }, []);


  if(loading) return <div>Loading...</div>; // Refactor this to a more elegant loading spinner

  return (
    <>
      <div>Booking History Page</div>
      <div style={{ width: "850px"}}>
        {
          bookings.length > 0 ? (
            bookings.map(booking => (
              <BookingCard
                key={booking.id}
                booking={booking}
                setSelectedID={setSelectedID}
                handleRefund={handleRefund}
              />
            ))
          ) : (
            <div>No bookings found</div>
          )
        }
      </div>
      {
        selectedID !== null && (
        <TicketModal
          isModalOpen={selectedID !== null}
          bookingID={selectedID}
          onClose={setSelectedID}
        />
        )
      }
    </>
  )
}

export default BookingPage;