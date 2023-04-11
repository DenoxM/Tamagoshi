package Tamagoshi;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import Tamagoshi.tamagoshis.*;

public class GameController implements Initializable {

    public static boolean avoirManger;
    public static boolean avoirJouer;
    public int cycle;

    public List<TamaController> tamaControllers;
    public TextArea console;
    private List<Tamagoshi> allTamagoshis;
    private List<Tamagoshi> aliveTamagoshis;

    private String getRandomName() {
        String[] names = {"Bob", "Jean", "Louis", "Alfred", "Batman", "JSP", "Gilles", "Mario", "Louigi", "Random"};
        int randomIndex = (int) (Math.random() * names.length);
        return names[randomIndex];
    }

    public void verifTour() {
        if ((avoirJouer == true)  &&  (avoirManger == true)) {
            for (Iterator<Tamagoshi> iterator = aliveTamagoshis.iterator(); iterator.hasNext(); ) {
                Tamagoshi t = iterator.next();
                if (!t.consommeEnergy() || !t.consommeFun() || t.vieillit())
                    iterator.remove();
            }

            reactiverBTN();
            MAJ();
            VerifGame();
        }
    }
    public void VerifGame() {

        if (! aliveTamagoshis.isEmpty()) {
            console.setText(console.getText()+ "\n" + "-------------" + (cycle+=1) + "-------------");
                console.setText(console.getText()+ "\n" +"QuelTamagoshiNourrir et avec qui jouer ?");
        }
        else {
            console.setText(console.getText() + "\n" + "\n\t--------- fin de partie !! ----------------\n\n");
            resultat();
        }
    }

    public void desactiverBTNJouer(){
        for (TamaController controller: tamaControllers) {
            controller.desactiverBTNJouer();
        }
        avoirJouer = true;
    }

    public void desactiverBTNManger(){
        for (TamaController controller: tamaControllers) {
            controller.desactiverBTNManger();
        }
        avoirManger = true;
    }

    public void reactiverBTN(){
        for (TamaController controller: tamaControllers) {
            controller.reactiverBTN();
        }
    }

    public void MAJ() {
        for (TamaController controller: tamaControllers) {
            controller.commentCaVa();
        }
    }

    private double score() {
        int score = 0;
        for (Tamagoshi t : allTamagoshis)
            score += t.getAge();
        return score * 100 / (Tamagoshi.getLifeTime() * allTamagoshis.size());
    }

    private void initialiser(Integer i){
        String name = getRandomName();
        Random random = new Random();
        switch (random.nextInt(5)) {
            case 0:
                allTamagoshis.add(new GrosMangeur(name));
                break;
            case 1:
                allTamagoshis.add(new GrosJoueur(name));
                break;
            case 2:
                allTamagoshis.add(new Lunatique(name));
                break;
            case 3:
                allTamagoshis.add(new Fou(name));
                break;
            case 4:
                allTamagoshis.add(new Joyeux(name));
                break;
            default:
                allTamagoshis.add(new Tamagoshi(name));
                break;
        }
        TamaController tamaController = new TamaController(allTamagoshis.get(i),this);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(TamaGameGraphic.class.getResource("TamagoshiGameMenu.fxml"));
        fxmlLoader.setController(tamaController);
        Stage stage = new Stage();
        stage.setTitle(name);
        stage.setX(i * 550);
        stage.setY(100);
        try {
            stage.setScene(new Scene(fxmlLoader.load(), 500, 500));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
        tamaControllers.add(tamaController);
        tamaController.setName(name);
    }

    private void resultat() {
        console.setText(console.getText()+ "\n" +"-------------bilan------------");
        for (Tamagoshi t : allTamagoshis) {
            String classe = t.getClass().getSimpleName();
            console.setText(console.getText()+ "\n" +t.getName() + " qui était un " + classe + " "
                    + (t.getAge() == Tamagoshi.getLifeTime() ? " a survécu et vous remercie :)"
                    : " n'est pas arrivé au bout et ne vous félicite pas :("));
        }
        console.setText(console.getText()+ "\n" +"\nniveau de difficulté : " + allTamagoshis.size() + ", score obtenu : " + score() + "%");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tamaControllers = new ArrayList<TamaController>();
        allTamagoshis = new ArrayList<Tamagoshi>();
        aliveTamagoshis = new ArrayList<Tamagoshi>();
        TextInputDialog textInput = new TextInputDialog();
        textInput.setTitle("Tamagoshi count");
        textInput.getDialogPane().setContentText("Entrez le nombre de Tamagoshi (1 à 5)");
        Optional<String> result = textInput.showAndWait();
        TextField input = textInput.getEditor();
        Integer nbTama = 1;
        nbTama=Integer.parseInt(input.getText());;
        if((nbTama > 0) && (nbTama <= 5)) {
            for (int i = 0; i < nbTama ; i++) {
                initialiser(i);
            }
        }
        aliveTamagoshis = new ArrayList<Tamagoshi>(allTamagoshis);
        avoirManger=false;
        avoirJouer=false;
        console.setText(console.getText()+ "\n" + "-------------" + (cycle+=1) + "-------------");
        console.setText(console.getText()+ "\n" +"QuelTamagoshiNourrir et avec qui jouer ?");
    }
    public void quitter(ActionEvent actionEvent) {
        Platform.exit();
    }
}