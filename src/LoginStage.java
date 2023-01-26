
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.util.Duration;

public class LoginStage extends Application {
    private boolean blocked = false;
    private static String title = ProgramManager.getPr().getProperty("title");
    private static int blockTime = Integer.parseInt(ProgramManager.getPr().getProperty("block-time"));
    private static int maximumError = Integer.parseInt(ProgramManager.getPr().getProperty("maximum-error-without-getting-blocked"));
    private static int blockCount = 0;
    private static MediaPlayer mp = new MediaPlayer(new Media(new File("assets\\effects\\error.mp3").toURI().toString()));
    private static Stage mainStage = new Stage();
    private static User currentUser;
    private Text errorMessage = new Text();
    private HBox errorBox = new HBox(errorMessage);
    private PasswordField passwordField = new PasswordField();
    private TextField usernameField = new TextField();
    private Text welcome = new Text(25,25,"Welcome to Hacettepe Reservation System! " +
            "\nPlease enter your credentials below and click LOGIN. \nYou can create a new account by clicking SIGN UP button.");
    private BorderPane bordered = new BorderPane();
    private HBox buttons = new HBox(100);
    private Button signUp = new Button("SIGN UP");
    private Button logIn = new Button("LOG IN");
    private HBox welcomeBox = new HBox(welcome);
    private GridPane indexedPane = new GridPane();
    public void start(Stage primaryStage){

        usernameField.setPromptText("example1212");
        passwordField.setPromptText("*********");


        welcome.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        welcome.setTextAlignment(TextAlignment.CENTER);
        welcomeBox.setPadding(new Insets(10,10,10,10));


        errorMessage.setTextAlignment(TextAlignment.CENTER);
        errorBox.setAlignment(Pos.CENTER);



        signUp.setOnAction(e ->  {
            if (!blocked) {
                SignUp signUpStage = new SignUp();
                signUpStage.start(primaryStage);
            }else{
                mp.play();
                mp.seek(Duration.ZERO);
            }
        });


        logIn.setOnAction(e -> loggingIn(primaryStage));


        buttons.getChildren().add(logIn);
        buttons.setAlignment(Pos.CENTER_RIGHT);;

        indexedPane.setHgap(10);
        indexedPane.setVgap(10);
        indexedPane.add(new Label("username:"), 0, 0);
        indexedPane.add(new Label("username:"), 0, 0);
        indexedPane.add(usernameField, 1, 0);
        indexedPane.add(new Label("password:"),0,1);
        indexedPane.add(passwordField, 1 ,1);
        indexedPane.add(signUp, 0, 2);
        indexedPane.add(buttons, 1, 2);
        indexedPane.setAlignment(Pos.CENTER);
        indexedPane.setPadding(new Insets(20));


        bordered.setTop(welcomeBox);
        bordered.setCenter(indexedPane);
        bordered.setBottom(errorBox);
        bordered.setPadding(new Insets(10, 30, 30, 30));



        Scene scene = new Scene(bordered);
        primaryStage.getIcons().add(new Image(new File("assets\\icons\\logo.png").toURI().toString()));
        primaryStage.setTitle(title);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();


    }
    private void newPage(Stage stage){
            //By using current user I adjusted the upper text.
            blockCount = 0;
            FilmChoosingStage as = new FilmChoosingStage();
            as.start(stage);
    }
    public void setTimer() {
        Timer timer = new Timer();
        errorMessage.setText(String.format("ERROR: Please wait %d seconds to make a new operation!", blockTime));
        timer.scheduleAtFixedRate(new TimerTask() {
            int seconds = blockTime;
            public void run() {
                if(seconds > 0) {
                    seconds--;
                }
                else{
                    timer.cancel();
                    errorMessage.setText("");
                    blocked=false;
                }
            }
        }, 0,1000);
    }

    /**
     * Plays the error sound,
     * clears the textses,
     * and increases the blockCount when wrong input entered.
     * @param error
     */
    public void delete(String error){
        passwordField.clear();
        mp.play();
        mp.seek(Duration.ZERO);
        if(!errorMessage.getText().equals(String.format("ERROR: Please wait %d seconds to make a new operation!", blockTime)))
            blockCount++;
        errorMessage.setText(error);
    }

    /**
     * Controls the inputs whether expected conditions are ensured for a successfull login.
     * @param primaryStage
     */
    public void loggingIn(Stage primaryStage){
        String username = usernameField.getText();
        String hashedPassword = User.hashPassword(passwordField.getText());
        if (blockCount == maximumError - 1) {
            if (!User.getUsers().containsKey(username) || !User.getUsers().get(username).getHash().equals(hashedPassword)){
                delete("");
                blockCount = 0;
                blocked = true;
                setTimer();
            }else {
                blockCount = 0;
                setCurrentUser(User.getUsers().get(username));
                newPage(primaryStage);
            }
        }else if(blocked){
            delete(String.format("ERROR: Please wait %d seconds to make a new operation!", blockTime));
        }else if (usernameField.getText().equals("")){
            delete("ERROR: Username cannot be empty!");
        }else if (passwordField.getText().equals("")){
            delete("ERROR: Password cannot be empty!");
        }else if(!User.getUsernames().contains(username) || !User.getUsers().get(username).getHash().equals(hashedPassword)){
            delete("ERROR: There is no such a crediental!");
        }else{
            setCurrentUser(User.getUsers().get(username));
            newPage(primaryStage);
        }
    }
    public static Stage getMainStage(){
        return mainStage;
    }
    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        LoginStage.currentUser = currentUser;
    }

    public static MediaPlayer getMp() {
        return mp;
    }

    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        LoginStage.title = title;
    }

    public static int getBlockTime() {
        return blockTime;
    }

    public static void setBlockTime(int blockTime) {
        LoginStage.blockTime = blockTime;
    }

    public static int getBlockCount() {
        return blockCount;
    }

    public static void setBlockCount(int blockCount) {
        LoginStage.blockCount = blockCount;
    }

    public static int getMaximumError() {
        return maximumError;
    }

    public static void setMaximumError(int maximumError) {
        LoginStage.maximumError = maximumError;
    }
}