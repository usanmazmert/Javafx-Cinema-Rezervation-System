import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.*;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class AddHallStage extends Application{
    private Text filmName = new Text(String.format("%s (%d minutes)",FilmChoosingStage.getCurrentFilm().getMovieName(), FilmChoosingStage.getCurrentFilm().getDuration()));
    private Label row = new Label("Row:");
    private Label column = new Label("Column:");
    private Label name = new Label("Name:");
    private Label price = new Label("Price:");
    private ComboBox rowBox = new ComboBox(FXCollections.observableArrayList(Arrays.asList(3,4,5,6,7,8,9,10)));
    private ComboBox columnBox = new ComboBox(FXCollections.observableArrayList(Arrays.asList(3,4,5,6,7,8,9,10)));
    private TextField nameField = new TextField();
    private TextField priceField = new TextField();
    private Button back = new Button("< BACK");
    private Button ok = new Button("OK");
    private HBox hbox1 = new HBox(82);
    private HBox hbox2 = new HBox(60);
    private HBox hbox3 = new HBox(10);
    private HBox hbox4 = new HBox(16);
    private HBox hbox5 = new HBox(128);
    private VBox vbox = new VBox(15);
    private Text errorText = new Text();

    public void start(Stage stage){


        back.setOnAction(e -> {
            FilmTrailerStage fts = new FilmTrailerStage();
            fts.start(stage);
        });


        ok.setOnAction(e -> addHall());


        rowBox.setValue(3);


        columnBox.setValue(3);



        hbox1.getChildren().addAll(row, rowBox);
        hbox1.setAlignment(Pos.CENTER);
        hbox1.setPadding(new Insets(0,62,0,0));



        hbox2.getChildren().addAll(column, columnBox);
        hbox2.setAlignment(Pos.CENTER);
        hbox2.setPadding(new Insets(0,63,0,0));



        hbox3.getChildren().addAll(name, nameField);
        hbox3.setAlignment(Pos.CENTER);



        hbox4.getChildren().addAll(price, priceField);
        hbox4.setAlignment(Pos.CENTER);



        hbox5.getChildren().addAll(back, ok);
        hbox5.setAlignment(Pos.CENTER);



        vbox.getChildren().addAll(filmName, hbox1, hbox2, hbox3, hbox4, hbox5, errorText);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);



        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setWidth(500);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    public void delete(String error){
        errorText.setText(error);
        nameField.clear();
        priceField.clear();
        rowBox.setValue(3);
        columnBox.setValue(3);
        LoginStage.getMp().play();
        LoginStage.getMp().seek(Duration.ZERO);
    }

    /**
     * Creates the hall and that halls' stages' objects
     * and add them to the cinema halls
     */
    public void addHall(){
        String hallName = nameField.getText();
        String price = priceField.getText();
        if (hallName.equals("")) {
            delete("ERROR: Hall name could not be empty!");
        }else if(price.equals("")){
            delete("ERROR: Price could not be empty!");
        }else if(Cinema.getHalls().containsKey(hallName)){
            delete(String.format("ERROR: There is already a hall named %s!", hallName));
        }else if(price.contains(".") || price.contains(",")) {
            delete("ERROR: Price should be a positive integer!");
        }else{
            try{
                Integer priceI = Integer.parseInt(price);
                if(priceI <= 0)
                    throw new NumberFormatException();
                Cinema.getHalls().put(hallName, new Hall(FilmChoosingStage.getCurrentFilm(), hallName, priceI, (Integer)rowBox.getValue(), (Integer)columnBox.getValue()));
                for (int rowIndex = 0; rowIndex < (Integer)rowBox.getValue(); rowIndex++){
                    ArrayList<Seat> row = new ArrayList<Seat>();
                    for (int columnIndex = 0; columnIndex < (Integer) columnBox.getValue(); columnIndex++) {
                        row.add(new Seat(FilmChoosingStage.getCurrentFilm().getMovieName(), hallName, rowIndex, columnIndex, 0, "null"));
                    }
                    Cinema.getHalls().get(hallName).getSeats().add(row);
                }
                FilmChoosingStage.getCurrentFilm().getHalls().add(hallName);
                errorText.setText("SUCCESS: Hall successfully created!");
                nameField.clear();
                priceField.clear();
                rowBox.setValue(3);
                columnBox.setValue(3);
            }catch(NumberFormatException ex){
                delete("ERROR: Price should be a positive integer!");
            }
        }
    }
}
