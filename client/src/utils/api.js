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

