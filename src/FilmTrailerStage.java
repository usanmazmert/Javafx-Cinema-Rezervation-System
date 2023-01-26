
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.*;

import java.io.File;
import javafx.util.Duration;

public class FilmTrailerStage extends Application{
    private static Hall currentHall;
    private ObservableList<String> obl = FXCollections.observableArrayList(FilmChoosingStage.getCurrentFilm().getHalls());
    private Text filmNameText = new Text(String.format(FilmChoosingStage.getCurrentFilm().getMovieName() + " (" + FilmChoosingStage.getCurrentFilm().getDuration() + " minutes)"));
    private BorderPane bp = new BorderPane();
    private VBox vb = new VBox(10);
    private HBox hb = new HBox(10);
    private Button play = new Button(">");
    private Button back = new Button("< BACK");
    private Button addHall = new Button("Add Hall");
    private Button removeHall = new Button("Remove Hall");
    private Button rewindButton = new Button("|<<");
    private Button fiveSecondBack = new Button("<<");
    private Button fiveSecondForward = new Button(">>");
    private VBox mediaBox = new VBox(15);
    private Button ok = new Button("OK");
    private ComboBox<String> halls = new ComboBox<String>(obl);
    public void start(Stage stage){


        try {
            halls.setValue(obl.get(0));
        }catch (IndexOutOfBoundsException ignored){}


        String media = new File("assets\\trailers\\"+ FilmChoosingStage.getCurrentFilm().getVideoPath() ).toURI().toString();
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(media));
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaView.setFitHeight(700);
        mediaView.setFitWidth(630);


        play.setOnAction(e -> {
            if (play.getText().equals(">")) {
                mediaPlayer.play();
                play.setText("||");
            }else if(mediaPlayer.getCurrentTime().equals(mediaPlayer.getTotalDuration())){
                mediaPlayer.pause();
                play.setText(">");
            }else {
                mediaPlayer.pause();
                play.setText(">");
            }
        });
        play.setPrefWidth(60);



        back.setOnAction(e -> {
            FilmChoosingStage as = new FilmChoosingStage();
            Stage newStage = new Stage();
            newStage.getIcons().add(new Image(new File("assets\\icons\\logo.png").toURI().toString()));
            newStage.setTitle(LoginStage.getTitle());
            as.start(newStage);
            stage.close();
            mediaPlayer.pause();
        });



        fiveSecondBack.setOnAction(e -> {
            Duration time;
            time = Duration.seconds(mediaPlayer.getCurrentTime().toSeconds() - 5);
            mediaPlayer.seek(time);
        });
        fiveSecondBack.setPrefWidth(60);



        fiveSecondForward.setOnAction(e -> {
            Duration time;
            time = Duration.seconds(mediaPlayer.getCurrentTime().toSeconds() + 5);
            mediaPlayer.seek(time);
        });
        fiveSecondForward.setPrefWidth(60);



        removeHall.setOnAction(e -> {
            RemoveHallStage.setCurrenFilm(FilmChoosingStage.getCurrentFilm());
            RemoveHallStage rhs = new RemoveHallStage();
            Stage newStage = new Stage();
            newStage.getIcons().add(new Image(new File("assets\\icons\\logo.png").toURI().toString()));
            newStage.setTitle(LoginStage.getTitle());
            rhs.start(newStage);
            mediaPlayer.pause();
            stage.close();
        });



        addHall.setOnAction(e -> {
            Stage newStage = new Stage();
            newStage.getIcons().add(new Image(new File("assets\\icons\\logo.png").toURI().toString()));
            newStage.setTitle(LoginStage.getTitle());
            AddHallStage ahs = new AddHallStage();
            ahs.start(newStage);
            stage.close();
            mediaPlayer.pause();
        });



        ok.setOnAction(e -> {
            try {
                setCurrentHall(Cinema.getHalls().get(halls.getValue()));
                Stage newStage = new Stage();
                newStage.getIcons().add(new Image(new File("assets\\icons\\logo.png").toURI().toString()));
                newStage.setTitle(LoginStage.getTitle());
                SeatsStage ss = new SeatsStage();
                ss.start(newStage);
                mediaPlayer.pause();
                stage.close();
            }catch(NullPointerException ignored){}
        });



        rewindButton.setOnAction(e -> mediaPlayer.seek(Duration.ZERO));
        rewindButton.setPrefWidth(60);


        Slider volume = new Slider();
        volume.setOrientation(Orientation.VERTICAL);
        volume.setValue(50);
        mediaPlayer.volumeProperty().bind(volume.valueProperty().divide(100));


        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(play, fiveSecondBack, fiveSecondForward,rewindButton, volume);
        vb.setPadding(new Insets(0,20,0,0));


        if(LoginStage.getCurrentUser().isAdmin())
            hb.getChildren().addAll(back, addHall, removeHall, halls, ok);
        else
            hb.getChildren().addAll(back, halls, ok);
        hb.setPadding(new Insets(0,10,10,10));
        hb.setAlignment(Pos.CENTER);


        mediaBox.setAlignment(Pos.CENTER);
        mediaBox.getChildren().addAll(filmNameText,mediaView, hb);
        mediaBox.setPadding(new Insets(10,0,10,20));


        bp.setLeft(mediaBox);
        bp.setRight(vb);


        Scene scene = new Scene(bp);
        stage.setScene(scene);
        stage.setWidth(780);
        stage.setHeight(550);
        stage.show();
    }

    public static Hall getCurrentHall() {
        return currentHall;
    }
    public static void setCurrentHall(Hall currentHall) {
        FilmTrailerStage.currentHall = currentHall;
    }
}
