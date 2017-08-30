package bg.uni_ruse.stoyanov.bsilentorganizer.note;

import android.content.Context;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import bg.uni_ruse.stoyanov.bsilentorganizer.MainActivity;

import static bg.uni_ruse.stoyanov.bsilentorganizer.helpers.SocialId.getUserId;

/**
 * Created by stoyanovst on 8/30/17.
 */

public class NoteList {

    public static List<Note> getNotes(Context context) {
        NoteDao noteDao = MainActivity.getDaoSession().getNoteDao();
        QueryBuilder<Note> queryBuilder = noteDao.queryBuilder();
        queryBuilder.where(NoteDao.Properties.UserSocialId.like(getUserId(context)));
        return queryBuilder.list();
    }
}
