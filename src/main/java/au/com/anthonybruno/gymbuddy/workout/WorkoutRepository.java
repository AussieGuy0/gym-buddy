package au.com.anthonybruno.gymbuddy.workout;

import au.com.anthonybruno.gymbuddy.Server;
import au.com.anthonybruno.gymbuddy.db.Database;
import au.com.anthonybruno.gymbuddy.util.IOUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkoutRepository {

    private static final Database db = Server.DATABASE;
    private static final String TABLE_NAME = "workouts";


    public List<Workout> getWorkouts(int userId) {
        List<Workout> workouts = new ArrayList<>();
        ResultSet results = null;
        try {
            results = db.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE user_id = ?",
                    (statement) -> {
                        try {
                            statement.setInt(0, userId);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
            while (results.next()) {
                Workout workout = new Workout(results.getInt("id"),
                        results.getDate("date_start"),
                        null,
                        results.getString("title"),
                        results.getString("description"));
                workouts.add(workout);
            }
            return workouts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeNoException(results);
        }

    }

    public Workout addWorkout(int userId, Workout workout) {
        return null;
    }
}
