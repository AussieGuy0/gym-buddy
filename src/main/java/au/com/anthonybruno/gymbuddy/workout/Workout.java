package au.com.anthonybruno.gymbuddy.workout;

import java.time.Duration;
import java.util.Date;

public class Workout {

    private final int id;
    private final Date date;
    private final Duration duration;
    private final String title;
    private final String description;

    public Workout(int id, Date date, Duration duration, String title, String description) {
        this.id = id;
        this.date = date;
        this.duration = duration;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Duration getDuration() {
        return duration;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
