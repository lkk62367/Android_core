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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.ros.android.MasterChooser;
import org.ros.android.MessageCallable;
import org.ros.android.RosActivity;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author damonkohler@google.com (Damon Kohler)
 */
public class MainActivity extends RosActivity implements Button.OnClickListener{
  private Button SLAMmode,Navmode,Cancel,reboot,shutdown;


  private Talker talker;

  public MainActivity() {
    // The RosActivity constructor configures the notification title and ticker
    // messages.
    super("Control Tutorial", "Control Tutorial");
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

    shutdown = (Button)findViewById(R.id.SHUTDOWN);
    shutdown.setOnClickListener(this);

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
    Cancel.setEnabled(true);
    if (v.getId() == R.id.Reboot) {
      String diamsg = "Reboot";
      String msg = "3";
      String toasts = "reboot";
      dialog(diamsg,msg,toasts);
    }else if (v.getId() == R.id.Cancel){
      String msg = "cancel goal";
      String toasts = "Cancel Goal";
      fun1(msg, toasts);
      Cancel.setEnabled(false);
      Timer buttonTimer = new Timer();
      buttonTimer.schedule(new TimerTask() {
        @Override
        public void run() {
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              Cancel.setEnabled(true);
            }
          });
        }
      }, 3000);
    }else if (v.getId()==R.id.Navmode){
      String msg = "2";
      String toasts = "Nav. Mode";
      fun1(msg, toasts);
    }else if (v.getId()==R.id.SLAMmode){
      String msg = "1";
      String toasts = "SLAM Mode";
      fun1(msg,toasts);
    }else if (v.getId()==R.id.SHUTDOWN){
      String diamsg = "Shutdown";
      String msg = "4";
      String toasts = "Shutdown";
      dialog(diamsg,msg,toasts);
    }
  }


  private void dialog(String diamsg, final String msg, final String toasts) {
    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); //創建訊息方塊

    builder.setMessage("Sure want to "+diamsg +"?");

    builder.setTitle(diamsg);

    builder.setPositiveButton("OK", new DialogInterface.OnClickListener()  {

      @Override

      public void onClick(DialogInterface dialog, int which) {
        fun1(msg, toasts);
      }

    });

    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()  {

      @Override

      public void onClick(DialogInterface dialog, int which) {

        dialog.dismiss();

      }

    });

    builder.create().show();
  }


  private void fun1(String msg, String toasts){
    talker.setVar(msg);
    Toast.makeText(this, toasts, Toast.LENGTH_SHORT).show();
  }

}
