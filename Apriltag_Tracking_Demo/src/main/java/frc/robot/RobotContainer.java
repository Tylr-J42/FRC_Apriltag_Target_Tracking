// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Subsystems.Drivetrain;
import frc.robot.Subsystems.Vision;

public class RobotContainer {
    private CommandXboxController driver;

    private Drivetrain drivetrain;
    private Vision vision;
    
    public RobotContainer() {
        driver = new CommandXboxController(0);
        drivetrain = new Drivetrain();
        vision = new Vision();

        configureBindings();
    }

    private void configureBindings() {
        drivetrain.setDefaultCommand(drivetrain.arcadeDrive(
            () -> driver.getLeftY(), 
            () -> driver.getRightX()));

        driver.a().onTrue(drivetrain.trackApriltag(vision.getTag3ty()));
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}
