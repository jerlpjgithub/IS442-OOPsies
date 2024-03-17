import { Routes, Route } from "react-router-dom";
import {
  PUBLIC_ROUTES,
  PRIVATE_ROUTES,
  EVENT_MANAGER_ROUTES,
  TICKETING_OFFICER_ROUTES,
  ERROR_ROUTES
} from "./RouterMapper";
import { PublicRoute } from "./PublicRoute";
import { PrivateRoute } from "./PrivateRoute";
import { ManagerRoute } from "./ManagerRoute";
import { OfficerRoute } from "./OfficerRoute";
import Navbar from '../components/navbar/Navbar';
import Footer from '../components/footer/Footer';

/* To add new routes, it should be done through RouterMapper.js instead */
export const AppRouter = () => {
  const renderRoutes = (routes, prefix = "", isPublic = false) => {
    return routes.map((route, index) => (
      <Route
        key={index}
        path={`${prefix}${route.path}`}
        element={
          isPublic ? 
          route.element :
          <>
            <Navbar />
            {route.element}
            <Footer />
          </>
        }
      />
    ));
  };

  return (
    <Routes>
      {/* Public Routes Processed here */}
      <Route element={<PublicRoute strict={true} />}>
        {renderRoutes(PUBLIC_ROUTES, "", true)};
      </Route>

      {/* Private Routes Processed here (Authentication required) */}
      <Route path="/" element={<PrivateRoute />}>
        {renderRoutes(PRIVATE_ROUTES)};
      </Route>

      {/* Manager Routes processed here (Authentication & role required) */}
      <Route path="/" element={<ManagerRoute />}>
        {renderRoutes(EVENT_MANAGER_ROUTES, "/event-manager")};
      </Route>

      {/* Ticketing Officer processed here (Authentication & role required) */}
      <Route path="/" element={<OfficerRoute />}>
        {renderRoutes(TICKETING_OFFICER_ROUTES, "/ticketing-officer")}
      </Route>

      {/* Routes that are not found */}
      {renderRoutes(ERROR_ROUTES)}
    </Routes>
  );
};