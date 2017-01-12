package org.ros.android.android_tutorial_control.listener;

import android.util.Log;

import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;


/**
 * Created by william on 2017/1/12.
 */

public class LaserListener extends AbstractNodeMain {

    private String status;
    private float ss;

    private LaserCallback mLaserCallBack;

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("adamgo/laserlistener");
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        Subscriber<std_msgs.Float32> subscriber = connectedNode.newSubscriber("LaserScan_hz", std_msgs.Float32._TYPE);
        subscriber.addMessageListener(new MessageListener<std_msgs.Float32>() {
            @Override
            public void onNewMessage(std_msgs.Float32 message) {
                ss =  message.getData();
                status = String.valueOf(ss);
                mLaserCallBack.lasersuccess(status);
                Log.e("LaserScan", status);
            }
        });
    }

    public void addCallBack(LaserCallback callback){
        mLaserCallBack = callback;
    }
    public interface LaserCallback{
        public abstract void lasersuccess(String status);

    }

}
