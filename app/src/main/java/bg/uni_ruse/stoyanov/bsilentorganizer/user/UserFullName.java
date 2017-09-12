package bg.uni_ruse.stoyanov.bsilentorganizer.user;

import android.content.Context;

import org.greenrobot.greendao.query.QueryBuilder;

import bg.uni_ruse.stoyanov.bsilentorganizer.MainActivity;

import static bg.uni_ruse.stoyanov.bsilentorganizer.helpers.SocialId.getUserId;

/**
 * Created by stoyanovst on 9/12/17.
 */

public class UserFullName {
    public static String getUserFullName(Context context) {
        UserDao userDao; userDao = MainActivity.getDaoSession().getUserDao();
        QueryBuilder<User> queryBuilder = userDao.queryBuilder();
        queryBuilder.where(UserDao.Properties.SocialId.like(getUserId(context)));
        User user = queryBuilder.list().get(0);
        return user.getFullName();
    }
}
