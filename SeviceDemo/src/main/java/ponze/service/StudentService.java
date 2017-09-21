package ponze.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.github.yuezp.ipc.IStudentAidlInterface;
import io.github.yuezp.ipc.Student;

/**
 * Created by pangzhen on 2017/9/21.
 */

public class StudentService extends Service {

    List<Student> students = new ArrayList<>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("pang","Received start command");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("pang","Received  onBind");
        return stub;
    }


    private IStudentAidlInterface.Stub stub = new IStudentAidlInterface.Stub() {
        @Override
        public List<Student> getStudents() throws RemoteException {

            return students;
        }

        @Override
        public void addStudent(Student student) throws RemoteException {
            students.add(student);
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    };
}

