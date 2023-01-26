import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class User {
    private static int memberDiscount = Integer.parseInt(ProgramManager.getPr().getProperty("discount-percentage"));
    private String hash;
    private String username;
    private boolean admin;
    private boolean member;
    private static HashMap<String, User> users= new HashMap<String,User>();
    private static ArrayList<String> usernames = new ArrayList<String>();
    private ArrayList<Ticket> tickets = new ArrayList<Ticket>();

    public User(String username, String hash, String member, String admin){
        this.username = username;
        this.hash = hash;
        this.admin = admin.equals("true");
        this.member = member.equals("true");
    }
    public static ArrayList<String> getUsernames(){
        return usernames;
    }
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isMember() {
        return member;
    }

    public void setMember(boolean member) {
        this.member = member;
    }

    public static void setUsers(HashMap<String, User> users) {
        User.users = users;
    }

    public static HashMap<String,User> getUsers(){
        return users;
    }
    public static String hashPassword(String password) {
        byte[] bytesOfPassword = password.getBytes(StandardCharsets.UTF_8);
        byte[] md5Digest = new byte[0];
        try {
            md5Digest = MessageDigest.getInstance("MD5").digest(bytesOfPassword);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return Base64.getEncoder().encodeToString(md5Digest);
    }

    public static int getMemberDiscount() {
        return memberDiscount;
    }

    public static void setMemberDiscount(int memberDiscount) {
        User.memberDiscount = memberDiscount;
    }

    public static void setUsernames(ArrayList<String> usernames) {
        User.usernames = usernames;
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }
}
