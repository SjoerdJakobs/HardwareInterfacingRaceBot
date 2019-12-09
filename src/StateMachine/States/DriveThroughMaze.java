package StateMachine.States;

import Modules.Ultrasonic;
import Modules.Engine;
import Modules.Phototransistor;
import StateMachine.State;
import StateMachine.StateID;
import TI.BoeBot;

public class DriveThroughMaze extends State {

    public DriveThroughMaze(StateID stateID, Engine engine)
    {
        super(stateID);
        this.engine = engine;
    }

    private boolean hasLine;
    private Engine engine;
    private boolean currentTurning = false;

    Phototransistor pin0 = new Phototransistor(0); // links
    Phototransistor pin1 = new Phototransistor(1);
    Phototransistor pin2 = new Phototransistor(2);
    Phototransistor pin3 = new Phototransistor(3); // rechts
    Ultrasonic uLeft = new Ultrasonic(4, 5); //links
    Ultrasonic uMid = new Ultrasonic(6, 7); //midden
    Ultrasonic uRight = new Ultrasonic(8, 9); //rechts

    @Override
    protected void enter()
    {
        super.enter();
        engine.SetTargetSpeed(80, 0);
        hasLine = true;
        System.out.println("entered");
        // write code here
    }

    @Override
    protected void CheckForStateSwitch()
    {
        super.CheckForStateSwitch();
    }

    @Override
    protected void logic()
    {
        super.logic();

        if (!currentTurning) {

            if (pin0.detectLine() && pin1.detectLine() && pin2.detectLine() && pin3.detectLine()) {
                System.out.println("All lines detected.");

                if (uLeft.getDistance() > 100) {
                    engine.Steer(true, 2);
                    currentTurning = true;
                    System.out.println("Links geen muur gedetecteerd.");
                }
                else if (uMid.getDistance() > 100) {
                    engine.Steer(true, 0);
                    currentTurning = false;
                    System.out.println("Rechtdoor geen muur gedecteerd.");
                }
                else if (uRight.getDistance() > 100) {
                    engine.Steer(false, 2);
                    currentTurning = true;
                    System.out.println("Rechts geen muur gedetecteerd.");
                }
                else {
                    engine.Steer(false, 2);
                    currentTurning = true;
                    System.out.println("Drie mure gedetecteerd.");
                }
                BoeBot.wait(1000); // To prevent the BoeBot from detecting the line when starting to turn
            }

            // Make sure to stay on the line while not turning
            if (pin1.detectLine() || pin2.detectLine()) {
                engine.Steer(true, 0);
            }
            else if (pin2.detectLine()) {
                engine.Steer(false, 0.6);
            }
            else if (pin1.detectLine()) {
                engine.Steer(true, 0.6);
            }
            else if (pin0.detectLine() || pin1.detectLine()) {
                engine.Steer(true, 0.75);
            }
            else if (pin2.detectLine() || pin3.detectLine()) {
                engine.Steer(false, 0.75);
            }
            else if (pin3.detectLine()) {
                engine.Steer(false, 0.99);
            }
            else if (pin0.detectLine()) {
                engine.Steer(true, 0.99);
            }

        }

        // When turning, continue detecting a line after which the turning will stop
        if (currentTurning) {

            if (pin1.detectLine() || pin2.detectLine()) {
                currentTurning = false;
                engine.Steer(false, 0);
                System.out.println("Twee middelste sensoren gedetecteerd.");
            }

        }

        engine.drive();
    }

    @Override
    protected void leave()
    {
        super.leave();
    }
}