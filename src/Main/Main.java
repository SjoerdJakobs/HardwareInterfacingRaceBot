package Main;

import TI.Servo;

public class Main
{
    public static void main(String[] args)
    {
        /*
        Servo rightWheel    = new Servo(14);
        Servo leftWheel     = new Servo(15);

        rightWheel.update(1500);
        leftWheel.update(1500);

        while (true)
        {
            System.out.println("hey");
        }*/
        Program program = new Program();
        program.run();
    }
}
