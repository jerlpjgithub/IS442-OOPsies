import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { AppRouter } from "./routes/AppRouter";

import { AuthProvider } from "./context/AuthContext";

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <AppRouter />
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
