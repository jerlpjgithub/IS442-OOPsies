import React, { useState, createContext, useEffect, useContext, useCallback } from "react";
import axios from 'axios';

// Set withCredentials globally if your backend relies on it for session or cookie handling
axios.defaults.withCredentials = true;

const AuthContext = createContext(null);

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
}

export const AuthProvider = ({ children }) => {
  const [authUser, setAuthUser] = useState(null);
  const [isAuthenticated, setAuthenticated] = useState(false);

  useEffect(() => {
    checkAuthStatus();
  }, []);

  const checkAuthStatus = async () => {
    try {
      // Ensure this endpoint correctly verifies the user's authentication status
      const response = await axios.get("http://localhost:8080/api/auth/status", {
        withCredentials: true // Ensure withCredentials is set if needed
      });
      if (response.status === 200 && response.data) { // Make sure the backend sends `isAuthenticated` status
        setAuthenticated(true);
        setAuthUser(response.data.email); // Adjust according to the actual response structure
      }
    } catch (error) {
      console.error("Authentication check failed:", error);
      setAuthenticated(false); // Ensure isAuthenticated is set to false if check fails
    }
  }

  const login = async (email, password) => {
    try {
      const response = await axios.post("http://localhost:8080/api/auth/signin", { email, password });
      if (response.data && response.status === 200) {
        setAuthUser(response.data); // Adjust according to your response structure
        setAuthenticated(true);
      }
      return response;
    } catch (error) {
      console.error("Login failed:", error);
      throw error;
    }
  };

  const logout = useCallback(async () => {
    try {
      await axios.post("http://localhost:8080/api/auth/logout");
      setAuthUser(null);
      setAuthenticated(false);
    } catch (error) {
      console.error("Logout failed:", error);
    }
  }, []);

  const refreshAccessToken = async () => {
    try {
      const response = await axios.post("http://localhost:8080/api/auth/refreshtoken");
    } catch (error) {
      console.log(error);
    }
  }

  // Refresh token function and axios interceptor remain unchanged
  axios.interceptors.response.use(
    response => response,
    async (error) => {
      const originalRequest = error.config;

      if (error.response.status === 401 && !originalRequest._retry) {
        originalRequest._retry = true;
        await refreshAccessToken();
        return axios(originalRequest);
      }

      return Promise.reject(error);
    }
  );

  return (
    <AuthContext.Provider value={{ authUser, isAuthenticated, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
