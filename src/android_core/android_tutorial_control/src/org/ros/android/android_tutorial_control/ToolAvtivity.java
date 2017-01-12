package org.ros.android.android_tutorial_control;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.ros.android.MessageCallable;
import org.ros.android.RosActivity;
import org.ros.android.android_tutorial_control.listener.ImuListener;
import org.ros.android.android_tutorial_control.listener.LaserListener;
import org.ros.android.android_tutorial_control.listener.WheelListener;
import org.ros.android.view.RosTextView;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;




/**
 * Created by william on 2017/1/9.
 */

public class ToolAvtivity extends RosActivity implements Button.OnClickListener, Listener.Callback, ImuListener.ImuCallback, LaserListener.LaserCallback
                                                            ,WheelListener.WheelCallback{

    private TextView  lasertextview, wheeltextview;
    private Button teston, testoff;
    private ToolTalker tooltalker;
    private Listener listener;
    private ImuListener imuListener;
    private LaserListener laserListener;
    private WheelListener wheelListener;
    private TextView statustext,imutextview;
    private String mstatus, imutext, lasertext, wheelfbtext;



    public ToolAvtivity() {
        // The RosActivity constructor configures the notification title and ticker
        // messages.
        super("Tool Mode", "Tool Mode");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tool_layout);

        teston = (Button) findViewById(R.id.TestON);
        teston.setOnClickListener(this);

        testoff = (Button) findViewById(R.id.TestOFF);
        testoff.setOnClickListener(this);
        testoff.setEnabled(false);

        imutextview = (TextView) findViewById(R.id.subimu);
        statustext = (TextView) findViewById(R.id.status);
        lasertextview = (TextView) findViewById(R.id.sublaser);
        wheeltextview = (TextView) findViewById(R.id.subwheelfb);


    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {

        tooltalker = new ToolTalker();
        listener = new Listener();
        listener.addCallBack(this);
        imuListener = new ImuListener();
        imuListener.addCallBack(this);
        laserListener = new LaserListener();
        laserListener.addCallBack(this);
        wheelListener = new WheelListener();
        wheelListener.addCallBack(this);

        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(getRosHostname());
        nodeConfiguration.setMasterUri(getMasterUri());

        nodeMainExecutor.execute(tooltalker, nodeConfiguration);
        nodeMainExecutor.execute(listener, nodeConfiguration);
        nodeMainExecutor.execute(imuListener, nodeConfiguration);
        nodeMainExecutor.execute(laserListener, nodeConfiguration);
        nodeMainExecutor.execute(wheelListener, nodeConfiguration);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.TestON) {
            String msg = "6";
            String toasts = "Test ON";
            fun1(msg, toasts);
            teston.setEnabled(false);
            testoff.setEnabled(true);
        } else if (v.getId() == R.id.TestOFF) {
            String msg = "7";
            String toasts = "Test OFF";
            fun1(msg, toasts);
            teston.setEnabled(true);
            testoff.setEnabled(false);
        }
    }


    public void fun1(String msg, String toasts) {
        tooltalker.setVar(msg);
        Toast.makeText(this, toasts, Toast.LENGTH_SHORT).show();
    }



    @Override
    public void success(String status) {
        mstatus = status;

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mstatus.compareTo("0") == 0){
                    statustext.setText("PENDING");
                } else if (mstatus.compareTo("1") == 0){
                    statustext.setText("ACTIVE");
                }else if (mstatus.compareTo("2") == 0){
                    statustext.setText("PREEMPTED");
                }else if (mstatus.compareTo("3") == 0){
                    statustext.setText("SUCCEEDED");
                }else if (mstatus.compareTo("4") == 0){
                    statustext.setText("ABORTED");
                }else if (mstatus.compareTo("5") == 0){
                    statustext.setText("REJECTED");
                }else if (mstatus.compareTo("6") == 0){
                    statustext.setText("PREEMPTING");
                }else if (mstatus.compareTo("7") == 0){
                    statustext.setText("RECALLING");
                }else if (mstatus.compareTo("8") == 0){
                    statustext.setText("RECALLED");
                }else if (mstatus.compareTo("9") == 0){
                    statustext.setText("LOST");
                }else if (mstatus.compareTo("") == 0){
                    statustext.setText("ERROR");
                }else{
                    statustext.setText(mstatus);
                }
            }
        });

    }

    @Override
    public void imusuccess(String status) {
        imutext=status;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imutextview.setText(imutext);
            }
        });
    }


    @Override
    public void lasersuccess(String status) {
        lasertext = status;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lasertextview.setText(lasertext);
            }
        });

    }

    @Override
    public void wheelsuccess(String status) {
        wheelfbtext = status;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                wheeltextview.setText(wheelfbtext);
            }
        });
    }
}




