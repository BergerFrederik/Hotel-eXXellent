// --import.sql is used to persist 3 hotel rooms into the database at startup

INSERT INTO hotel_rooms (room_number, room_size, has_minibar, occupied) VALUES (1, 'DOPPELZIMMER', 1, 0), (2, 'EINZELZIMMER', 1, 0), (3, 'SUITE', 0, 0);
