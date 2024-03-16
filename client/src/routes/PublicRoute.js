import PropTypes from "prop-types";
import { useAuth } from "../context/AuthContext.js";
import { Outlet, Navigate } from "react-router-dom";

export const PublicRoute = ({ strict }) => {
  const { isAuthenticated, authUser } = useAuth();

  // Function to determine the redirection path based on user roles
  const determineRedirectPath = () => {
    if (authUser['roles'].includes("ROLE_MANAGER")) {
      return "/event-manager/home";
    } else if (authUser['roles'].includes("ROLE_OFFICER")) {
      return "/ticketing-officer";
    } else {
      return "/home"; // Default redirect if no specific role is found
    }
  };

  // Redirect based on roles if authenticated and strict mode is enabled
  return isAuthenticated && strict ? (
    <Navigate to={determineRedirectPath()} />
  ) : (
    <Outlet />
  );
};

PublicRoute.propTypes = {
  strict: PropTypes.bool,
};
