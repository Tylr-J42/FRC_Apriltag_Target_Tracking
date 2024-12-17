package frc.robot.Subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.driveConstants;

public class Drivetrain extends SubsystemBase{
    private DifferentialDrive drivetrain;

    private WPI_VictorSPX frontLeft;
    private WPI_VictorSPX frontRight;
    private WPI_VictorSPX backLeft;
    private WPI_VictorSPX backRight;
    
    private ADXRS450_Gyro gyro;

    private PIDController turningPID;

    public Drivetrain(){
        frontLeft = new WPI_VictorSPX(0);
        frontRight = new WPI_VictorSPX(1);
        backLeft = new WPI_VictorSPX(2);
        backRight = new WPI_VictorSPX(3);

        backLeft.follow(frontLeft);
        backRight.follow(frontRight);

        drivetrain = new DifferentialDrive(frontLeft, frontRight);

        gyro = new ADXRS450_Gyro();

        turningPID = new PIDController(driveConstants.kturningP, 0, 0);
    }

    public Command arcadeDrive(DoubleSupplier x, DoubleSupplier y){
        return run(() -> {
            drivetrain.arcadeDrive(x.getAsDouble(), y.getAsDouble());
        });
    }

    public Command tankDrive(DoubleSupplier left, DoubleSupplier right){
        return run(() -> {
            drivetrain.tankDrive(left.getAsDouble(), right.getAsDouble());
        });
    }

    public Command trackApriltag(DoubleSupplier zError){
        return run(() -> {
            drivetrain.tankDrive(
                turningPID.calculate(gyro.getAngle(), gyro.getAngle()+zError.getAsDouble()),
                -turningPID.calculate(gyro.getAngle(), gyro.getAngle()+zError.getAsDouble())
                );
        });
    }
}
