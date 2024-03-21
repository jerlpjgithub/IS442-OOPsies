/* Customer */
import LoginPage from '../pages/LoginPage';
import RegisterPage from '../pages/customer/RegisterPage';
import HomePage from '../pages/customer/HomePage';
import ProfilePage from '../pages/customer/ProfilePage';
import BookingPage from '../pages/customer/BookingPage';
import Error404 from '../pages/error-pages/Error404';

/* Event Manager */
import { HomePage as EventManagerHomePage } from '../pages/event-manager/HomePage';
import { AddEventPage } from '../pages/event-manager/AddEventPage';

/* Ticketing Officer */
import { HomePage as TicketingOfficerHomePage } from '../pages/ticketing-officer/HomePage';

/* By adding routes here, it will be simply localhost:3000/login */
export const PUBLIC_ROUTES = [
  { path: "/login", element: <LoginPage />},
  { path: "/register", element: <RegisterPage />},
];

export const PRIVATE_ROUTES = [
  { path: "/home", element: <HomePage />},
  { path: "/profile/:id", element: <ProfilePage />},
  { path: "/booking/:id", element: <BookingPage />},
];

/* Temporarily toggle isPrivate false until we are capable to check for authorisation based on jwt */
/* Eg. By adding routes here, it will be simply localhost:3000/event-manager/home */
export const EVENT_MANAGER_ROUTES = [
  { path: "/home", element: <EventManagerHomePage />},
  { path: "/addevent", element: <AddEventPage />},
];

/* Eg. By adding routes here, it will be simply localhost:3000/ticketing-officer/home */
export const TICKETING_OFFICER_ROUTES = [
  { path: "/home", element: <TicketingOfficerHomePage />},
];

/* Adding an ERROR_ROUTE to catch all pages that are not valid */
export const ERROR_ROUTES = [
  { path: "*", element: <Error404 />},
  { path: "/pagenotfound", element: <Error404 />}
]