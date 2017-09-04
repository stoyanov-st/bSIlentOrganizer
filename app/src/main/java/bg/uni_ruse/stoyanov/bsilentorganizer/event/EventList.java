package bg.uni_ruse.stoyanov.bsilentorganizer.event;

import android.content.Context;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import bg.uni_ruse.stoyanov.bsilentorganizer.MainActivity;

/**
 * Created by stoyanovst on 9/4/17.
 */

public class EventList {
    public static List<Event> getEventsByDate(Long date) {
        EventDao eventDao = MainActivity.getDaoSession().getEventDao();
        QueryBuilder<Event> queryBuilder = eventDao.queryBuilder();
        queryBuilder.where(EventDao.Properties.EventDate.eq(date));
        return queryBuilder.list();
    }
}
