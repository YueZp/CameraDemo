package io.github.yuezp.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.github.yuezp.camerademo.R;

public class AidlTestActivity extends AppCompatActivity implements View.OnClickListener {
    private android.widget.Button startservice;
    private android.widget.Button closeservice;
    IStudentAidlInterface iStudentAidlInterface;
    private Button addstudent;
    private Button getstudents;
    private android.widget.TextView textView;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl_test);
        this.textView = (TextView) findViewById(R.id.textView);
        this.getstudents = (Button) findViewById(R.id.get_students);
        this.addstudent = (Button) findViewById(R.id.add_student);
        this.closeservice = (Button) findViewById(R.id.close_service);
        this.startservice = (Button) findViewById(R.id.start_service);

        this.startservice.setOnClickListener(this);
        this.closeservice.setOnClickListener(this);
        this.addstudent.setOnClickListener(this);
        this.getstudents.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.start_service) {
            connect();
        } else if (view.getId() == R.id.close_service) {
            disconnect();
        } else if (view.getId() == R.id.get_students) {
            getStudents();
        } else if (view.getId() == R.id.add_student) {
            addStudent();
        }
    }


    private void disconnect() {
        if (serviceConnection != null)
            unbindService(serviceConnection);
    }

    private void connect() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("ponze.service", "ponze.service.StudentService"));
//        intent.setAction("ponze.service.student");
        startService(intent);
        boolean b = bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        Log.d("pang","bindService = " + b);
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iStudentAidlInterface = IStudentAidlInterface.Stub.asInterface(service);
            Toast.makeText(AidlTestActivity.this, "连接成功", Toast.LENGTH_SHORT);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iStudentAidlInterface = null;
            Toast.makeText(AidlTestActivity.this, "断开连接", Toast.LENGTH_SHORT);

        }
    };

    public void getStudents() {
        if (iStudentAidlInterface != null) {
            try {
                List<Student> students = iStudentAidlInterface.getStudents();
                if (students == null) {
                    textView.setText("没有数据");
                } else
                    textView.setText(students.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void addStudent() {
        Student student = new Student();
        student.name = "Jack_" + count;
        student.address = "USA_" + count;
        student.age = count;
        student.isMale = true;
        count++;
    }

}
