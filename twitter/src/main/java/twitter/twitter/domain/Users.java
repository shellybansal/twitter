package twitter.twitter.domain;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="users")
public class Users implements Serializable {

    @Id
    @Column(name="user_t_name")
    String userTName;
    @Column(name="f_name")
    String fName;
    @Column(name="l_name")
    String lName;
    @Column(name="mobile")
    String mobile;
    @Column(name="email")
    String email;
    @Column(name="age")
    int age;
    @Column(name="city")
    String city;
    @Column(name="about")
    String about;
    @Column(name="user_password")
    String userPassword;

    public Users(){
    }
    //constructor hit during registration
    public Users(Users users){

        this.userTName=users.userTName;
        this.fName=users.fName;
        this.lName=users.lName;
        this.mobile=users.mobile;
        this.age=users.age;
        this.city=users.city;
        this.userPassword=users.userPassword;
    }


    //getter setter
    public String getUserTName() {
        return userTName;
    }

    public void setUserTName(String userTName) {
        this.userTName = userTName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}