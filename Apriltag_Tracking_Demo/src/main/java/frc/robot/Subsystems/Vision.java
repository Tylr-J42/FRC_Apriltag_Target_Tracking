package frc.robot.Subsystems;

import java.util.function.DoubleSupplier;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase{

    private NetworkTable visionTable = NetworkTableInstance.getDefault().getTable("Fiducial");

    private NetworkTableEntry tag3tx;
    private NetworkTableEntry tag3ty;

    public Vision(){

        tag3tx = visionTable.getEntry("tag3tx");
        tag3ty = visionTable.getEntry("tag3ty");
        
    }

    public DoubleSupplier getTag3tx(){
        return () -> tag3tx.getDouble(0.0);
    }

    public DoubleSupplier getTag3ty(){
        return () -> tag3ty.getDouble(0.0);
    }
    
}
