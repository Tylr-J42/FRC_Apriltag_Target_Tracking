package frc.robot.Subsystems;

import edu.wpi.first.networktables.BooleanSubscriber;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase{

    private DoubleSubscriber cam1tag3tx;
    private DoubleSubscriber cam1tag3ty;
    private DoubleSubscriber cam2tag3tx;
    private DoubleSubscriber cam2tag3ty;
    private BooleanSubscriber cam1TagDetected;
    private BooleanSubscriber cam2TagDetected;

    private DoubleSubscriber framerate;

    public Vision(){
        NetworkTableInstance inst = NetworkTableInstance.getDefault();

        NetworkTable visionTable = inst.getTable("Fiducial");

        cam1tag3tx = visionTable.getDoubleTopic("cam1tag3tx").subscribe(0.0);
        cam1tag3ty = visionTable.getDoubleTopic("cam1tag3ty").subscribe(0.0);

        cam2tag3tx = visionTable.getDoubleTopic("cam2tag3tx").subscribe(0.0);
        cam2tag3ty = visionTable.getDoubleTopic("cam2tag3ty").subscribe(0.0);

        cam1TagDetected = visionTable.getBooleanTopic("cam1_visible").subscribe(false);
        cam2TagDetected = visionTable.getBooleanTopic("cam2_visible").subscribe(false);

        framerate = visionTable.getDoubleTopic("fps").subscribe(0.0);
    }

    public double getCam1Tag3tx(){
        return cam1tag3tx.get();
    }

    public double getCam1Tag3ty(){
        return cam1tag3ty.get();
    }

    public double getCam2Tag3tx(){
        return cam2tag3tx.get();
    }

    public double getCam2Tag3ty(){
        return cam2tag3ty.get();
    }

    public boolean getCam1Detected(){
        return cam1TagDetected.get();
    }

    public boolean getCam2Detected(){
        return cam2TagDetected.get();
    }

    public double getFPS(){
        return framerate.get();
    }

    public double getTargetOffset(){
        if(getCam1Detected() && !getCam2Detected()){
            return getCam1Tag3ty() + 35.0;
        }else if(!getCam1Detected() && getCam2Detected()){
            return getCam2Tag3ty() - 35.0;
        }else if(getCam1Detected() && getCam2Detected()){
            return getCam1Tag3ty() + 35.0;
        }else{
            return 0.0;
        }
    }
    
}
