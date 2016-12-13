/*
 * Copyright (C) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.ros.android.android_tutorial_control;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.ros.android.MasterChooser;
import org.ros.android.MessageCallable;
import org.ros.android.RosActivity;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

/**
 * @author damonkohler@google.com (Damon Kohler)
 */
public class MainActivity extends RosActivity implements Button.OnClickListener{
  private Button SLAMmode,Navmode,Cancel,reboot;


  private Talker talker;

  public MainActivity() {
    // The RosActivity constructor configures the notification title and ticker
    // messages.
    super("Pubsub Tutorial", "Pubsub Tutorial");
  }

  @SuppressWarnings("unchecked")
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    reboot = (Button)findViewById(R.id.Reboot);
    reboot.setOnClickListener(this);

    SLAMmode = (Button)findViewById(R.id.SLAMmode);
    SLAMmode.setOnClickListener(this);

    Navmode = (Button)findViewById(R.id.Navmode);
    Navmode.setOnClickListener(this);

    Cancel = (Button)findViewById(R.id.Cancel);
    Cancel.setOnClickListener(this);

  }

  @Override
  protected void init(NodeMainExecutor nodeMainExecutor) {
    talker = new Talker();

    // At this point, the user has already been prompted to either enter the URI
    // of a master to use or to start a master locally.

    // The user can easily use the selected ROS Hostname in the master chooser
    // activity.
    NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(getRosHostname());
    nodeConfiguration.setMasterUri(getMasterUri());
    nodeMainExecutor.execute(talker, nodeConfiguration);
    // The RosTextView is also a NodeMain that must be executed in order to
    // start displaying incoming messages.
    //nodeMainExecutor.execute(rosTextView, nodeConfiguration);
  }
  public void onClick(View v) {
    if (v.getId() == R.id.Reboot) {
      String msg = "reboot";
      String toasts = "Reboot";
      fun1(msg, toasts);
    }else if (v.getId() == R.id.Cancel){
      String msg = "cancel";
      String toasts = "Cancel Goal";
      fun1(msg, toasts);
    }else if (v.getId()==R.id.Navmode){
      String msg = "navmode";
      String toasts = "Nav. Mode";
      fun1(msg, toasts);
    }else if (v.getId()==R.id.SLAMmode){
      String msg = "slammode";
      String toasts = "SLAM Mode";
      fun1(msg,toasts);
    }
  }

  private void fun2(String toasts) {
  }

  private void fun1(String msg, String toasts){
    talker.setVar(msg);
    Toast.makeText(this, toasts, Toast.LENGTH_SHORT).show();
  }

}
