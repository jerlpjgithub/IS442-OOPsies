import { Routes, Route } from 'react-router-dom';
import { lazy, Suspense } from 'react';

// public routes
import LoginPage from '../pages/LoginPage';
import RegisterPage from '../pages/RegisterPage';

// private routes
import HomePage from '../pages/HomePage';

// admin routes

export const AppRouter = () => {
    return (
        <Routes>
            <Route path='/login' element={<LoginPage />} />
            <Route path='/register' element={<RegisterPage />} />
            <Route path='/home' element={<HomePage />} />
        </Routes>
    );
}