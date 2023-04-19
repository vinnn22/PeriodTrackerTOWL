package my.edu.utar.periodtrackertowl;

public class helper {

    String username,password,re_password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRe_password() {
        return re_password;
    }

    public void setRe_password(String re_password) {
        this.re_password = re_password;
    }

    public helper(String username, String password, String re_password) {
        this.username = username;
        this.password = password;
        this.re_password = re_password;
    }

    public helper() {
    }

}
