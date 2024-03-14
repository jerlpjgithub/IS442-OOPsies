USE mydb;

INSERT INTO events (id, cancellation_fee, capacity, date_time, event_cancelled, event_name, managerid, ticket_price, venue)
VALUES
(1, 10.00, 100, '2024-03-15 09:00:00', 0, 'Tech Conference', 1, 50.00, 'Conference Center'),
(2, 5.00, 50, '2024-04-20 18:30:00', 0, 'Music Concert', 2, 25.00, 'Stadium'),
(3, 8.00, 80, '2024-05-10 10:00:00', 1, 'Art Exhibition', 1, 20.00, 'Art Gallery');
