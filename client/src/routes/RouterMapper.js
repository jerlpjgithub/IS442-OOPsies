/* Customer */
import LoginPage from '../pages/LoginPage';
import RegisterPage from '../pages/customer/RegisterPage';
import HomePage from '../pages/customer/HomePage';
import ProfilePage from '../pages/customer/ProfilePage';
import BookingPage from '../pages/customer/BookingPage';

/* Event Manager */
import { HomePage as EventManagerHomePage } from '../pages/event-manager/HomePage';

/* Ticketing Officer */
import { HomePage as TicketingOfficerHomePage } from '../pages/ticketing-officer/HomePage';

/* Ideally, all the new path and elements should be here */
export const USER_TYPES = {
  CUSTOMER: "customer",
  EVENT_MANAGER: "event-manager",
  TICKETING_OFFICER: "ticket-officer",
};

/* By adding routes here, it will be simply localhost:8000/login */
export const CUSTOMER_ROUTES= [
  { path: "/login", element: <LoginPage />, isPrivate: false },
  { path: "/register", element: <RegisterPage />, isPrivate: false },
  { path: "/home", element: <HomePage />, isPrivate: true },
  { path: "/profile", element: <ProfilePage />, isPrivate: true },
  { path: "/booking/:id", element: <BookingPage />, isPrivate: true },
];

/* Temporarily toggle isPrivate false until we are capable to check for authorisation based on jwt */
/* Eg. By adding routes here, it will be simply localhost:8000/event-manager/home */
export const EVENT_MANAGER_ROUTES = [
  { path: "/home", element: <EventManagerHomePage />, isPrivate: false },
];

/* Eg. By adding routes here, it will be simply localhost:8000/ticketing-officer/home */
export const TICKETING_OFFICER_ROUTES = [
  { path: "/home", element: <TicketingOfficerHomePage />, isPrivate: false },
];