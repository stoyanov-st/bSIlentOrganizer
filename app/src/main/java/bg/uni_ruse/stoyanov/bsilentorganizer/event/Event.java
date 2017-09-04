package bg.uni_ruse.stoyanov.bsilentorganizer.event;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by stoyanovst on 9/4/17.
 */
@Entity
public class Event {

    @Id(autoincrement = true)
    private Long id;

    private String eventName;
    private Long eventDate;
    private Long timestamp;
    private boolean importanceFlag;
    @Generated(hash = 1626966708)
    public Event(Long id, String eventName, Long eventDate, Long timestamp,
            boolean importanceFlag) {
        this.id = id;
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
    public String getEventName() {
        return this.eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public Long getEventDate() {
        return this.eventDate;
    }
    public void setEventDate(Long eventDate) {
        this.eventDate = eventDate;
    }
    public boolean getImportanceFlag() {
        return this.importanceFlag;
    }
    public void setImportanceFlag(boolean importanceFlag) {
        this.importanceFlag = importanceFlag;
    }
    public Long getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

 
}
