-- Previously, we were using TIMESTAMP in the date column in workouts. This has the problem that it drops timezone
-- information. For instance, storing "11/02/2020 5PM +3 GMT" would become "11/02/2020 5PM", which is the equivalent
-- of storing a LocalDateTime

-- We want to adjust the current date column to use TIMESTAMP WITH TIME ZONE, which coverts dates into UTC to store
-- them. In the above example storing "11/02/2020 5PM +3 GMT" would become "11/02/2020 2PM UTC".

-- We have been converting to UTC time in the application level, but we want to play it safe and ensure the db
-- is storing the correct data. Also, this would open up functionality of a user being able to add a workout
-- that is in a different time zone to their normal one, and display it appropriately in the UI.

-- The data migration itself is handled in V6__MakeWorkoutsTimeZoneAware.kt

ALTER TABLE users
    ADD COLUMN timezone text NOT NULL default 'Australia/Adelaide';

ALTER TABLE workouts
    ALTER COLUMN date type TIMESTAMP WITH TIME ZONE;

ALTER TABLE workouts
    ADD COLUMN timezone text;