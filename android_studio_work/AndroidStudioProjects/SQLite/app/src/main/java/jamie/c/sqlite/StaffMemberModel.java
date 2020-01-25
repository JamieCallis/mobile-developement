package jamie.c.sqlite;

import java.io.Serializable;

public class StaffMemberModel implements Serializable{

    private String firstName;
    private String lastName;
    private String officeNumber;
    private int id;

    public int getId() {
        return id;
    }//getId

    public void setId(int id) {
        this.id = id;
    }//setId

    public String getFirstName() {
        return firstName;
    }//getFirstName

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }//setFirstName

    public String getLastName() {
        return lastName;
    }//getLastName

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }//setLastName

    public String getOfficeNumber() {
        return officeNumber;
    }//getOfficeNumber

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }//setOfficeNumber

    public String toString() {
        String s = "firstnName:" + firstName + "\n";
        s += "lastnName:" + lastName + "\n";
        s += "officeNumber:" + officeNumber;
        return s;
    }//toString

}//StaffMemberModel class


