package Tamagoshi;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import Tamagoshi.tamagoshis.*;

public class MenuController {
    private Stage stage;
    public void quitter(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void Jouer(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(TamaGameGraphic.class.getResource("TamagoshiConsole.fxml"));
        Parent root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Console");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
