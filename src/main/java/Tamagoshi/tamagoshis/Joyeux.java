package Tamagoshi.tamagoshis;

public class Joyeux extends Tamagoshi {
    /**
     * @param name
     */
    public Joyeux(String name) {
        super(name);
    }

    @Override
    public boolean consommeFun() {
        setFun(getFun());
        return super.consommeFun();
    }
}
