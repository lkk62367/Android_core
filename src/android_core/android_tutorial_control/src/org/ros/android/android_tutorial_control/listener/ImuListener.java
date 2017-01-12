package org.ros.android.android_tutorial_control.listener;

import android.util.Log;

import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

import actionlib_msgs.GoalStatusArray;

/**
 * Created by william on 2017/1/12.
 */

public class ImuListener extends AbstractNodeMain {


    private String status;
    private float ss;
    private ImuCallback mImuCallBack;

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("adamgo/imulistener");
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        Subscriber<std_msgs.Float32> subscriber = connectedNode.newSubscriber("Imu_hz", std_msgs.Float32._TYPE);
        subscriber.addMessageListener(new MessageListener<std_msgs.Float32>() {
            @Override
            public void onNewMessage(std_msgs.Float32 message) {
                ss =  message.getData();
                status = String.valueOf(ss);
                mImuCallBack.imusuccess(status);
                Log.e("Imu", status);
            }
        });
    }

    public void addCallBack(ImuCallback callback){
        mImuCallBack = callback;
    }
    public interface ImuCallback{
        public abstract void imusuccess(String status);

    }
}
