package Tamagoshi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 * Classe Permettant de gérer le menu Principal
 */
public class TamaGameGraphic extends Application {

    /**
     *
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(TamaGameGraphic.class.getResource("menuTamagoshi.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 385);
        stage.setTitle("Menu");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
