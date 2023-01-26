

import javafx.application.Application;

import java.io.IOException;

public class Main {
    public static void main(String[] args)throws IOException {
        ProgramManager.readProperties("assets\\data\\properties.dat");
        ProgramManager.getData("assets\\data\\backup.dat");
        Application.launch(LoginStage.class, args);
        ProgramManager.writeData("assets\\data\\backup.dat");
    }
}
