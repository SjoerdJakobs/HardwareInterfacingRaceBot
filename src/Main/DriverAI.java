package Main;

import Modules.Engine;
import OOFramework.FrameworkProgram;
import OOFramework.StandardObject;
import StateMachine.*;
import StateMachine.States.*;

public class DriverAI extends StandardObject
{
    private StateMachine stateMachine;
    private Engine engine;

    private FindLine findLineState;
    private FollowLine followLineState;

    public DriverAI(FrameworkProgram frameworkProgram) {
        super(frameworkProgram);
    }

    public DriverAI(FrameworkProgram frameworkProgram,
                    boolean usesInput, boolean usesMain,
                    boolean usesRenderer, boolean startsActivated,
                    Engine engine, StateMachine stateMachine) {

        super(frameworkProgram, usesInput, usesMain, usesRenderer, startsActivated);
        this.stateMachine = stateMachine;
        this.engine = engine;

        MakeStates();
        //stateMachine.SetState(StateID.FollowLine);
        stateMachine.SetState(StateID.DriveThroughMaze);
        //System.out.println("the state should be started");
    }

    void MakeStates() {
        stateMachine.AddState( StateID.FindLine, new FindLine(StateID.FindLine,engine));
        stateMachine.AddState( StateID.FollowLine, new FollowLine(StateID.FollowLine,engine));
        stateMachine.AddState( StateID.DriveThroughMaze, new DriveThroughMaze2(StateID.DriveThroughMaze, engine));
    }

    @Override
    protected void mainLoop(double deltaTime) {
        super.mainLoop(deltaTime);
        engine.drive();


    }

    @Override
    protected void destroy() {
        super.destroy();
    }
}
