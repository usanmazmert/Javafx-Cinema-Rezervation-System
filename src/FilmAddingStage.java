
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.io.*;

public class FilmAddingStage extends Application{
    private TextField name = new TextField();
    private TextField path = new TextField();
    private TextField duration = new TextField();
    private Button back = new Button("< BACK");
    private Button ok = new Button("OK");
    private HBox hboxAlign = new HBox();
    private GridPane grid = new GridPane();
    private BorderPane borderPane = new BorderPane();
    private HBox hbox = new HBox();
    private Text welcome = new Text(25,25,"Please give name, relative path of the trailer and duration of the film.");
    Text errorText = new Text();
    HBox errorBox = new HBox(errorText);
    public void start(Stage stage){



        errorBox.setAlignment(Pos.CENTER);
        errorText.setTextAlignment(TextAlignment.CENTER);
        errorBox.setPadding(new Insets(0,0,10,0));



        back.setOnAction(e -> {
            FilmChoosingStage as = new FilmChoosingStage();
            as.start(stage);
        });



        ok.setOnAction(e -> addFilm());



        welcome.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        welcome.setTextAlignment(TextAlignment.CENTER);
        hbox.getChildren().add(welcome);



        hboxAlign.getChildren().add(ok);
        hboxAlign.setAlignment(Pos.CENTER_RIGHT);



        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Name:"), 0, 0);
        grid.add(new Label("Trailer (Path):"), 0, 1);
        grid.add(new Label("Duration (m):"), 0, 2);
        grid.add(name, 1, 0);
        grid.add(path, 1, 1);
        grid.add(duration, 1, 2);
        grid.add(back, 0,3);
        grid.add(hboxAlign, 1, 3);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10,20,20,20));



        borderPane.setTop(hbox);
        borderPane.setCenter(grid);
        borderPane.setBottom(errorBox);
        borderPane.setPadding(new Insets(10,20,10,20));



        Scene scene = new Scene(borderPane);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    public void delete(String error){
        errorText.setText(error);
        name.clear();
        path.clear();
        duration.clear();
        LoginStage.getMp().play();
        LoginStage.getMp().seek(Duration.ZERO);
    }

    public void addFilm(){
        String filmName = name.getText();
        String pathS = path.getText();
        String durationS = duration.getText();
        if (filmName.equals("")){
            delete("ERROR: Film name cannot be empty!");
        }else if(pathS.equals("")){
            delete("ERROR: Trailer path cannot be empty!");
        }else if(Film.getFilms().keySet().contains(filmName)){
            delete("ERROR: This film already exists!");
        }else if(durationS.contains(".") || durationS.contains(",")) {
            delete("ERROR: Duration should be a positive integer!");
        } else{
            try{
                Integer durationI = Integer.parseInt(durationS);
                if(durationI <= 0)
                    throw new NumberFormatException();
                try{
                    FileReader f = new FileReader(new File("assets\\trailers\\" + pathS));
                    Film.getFilms().put(filmName, new Film(filmName, pathS, durationI));
                    errorText.setText("SUCCESS: Film added successfully!");
                    name.clear();
                    path.clear();
                    duration.clear();
                    f.close();
                }catch(IOException e2){
                    delete("ERROR: There is no such a trailer!");
                }
            }catch(NumberFormatException ex){
                delete("ERROR: Duration has to be positive integer!");
            }
        }
    }
}