package Modules;

import TI.BoeBot;
import TI.Servo;

public class Engine
{
    public Engine()
    {
        shouldDrive = true;
    }

    Servo rightWheel    = new Servo(12);
    Servo leftWheel     = new Servo(13);

    public int leftSpeed    = 0;
    public int rightSpeed   = 0;

    public int currentSpeed = 0;
    public int targetSpeed  = 0;

    public double turnRate          = 0;
    public double accelerateSpeed   = 0;


    public boolean left;
    public boolean shouldDrive;

    public void drive()
    {
        //System.out.println("is driving");
        if(shouldDrive)
        {
            ChangeSpeed();
            TakeCorner();
            //System.out.println("rightSpeed" + rightSpeed);
            //System.out.println("leftSpeed" + leftSpeed);
            //System.out.println("currentSpeed" + currentSpeed);
            //System.out.println("targetSpeed" + targetSpeed);
            //System.out.println("turnRate" + turnRate);
            rightWheel.update(rightSpeed);
            leftWheel.update(leftSpeed);
        }
    }

    private void TakeCorner()
    {
        if (turnRate != 0) {
            if (!left) {
                rightSpeed = (int) (1500 - (currentSpeed * (turnRate+1)));
                leftSpeed = (int) (1500 + (currentSpeed * (1-turnRate)));
            }
            else
            {
                rightSpeed = (int) (1500 - (currentSpeed * (1- turnRate)));
                leftSpeed = (int) (1500 + (currentSpeed * (turnRate+1)));
            }
        }
    }

    private void ChangeSpeed()
    {
        if(currentSpeed != targetSpeed)
        {
            if(accelerateSpeed == 0)
            {
                currentSpeed = targetSpeed;
            }
            else
            {
                currentSpeed += accelerateSpeed;
                if(currentSpeed > targetSpeed)
                {
                    currentSpeed = targetSpeed;
                }
            }
            rightSpeed  = 1500 - currentSpeed;
            leftSpeed   = 1500 + currentSpeed;
        }
    }

    public void SetTargetSpeed(int speed, double accelerationSpeed)
    {
        targetSpeed = speed;
        if(targetSpeed < currentSpeed)
        {
            accelerateSpeed = (accelerationSpeed * -1);
        }
    }

    public void Steer(boolean left, double turnRate)
    {
        this.left = left;
        this.turnRate = turnRate;
    }
}

