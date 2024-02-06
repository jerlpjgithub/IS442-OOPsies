import React, { useState, createContext, useEffect, useContext, useCallback } from "react";
import axios from 'axios';

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
  const [isLoggedIn, setLoggedIn] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem("authToken");
    if (token) {
      setLoggedIn(true);
      setAuthUser(JSON.parse(token));
    }
  }, []);

  const login = async (email, password) => {
    try {
      const response = await axios.post("http://localhost:8080/login", {
        email: email,
        password: password,
      });
      console.log(response.data);
      setAuthUser(response.data.data);
      setLoggedIn(true);
      localStorage.setItem("authToken", JSON.stringify(response.data.data));
      return response.data;
    } catch (error) {
      console.log(error);
      throw error;
    }
  };

  const logout = useCallback(() => {
    setAuthUser(null);
    setLoggedIn(false);
    localStorage.removeItem("authToken");
  }, []);

  return (
    <AuthContext.Provider value={{ authUser, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
