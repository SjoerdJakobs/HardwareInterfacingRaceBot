package Main;

import Modules.Engine;
import OOFramework.FrameworkProgram;
import StateMachine.StateMachine;

public class Program extends FrameworkProgram
{
    Program()
    {

    }

    @Override
    protected void start() {
        super.start();
        Engine engine = new Engine();
        StateMachine stateMachine = new StateMachine(this);
        DriverAI driverAI = new DriverAI(this,true,false,false,true,engine,stateMachine);
    }

    @Override
    protected void addToLoop() {
        super.addToLoop();

    }

    @Override
    protected void exitProgram() {
        super.exitProgram();
    }
}
