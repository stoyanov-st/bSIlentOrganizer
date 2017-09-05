package bg.uni_ruse.stoyanov.bsilentorganizer.event;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by stoyanovst on 9/4/17.
 */
@Entity
public class Event {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String userSocialId;

    private String eventName;
    private String eventDate;
    private String timestamp;
    private boolean importanceFlag;
    @Generated(hash = 196634170)
    public Event(Long id, @NotNull String userSocialId, String eventName,
            String eventDate, String timestamp, boolean importanceFlag) {
        this.id = id;
        this.userSocialId = userSocialId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.timestamp = timestamp;
        this.importanceFlag = importanceFlag;
    }
    @Generated(hash = 344677835)
    public Event() {
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
    public String getEventName() {
        return this.eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public String getEventDate() {
        return this.eventDate;
    }
    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
    public String getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public boolean getImportanceFlag() {
        return this.importanceFlag;
    }
    public void setImportanceFlag(boolean importanceFlag) {
        this.importanceFlag = importanceFlag;
    }

  
 
}
