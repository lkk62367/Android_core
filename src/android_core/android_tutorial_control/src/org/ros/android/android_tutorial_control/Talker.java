package org.ros.android.android_tutorial_control;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;

/**
 * Created by williamhuang on 2016/11/25.
 */

public class Talker extends AbstractNodeMain {
    private String topic_name;

    public Talker() {
        this.topic_name = "control";
    }

    public Talker(String topic) {
        this.topic_name = topic;
    }

    public GraphName getDefaultNodeName() {
        return GraphName.of("rosjava_tutorial_pubsub/talker");
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
