import { useAuth } from "../context/AuthContext.js";
import { Outlet, Navigate } from 'react-router-dom';

export const PrivateRoute = () => {
    const { isAuthenticated } = useAuth();

    return isAuthenticated ? (
        <>
            <Outlet />
        </>
    ) : (
        <Navigate to="/login" />
    )
}