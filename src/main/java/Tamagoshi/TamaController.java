package Tamagoshi;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import Tamagoshi.tamagoshis.*;

import java.util.concurrent.CountDownLatch;


public class TamaController {
    private Tamagoshi tamagoshi;
    @FXML
    private Label name;
    @FXML
    private Label etat;
    @FXML
    private Button btnManger;
    @FXML
    private Button btnJouer;
    private GameController GameController;

    public TamaController(Tamagoshi tamagoshi, GameController GameController)
    {
        this.tamagoshi = tamagoshi;
        this.GameController = GameController;
    }

    public void Manger(ActionEvent actionEvent) {
        tamagoshi.mange();
        GameController.desactiverBTNManger();
        GameController.verifTour();
    }

    public void Jouer(ActionEvent actionEvent) {
        tamagoshi.joue();
        GameController.desactiverBTNJouer();
        GameController.verifTour();
    }

    public void commentCaVa(){
        etat.setText(tamagoshi.parle());
    }

    public void desactiverBTNManger(){
        btnManger.setDisable(true);
    }

    public void desactiverBTNJouer(){
        btnJouer.setDisable(true);
    }

    public void reactiverBTN(){
        btnManger.setDisable(false);
        btnJouer.setDisable(false);

    }

    public void setName(String nameTama) {
        name.setText(nameTama);
        etat.setText(tamagoshi.parle());

    }

}
