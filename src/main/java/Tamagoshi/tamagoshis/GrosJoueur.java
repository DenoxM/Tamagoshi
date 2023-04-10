package Tamagoshi.tamagoshis;

public class GrosJoueur extends Tamagoshi {

	public GrosJoueur(String name) {
		super(name);
	}

	@Override
	public boolean consommeFun() {
		setFun(getFun()-1);
		return super.consommeFun();
	}

}
