package StateMachine.States;

import Modules.Engine;
import Modules.Phototransistor;
import StateMachine.State;
import StateMachine.StateID;

public class DriveThroughMaze extends State {

    public DriveThroughMaze(StateID stateID, Engine engine)
    {
        super(stateID);
        this.engine = engine;
    }

    private boolean hasLine;
    private Engine engine;

    Phototransistor pin0 = new Phototransistor(0);
    Phototransistor pin1 = new Phototransistor(1);
    Phototransistor pin2 = new Phototransistor(2);
    Phototransistor pin3 = new Phototransistor(3);

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
        if(pin1.detectLine()||pin2.detectLine())
        {
            // write code here
        }
        else if(pin2.detectLine())
        {
            // write code here
        }
        else if(pin1.detectLine())
        {
            // write code here
        }
        else if(pin0.detectLine()||pin1.detectLine())
        {
            // write code here
        }
        else if(pin2.detectLine()||pin3.detectLine())
        {
            // write code here
        }
        else if(pin3.detectLine())
        {
            // write code here
        }
        else if(pin0.detectLine())
        {
            // write code here
        }
        engine.drive();
    }

    @Override
    protected void leave()
    {
        super.leave();
    }
}