package frc.robot.subsystems;

import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase{

    private DoubleSubscriber tag3tx;
    private DoubleSubscriber tag3ty;

    public Vision(){
        NetworkTableInstance inst = NetworkTableInstance.getDefault();

        NetworkTable visionTable = inst.getTable("Fiducial");

        tag3tx = visionTable.getDoubleTopic("tag3tx").subscribe(0.0);
        tag3ty = visionTable.getDoubleTopic("tag3ty").subscribe(0.0);
    }

    public double getTag3tx(){
        return tag3tx.get();
    }

    public double getTag3ty(){
        return tag3ty.get();
    }
    
}
