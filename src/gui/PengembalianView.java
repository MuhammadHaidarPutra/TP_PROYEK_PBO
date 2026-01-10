package gui;

import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Penyewaan;
import service.DataStore;

public class PengembalianView {

    public void show(Stage stage) {

        ObservableList<Penyewaan> data =
                FXCollections.observableArrayList(DataStore.penyewaanList);

        ListView<Penyewaan> listView = new ListView<>(data);

        Button btnKembaliin = new Button("Kembalikan");
        Button btnBack = new Button("Kembali");

        btnKembaliin.setOnAction(e -> {
            Penyewaan p = listView.getSelectionModel().getSelectedItem();
            p.pengembalian();
            data.remove(p);
        });

        btnBack.setOnAction(e -> new PetugasView().show(stage));

        VBox root = new VBox(10, listView, btnKembaliin, btnBack);
        root.setStyle("-fx-padding:20");
        stage.setScene(new Scene(root, 400, 400));
    }
}
