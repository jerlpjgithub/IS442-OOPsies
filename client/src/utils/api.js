import axios from 'axios'

// URLS
const BASE_URL = 'http://localhost:8080/api'

// Login the user
export async function loginUser(data) {
  try {
    const response = await axios.post(`${BASE_URL}/auth/signin`, data)
    return response.data
  } catch (error) {
    throw error
  }
}

// Get User Information
export async function getUserData(id) {
  try {
    const response = await axios.get(`${BASE_URL}/user/${id}`)
    return response.data
  } catch (error) {
    throw error
  }
}

export async function updateUser(id, body) {
  try {
    const response = await axios.post(`${BASE_URL}/user/${id}`, body)
    return response.data
  } catch (error) {
    throw error
  }
}

/* Booking Related */
export async function retrieveBookingByUserId(user_id) {
  try {
    const response = await axios.get(`${BASE_URL}/booking/get/${user_id}`)
    return response.data
  } catch (error) {
    throw error
  }
}

/* Ticket Related */
export async function retrieveTicketByBookingId(booking_id) {
  try {
    const response = await axios.get(
      `${BASE_URL}/ticket/get/booking/${booking_id}`
    )
    return response.data
  } catch (error) {
    throw error
  }
}

export async function cancelBooking(booking_id) {
  try {
    const response = await axios.post(
      `${BASE_URL}/booking/refund/${booking_id}`
    )
    return response.data
  } catch (error) {
    throw error
  }
}

/* Event Related */

export async function createEvent(body) {
  try {
    const response = await axios.post(`${BASE_URL}/event/create`, body);
    return response.data;

  } catch (error) {
    throw error;
  }
}

export async function getAllEvents() {
  try {
    const response = await axios.get(
      `${BASE_URL}/event/get/all`
    )
    return response.data.data
  } catch (error) {
    throw error
  }
}

export async function updateEvent(eventId, body) {
  try {
    const response = await axios.put(`${BASE_URL}/event/update/${eventId}`, body);
    return response;
  } catch (error) {
    throw error;
  }
}

export async function cancelEvent(eventId) {
  try {
    const response = await axios.post(`${BASE_URL}/event/cancel/${eventId}`);
    return response;
  } catch (error) {
    throw error;
  }
}

export async function getManagedEventsById(managerId) {
  try {
    const response = await axios.get(
      `${BASE_URL}/event/get/all/${managerId}`
    )
    return response.data.data
  } catch (error) {
    throw error
  }
}

export async function getEvent(eventId) {
  try {
    const response = await axios.get(
      `${BASE_URL}/event/get/${eventId}`
    )
    return response.data.data
  } catch (error) {
    throw error
  }
}