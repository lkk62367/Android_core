package org.ros.android.android_tutorial_control;


import android.util.Log;

import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;
/**
 * Created by william on 2017/1/6.
 */

public class Listener extends AbstractNodeMain {

    public static String status;
    private byte ss;
    private Callback mCallBack;
    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("rosjava_tutorial_pubsub/listener");
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        Subscriber<actionlib_msgs.GoalStatusArray> subscriber = connectedNode.newSubscriber("move_base/status", actionlib_msgs.GoalStatusArray._TYPE);
        subscriber.addMessageListener(new MessageListener<actionlib_msgs.GoalStatusArray>() {
            @Override
            public void onNewMessage(actionlib_msgs.GoalStatusArray message) {
                if (message.getStatusList().size() >0) {
                    ss = message.getStatusList().get(0).getStatus();
                    status = String.valueOf(ss);
                    mCallBack.success(status);
                    Log.e("william", String.valueOf(ss));
                }else{
                    status = "No Data";
                    mCallBack.success(status);
                }
            }
        });
    }

    public void addCallBack(Callback callback){
        mCallBack = callback;
    }
    public interface Callback{
        public abstract void success(String status);

    }

}
