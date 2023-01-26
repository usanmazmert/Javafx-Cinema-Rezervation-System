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

public class FilmChoosingStage extends Application {
    private static Film currentFilm;
    private Text welcome = new Text(formatUserText(LoginStage.getCurrentUser()));
    private ObservableList<String> obl = FXCollections.observableArrayList(Film.getFilms().keySet());
    private ComboBox<String> cb = new ComboBox<String>(obl);
    private Button addFilm = new Button("Add Film");
    private Button removeFilm = new Button("Remove Film");
    private Button editUsers = new Button("Edit Users");
    private Button logOut = new Button("LOG OUT");
    private Button ok = new Button("OK");
    private Button ticket = new Button("Tickets");
    private VBox vbox = new VBox();
    private HBox hbox1 = new HBox(10);
    private HBox hbox2 = new HBox(10);
    private HBox hbox3 = new HBox(290);

    public void start(Stage stage) {
        welcome.setTextAlignment(TextAlignment.CENTER);
        welcome.setFont(Font.font("Times New Roman", 20));


        addFilm.setOnAction(e -> {
            FilmAddingStage fas = new FilmAddingStage();
            fas.start(stage);
        });


        removeFilm.setOnAction(e -> {
            FilmRemoveStage frs = new FilmRemoveStage();
            frs.start(stage);
        });


        logOut.setOnAction(e -> {
            LoginStage ms = new LoginStage();
            ms.start(stage);
        });


        editUsers.setOnAction(e -> {
            EditUsersStage eus = new EditUsersStage();
            eus.start(stage);
        });


        ok.setOnAction(e -> {
            try {
                Film film = Film.getFilms().get(cb.getValue());
                setCurrentFilm(film);
                FilmTrailerStage fts = new FilmTrailerStage();
                fts.start(stage);
            }catch(NullPointerException ignored){}
        });


        ticket.setOnAction(e -> {
            TicketStage ts = new TicketStage();
            ts.start(stage);
        });

        cb.setPrefWidth(400);
        try{
        cb.setValue(obl.get(0));
        }catch (IndexOutOfBoundsException ignored){}


        ok.setAlignment(Pos.CENTER_RIGHT);


        hbox1.setPadding(new Insets(20, 40, 20, 40));
        hbox1.getChildren().addAll(cb, ok);
        hbox1.setAlignment(Pos.CENTER);
        hbox2.getChildren().addAll(addFilm, removeFilm, editUsers);
        hbox2.setAlignment(Pos.CENTER);
        hbox3.getChildren().addAll(ticket,logOut);
        hbox3.setPadding(new Insets(20, 70, 20, 70));
        hbox3.setAlignment(Pos.CENTER);


        if (LoginStage.getCurrentUser().isAdmin())
            vbox.getChildren().addAll(welcome, hbox1, hbox2, hbox3);
        else
            vbox.getChildren().addAll(welcome, hbox1, hbox3);
        vbox.setAlignment(Pos.CENTER);


        Scene scene = new Scene(vbox);
        stage.setWidth(600);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    public String formatUserText(User user) {
        if (user.isMember() && user.isAdmin()){
            return String.format("Welcome %s (Admin - Club Member)!\nYou can either select film below or do edits!",user.getUsername());
        }else if (user.isAdmin()){
            return String.format("Welcome %s (Admin)!\nYou can either select film below or do edits!",user.getUsername());
        }else if (LoginStage.getCurrentUser().isMember())
            return String.format("Welcome %s (Club Member)!\nSelect a film and then click OK to continue!", LoginStage.getCurrentUser().getUsername());
        else{
            return String.format("Welcome %s!\nSelect a film and then click OK to continue!", LoginStage.getCurrentUser().getUsername());
        }
    }

    public static Film getCurrentFilm() {
        return currentFilm;
    }

    public static void setCurrentFilm(Film currentFilm) {
        FilmChoosingStage.currentFilm = currentFilm;
    }
}
