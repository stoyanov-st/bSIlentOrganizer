package bg.uni_ruse.stoyanov.bsilentorganizer.user;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;

import bg.uni_ruse.stoyanov.bsilentorganizer.event.Event;
import bg.uni_ruse.stoyanov.bsilentorganizer.note.Note;
import org.greenrobot.greendao.DaoException;
import bg.uni_ruse.stoyanov.bsilentorganizer.note.NoteDao;
import bg.uni_ruse.stoyanov.bsilentorganizer.event.EventDao;

/**
 * Created by stoyanovst on 11.07.17.
 */

@Entity
public class User {

    @Id(autoincrement = true)
    private Long userId;
    @Unique
    private String socialId;

    @ToMany(joinProperties = {
            @JoinProperty(name = "socialId", referencedName = "userSocialId")
    })
    private List<Note> notes;

    @ToMany(joinProperties = {
            @JoinProperty(name = "socialId", referencedName = "userSocialId")
    })
    private List<Event> events;

    private String firstName;
    private String lastName;
    private String fullName;
    private String imageUrl;
    private Boolean isGoogleProfile;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1507654846)
    private transient UserDao myDao;

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

   public User(String socialId, String firstName, String lastName, String fullName, String imageUrl, Boolean accType) {
        this.socialId = socialId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.imageUrl = imageUrl;
        this.isGoogleProfile = accType;
    }

    @Generated(hash = 704237786)
    public User(Long userId, String socialId, String firstName, String lastName, String fullName,
            String imageUrl, Boolean isGoogleProfile) {
        this.userId = userId;
        this.socialId = socialId;
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
                ", socialId='" + socialId + '\'' +
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

    public String getSocialId() {
        return this.socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1831601126)
    public List<Note> getNotes() {
        if (notes == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            NoteDao targetDao = daoSession.getNoteDao();
            List<Note> notesNew = targetDao._queryUser_Notes(socialId);
            synchronized (this) {
                if (notes == null) {
                    notes = notesNew;
                }
            }
        }
        return notes;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 2032098259)
    public synchronized void resetNotes() {
        notes = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 2090355362)
    public List<Event> getEvents() {
        if (events == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            EventDao targetDao = daoSession.getEventDao();
            List<Event> eventsNew = targetDao._queryUser_Events(socialId);
            synchronized (this) {
                if (events == null) {
                    events = eventsNew;
                }
            }
        }
        return events;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1830105409)
    public synchronized void resetEvents() {
        events = null;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2059241980)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDao() : null;
    }
}
