package Modules;

import TI.BoeBot;
import TI.Timer;

public class Ultrasonic {
    private int triggerPin; // On Sjoerd's en Dennis' BoeBot, pin = 1
    private int echoPin; // On Sjoerd's en Dennis' BoeBot, pin = 0

    private Timer waitForEcho;
    private Timer initialise = new Timer(1);

    private boolean isTriggerOn = false;

    private int distance;

    /**
     * Initialise the 2-pin mode ultrasonic sensor
     * @param triggerPin Pin to trigger the ultrasonic sensor
     * @param echoPin Pin to read the detected distance (between 100 and 9600)
     * @throws IllegalArgumentException Pins must be between 0 and 15
     */
    public Ultrasonic(int triggerPin, int echoPin) throws IllegalArgumentException {
        if (triggerPin < 0)
            throw new IllegalArgumentException("Ultrasonic trigger pin is invalid: below 0.");
        if (triggerPin > 15)
            throw new IllegalArgumentException("Ultrasonic trigger pin is invalid: above 15.");
        if (echoPin < 0)
            throw new IllegalArgumentException("Ultrasonic echo pin is invalid: below 0.");
        if (echoPin > 15)
            throw new IllegalArgumentException("Ultrasonic echo pin is invalid: above 15");

        this.triggerPin = triggerPin;
        this.echoPin = echoPin;
        this.waitForEcho = new Timer(5);
    }

    /**
     * Initialise the 1-pin mode ultrasonic sensor
     * @param echoTriggerPin 1-pin mode ultrasonic sensor pin
     * @throws IllegalArgumentException Pin must be between 0 and 15.
     */
    public Ultrasonic(int echoTriggerPin) throws IllegalArgumentException {
        if (echoTriggerPin < 0)
            throw new IllegalArgumentException("Echo-trigger pin is invalid: below 0.");
        if (echoTriggerPin > 15)
            throw new IllegalArgumentException("Echo-trigger pin is invalid: above 15.");

        this.triggerPin = echoTriggerPin;
        this.echoPin = echoTriggerPin;
        this.waitForEcho = new Timer(50);
    }

    /**
     * Detect the distance of an object in front of the ultrasonic sensor and save in millimeters
     */
    public int detectDistance() {
        if (waitForEcho.timeout()) {
            BoeBot.digitalWrite(this.triggerPin, true);
            isTriggerOn = true;
        }
        if (initialise.timeout() && isTriggerOn) {
            BoeBot.digitalWrite(this.triggerPin, false);
            isTriggerOn = false;
        }

        int pulse = BoeBot.pulseIn(echoPin, true, 10000);
        if (pulse > 0)
            // Distane is in millimeters
            this.distance = (int) Math.round(pulse / 5.82);

        return this.distance;
    }

    public boolean detectWall() {
        if (detectDistance() < 100) return true;
        else return false;
    }

    public int getDistance() { return this.distance; }
}