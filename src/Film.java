

import java.util.ArrayList;
import java.util.HashMap;

public class Film {
    private String movieName;
    private String videoPath;
    private int duration;
    private static HashMap<String, Film> films = new HashMap<String,Film>();
    private ArrayList<String> halls = new ArrayList<String>();
    public Film(String name, String videoPath, int duration){
        movieName = name;
        this.videoPath = videoPath;
        this.duration = duration;
    }
    public static HashMap<String, Film> getFilms(){
        return films;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public static void setFilms(HashMap<String, Film> films) {
        Film.films = films;
    }

    public ArrayList<String> getHalls() {
        return halls;
    }
}