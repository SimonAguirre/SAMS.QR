package com.sams.samsqr;

public class Student {
    private long id;
    private String fullname;
    private String firstName;
    private String middleName;
    private String lastName;
    private String nameSuffix;
    private int gradeLevel;
    private  String section;
    private long lrn;

    public Student(long id, String fullname, String firstName, String middleName, String lastName, String nameSuffix,
                   int gradeLevel, String section, long lrn){
        this.fullname = fullname;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.nameSuffix = nameSuffix;
        this.gradeLevel = gradeLevel;
        this.section = section;
        this.lrn = lrn;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setGradeLevel(int gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public void setSection(String section) {
        this.section = section;
    }
    public void setLrn(long lrn) {
        this.lrn = lrn;
    }

    public long getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNameSuffix() {
        return nameSuffix;
    }

    public int getGradeLevel() {
        return gradeLevel;
    }

    public String getSection() {
        return section;
    }

    public long getLrn() {
        return lrn;
    }
}
