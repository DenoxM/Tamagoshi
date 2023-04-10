package Tamagoshi.jeu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import Tamagoshi.tamagoshis.GrosJoueur;
import Tamagoshi.tamagoshis.GrosMangeur;
import Tamagoshi.util.Utilisateur;
import Tamagoshi.tamagoshis.Tamagoshi;
import java.util.Random;

/**
 * un petit jeu avec des tamagoshis
 */
public class TamaGame {

	/**
	 * contains all the initial tamagoshis
	 */
	private List<Tamagoshi> allTamagoshis;
	/**
	 * contains only alive tamagoshis
	 */
	private List<Tamagoshi> aliveTamagoshis;

	/** build the game */
	private TamaGame() {
		allTamagoshis = new ArrayList<Tamagoshi>();
		aliveTamagoshis = new ArrayList<Tamagoshi>();
		initialisation();
	}

	@SuppressWarnings("unchecked")
	private void initialisation() {
		System.out.println("Entrez le nombre de tamagoshis désiré !");
		int n = 0;
		while (n < 1) {
			System.out.println("Saisisez un nombre > 0 :");
			try {
				n = Integer.parseInt(Utilisateur.saisieClavier());
			} catch (NumberFormatException e) {
				System.out.println("Il faut saisir un nombre !");
			}
		}
		boolean randomNames = true;
	    System.out.println("Voulez-vous choisir les noms de vos tamagoshis ? (y/n)");
	    String answer = Utilisateur.saisieClavier();
	    if (answer.toLowerCase().equals("y")) {
	        randomNames = false;
	    }
		for (int i = 0; i < n; i++) {
			if (randomNames) {
	            if (Math.random() < .5)
	                allTamagoshis.add(new GrosJoueur(getRandomName()));
	            else
	                allTamagoshis.add(new GrosMangeur(getRandomName()));
	        } else {
	        	System.out.println("Entrez le nom du tamagoshi numéro " + i + " : ");
	            String name = Utilisateur.saisieClavier();
	            if (Math.random() < .5)
	                allTamagoshis.add(new GrosJoueur(name));
	            else
	                allTamagoshis.add(new GrosMangeur(name));
	        }

		}
//		aliveTamagoshis = (List<Tamagoshi>) allTamagoshis.clone();
		// ou encore pour le même résultat
		 aliveTamagoshis = new ArrayList<Tamagoshi>(allTamagoshis);
	}

	/**
	 * returns the selected tamagoshi
	 * 
	 * @param question the question to ask to the user
	 * @return the selected instance
	 */
	private Tamagoshi tamaSelection(String question) {
		System.out.println(question);
		int index = 0;
		for (ListIterator<Tamagoshi> iterator = aliveTamagoshis.listIterator(); iterator.hasNext();) {
			System.out.print("\t(" + (iterator.nextIndex()) + ") " + iterator.next().getName() + "  ");
		}
		System.out.print("\n\tEntrez un choix : ");
		try {
			index = Integer.parseInt(Utilisateur.saisieClavier());
		} catch (NumberFormatException e) {
			System.out.println("Il faut saisir un nombre !");
			return tamaSelection(question);
		}
		if (index < 0 || index >= aliveTamagoshis.size()) {
			System.out.println("il n'y a pas de tamagoshi portant le numéro " + index);
			return tamaSelection(question);
		}
		return aliveTamagoshis.get(index);
	}
	
	 private String getRandomName() {     
		 String[] names = {"Bob", "Jean", "Louis", "Alfred", "Batman", "JSP", "Gilles", "Mario", "Louigi", "Random"};     
		 int randomIndex = (int) (Math.random() * names.length);     
		 return names[randomIndex]; 
		  }

	/**
	 * Starts the game
	 */
	public void play() {
		int cycle = 1;
		while (! aliveTamagoshis.isEmpty()) {
			System.out.println("\n------------Cycle n°" + (cycle++) + "-------------");
			for (Tamagoshi t : aliveTamagoshis)
				t.parle();
			tamaSelection("\n\tNourrir quel tamagoshi ?").mange();
			tamaSelection("\n\tJouer avec quel tamagoshi ?").joue();
			
			for (Iterator<Tamagoshi> iterator = aliveTamagoshis.iterator(); iterator.hasNext();) {
				Tamagoshi t = iterator.next();
				if (!t.consommeEnergy() || !t.consommeFun() || t.vieillit())
					iterator.remove();
			}
		}
		System.out.println("\n\t--------- fin de partie !! ----------------\n\n");
		resultat();
	}

	private double score() {
		int score = 0;
		for (Tamagoshi t : allTamagoshis)
			score += t.getAge();
		return score * 100 / (Tamagoshi.getLifeTime() * allTamagoshis.size());
	}

	private void resultat() {
		System.out.println("-------------bilan------------");
		for (Tamagoshi t : allTamagoshis) {
			String classe = t.getClass().getSimpleName();
			System.out.println(t.getName() + " qui était un " + classe + " "
					+ (t.getAge() == Tamagoshi.getLifeTime() ? " a survécu et vous remercie :)"
							: " n'est pas arrivé au bout et ne vous félicite pas :("));
		}
		System.out.println("\nniveau de difficulté : " + allTamagoshis.size() + ", score obtenu : " + score() + "%");
	}

	/** Launch a new instance of the game */
	public static void main(String[] args) {
		TamaGame jeu = new TamaGame();
		jeu.play();
	}
	
	@Override
	public String toString() {
		return "tamagame";
	}

}
