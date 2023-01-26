import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;
import java.util.Random.*;

public class DetailsStage extends Application{
    private Text filmName = new Text("Film: " + TicketStage.getCurrentTicket().getFilm());
    private Text hallName = new Text("Hall: " + TicketStage.getCurrentTicket().getHall());
    private Text price = new Text("Price: " + TicketStage.getCurrentTicket().getPrice() + " TL");
    private Text seat = new Text("Seat: " + TicketStage.getCurrentTicket().getRow()+ "-" + TicketStage.getCurrentTicket().getColumn());
    private VBox vbox = new VBox(20);
    private static final String randomString = "qwertyuopasdfghjklizxcvbnmQWERTYUOPASDFGHJKLIZXCVBNM123456789";
    private Button back = new Button("< Back");
    private Text referanceNumber = new Text("Reference Number: " + TicketStage.getCurrentTicket().getReferenceNumber());
    private HBox hbox = new HBox(back);
    public void start(Stage stage){
        back.setOnAction(e -> {
            TicketStage ts = new TicketStage();
            ts.start(stage);
        });
        vbox.setPadding(new Insets(10));
        hbox.setPadding(new Insets(50,10,10,0));
        vbox.getChildren().addAll(filmName, hallName, price, seat, referanceNumber,hbox);
        Scene scene = new Scene(vbox);
        stage.setResizable(false);
        stage.setScene(scene);
    }
    public static String getRandomString(){
        return randomString;
    }
}
