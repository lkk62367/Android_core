package org.ros.android.android_tutorial_service;

/**
 * Created by william on 2016/12/13.
 */
import org.ros.exception.RemoteException;
import org.ros.exception.RosRuntimeException;
import org.ros.exception.ServiceNotFoundException;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.service.ServiceClient;
import org.ros.node.service.ServiceResponseListener;
public class Client extends AbstractNodeMain{
    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("rosjava_tutorial_services/client");
    }

    @Override
    public void onStart(final ConnectedNode connectedNode) {
        ServiceClient<rosjava_test_msgs.AddTwoIntsRequest, rosjava_test_msgs.AddTwoIntsResponse> serviceClient;
        try {
            serviceClient = connectedNode.newServiceClient("add_two_ints", rosjava_test_msgs.AddTwoInts._TYPE);
        } catch (ServiceNotFoundException e) {
            throw new RosRuntimeException(e);
        }
        final rosjava_test_msgs.AddTwoIntsRequest request = serviceClient.newMessage();
        request.setA(2);
        request.setB(2);
        serviceClient.call(request, new ServiceResponseListener<rosjava_test_msgs.AddTwoIntsResponse>() {
            @Override
            public void onSuccess(rosjava_test_msgs.AddTwoIntsResponse response) {
                connectedNode.getLog().info(
                        String.format("%d + %d = %d", request.getA(), request.getB(), response.getSum()));
            }

            @Override
            public void onFailure(RemoteException e) {
                throw new RosRuntimeException(e);
            }
        });
    }
}
