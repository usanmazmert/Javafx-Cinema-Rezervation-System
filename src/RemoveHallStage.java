import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;

public class RemoveHallStage extends Application{
    private static Film currentFilm;
    private ObservableList<String> obl = FXCollections.observableArrayList(currentFilm.getHalls());
    private ComboBox<String> cb = new ComboBox<String>(obl);
    private Text welcome = new Text(String.format("Select the hall that you desire to remove from %s and then click OK.",currentFilm.getMovieName()));
    private Button back = new Button("< BACK");
    private Button ok = new Button("OK");
    private VBox vbox = new VBox();
    private HBox hbox1 = new HBox(10);
    private HBox hbox2 = new HBox(10);
    public void start(Stage stage){



        welcome.setTextAlignment(TextAlignment.CENTER);
        welcome.setFont(Font.font("Times New Roman", 20));



        back.setOnAction(e -> {
            FilmTrailerStage fts = new FilmTrailerStage();
            fts.start(stage);
        });


        ok.setOnAction(e -> okButton());


        cb.setPrefWidth(400);
        try {
            cb.setValue(obl.get(0));
        }catch(RuntimeException ignored){}


        hbox1.setPadding(new Insets(20,40,20,40));
        hbox1.getChildren().addAll(cb);
        hbox1.setAlignment(Pos.CENTER);



        hbox2.getChildren().addAll(back, ok);
        hbox2.setAlignment(Pos.CENTER);



        vbox.getChildren().addAll(welcome, hbox1,hbox2);
        vbox.setPadding(new Insets(10,20,10,20));
        vbox.setAlignment(Pos.CENTER);



        Scene scene = new Scene(vbox);
        stage.setWidth(750);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Deletes the selected hall from the list of the current film
     * Deletes bought tickets that are for that hall from the system
     */
    public void okButton(){
        String hall = cb.getValue();
        currentFilm.getHalls().remove(hall);
        Cinema.getHalls().remove(hall);
        obl.remove(hall);
        ArrayList<Ticket> delete = new ArrayList<Ticket>();
        for(Ticket ticket : Ticket.getTickets()){
            if(ticket.getHall().equals(hall)){
                delete.add(ticket);
            }
        }
        for (Ticket ticket : delete){
            Ticket.getTickets().remove(ticket);
            User.getUsers().get(ticket.getOwner()).getTickets().remove(ticket);
        }
        try {
            cb.setValue(obl.get(0));
        }catch(IndexOutOfBoundsException ignored){}
    }

    public static void setCurrenFilm(Film currenFilm) {
        RemoveHallStage.currentFilm = currenFilm;
    }
}