package bg.uni_ruse.stoyanov.bsilentorganizer.note;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;


/**
 * Created by stoyanovst on 8/29/17.
 */

@Entity
public class Note {

    @Id
    private long id;
    @NotNull
    private String userSocialId;

    private String noteTitle;
    private String noteText;

    public Note(@NotNull String userSocialId, String noteTitle,
                String noteText) {
        this.userSocialId = userSocialId;
        this.noteTitle = noteTitle;
        this.noteText = noteText;
    }

    public Note(@NotNull String userSocialId, String noteTitle) {
        this.userSocialId = userSocialId;
        this.noteTitle = noteTitle;
    }

    @Generated(hash = 1631496132)
    public Note(long id, @NotNull String userSocialId, String noteTitle,
            String noteText) {
        this.id = id;
        this.userSocialId = userSocialId;
        this.noteTitle = noteTitle;
        this.noteText = noteText;
    }
    @Generated(hash = 1272611929)
    public Note() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getUserSocialId() {
        return this.userSocialId;
    }
    public void setUserSocialId(String userSocialId) {
        this.userSocialId = userSocialId;
    }
    public String getNoteTitle() {
        return this.noteTitle;
    }
    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }
    public String getNoteText() {
        return this.noteText;
    }
    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }


}
