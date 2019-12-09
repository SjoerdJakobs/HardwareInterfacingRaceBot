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
        if(pin1.detectLine()||pin2.detectLine())
        {
            engine.Steer(true, 0);
            engine.SetTargetSpeed(550, 0);
        }
        else if(pin2.detectLine())
        {
            engine.Steer(false, 0.6);
            engine.SetTargetSpeed(200, 0);
        }
        else if(pin1.detectLine())
        {
            engine.Steer(true, 0.6);
            engine.SetTargetSpeed(200, 0);
        }
        else if(pin0.detectLine()||pin1.detectLine())
        {
            engine.Steer(true, 0.75);

            engine.SetTargetSpeed(100, 0);
        }
        else if(pin2.detectLine()||pin3.detectLine())
        {
            engine.Steer(false, 0.75);
            engine.SetTargetSpeed(100, 0);
        }
        else if(pin3.detectLine())
        {
            engine.Steer(false, 0.99);
            engine.SetTargetSpeed(50, 0);
        }
        else if(pin0.detectLine())
        {
            engine.Steer(true, 0.99);
            engine.SetTargetSpeed(50, 0);
        }
        engine.drive();
    }

    @Override
    protected void leave()
    {
        super.leave();
    }
}
