package au.com.anthonybruno.gymbuddy.workout;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.util.Date;

public class Workout {

    private final int id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final Date date;
    private final String title;
    private final String description;

    public Workout(@JsonProperty("id") int id, @JsonProperty("date") Date date, @JsonProperty("title") String title, @JsonProperty("description") String description) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
