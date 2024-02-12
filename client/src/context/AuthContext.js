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
  const [isAuthenticated, setAuthenticated] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token && token !== 'undefined') {
      setAuthenticated(true);
      setAuthUser(JSON.parse(token));
    }
  }, []);

  const login = async (email, password) => {
    try {
      const response = await axios.post("http://localhost:8080/api/auth/signin", {
        email: email,
        password: password,
      });
      setAuthUser(response.data);
      setAuthenticated(true);
      localStorage.setItem("token", JSON.stringify(response.data));
      return response.data;
    } catch (error) {
      console.log(error);
      throw error;
    }
  };

  const logout = useCallback(() => {
    setAuthUser(null);
    setAuthenticated(false);
    localStorage.removeItem("token");
  }, []);

  return (
    <AuthContext.Provider value={{ authUser, isAuthenticated, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
