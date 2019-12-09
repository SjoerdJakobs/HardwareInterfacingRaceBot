package StateMachine.States;

import Modules.Engine;
import Modules.Phototransistor;
import StateMachine.State;
import StateMachine.StateID;

public class FollowLine extends State
{
    private boolean hasLine;
    private Engine engine;

    Phototransistor pin0 = new Phototransistor(0);
    Phototransistor pin1 = new Phototransistor(1);
    Phototransistor pin2 = new Phototransistor(2);
    Phototransistor pin3 = new Phototransistor(3);

    int lastDetectedPin = 0;

    public FollowLine(StateID stateID, Engine engine)
    {
        super(stateID);
        this.engine = engine;
    }

    @Override
    protected void enter()
    {
        super.enter();
        engine.SetTargetSpeed(80, 0);
        hasLine = true;
        System.out.println("entered");
        if(pin0.detectLine()||pin1.detectLine())
        {
            engine.Steer(false, 0.95);
        }
        else
        {
            engine.Steer(true, 0.95);
        }
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

        engine.drive();
    }

    @Override
    protected void leave()
    {
        super.leave();
    }
}
