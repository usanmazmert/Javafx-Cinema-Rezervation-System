
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

public class FilmRemoveStage extends Application{
    private ObservableList<String> obl = FXCollections.observableArrayList(Film.getFilms().keySet());
    private ComboBox<String> cb = new ComboBox<String>(obl);
    private Text welcome = new Text("Select the film that you desire to remove and then click OK.");
    private Button back = new Button("< BACK");
    private Button ok = new Button("OK");
    private VBox vbox = new VBox();
    private HBox hbox1 = new HBox(10);
    private HBox hbox2 = new HBox(10);
    public void start(Stage stage){


        welcome.setTextAlignment(TextAlignment.CENTER);
        welcome.setFont(Font.font("Times New Roman", 20));


        //Adjusting button actions
        back.setOnAction(e -> {
            FilmChoosingStage as = new FilmChoosingStage();
            as.start(stage);
        });


        ok.setOnAction(e -> removeFilm());



        cb.setPrefWidth(400);
        try {
            cb.setValue(obl.get(0));
        }catch(IndexOutOfBoundsException ignored){}



        hbox1.setPadding(new Insets(20,40,20,40));
        hbox1.getChildren().add(cb);
        hbox1.setAlignment(Pos.CENTER);



        hbox2.getChildren().addAll(back, ok);
        hbox2.setAlignment(Pos.CENTER);



        vbox.getChildren().addAll(welcome, hbox1,hbox2);
        vbox.setPadding(new Insets(10,20,10,20));
        vbox.setAlignment(Pos.CENTER);



        Scene scene = new Scene(vbox);
        stage.setWidth(600);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    public void removeFilm(){
        String film = cb.getValue();
        Film.getFilms().remove(film);
        obl.remove(film);
        ArrayList<Ticket> delete = new ArrayList<Ticket>();
        for(Ticket ticket : Ticket.getTickets()){
            if(ticket.getFilm().equals(film)){
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
}