// IStudentAidlInterface.aidl
package io.github.yuezp.ipc;

import io.github.yuezp.ipc.Student;

// Declare any non-default types here with import statements

interface IStudentAidlInterface {

    List<Student> getStudents();

    void addStudent(in Student student);
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
