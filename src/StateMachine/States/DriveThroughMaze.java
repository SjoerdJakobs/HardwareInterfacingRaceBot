package StateMachine.States;

import Modules.Engine;
import Modules.Phototransistor;
import Modules.Ultrasonic;
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
    private int direction = 0; // -1 = left, 0 = mid, 1 = right, 2 = go back!
    private boolean currentTurning = false;
    private int numberOfTurns = 0;

    Phototransistor pin0 = new Phototransistor(0); // links
    Phototransistor pin1 = new Phototransistor(1);
    Phototransistor pin2 = new Phototransistor(2);
    Phototransistor pin3 = new Phototransistor(3); // rechts
    Ultrasonic uLeft = new Ultrasonic(6); //links
    Ultrasonic uMid = new Ultrasonic(7); //midden
    Ultrasonic uRight = new Ultrasonic(8); //rechts


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
                if (!uLeft.detectWall()) {
                    engine.Steer(true, 2);
                    currentTurning = true;
                }
                else if (!uMid.detectWall()) {
                    engine.Steer(true, 0);
                    currentTurning = false;
                }
                else if (!uRight.detectWall()) {
                    engine.Steer(false, 2);
                    currentTurning = true;
                }
                else {
                    engine.Steer(false, 2);
                    currentTurning = true;
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