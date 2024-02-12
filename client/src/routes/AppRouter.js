import { Routes, Route } from 'react-router-dom';
import { lazy, Suspense } from 'react';

// public pages
import LoginPage from '../pages/LoginPage';
import RegisterPage from '../pages/RegisterPage';

// private pages
import HomePage from '../pages/HomePage';

// admin pages

// Routes
import { PrivateRoute } from "./PrivateRoute";
import { PublicRoute } from './PublicRoute';

export const AppRouter = () => {
    return (
        <Routes>
            // Public Routes
            <Route element={<PublicRoute strict={true} />}>
                <Route path='/login' element={<LoginPage />} />
                <Route path='/register' element={<RegisterPage />} />
            </Route>
            // Private Routes
            <Route exact path={"/"} element={<PrivateRoute />}>
                <Route path='/home' element={<HomePage />} />
            </Route>
        </Routes>
    );
}