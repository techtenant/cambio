package phcl.biz.camb.model;

import org.parceler.Parcel;

@Parcel
public class User {
    String phoneNum;
    String fName;
    String lName;

    public User() {
    }

    public User(String phoneNum, String fName, String lName) {
        this.phoneNum = phoneNum;
        this.fName = fName;
        this.lName = lName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }
}
