package StateMachine.States;

import Modules.Engine;
import Modules.Phototransistor;
import StateMachine.State;
import StateMachine.StateID;

public class FindLine extends State
{
    private boolean foundLine;
    private Engine engine;

    Phototransistor pin0 = new Phototransistor(0);
    Phototransistor pin1 = new Phototransistor(1);
    Phototransistor pin2 = new Phototransistor(2);
    Phototransistor pin3 = new Phototransistor(3);

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
        engine.SetTargetSpeed(100, 0);
    }

    @Override
    protected void CheckForStateSwitch()
    {
        super.CheckForStateSwitch();
        if(foundLine)
        {
            System.out.println("switch");
            stateMachine.SetState(StateID.FollowLine);
        }
    }

    private double counter = 7;
    private double counterLimit = 1;
    private double turnRate =  0.9;

    @Override
    protected void logic()
    {
        super.logic();
        counter += stateMachine.getDeltaTime();
        if(counter > counterLimit)
        {
            engine.Steer(true, turnRate);
            turnRate -= 0.06;
            counterLimit += 0.3;
            if(turnRate < 0)
            {
                turnRate = 0;
            }
            counter = 0;
        }
        if(pin0.detectLine() ||pin1.detectLine()||pin2.detectLine()||pin3.detectLine())
        {
            foundLine = true;
            System.out.println("switch2");
        }
        /*if(pin0.detectLightLevel() > 1300||pin1.detectLightLevel() > 1300||pin2.detectLightLevel() > 1300||pin3.detectLightLevel() > 1300)
        {
            System.out.println("switch3");
        }*/
        //System.out.println("0 = " + pin0.detectLightLevel());
        //System.out.println("1 = " + pin1.detectLightLevel() );
        //System.out.println("2 = " + pin2.detectLightLevel() );
        //System.out.println("3 = " + pin3.detectLightLevel() );

        engine.drive();
    }

    @Override
    protected void leave()
    {
        super.leave();

        System.out.println("executeState");
        engine.SetTargetSpeed(0, 0);
    }
}
