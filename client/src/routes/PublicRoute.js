import PropTypes from 'prop-types';
import { useAuth } from "../context/AuthContext.js";
import { Outlet, Navigate } from 'react-router-dom';

export const PublicRoute = ({ strict }) => {
    const { isAuthenticated } = useAuth();

    return isAuthenticated && strict ? (
        <Navigate to="/" />
    ) : (
        <Outlet />
    );
}

PublicRoute.propTypes = {
    strict: PropTypes.bool,
};