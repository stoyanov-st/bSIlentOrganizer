package bg.uni_ruse.stoyanov.bsilentorganizer.silent_manager;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by stoyanovst on 9/6/17.
 */

@Entity
public class WeekDays {

    @Id(autoincrement = true)
    private Long id;

    private boolean mon, tue, wen, thu, fri, sat, sun;

    @Generated(hash = 658695661)
    public WeekDays(Long id, boolean mon, boolean tue, boolean wen, boolean thu,
            boolean fri, boolean sat, boolean sun) {
        this.id = id;
        this.mon = mon;
        this.tue = tue;
        this.wen = wen;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
        this.sun = sun;
    }

    public String getDays() {
        String days = "";

        if (isMon()) {
            days += "Mon ";
        }
        if (isTue()) {
            days += "Tue ";
        }
        if (isWen()) {
            days += "Wed ";
        }
        if (isThu()) {
            days += "Thu ";
        }
        if (isFri()) {
            days += "Fri ";
        }
        if (isSat()) {
            days += "Sat ";
        }
        if (isSun()) {
            days += "Sun ";
        }


        return days;
    }

    @Generated(hash = 200776335)
    public WeekDays() {
    }

    public boolean isMon() {
        return mon;
    }

    public void setMon(boolean mon) {
        this.mon = mon;
    }

    public boolean isTue() {
        return tue;
    }

    public void setTue(boolean tue) {
        this.tue = tue;
    }

    public boolean isWen() {
        return wen;
    }

    public void setWen(boolean wen) {
        this.wen = wen;
    }

    public boolean isThu() {
        return thu;
    }

    public void setThu(boolean thu) {
        this.thu = thu;
    }

    public boolean isFri() {
        return fri;
    }

    public void setFri(boolean fri) {
        this.fri = fri;
    }

    public boolean isSat() {
        return sat;
    }

    public void setSat(boolean sat) {
        this.sat = sat;
    }

    public boolean isSun() {
        return sun;
    }

    public void setSun(boolean sun) {
        this.sun = sun;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getMon() {
        return this.mon;
    }

    public boolean getTue() {
        return this.tue;
    }

    public boolean getWen() {
        return this.wen;
    }

    public boolean getThu() {
        return this.thu;
    }

    public boolean getFri() {
        return this.fri;
    }

    public boolean getSat() {
        return this.sat;
    }

    public boolean getSun() {
        return this.sun;
    }
}
