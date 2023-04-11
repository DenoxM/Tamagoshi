package Tamagoshi;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import Tamagoshi.tamagoshis.*;

/**
 * Classe permettant de gérer les actions sur les tamagoshis en graphique
 */
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

    /**
     * Constructeur de la classe TamaController
     * @param tamagoshi
     * @param GameController
     */
    public TamaController(Tamagoshi tamagoshi, GameController GameController)
    {
        this.tamagoshi = tamagoshi;
        this.GameController = GameController;
    }

    /**
     * Action liée au click sur le boutton Manger
     * @param actionEvent
     */
    public void Manger(ActionEvent actionEvent) {
        tamagoshi.mange();
        GameController.desactiverBTNManger();
        GameController.verifTour();
    }

    /**
     * Action liée au click sur le boutton Jouer
     * @param actionEvent
     */
    public void Jouer(ActionEvent actionEvent) {
        tamagoshi.joue();
        GameController.desactiverBTNJouer();
        GameController.verifTour();
    }
    /**
     * Méthode permettant de mettre l'état du Tamagoshi en sous forme de Label
     */
    public void commentCaVa(){
        etat.setText(tamagoshi.parle());
    }

    /**
     * Méthode permettant de désactiver le boutton Manger
     */
    public void desactiverBTNManger(){
        btnManger.setDisable(true);
    }

    /**
     * Méthode permettant de désactiver le boutton Jouer
     */
    public void desactiverBTNJouer(){
        btnJouer.setDisable(true);
    }

    /**
     * Méthode permettant de réactiver les bouttons
     */
    public void reactiverBTN(){
        btnManger.setDisable(false);
        btnJouer.setDisable(false);

    }

    /**
     * Permet de passer en paramètre le futur nom du Tamagoshi
     * @param nameTama
     */
    public void setName(String nameTama) {
        name.setText(nameTama);
        etat.setText(tamagoshi.parle());
    }

    public boolean verifEtatTama() {
        return tamagoshi.isAlive();
    }

}
