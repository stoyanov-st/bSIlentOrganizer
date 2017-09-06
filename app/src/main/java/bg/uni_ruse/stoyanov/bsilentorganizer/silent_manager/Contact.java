package bg.uni_ruse.stoyanov.bsilentorganizer.silent_manager;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by stoyanovst on 9/6/17.
 */

@Entity
public class Contact {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private Long silentModelId;
    private Long contactId;
    @Generated(hash = 1333793287)
    public Contact(Long id, @NotNull Long silentModelId, Long contactId) {
        this.id = id;
        this.silentModelId = silentModelId;
        this.contactId = contactId;
    }
    @Generated(hash = 672515148)
    public Contact() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getSilentModelId() {
        return this.silentModelId;
    }
    public void setSilentModelId(Long silentModelId) {
        this.silentModelId = silentModelId;
    }
    public Long getContactId() {
        return this.contactId;
    }
    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

}
