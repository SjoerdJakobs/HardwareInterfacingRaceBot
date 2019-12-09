package StateMachine.States;

import Modules.Ultrasonic;
import Modules.Engine;
import Modules.Phototransistor;
import StateMachine.State;
import StateMachine.StateID;
import TI.BoeBot;

public class DriveThroughMaze2 extends State {

    public DriveThroughMaze2(StateID stateID, Engine engine)
    {
        super(stateID);
        this.engine = engine;
    }

    private boolean left;
    private boolean right;
    private boolean forward;

    int turn = 0;
    int turnCounter = 0;

    private boolean hasLine;
    private Engine engine;
    private boolean currentTurning = false;

    int lastDetectedPin = 0;

    Phototransistor pin0 = new Phototransistor(0); // links
    Phototransistor pin1 = new Phototransistor(1);
    Phototransistor pin2 = new Phototransistor(2);
    Phototransistor pin3 = new Phototransistor(3); // rechts
    Ultrasonic uLeft = new Ultrasonic(5, 4); //links
    Ultrasonic uMid = new Ultrasonic(7, 6); //midden
    Ultrasonic uRight = new Ultrasonic(9, 8); //rechts

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
                left = uLeft.detectWall();
                right = uRight.detectWall();
                forward = uMid.detectWall();

                if(left&&right&&forward)
                {
                    turn = 0;
                }
                else if(left&&right)
                {
                    turn = 1;
                }
                else if(left&&forward)
                {
                    turn = 0;
                }
                else if(forward&&right)
                {
                    turn = 2;
                }
                currentTurning = true;
            }

            if(pin1.detectLine()&&pin2.detectLine())
            {
                System.out.println("pin 1 and 2");
                engine.Steer(true, 0);
                engine.SetTargetSpeed(500, 0);
            }
            else if(pin0.detectLine()&&pin1.detectLine())
            {
                System.out.println("pin 0 and 1");
                engine.Steer(true, 0.8);

                engine.SetTargetSpeed(200, 0);
            }
            else if(pin2.detectLine()&&pin3.detectLine())
            {
                System.out.println("pin 2 and 3");
                engine.Steer(false, 0.8);
                engine.SetTargetSpeed(200, 0);
            }
            else if(pin2.detectLine())
            {
                System.out.println("pin 2");
                engine.Steer(false, 0.70);
                engine.SetTargetSpeed(200, 0);
                lastDetectedPin = 2;
            }
            else if(pin1.detectLine())
            {
                System.out.println("pin 1");
                engine.Steer(true, 0.70);
                engine.SetTargetSpeed(200, 0);
                lastDetectedPin = 1;
            }
            else if(pin3.detectLine())
            {
                System.out.println("pin 3");
                engine.Steer(false, 1.6);
                engine.SetTargetSpeed(200, 0);
                lastDetectedPin = 3;
            }
            else if(pin0.detectLine())
            {
                System.out.println("pin 0");
                engine.Steer(true, 1.6);
                engine.SetTargetSpeed(200, 0);
                lastDetectedPin = 0;
            }
            else if(lastDetectedPin == 2||lastDetectedPin == 3)
            {
                System.out.println("last pin 2 or 3");
                engine.Steer(false, 1.2);
                engine.SetTargetSpeed(200, 0);
            }
            else if(lastDetectedPin == 1 || lastDetectedPin == 0)
            {
                System.out.println("last pin 1 or 0");
                engine.Steer(true, 1.2);
                engine.SetTargetSpeed(200, 0);
            }

        }


        // When turning, continue detecting a line after which the turning will stop
        if (currentTurning) {
            engine.SetTargetSpeed(100, 0);
            engine.Steer(false, 3);
            if (pin0.detectLine()) {
                turnCounter = 1;
            }

            if(turnCounter == 1) {
                if (pin1.detectLine() || pin2.detectLine()) {
                    currentTurning = false;
                    turnCounter = 0;
                }
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