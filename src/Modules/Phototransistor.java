package Modules;

import TI.BoeBot;

public class Phototransistor {

    private int pin;

    public Phototransistor(int pin) throws IllegalArgumentException {
        if (pin < 0)
            throw new IllegalArgumentException("Phototransistor pin is invalid: below 0.");
        if (pin > 15)
            throw new IllegalArgumentException("Phototransistor pin is invalid: above 15.");

        this.pin = pin;
    }

    public int detectLightLevel() { return BoeBot.analogRead(pin); }

    public boolean detectLine() { return (BoeBot.analogRead(pin) > 1350); }
}
