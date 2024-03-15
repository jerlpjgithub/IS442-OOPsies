import { Routes, Route } from 'react-router-dom';
import { CUSTOMER_ROUTES, EVENT_MANAGER_ROUTES, TICKETING_OFFICER_ROUTES } from './RouterMapper';
import { PrivateRoute } from "./PrivateRoute";

/* To add new routes, it should be done through RouterMapper.js instead */
export const AppRouter = () => {
  const renderRoutes = (routes, privateRouting, prefix = '') => {
    return routes
      .filter(route => route.isPrivate === privateRouting)
      .map((route, index) => (
        <Route key={index} path={`${prefix}${route.path}`} element={route.element} />
      ));
  };

  return (
    <Routes>
      {renderRoutes(CUSTOMER_ROUTES, false)}
      {renderRoutes(EVENT_MANAGER_ROUTES, false, '/event-manager')}
      {renderRoutes(TICKETING_OFFICER_ROUTES, false, '/ticketing-officer')}
      <Route element={<PrivateRoute />}>
        {renderRoutes(CUSTOMER_ROUTES, true)}
        {renderRoutes(EVENT_MANAGER_ROUTES, true, '/event-manager')}
        {renderRoutes(TICKETING_OFFICER_ROUTES, true, '/ticketing-officer')}
      </Route>
    </Routes>
  );
};
