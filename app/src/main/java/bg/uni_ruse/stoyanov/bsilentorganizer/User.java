package bg.uni_ruse.stoyanov.bsilentorganizer;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;

/**
 * Created by stoyanovst on 11.07.17.
 */

@Entity
class User {

    @Id(autoincrement = true)
    private Long id;
    private String userId;
    private String firstName;
    private String lastName;
    private String fullName;
    private String imageUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    User(String userId, String firstName, String lastName, String fullName,
         String imageUrl) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.imageUrl = imageUrl;
    }

    @Keep
    public User(Long id, String userId, String firstName, String lastName, String fullName,
            String imageUrl) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.imageUrl = imageUrl;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                ", userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
