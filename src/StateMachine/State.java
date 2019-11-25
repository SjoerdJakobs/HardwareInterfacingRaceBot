package StateMachine;

public class State
{
    public StateID      stateID;
    public StateMachine stateMachine;

    protected State(StateID stateID)
    {
        this.stateID = stateID;
    }

    protected void enter()
    {

    }

    protected void leave()
    {

    }

    protected void CheckForStateSwitch()
    {

    }

    protected void logic()
    {

    }
}
