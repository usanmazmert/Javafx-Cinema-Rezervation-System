
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Hall {
    private Film movie;
    private String hallName;
    private int pricePerSeat;
    private int row;
    private int column;
    private ArrayList<ArrayList<Seat>> seats = new ArrayList<ArrayList<Seat>>(row);

    public Hall(Film movie, String hallName, int pricePerSeat, int row, int column) {
        this.movie = movie;
        this.hallName = hallName;
        this.pricePerSeat = pricePerSeat;
        this.row = row;
        this.column = column;
    }

    public ArrayList<ArrayList<Seat>> getSeats(){
        return seats;
    }

    public Film getMovie() {
        return movie;
    }

    public void setMovie(Film movie) {
        this.movie = movie;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public int getPricePerSeat() {
        return pricePerSeat;
    }

    public void setPricePerSeat(int pricePerSeat) {
        this.pricePerSeat = pricePerSeat;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}