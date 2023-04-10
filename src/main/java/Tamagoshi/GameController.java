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

    public TextArea console;
    private List<Tamagoshi> allTamagoshis;
    private List<Tamagoshi> aliveTamagoshis;

    private String getRandomName() {
        String[] names = {"Bob", "Jean", "Louis", "Alfred", "Batman", "JSP", "Gilles", "Mario", "Louigi", "Random"};
        int randomIndex = (int) (Math.random() * names.length);
        return names[randomIndex];
    }

    public void play() {
        int cycle = 1;
        while (! aliveTamagoshis.isEmpty()) {
            console.setText(console.getText()+ "\n" + "-------------" + (cycle++) + "-------------");
            for (Tamagoshi t : aliveTamagoshis)
                console.setText(console.getText()+ "\n" +"QuelTamagoshiNourrir ?");
            for (Iterator<Tamagoshi> iterator = aliveTamagoshis.iterator(); iterator.hasNext();) {
                Tamagoshi t = iterator.next();
                if (!t.consommeEnergy() || !t.consommeFun() || t.vieillit())
                    iterator.remove();
            }
        }
        console.setText(console.getText()+ "\n" +"\n\t--------- fin de partie !! ----------------\n\n");
        resultat();
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
        switch (random.nextInt(4)) {
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
            default:
                allTamagoshis.add(new Tamagoshi(name));
                break;
        }
        TamaController controller = new TamaController(allTamagoshis.get(i));
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(TamaGameGraphic.class.getResource("TamagoshiGameMenu.fxml"));
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
        controller.setName(name);
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
        play();
    }
    public void quitter(ActionEvent actionEvent) {
        Platform.exit();
    }
}