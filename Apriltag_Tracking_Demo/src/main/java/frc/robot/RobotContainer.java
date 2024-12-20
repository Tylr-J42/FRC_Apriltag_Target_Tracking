// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.constants.OIConstants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;

public class RobotContainer {
    private CommandXboxController driver;

    private Drivetrain drivetrain;
    private Vision vision;

    private static final String kAutoTabName = "Autonomous";
    
    public RobotContainer() {
        driver = new CommandXboxController(0);
        drivetrain = new Drivetrain(new Pose2d());
        vision = new Vision();

        configureBindings();
        shuffleboardSetup();
    }

    private void shuffleboardSetup(){
        ShuffleboardTab autoTab = Shuffleboard.getTab(kAutoTabName);

        //Always select the auto tab on startup
        Shuffleboard.selectTab(kAutoTabName);

        autoTab.addDouble("tx", vision::getTag3tx);
        autoTab.addDouble("ty", vision::getTag3ty);
    }

    private void configureBindings() {
        drivetrain.setDefaultCommand(
            drivetrain.teleopCommand(
                () -> { return driver.getLeftY();}, 
                () -> { return driver.getLeftX();}, 
                () -> { return driver.getRightX();}, 
                OIConstants.kTeleopDriveDeadband
            )
        );
    }

    //public Command getAutonomousCommand() {
     //   return drivetrain.trackApriltag(vision::getTag3tx);
    //}

}
