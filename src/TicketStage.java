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

public class TicketStage extends Application{
    private ObservableList<Ticket> obl;
    private static Ticket currentTicket;
    private Button back = new Button("< BACK");
    private Button details = new Button("Details");
    private HBox hbox = new HBox(365);
    private BorderPane bp = new BorderPane();
    public void start(Stage stage) {
        if (LoginStage.getCurrentUser().isAdmin() && !Ticket.getTickets().isEmpty()) {
            obl = FXCollections.observableArrayList(Ticket.getTickets());
            createTableView(obl);
        }else if(!LoginStage.getCurrentUser().getTickets().isEmpty()) {
            obl = FXCollections.observableArrayList(LoginStage.getCurrentUser().getTickets());
            createTableView(obl);
        }


        TableView<Ticket> tv = createTableView(obl);
        ScrollPane sp = new ScrollPane(tv);
        sp.setPadding(new Insets(10, 0, 10, 20));


        details.setOnAction(e -> goDetails(stage, tv));


        back.setOnAction(e -> goBack(stage));


        hbox.getChildren().addAll(back, details);
        hbox.setPadding(new Insets(10, 10, 10, 20));


        bp.setCenter(sp);
        bp.setBottom(hbox);


        Scene scene = new Scene(bp);
        stage.setWidth(560);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();


    }

    public TableView<Ticket> createTableView(ObservableList<Ticket> obl){
        TableColumn<Ticket, String> nameColumn = new TableColumn<Ticket, String>("Username");
        nameColumn.setMinWidth(250);
        nameColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("owner"));
        TableColumn<Ticket, String> filmColumn = new TableColumn<Ticket, String>("Film");
        filmColumn.setMinWidth(250);
        filmColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("film"));
        TableView<Ticket> tv = new TableView<Ticket>();
        tv.setItems(obl);
        tv.setPlaceholder(new Label("There is no ticket in database!"));
        tv.getColumns().addAll(nameColumn, filmColumn);
        return tv;
    }

    public void goBack(Stage stage){
        FilmChoosingStage as = new FilmChoosingStage();
        as.start(stage);
    }

    public void goDetails(Stage stage, TableView<Ticket> tv){
        try {
            setCurrentTicket(tv.getSelectionModel().getSelectedItem());
            DetailsStage ds = new DetailsStage();
            ds.start(stage);
        }catch (RuntimeException ignored){}
    }

    public static Ticket getCurrentTicket() {
        return currentTicket;
    }

    public static void setCurrentTicket(Ticket currentTicket) {
        TicketStage.currentTicket = currentTicket;
    }
}
