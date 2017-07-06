package bg.uni_ruse.stoyanov.bsilentorganizer;

class UserDTO {
    private String email, username, password, initToken;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getInitToken() {
        return initToken;
    }

    public void setInitToken(String initToken) {
        this.initToken = initToken;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", initToken='" + initToken + '\'' +
                '}';
    }

    UserDTO(String email, String username, String password, String initToken) {

        this.email = email;
        this.username = username;
        this.password = password;
        this.initToken = initToken;
    }
}
