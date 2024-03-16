import { useAuth } from "../context/AuthContext";
import { Outlet, Navigate } from "react-router-dom";

export const OfficerRoute = () => {
  const { isAuthenticated, authUser } = useAuth();

  // Check if the user is authenticated and has the ROLE_OFFICER role
  return isAuthenticated && authUser["roles"].includes("ROLE_OFFICER") ? (
    <Outlet />
  ) : (
    <Navigate to="/unauthorisedpage" />
  );
};
