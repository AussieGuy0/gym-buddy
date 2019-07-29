package dev.anthonybruno.gymbuddy.workout;

import dev.anthonybruno.gymbuddy.common.Repository;
import dev.anthonybruno.gymbuddy.util.IOUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkoutRepository extends Repository {

    public WorkoutRepository() {
        super("workouts");
    }


    public List<Workout> getWorkouts(int userId) {
        List<Workout> workouts = new ArrayList<>();
        ResultSet results = null;
        try (Connection conn = db.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM " +  tableName + " WHERE user_id = ?")) {
            statement.setInt(1, userId);
            results = statement.executeQuery();
            while (results.next()) {
                Workout workout = new Workout(results.getInt("id"),
                        results.getDate("date"),
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
        try (Connection conn = db.getConnection();
             PreparedStatement statement = conn.prepareStatement("INSERT INTO " + tableName + "(user_id, title, description, date) VALUES (?,?,?,?) RETURNING id")) {
            statement.setInt(1, userId);
            statement.setString(2, workout.getTitle());
            statement.setString(3, workout.getDescription());
            statement.setDate(4, new Date(workout.getDate().getTime()));
            statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
