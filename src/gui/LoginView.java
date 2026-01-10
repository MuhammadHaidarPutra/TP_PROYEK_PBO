package gui;


import auth.LoginService;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginView {

    public void show(Stage stage) {

        Label title = new Label("SewainAja");
        title.getStyleClass().add("title");

        TextField username = new TextField();
        username.setPromptText("Username");
        username.getStyleClass().add("text-field");

        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        password.getStyleClass().add("password-field");

        Label info = new Label();
        info.getStyleClass().add("info");

        Button login = new Button("Login");
        login.getStyleClass().add("button");

        login.setOnAction(e -> {
            String result = LoginService.login(
                username.getText(),
                password.getText()
            );

            if (result.equals("ADMIN")) {
                new AdminView().show(stage);
            } else if (result.equals("PETUGAS")) {
                new PetugasView().show(stage);
            } else if (result.equals("KOSONG")) {
                info.setText("Username dan password wajib diisi!");
            } else {
                info.setText("Login gagal!");
            }
        });

        VBox card = new VBox(15);
        card.getChildren().addAll(title, username, password, login, info);
        card.setAlignment(Pos.CENTER);
        card.getStyleClass().add("card");

        StackPane root = new StackPane(card);

        Scene scene = new Scene(root, 600, 400);

        java.net.URL cssResource = getClass().getResource("login.css");
        try (java.io.FileWriter fw = new java.io.FileWriter("D:/TP_PBO/css_debug.txt", true)) {
            fw.write("CSS Resource: " + cssResource + "\n");
        } catch (Exception ex) { ex.printStackTrace(); }
        if (cssResource != null) {
            scene.getStylesheets().add(cssResource.toExternalForm());
        } else {
            // Fallback: load CSS dengan path absolut
            scene.getStylesheets().add("file:/D:/TP_PBO/src/gui/login.css");
            try (java.io.FileWriter fw = new java.io.FileWriter("D:/TP_PBO/css_debug.txt", true)) {
                fw.write("Fallback: file:/D:/TP_PBO/src/gui/login.css\n");
            } catch (Exception ex) { ex.printStackTrace(); }
        }
