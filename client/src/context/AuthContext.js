import React, {
  useState,
  createContext,
  useEffect,
  useContext,
  useCallback,
} from "react";
import axios from "axios";

// Set withCredentials globally if your backend relies on it for session or cookie handling
axios.defaults.withCredentials = true;

const AuthContext = createContext(null);

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
};

export const AuthProvider = ({ children }) => {
  const [authUser, setAuthUser] = useState(null);
  const [isAuthenticated, setAuthenticated] = useState(false);

  useEffect(() => {
    checkAuthStatus();
  }, []);

  const checkAuthStatus = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/api/auth/status",
        {
          withCredentials: true,
        }
      );
      if (response.status === 200 && response.data) {
        setAuthenticated(true);
        setAuthUser(response.data.email);
      } else {
        setAuthUser(null);
        setAuthenticated(false);
      }
    } catch {
      // Attempt to refresh the token without logging errors to console
      refreshAccessToken().catch(() => {});
    }
  };

  const login = async (email, password) => {
    try {
      const response = await axios.post(
        "http://localhost:8080/api/auth/signin",
        { email, password }
      );
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

  const register = async (email, password, firstName, lastName) => {
    try {
      const response = await axios.post(
        "http://localhost:8080/api/auth/signup",
        { email, password, firstName, lastName }
      );
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
      await axios.post("http://localhost:8080/api/auth/refreshtoken");
    } catch {
      setAuthenticated(false);
      setAuthUser(null);
      // Silently handle the refresh token failure
    }
  };

  // Refresh token function and axios interceptor remain unchanged
  axios.interceptors.response.use(
    (response) => response,
    async (error) => {
      const originalRequest = error.config;
      if (error.response?.status === 401 && !originalRequest._retry) {
        originalRequest._retry = true;
        try {
          await refreshAccessToken();
          return axios(originalRequest);
        } catch {
          // Silently catch errors during the refresh token process
        }
      }
      // Return rejection silently without logging to console
      return Promise.reject({ ...error, _silent: true });
    }
  );

  return (
    <AuthContext.Provider
      value={{ authUser, isAuthenticated, login, logout, register }}
    >
      {children}
    </AuthContext.Provider>
  );
};
