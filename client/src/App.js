import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { AppRouter } from "./routes/AppRouter";
import { GoogleOAuthProvider } from "@react-oauth/google";
import Navbar from './components/navbar/Navbar';
import Footer from './components/footer/Footer';

import { AuthProvider } from "./context/AuthContext";

function App() {
  return (
    <GoogleOAuthProvider clientId={process.env.REACT_APP_GOOGLE_CLIENT_ID}>
      <AuthProvider>
        <BrowserRouter>
        <Navbar/>
          <AppRouter />
        <Footer/>
        </BrowserRouter>
      </AuthProvider>
    </GoogleOAuthProvider>
  );
}

export default App;
