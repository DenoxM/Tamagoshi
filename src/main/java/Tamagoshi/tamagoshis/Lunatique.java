/**
 * 
 */
package Tamagoshi.tamagoshis;

/**
 * @author fab
 *
 */
public class Lunatique extends Tamagoshi {

	/**
	 * @param name
	 */
	public Lunatique(String name) {
		super(name);
	}

	/**
	 * @see Tamagoshi#consommeEnergie()
	 */
	@Override
	public boolean consommeEnergy() {
		if (generateur.nextBoolean())
			energy--;
		return super.consommeEnergy();
	}

	/**
	 * @see Tamagoshi#consommeFun()
	 */
	@Override
	public boolean consommeFun() {
		if (generateur.nextBoolean())
			setFun(getFun() - 1);
		return super.consommeFun();
	}

}
