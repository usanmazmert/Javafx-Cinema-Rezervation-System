
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class SignUp extends Application{
    private TextField username = new TextField();
    private PasswordField password = new PasswordField();
    private PasswordField password2 = new PasswordField();
    private Button logIn = new Button("LOG IN");
    private Button signUp = new Button("SIGN UP");
    private HBox hboxAlign = new HBox();
    private GridPane grid = new GridPane();
    private BorderPane borderPane = new BorderPane();
    private HBox hbox = new HBox();
    private Text welcome = new Text(25,25,"Welcome to Hacettepe Reservation System! \nFill the form below to create a new account. \nYou can go to log in page by clicking LOG IN button.");
    Text error = new Text();
    HBox errorBox = new HBox(error);
    public void start(Stage stage){


        errorBox.setPadding(new Insets(0,0,10,0));
        error.setTextAlignment(TextAlignment.CENTER);
        errorBox.setAlignment(Pos.CENTER);


        welcome.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        welcome.setTextAlignment(TextAlignment.CENTER);
        hbox.getChildren().add(welcome);



        username.setPromptText("example1212");

        password.setPromptText("********");
        password2.setPromptText("********");



        hboxAlign.getChildren().add(signUp);
        hboxAlign.setAlignment(Pos.CENTER_RIGHT);



        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Username:"), 0, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(new Label("Password:"), 0, 2);
        grid.add(username, 1, 0);
        grid.add(password, 1, 1);
        grid.add(password2, 1, 2);
        grid.add(logIn, 0,3);
        grid.add(hboxAlign, 1, 3);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10,20,20,20));


        logIn.setOnAction(e -> {
            LoginStage ms = new LoginStage();
            ms.start(stage);
        });



        signUp.setOnAction(e -> signingUp());


        borderPane.setTop(hbox);
        borderPane.setCenter(grid);
        borderPane.setBottom(errorBox);
        borderPane.setPadding(new Insets(10,20,10,20));


        Scene scene = new Scene(borderPane);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Controls the sutiations for a successfull sign up
     */
    public void signingUp(){
        String name = username.getText();
        String pass = password.getText();
        String pass2 = password2.getText();
        if (name.equals("")){
            delete("ERROR: username cannot be empty!", true);
        }else if(pass.equals("")){
            delete("ERROR: password cannot be empty!", true);
        }else if(!pass.equals(pass2)){
            delete("ERROR: passwords doesn't match!", true);
        }else if(User.getUsernames().contains(name)){
            delete("ERROR: this username already exists!",true);
        }else{
            User.getUsernames().add(name);
            User.getUsers().put(name, new User(name, User.hashPassword(pass), "false", "false"));
            delete("SUCCESS: you have successfully registered with your crediental!", false);
        }
    }
    public void delete(String errorMessage, boolean play){
        if(play){
            LoginStage.getMp().play();
            LoginStage.getMp().seek(Duration.ZERO);
        }
        error.setText(errorMessage);
        username.clear();
        password.clear();
        password2.clear();
    }
}
