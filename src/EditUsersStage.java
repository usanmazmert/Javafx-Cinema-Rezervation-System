
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class EditUsersStage extends Application{
    private Button back = new Button("< BACK");
    private Button promoteClub = new Button("Promote/Demote Club Member");
    private Button promoteAdmin = new Button("Promote/Demote Admin");
    private HBox hbox = new HBox(10);
    private ScrollPane sp;
    private BorderPane bp = new BorderPane();


    public void start(Stage stage){


        TableColumn<User, String> nameColumn = new TableColumn<User, String>("Username");
        nameColumn.setMinWidth(250);
        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));



        TableColumn<User, Boolean> clubMemberColumn = new TableColumn<User, Boolean>("Club Member");
        clubMemberColumn.setMinWidth(125);
        clubMemberColumn.setCellValueFactory(new PropertyValueFactory<User, Boolean>("member"));



        TableColumn<User, Boolean> adminColumn = new TableColumn<User, Boolean>("Admin");
        adminColumn.setMinWidth(125);
        adminColumn.setCellValueFactory(new PropertyValueFactory<User, Boolean>("admin"));



        TableView<User> tv = new TableView<User>();


        ObservableList<User> obl = FXCollections.observableArrayList(User.getUsers().values());
        obl.remove(LoginStage.getCurrentUser());


        tv.setItems(obl);
        tv.getColumns().addAll(nameColumn, clubMemberColumn, adminColumn);
        tv.setPlaceholder(new Label("No user available in database!"));




        sp = new ScrollPane(tv);
        sp.setPadding(new Insets(10, 10, 10, 20));




        //Setting buttons' Actions
        promoteClub.setOnAction(e -> {
            try {
                User selected = tv.getSelectionModel().getSelectedItem();
                selected.setMember(!selected.isMember());
                tv.refresh();
            } catch (NullPointerException ignored) {
            }
        });



        promoteAdmin.setOnAction(e -> {
            try {
                User selected = tv.getSelectionModel().getSelectedItem();
                selected.setAdmin(!selected.isAdmin());
                tv.refresh();
            } catch (NullPointerException ignored) {
            }
        });



        back.setOnAction(e -> {
            FilmChoosingStage as = new FilmChoosingStage();
            as.start(stage);
        });



        hbox.getChildren().addAll(back, promoteClub, promoteAdmin);
        hbox.setPadding(new Insets(10, 10, 10, 20));


        bp.setCenter(sp);
        bp.setBottom(hbox);


        Scene scene = new Scene(bp);
        stage.setWidth(570);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
