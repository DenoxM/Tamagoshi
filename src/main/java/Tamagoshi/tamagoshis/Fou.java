/**
 * 
 */
package Tamagoshi.tamagoshis;

/**
 * @author fab
 *
 */
public class Fou extends Tamagoshi {

	/**
	 * @param name
	 */
	public Fou(String name) {
		super(name);
	}


	@Override
	public boolean consommeEnergy() {
		energy -= 3;
		return energy > 0;
	}

	@Override
	public boolean consommeFun() {
		setFun(getFun()-3);
		return super.consommeFun();
	}

	@Override
	public boolean vieillit() {
		if (Math.random() > 0.5) {
			energy--;
		}
		if (Math.random() > 0.5) {
			setFun(getFun()-1);
		}
		return super.vieillit();
	}

}
