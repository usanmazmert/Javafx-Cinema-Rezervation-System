
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

public class Seat {
    private Ticket ticket;
    private String movie;
    private String hall;
    private int rowIndex;
    private int columnIndex;
    private String owner;
    private int price;
    public Seat(String movie, String hall, int rowIndex, int columnIndex, int price, String owner){
        this.movie = movie;
        this.hall = hall;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.price = price;
        this.owner = owner;
    }
    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
