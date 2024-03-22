import axios from 'axios';

// URLS
const BASE_USER_URL = "http://localhost:8080/api";

// Login the user
export async function loginUser(data) {
    try {
        const response = await axios.post(`${BASE_USER_URL}/login`, data);
        return response.data;
    } catch (error) {
        throw error;
    }
}

// Get User Information
export async function getUserData(id) {
    try {
        const response = await axios.get(`${BASE_USER_URL}/user/${id}`);
        return response.data;
    } catch (error) {
        throw error;
    }
}

export async function updateUser(id, body) {
    try {
        const response = await axios.put(`${BASE_USER_URL}/user/${id}`, body);
        return response.data;
    } catch (error) {
        throw error;
    }
}

/* Booking Related */
export async function retrieveBookingByUserId(user_id) {
  try {
    const response = await axios.get(`${BASE_USER_URL}/booking/get/${user_id}`);

    return response.data;
  } catch (error) {
    throw error;
  }
}

