package com.max.idea.XML;

public class People {
    private String firstname;
    private String Lastname;
    private String Profession;
    private String Range;
    private  int salary;

    public People(String firstname, String lastname, String profession, String range, int salary) {
        this.firstname = firstname;
        Lastname = lastname;
        Profession = profession;
        Range = range;
        this.salary = salary;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public String getProfession() {
        return Profession;
    }

    public String getRange() {
        return Range;
    }

    public int getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "People{" +
                "firstname='" + firstname + '\'' +
                ", Lastname='" + Lastname + '\'' +
                ", Profession='" + Profession + '\'' +
                ", Range='" + Range + '\'' +
                ", salary=" + salary +
                '}';
    }
}
