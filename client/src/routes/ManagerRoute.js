import { useAuth } from "../context/AuthContext";
import { Outlet, Navigate } from "react-router-dom";

export const ManagerRoute = () => {
  const { isAuthenticated, authUser } = useAuth();

  // Check if the user is authenticated and has the ROLE_MANAGER role
  return isAuthenticated && authUser["roles"].includes("ROLE_MANAGER") ? (
    <Outlet />
  ) : (
    <Navigate to="/pagenotfound" />
  );
};
