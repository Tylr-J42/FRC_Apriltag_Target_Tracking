// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Subsystems.Drivetrain;
import frc.robot.Subsystems.Vision;

public class RobotContainer {
    private CommandXboxController driver;

    private Drivetrain drivetrain;
    private Vision vision;

    private static final String kAutoTabName = "Autonomous";
    
    public RobotContainer() {
        driver = new CommandXboxController(0);
        drivetrain = new Drivetrain();
        vision = new Vision();

        configureBindings();
        shuffleboardSetup();
    }

    private void shuffleboardSetup(){
        ShuffleboardTab autoTab = Shuffleboard.getTab(kAutoTabName);

        //Always select the auto tab on startup
        Shuffleboard.selectTab(kAutoTabName);

        autoTab.addDouble("cam1_tx", vision::getCam1Tag3tx);
        autoTab.addDouble("cam1_ty", vision::getCam1Tag3ty);
        autoTab.addBoolean("cam1_tag_detected", vision::getCam1Detected);
        autoTab.addBoolean("cam2_tag_detected", vision::getCam2Detected);
        autoTab.addDouble("cam2_tx", vision::getCam2Tag3tx);
        autoTab.addDouble("cam2_ty", vision::getCam2Tag3ty);
        autoTab.addDouble("FPS", vision::getFPS);
        autoTab.addDouble("Output Voltage", drivetrain::getTurningPIDOutput);
        autoTab.addDouble("gyro angle", drivetrain::getGyroAngle);
        autoTab.addDouble("Target Offset", vision::getTargetOffset);
    }

    private void configureBindings() {
        drivetrain.setDefaultCommand(drivetrain.arcadeDrive(
            () -> -driver.getLeftY(), 
            () -> driver.getRightX()));

        driver.a().onTrue(drivetrain.trackApriltag(vision::getTargetOffset));
    }

    public Command getAutonomousCommand() {
        return drivetrain.trackApriltag(vision::getTargetOffset);
    }

}
