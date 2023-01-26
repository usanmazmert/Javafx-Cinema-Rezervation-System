
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
public class ProgramManager {
    private static Properties pr = new Properties();

    /**
     * Gets the data from backup and creates the proper objects and collection types.
     * @param path
     * @throws IOException
     */
    public static void getData(String path) throws IOException{
        try {
            File file = new File(path);
            Scanner scnr = new Scanner(file);
            while (scnr.hasNext()) {
                String[] line = scnr.nextLine().replace("\n", "").split("\t");
                switch (line[0]) {
                    case "user":
                        User.getUsers().put(line[1], new User(line[1], line[2], line[3], line[4]));
                        User.getUsernames().add(line[1]);
                        break;
                    case "film":
                        Film.getFilms().put(line[1], new Film(line[1], line[2], Integer.parseInt(line[3])));
                        break;
                    case "hall":
                        Film.getFilms().get(line[1]).getHalls().add(line[2]);
                        Cinema.getHalls().put(line[2], new Hall(Film.getFilms().get(line[1]), line[2], Integer.parseInt(line[3]), Integer.parseInt(line[4]), Integer.parseInt(line[5])));
                        break;

                    case "seat":
                        for (int i = 0; i < Cinema.getHalls().get(line[2]).getRow(); i++) {
                            ArrayList<Seat> row = new ArrayList<Seat>();
                            Cinema.getHalls().get(line[2]).getSeats().add(row);
                        }
                        Seat seat = new Seat(line[1], line[2], Integer.parseInt(line[3]), Integer.parseInt(line[4]), Integer.parseInt(line[6]), line[5]);
                        if (!line[5].equals("null")) {
                            Ticket ticket = new Ticket(line[5], line[1], line[2], Integer.parseInt(line[6]), Integer.parseInt(line[3]) + 1, Integer.parseInt(line[4]) + 1, User.getUsers().get(line[5]).isMember());
                            Ticket.getTickets().add(ticket);
                            User.getUsers().get(line[5]).getTickets().add(ticket);
                            seat.setTicket(ticket);
                        }
                        Cinema.getHalls().get(line[2]).getSeats().get(Integer.parseInt(line[3])).add(seat);
                        break;
                }
            }
            scnr.close();
        }catch (FileNotFoundException ex){
            User.getUsers().put("admin", new User("admin", User.hashPassword("password"), "true", "true"));
            User.getUsernames().add("admin");
        }
        try {
            File ticketFile = new File("assets\\data\\tickets.dat");
            Scanner scnrT = new Scanner(ticketFile);
            while (scnrT.hasNext()) {
                String[] line = scnrT.nextLine().split("\t");
                for (Ticket ticket : Ticket.getTickets()) {
                    if (ticket.getOwner().equals(line[1]) && ticket.getHall().equals(line[2]) && ticket.getRow() == Integer.parseInt(line[3]) && ticket.getColumn() == Integer.parseInt(line[4])) {
                        ticket.setReferenceNumber(line[5]);
                        break;
                    }

                }
            }
            scnrT.close();
        }catch (FileNotFoundException ignored){}
    }

    /**
     * Gets the system properties by using Properties class.
     * @param path
     * @throws IOException
     */
    public static void readProperties(String path) throws IOException{
        FileInputStream fis = new FileInputStream(path);
        pr.load(fis);
    }

    /**
     * Changes the backup and ticket data
     * @param path
     */
    public static void writeData(String path){
        try {
            File outputFile = new File(path);
            FileWriter fw = new FileWriter(outputFile);
            for(User user: User.getUsers().values()){
                fw.write(String.format("user\t%s\t%s\t%s\t%s\n", user.getUsername(), user.getHash(), Boolean.toString(user.isMember()), Boolean.toString(user.isAdmin())));
            }
            for(Film film : Film.getFilms().values()){
                fw.write(String.format("film\t%s\t%s\t%d\n", film.getMovieName(), film.getVideoPath(), film.getDuration()));
                for(String hallName : film.getHalls()){
                    Hall hall = Cinema.getHalls().get(hallName);
                    fw.write(String.format("hall\t%s\t%s\t%d\t%d\t%d\n", film.getMovieName(), hall.getHallName(), hall.getPricePerSeat(), hall.getRow(), hall.getColumn()));
                    for(ArrayList<Seat> row : hall.getSeats()) {
                        for (Seat seat : row) {
                            fw.write(String.format("seat\t%s\t%s\t%d\t%d\t%s\t%d\n", film.getMovieName(), hall.getHallName(), seat.getRowIndex(), seat.getColumnIndex(), seat.getOwner(),seat.getPrice()));
                        }
                    }
                }
            }
            fw.close();
            File tickets = new File("assets\\data\\tickets.dat");
            FileWriter fwt = new FileWriter(tickets);
            for(Ticket ticket : Ticket.getTickets()){
                fwt.write(String.format("ticket\t%s\t%s\t%d\t%d\t%s\n", ticket.getOwner(), ticket.getHall(), ticket.getRow(), ticket.getColumn(), ticket.getReferenceNumber()));
            }
            fwt.close();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public static Properties getPr() {
        return pr;
    }

    public static void setPr(Properties pr) {
        ProgramManager.pr = pr;
    }
}
