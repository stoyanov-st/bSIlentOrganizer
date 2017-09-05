package bg.uni_ruse.stoyanov.bsilentorganizer.event;

import android.content.Context;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import bg.uni_ruse.stoyanov.bsilentorganizer.MainActivity;

import static bg.uni_ruse.stoyanov.bsilentorganizer.helpers.SocialId.getUserId;

/**
 * Created by stoyanovst on 9/4/17.
 */

public class EventList {
    public static List<Event> getEventsByDate( Context context, String date) {
        EventDao eventDao = MainActivity.getDaoSession().getEventDao();
        QueryBuilder<Event> queryBuilder = eventDao.queryBuilder();
        queryBuilder.where(EventDao.Properties.EventDate.like(date))
            .where(EventDao.Properties.UserSocialId.like(getUserId(context)));
        return queryBuilder.list();
    }
}
