INSERT IGNORE INTO events (id, event_name, manager_id, date_time, venue, cancel_date, capacity, cancellation_fee, ticket_price) VALUES
(1, 'Taylor Swift - Era 1989', (SELECT id FROM users WHERE email = 'eventManager@gmail.com'), DATE_ADD(CURDATE(), INTERVAL 7 DAY), 'Singapore Indoor Sports Hall', NULL, 2000, 100.0, 500.0),
(2, 'Ed Sheeran - Divide Tour', (SELECT id FROM users WHERE email = 'eventManager@gmail.com'), DATE_ADD(CURDATE(), INTERVAL 7 DAY), 'Singapore Indoor Sports Hall', NULL, 3000, 150.0, 750.0),
(3, 'Michael Jackson - This Is It', (SELECT id FROM users WHERE email = 'eventManager@gmail.com'), DATE_ADD(CURDATE(), INTERVAL 7 DAY), 'National Stadium', NULL, 4000, 200.0, 1000.0),
(4, 'TWICE - World Tour', (SELECT id FROM users WHERE email = 'eventManager@gmail.com'), DATE_ADD(CURDATE(), INTERVAL 7 DAY), 'National Stadium', NULL, 5000, 250.0, 1250.0),
(5, 'Eason Chan - Live Concert', (SELECT id FROM users WHERE email = 'eventManager@gmail.com'), DATE_ADD(CURDATE(), INTERVAL 7 DAY), 'Singapore Indoor Sports Hall', NULL, 6000, 300.0, 1500.0),
(6, 'Jazz Night 2024', (SELECT id FROM users WHERE email = 'eventManager@gmail.com'), '2024-07-01 10:00:00', 'Marina Bay Sands', NULL, 100, 20.0, 50.0),
(7, 'Rock Revolution', (SELECT id FROM users WHERE email = 'eventManager@gmail.com'), '2024-07-02 10:00:00', 'Esplanade Theatre', NULL, 200, 30.0, 60.0),
(8, 'Pop Fiesta', (SELECT id FROM users WHERE email = 'eventManager@gmail.com'), '2024-07-03 10:00:00', 'Singapore Indoor Stadium', NULL, 300, 40.0, 70.0),
(9, 'Classical Harmony', (SELECT id FROM users WHERE email = 'eventManager@gmail.com'), '2024-07-04 10:00:00', 'Victoria Theatre', NULL, 400, 50.0, 80.0),
(10, 'Electronic Euphoria', (SELECT id FROM users WHERE email = 'eventManager@gmail.com'), '2024-07-05 10:00:00', 'Zouk Singapore', NULL, 500, 60.0, 90.0),
(11, 'Hip Hop Hustle', (SELECT id FROM users WHERE email = 'eventManager@gmail.com'), '2024-07-06 10:00:00', 'The Star Theatre', NULL, 600, 70.0, 100.0),
(12, 'Reggae Rhythm', (SELECT id FROM users WHERE email = 'eventManager@gmail.com'), '2024-07-07 10:00:00', 'Gardens by the Bay', NULL, 700, 80.0, 110.0),
(13, 'Country Carnival', (SELECT id FROM users WHERE email = 'eventManager@gmail.com'), '2024-07-08 10:00:00', 'National Stadium Singapore', NULL, 800, 90.0, 120.0),
(14, 'Blues Blast', (SELECT id FROM users WHERE email = 'eventManager@gmail.com'), '2024-07-09 10:00:00', 'Suntec Singapore Convention & Exhibition Centre', NULL, 900, 100.0, 130.0),
(15, 'Metal Madness', (SELECT id FROM users WHERE email = 'eventManager@gmail.com'), '2024-07-10 10:00:00', 'Changi Exhibition Centre', NULL, 1000, 110.0, 140.0),
(16, 'Soul Serenade', (SELECT id FROM users WHERE email = 'eventManager@gmail.com'), '2024-07-11 10:00:00', 'Fort Canning Park', NULL, 1100, 120.0, 150.0),
(17, 'Punk Party', (SELECT id FROM users WHERE email = 'eventManager@gmail.com'), '2024-07-12 10:00:00', 'Sentosa Island', NULL, 1200, 130.0, 160.0),
(18, 'Folk Festival', (SELECT id FROM users WHERE email = 'eventManager@gmail.com'), '2024-07-13 10:00:00', 'The Float @ Marina Bay', NULL, 1300, 140.0, 170.0),
(19, 'R&B Revival', (SELECT id FROM users WHERE email = 'eventManager@gmail.com'), '2024-07-14 10:00:00', 'Resorts World Sentosa', NULL, 1400, 150.0, 180.0),
(20, 'Indie Invasion', (SELECT id FROM users WHERE email = 'eventManager@gmail.com'), '2024-07-15 10:00:00', 'Clarke Quay', NULL, 1500, 160.0, 190.0),
(21, 'Latin Love', (SELECT id FROM users WHERE email = 'eventManager@gmail.com'), '2024-07-16 10:00:00', 'The Ritz-Carlton, Millenia Singapore', NULL, 1600, 170.0, 200.0),
(22, 'Disco Delight', (SELECT id FROM users WHERE email = 'eventManager@gmail.com'), '2024-07-17 10:00:00', 'ArtScience Museum', NULL, 1700, 180.0, 210.0),
(23, 'Gospel Gathering', (SELECT id FROM users WHERE email = 'eventManager@gmail.com'), '2024-07-18 10:00:00', 'The Fullerton Hotel Singapore', NULL, 1800, 190.0, 220.0),
(24, 'Techno Triumph', (SELECT id FROM users WHERE email = 'eventManager@gmail.com'), '2024-07-19 10:00:00', 'Universal Studios Singapore', NULL, 1900, 200.0, 230.0),
(25, 'Opera Odyssey', (SELECT id FROM users WHERE email = 'eventManager@gmail.com'), '2024-07-20 10:00:00', 'The Shoppes at Marina Bay Sands', NULL, 2000, 210.0, 240.0);