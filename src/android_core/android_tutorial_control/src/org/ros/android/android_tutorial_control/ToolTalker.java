package org.ros.android.android_tutorial_control;

import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

/**
 * Created by william on 2017/1/9.
 */

public class ToolTalker extends AbstractNodeMain {

    private String topic_name;

    public ToolTalker() {
        this.topic_name = "robot_utilities";
    }

    public ToolTalker(String topic) {
        this.topic_name = topic;
    }

    public GraphName getDefaultNodeName() {
        return GraphName.of("adamgo/tooltalker");
    }
    ConnectedNode connectedNode;
    Publisher publisher;

    public void onStart(ConnectedNode connectedNode) {
        publisher = connectedNode.newPublisher(this.topic_name, "std_msgs/String");
        std_msgs.String str = (std_msgs.String)publisher.newMessage();

//        connectedNode.executeCancellableLoop(new CancellableLoop() {
//            private int sequenceNumber;
//
//            protected void setup() {
//                this.sequenceNumber = 0;
//            }
//
//            protected void loop() throws InterruptedException {
//                std_msgs.String str = (std_msgs.String)publisher.newMessage();
//                str.setData(var);
//                publisher.publish(str);
//                ++this.sequenceNumber;
//                /*var = "";*/
//                Thread.sleep(1000L);
//            }
//        });
    }

    String var = "";

    public void setVar(String var){
        this.var = var;
        std_msgs.String str = (std_msgs.String)publisher.newMessage();
        str.setData(var);
        publisher.publish(str);
    }
}
