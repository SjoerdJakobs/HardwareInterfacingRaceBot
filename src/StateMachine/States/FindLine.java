package StateMachine.States;

import Modules.Engine;
import StateMachine.State;
import StateMachine.StateID;

public class FindLine extends State
{
    private boolean foundLine;
    private Engine engine;

    public FindLine(StateID stateID, Engine engine)
    {
        super(stateID);
        this.engine = engine;
        foundLine = false;
    }

    @Override
    protected void enter()
    {
        super.enter();
        engine.SetTargetSpeed(250, 0);
    }

    @Override
    protected void CheckForStateSwitch()
    {
        super.CheckForStateSwitch();
        if(foundLine)
        {
            stateMachine.SetState(StateID.FollowLine);
        }
    }

    double counter = 7;
    double counterLimit = 1;
    double turnRate =  0.9;
    @Override
    protected void logic()
    {
        super.logic();
        counter += stateMachine.getDeltaTime();
        if(counter > counterLimit)
        {
            engine.Steer(true, turnRate);
            turnRate -= 0.02;
            counterLimit += 0.3;
            if(turnRate < 0)
            {
                turnRate = 0;
            }
            counter = 0;

        }
        engine.drive();
    }

    @Override
    protected void leave()
    {
        super.leave();
    }


}
