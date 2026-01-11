package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Petugas;

public class PetugasController {
    @FXML private TextField tfUsername;
    @FXML private PasswordField tfPassword;
    @FXML private TableView<Petugas> tablePetugas;
    @FXML private TableColumn<Petugas, String> colUsername;
    @FXML private TableColumn<Petugas, String> colPassword;
    private ObservableList<Petugas> data = FXCollections.observableArrayList();

    public PetugasController() {}

    @FXML
    public void initialize() {
        colUsername.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        colPassword.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
        tablePetugas.setItems(data);
    }

    @FXML
    public void handleTambah() {
        Petugas p = new Petugas(tfUsername.getText(), tfPassword.getText());
        data.add(p);
        clearForm();
    }

    @FXML
    public void handleUbah() {
        Petugas p = tablePetugas.getSelectionModel().getSelectedItem();
        if (p != null) {
              String username = tfUsername.getText();
              String password = tfPassword.getText();
              if (!username.isEmpty()) p.setUsername(username);
              if (!password.isEmpty()) p.setPassword(password);
            tablePetugas.refresh();
            clearForm();
        }
    }

    @FXML
    public void handleHapus() {
        Petugas p = tablePetugas.getSelectionModel().getSelectedItem();
        if (p != null) {
            data.remove(p);
            clearForm();
        }
    }

    private void clearForm() {
        tfUsername.clear();
        tfPassword.clear();
    }
}
