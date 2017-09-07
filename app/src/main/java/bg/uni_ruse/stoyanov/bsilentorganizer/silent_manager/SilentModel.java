package bg.uni_ruse.stoyanov.bsilentorganizer.silent_manager;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import bg.uni_ruse.stoyanov.bsilentorganizer.user.DaoSession;


/**
 * Created by stoyanovst on 9/6/17.
 */

@Entity
public class SilentModel {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String userSocialId;

    @ToMany(joinProperties= {
            @JoinProperty(name = "id", referencedName = "silentModelId")
    })
    private List<Contact> contacts;

    private Long weekDaysId;
    @ToOne(joinProperty = "weekDaysId")
    private WeekDays weekDays;

    private int startHours;
    private int startMinutes;
    private int endHours;
    private int endMinutes;

    private boolean vibrationMode;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 776040987)
    private transient SilentModelDao myDao;

    @Generated(hash = 624518182)
    public SilentModel(Long id, @NotNull String userSocialId, Long weekDaysId,
            int startHours, int startMinutes, int endHours, int endMinutes,
            boolean vibrationMode) {
        this.id = id;
        this.userSocialId = userSocialId;
        this.weekDaysId = weekDaysId;
        this.startHours = startHours;
        this.startMinutes = startMinutes;
        this.endHours = endHours;
        this.endMinutes = endMinutes;
        this.vibrationMode = vibrationMode;
    }

    @Generated(hash = 724679041)
    public SilentModel() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserSocialId() {
        return this.userSocialId;
    }

    public void setUserSocialId(String userSocialId) {
        this.userSocialId = userSocialId;
    }

    public Long getWeekDaysId() {
        return this.weekDaysId;
    }

    public void setWeekDaysId(Long weekDaysId) {
        this.weekDaysId = weekDaysId;
    }

    public int getStartHours() {
        return this.startHours;
    }

    public void setStartHours(int startHours) {
        this.startHours = startHours;
    }

    public int getStartMinutes() {
        return this.startMinutes;
    }

    public void setStartMinutes(int startMinutes) {
        this.startMinutes = startMinutes;
    }

    public int getEndHours() {
        return this.endHours;
    }

    public void setEndHours(int endHours) {
        this.endHours = endHours;
    }

    public int getEndMinutes() {
        return this.endMinutes;
    }

    public void setEndMinutes(int endMinutes) {
        this.endMinutes = endMinutes;
    }

    public boolean getVibrationMode() {
        return this.vibrationMode;
    }

    public void setVibrationMode(boolean vibrationMode) {
        this.vibrationMode = vibrationMode;
    }

    @Generated(hash = 1151065297)
    private transient Long weekDays__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 2125166838)
    public WeekDays getWeekDays() {
        Long __key = this.weekDaysId;
        if (weekDays__resolvedKey == null || !weekDays__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            WeekDaysDao targetDao = daoSession.getWeekDaysDao();
            WeekDays weekDaysNew = targetDao.load(__key);
            synchronized (this) {
                weekDays = weekDaysNew;
                weekDays__resolvedKey = __key;
            }
        }
        return weekDays;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1751072763)
    public void setWeekDays(WeekDays weekDays) {
        synchronized (this) {
            this.weekDays = weekDays;
            weekDaysId = weekDays == null ? null : weekDays.getId();
            weekDays__resolvedKey = weekDaysId;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1540023459)
    public List<Contact> getContacts() {
        if (contacts == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ContactDao targetDao = daoSession.getContactDao();
            List<Contact> contactsNew = targetDao._querySilentModel_Contacts(id);
            synchronized (this) {
                if (contacts == null) {
                    contacts = contactsNew;
                }
            }
        }
        return contacts;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1818154294)
    public synchronized void resetContacts() {
        contacts = null;
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

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 192705266)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getSilentModelDao() : null;
    }
}
