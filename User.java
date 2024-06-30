import java.io.Serializable;

public class User implements Serializable{

    private static final long serialVersionUID = 1L;

    private String name;
    private String lastName;
    private String phoneNumber;
    private String username;
    private String password;
    private int balance;

    public User (String name, String lastName, String phoneNumber, String username, String password){
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.balance = 600;
    }

    public String getName(){
        return name;
    }

    public String getLastName(){
        return lastName;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public int getUserBalance(){
        return balance;
    }
}
