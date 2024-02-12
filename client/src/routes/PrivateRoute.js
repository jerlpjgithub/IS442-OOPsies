import { useAuth } from "../context/AuthContext.js";
import { Outlet, useNavigate } from 'react-router-dom';

export const PrivateRoute = () => {
    const { isAuthenticated } = useAuth();
    const navigate = useNavigate();
    return isAuthenticated ? (
        <>
            <Outlet />
        </>
    ) : (
        navigate("/login")
    )
}