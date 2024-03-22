import React, { useEffect, useState } from 'react'
import { useLocation } from "react-router-dom";

import { retrieveBookingByUserId } from '../../utils/api';
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

  useEffect(() => {
    const fetchBookingData = async () => {
      setLoading(true);
      const bookings = await retrieveBookingByUserId(user_id);

      setBookings(bookings.data);
      setLoading(false);
    };

    fetchBookingData();
  }, [user_id]);


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
              />
            ))
          ) : (
            <div>No bookings found</div>
          )
        }
      </div>
      <TicketModal
        isModalOpen={selectedID !== null}
        onClose={setSelectedID}
      />
    </>
  )
}

export default BookingPage;