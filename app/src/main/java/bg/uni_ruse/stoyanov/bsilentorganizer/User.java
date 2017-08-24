package bg.uni_ruse.stoyanov.bsilentorganizer;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by stoyanovst on 11.07.17.
 */

@Entity
class User {

    @Id(autoincrement = true)
    private Long userId;
    private String firstName;
    private String lastName;
    private String fullName;
    private String imageUrl;
    private Boolean isGoogleProfile;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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

    public Boolean isGoogleProfile() {
        return this.isGoogleProfile;
    }

    public void setIsGoogleProfile(Boolean isGoogleProfile) {
        this.isGoogleProfile = isGoogleProfile;
    }

    User(String firstName, String lastName, String fullName, String imageUrl, Boolean accType) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.imageUrl = imageUrl;
        this.isGoogleProfile = accType;
    }

    @Generated(hash = 61236430)
    public User(Long userId, String firstName, String lastName, String fullName, String imageUrl,
            Boolean isGoogleProfile) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.imageUrl = imageUrl;
        this.isGoogleProfile = isGoogleProfile;
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
                ", isGoogleProfile" + isGoogleProfile + '\'' +
                '}';
    }

    public Boolean getIsGoogleProfile() {
        return this.isGoogleProfile;
    }
}
