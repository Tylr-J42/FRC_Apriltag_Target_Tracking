package frc.robot.Subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.RobotController;
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
    
    //private ADXRS450_Gyro gyro;

    private PIDController turningPID;

    public Drivetrain(){
        frontLeft = new WPI_VictorSPX(3);
        frontRight = new WPI_VictorSPX(2);
        backLeft = new WPI_VictorSPX(4);
        backRight = new WPI_VictorSPX(1);

        backLeft.follow(frontLeft);
        backRight.follow(frontRight);

        backRight.setInverted(true);
        frontRight.setInverted(true);

        drivetrain = new DifferentialDrive(frontLeft, frontRight);

        //gyro = new ADXRS450_Gyro();

        turningPID = new PIDController(driveConstants.kturningP, driveConstants.kturningI, driveConstants.kturningD);
    }

    public Command arcadeDrive(DoubleSupplier x, DoubleSupplier z){
        return run(() -> {
            drivetrain.arcadeDrive(x.getAsDouble(), z.getAsDouble());
        });
    }

    public Command tankDrive(DoubleSupplier left, DoubleSupplier right){
        return run(() -> {
            drivetrain.tankDrive(left.getAsDouble(), right.getAsDouble());
        });
    }

    public void tankDriveVoltage(double left, double right){
            frontLeft.set(
                VictorSPXControlMode.PercentOutput,
                MathUtil.clamp(left, -8, 8)/RobotController.getBatteryVoltage()
                );

            frontRight.set(
                VictorSPXControlMode.PercentOutput,
                MathUtil.clamp(right, -8, 8)/RobotController.getBatteryVoltage()
                );
    }

    public Command trackApriltag(DoubleSupplier zError){
        return run(() -> {
            tankDriveVoltage(
                // turningPID.calculate(gyro.getAngle(), gyro.getAngle()+zError.getAsDouble()),
                //-turningPID.calculate(gyro.getAngle(), gyro.getAngle()+zError.getAsDouble())
                turningPID.calculate(0.0, zError.getAsDouble()),
                -turningPID.calculate(0.0, zError.getAsDouble())
            );
        });
    }

    public double getTurningPIDOutput(){
        return frontLeft.getMotorOutputVoltage();
    }
}
