import java.util.ArrayList;
import java.util.Random;

public class Ticket {
    private String referenceNumber;
    private String owner;
    private String film;
    private String hall;
    private int row;
    private int column;
    private static ArrayList<Ticket> tickets = new ArrayList<Ticket>();
    private boolean clubMemberDiscount;
    private int price;
    public Ticket(String owner, String film, String hall, int price,int row, int column, boolean member){
        this.row = row;
        this.column = column;
        this.owner = owner;
        this.film = film;
        this.hall = hall;
        this.price = price;
        this.clubMemberDiscount = member;
        Random random = new Random();
        String s = "";
        for(int i = 0; i < 7; i++)
            s += DetailsStage.getRandomString().charAt(random.nextInt(DetailsStage.getRandomString().length()));
        referenceNumber = s;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getFilm() {
        return film;
    }

    public void setFilm(String film) {
        this.film = film;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public static ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public static void setTickets(ArrayList<Ticket> tickets) {
        Ticket.tickets = tickets;
    }

    public boolean isClubMemberDiscount() {
        return clubMemberDiscount;
    }

    public void setClubMemberDiscount(boolean clubMemberDiscount) {
        this.clubMemberDiscount = clubMemberDiscount;
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

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
}
