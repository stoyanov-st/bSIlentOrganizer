package bg.uni_ruse.stoyanov.bsilentorganizer;

class UserDTO {
    private String email, username, password, initToken;

    UserDTO(String email, String username, String password, String initToken) {

        this.email = email;
        this.username = username;
        this.password = password;
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
}
