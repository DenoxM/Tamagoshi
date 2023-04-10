package Tamagoshi;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import Tamagoshi.tamagoshis.*;

import java.util.concurrent.CountDownLatch;


public class TamaController {
    private Tamagoshi tamagoshi;

    @FXML
    private Label name;

    @FXML
    private Label etat;

    private CountDownLatch latch;

    public TamaController() {
    }

    public TamaController(Tamagoshi tamagoshi, String name) {
        this.tamagoshi = tamagoshi;
        this.name.setText(name);
    }

    public TamaController(Tamagoshi tamagoshi) {
        this.tamagoshi = tamagoshi;
    }

    public void Manger(ActionEvent actionEvent) {
        tamagoshi.mange();
        latch.countDown();
    }

    public void Jouer(ActionEvent actionEvent) {
        tamagoshi.joue();
        latch.countDown();
    }

    @FXML
    public void setName(String nameTama) {
        name.setText(nameTama);
        etat.setText(this.tamagoshi.parle());

    }

}
