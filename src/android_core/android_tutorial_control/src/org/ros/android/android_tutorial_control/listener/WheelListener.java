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

public class WheelListener extends AbstractNodeMain {
    private String status;
    private float ss;
    private WheelCallback mWheelCallBack;

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("adamgo/wheellistener");
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        Subscriber<std_msgs.Float32> subscriber = connectedNode.newSubscriber("WheelFb_hz", std_msgs.Float32._TYPE);
        subscriber.addMessageListener(new MessageListener<std_msgs.Float32>() {
            @Override
            public void onNewMessage(std_msgs.Float32 message) {
                ss =  message.getData();
                status = String.valueOf(ss);
                mWheelCallBack.wheelsuccess(status);
                Log.e("WheelFB", status);
            }
        });
    }

    public void addCallBack(WheelCallback callback){
        mWheelCallBack = callback;
    }
    public interface WheelCallback{
        public abstract void wheelsuccess(String status);

    }
}
