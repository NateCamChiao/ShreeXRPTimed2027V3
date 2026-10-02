package first.robot.mechanisms;

import static org.wpilib.units.Units.Seconds;

import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.xrp.XRPServo;

public class Arm implements Mechanism {
    private XRPServo armMotor;

    public Arm() {
        armMotor = new XRPServo(4);
    }

    public Command rotateArm(double angle) {
        return run(coroutine -> {
            armMotor.setAngle(angle);
            coroutine.park();
        }).named("Rotate Arm Command");
    }

    public Command defaultCommand() {
        return run(coroutine -> {
            armMotor.setAngle(0);
            coroutine.park();
        }).named("Default Command for Arm Mech");
    }

    public Command rotateAtRPS(double rps) {
        double stepPerLoop = rps * 0.2;
        return run(coroutine -> {
            while (true) {
                double currentAngle = armMotor.getAngle();
                armMotor.setAngle(currentAngle + stepPerLoop);
                coroutine.yield();
            }

        }).named("Rotate At RPS");
    }

}
