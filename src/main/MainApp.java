package main;

import javafx.application.Application;
import javafx.stage.Stage;
import gui.LoginView;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        LoginView login = new LoginView();
        login.show(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
