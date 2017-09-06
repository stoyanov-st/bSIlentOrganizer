package bg.uni_ruse.stoyanov.bsilentorganizer.silent_manager;

import android.content.Context;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import bg.uni_ruse.stoyanov.bsilentorganizer.MainActivity;

import static bg.uni_ruse.stoyanov.bsilentorganizer.helpers.SocialId.getUserId;

/**
 * Created by stoyanovst on 9/6/17.
 */

public class SilentModelList {

    public static List<SilentModel> getSilentModels(Context context) {
        SilentModelDao silentModelDao = MainActivity.getDaoSession().getSilentModelDao();
        QueryBuilder<SilentModel> queryBuilder = silentModelDao.queryBuilder();
        queryBuilder.where(SilentModelDao.Properties.UserSocialId.like(getUserId(context)));
        return queryBuilder.list();
    }
}
