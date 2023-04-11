package Tamagoshi;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    /**
     * Permet de Générer un Noms aléatoire a attribuer a un Tamagoshi
     */
    private String getRandomName() {
        String[] names = {"Bob", "Jean", "Louis", "Alfred", "Batman", "JSP", "Gilles", "Mario", "Louigi", "Random", "Emma", "Lucas", "Sophie", "William", "Camille", "Antoine", "Charlotte", "Arthur", "Léa", "Thomas", "Eva", "Raphaël", "Alice", "Hugo", "Julie", "Paul", "Chloé", "Théo", "Manon", "Alexandre", "Lola", "Quentin", "Elodie", "Matthieu", "Clara", "Maxime", "Mathilde", "Nathan", "Laura", "Adrien", "Marie", "Enzo", "Anaïs", "Jules", "Mélanie", "Valentin", "Juliette", "Vincent", "Margaux", "Gabriel", "Emma", "Lucie", "Baptiste", "Emma", "Antonin", "Jade", "Simon", "Inès", "Martin", "Léna", "Nicolas", "Sarah", "Tristan", "Pauline", "Bastien", "Zoé", "Alexis", "Emma", "Victor", "Lucie", "Noé", "Clémence", "Axel", "Elisa", "Ethan", "Léonie", "Côme", "Olivia", "Oscar", "Romy", "Pierre", "Jade", "Gabin", "Louise", "Léo", "Eléonore", "Eliott", "Apolline", "Marius", "Capucine", "Emile", "Gabrielle", "Simon", "Héloïse", "Edouard", "Eugénie", "Valentine", "Rose", "Félix", "Madeleine", "Anatole", "Béatrice", "Célestin", "Emmanuelle", "Olivier", "Cécile", "Alphonse", "Marceline", "Clovis", "Lorraine", "Ondine", "Suzanne", "Albéric", "Aurélie", "Fanny", "Agathe", "Geoffroy", "Clarisse", "Thibault", "Diane", "Hector", "Henriette", "Lancelot", "Joséphine", "Sixtine", "Isidore", "Constance", "Octave", "Clotilde", "Ignace", "Adélaïde", "Sidoine", "Irène", "Aristide", "Euphémie", "Armel", "Hermine", "Géraud", "Cassandre", "Augustin", "Philippine", "Eloi", "Pénélope", "Hippolyte", "Eglantine", "Félicien", "Léopoldine", "Théodore", "Brigitte", "Baudouin", "Isaure", "Flavien", "Marjolaine", "Aurélien", "Aliénor", "Séraphin", "Sylvie", "Basile", "Amandine", "Blaise", "Mireille", "Aurélie", "Tiphaine", "Roch", "Ambre", "Rémi", "Maëlle", "Valérie"};        int randomIndex = (int) (Math.random() * names.length);
        return names[randomIndex];
    }

    /**
     * Méthode qui vérifie que le tour du joueur est bel et bien terminé
     */
    public void verifTour() {
        if ((avoirJouer == true)  &&  (avoirManger == true)) {
            for (Iterator<Tamagoshi> iterator = aliveTamagoshis.iterator(); iterator.hasNext(); ) {
                Tamagoshi t = iterator.next();
                if (!t.consommeEnergy() || !t.consommeFun() || t.vieillit())
                    iterator.remove();
            }

            reactiverBTN();
            VerifGame();
        }
    }

    /**
     * Méthode qui vérifie que la partie est ou n'est pas terminée
     */
    public void VerifGame() {
        if (! aliveTamagoshis.isEmpty()) {
            console.setText(console.getText()+ "\n" + "-------------" + (cycle+=1) + "-------------");
                console.setText(console.getText()+ "\n" +"QuelTamagoshiNourrir et avec qui jouer ?");
        }
        else {
            console.setText(console.getText() + "\n" + "\n\t--------- fin de partie !! ----------------\n\n");
            resultat();
        }
        MAJ();
    }

    /**
     * Méthode permettant de désactiver le boutton Jouer pour tout les TamaControllers
     */
    public void desactiverBTNJouer(){
        for (TamaController controller: tamaControllers) {
            controller.desactiverBTNJouer();
        }
        avoirJouer = true;
    }
    /**
     * Méthode permettant de désactiver le boutton Manger pour tout les TamaControllers
     */
    public void desactiverBTNManger(){
        for (TamaController controller: tamaControllers) {
            controller.desactiverBTNManger();
        }
        avoirManger = true;
    }
    /**
     * Méthode permettant de réactiver les bouttons pour tout les TamaControllers
     */
    public void reactiverBTN(){
        for (TamaController controller: tamaControllers) {
            controller.reactiverBTN();
        }
    }

    /**
     * Méthode permettant de connaître l'état du Tamagoshi
     */
    public void MAJ() {
        for (TamaController controller: tamaControllers) {
            controller.commentCaVa();
            if (controller.verifEtatTama() == false || aliveTamagoshis.isEmpty()) {
                controller.desactiverBTNJouer();
                controller.desactiverBTNManger();
            }
        }
    }

    /**
     * Méthode permettant de calculer le score
     */
    private double score() {
        int score = 0;
        for (Tamagoshi t : allTamagoshis)
            score += t.getAge();
        return score * 100 / (Tamagoshi.getLifeTime() * allTamagoshis.size());
    }

    /**
     * Méthode appelée lors de l'initialization du projet
     */
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

    /**
     * Méthode permettant d'afficher le bilan de la partie
     */
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

    /**
     * Méthode d'initialization qui mets en place le jeu
     */
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
        nbTama=Integer.parseInt(input.getText());
        if((nbTama > 0) && (nbTama <= 5)) {
            for (int i = 0; i < nbTama ; i++) {
                initialiser(i);
            }
            aliveTamagoshis = new ArrayList<Tamagoshi>(allTamagoshis);
            avoirManger=false;
            avoirJouer=false;
            console.setText(console.getText()+ "\n" + "-------------" + (cycle+=1) + "-------------");
            console.setText(console.getText()+ "\n" +"QuelTamagoshiNourrir et avec qui jouer ?");
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le nombre de Tamagoshi doit être compris entre 1 et 5");
            alert.showAndWait();
            Stage stage = (Stage) console.getScene().getWindow();
            stage.close();
        }
    }
    public void quitter(ActionEvent actionEvent) {
        Platform.exit();
    }
}