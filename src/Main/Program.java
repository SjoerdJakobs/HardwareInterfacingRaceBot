package Main;

import Modules.Engine;
import OOFramework.FrameworkProgram;

public class Program extends FrameworkProgram
{
    Program()
    {

    }

    @Override
    protected void start() {
        super.start();
        Engine engine = new Engine();
        Remote remote = new Remote(this,true,false,false,true);
        DriverAI driverAI = new DriverAI(this,true,false,false,true,engine,remote);
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