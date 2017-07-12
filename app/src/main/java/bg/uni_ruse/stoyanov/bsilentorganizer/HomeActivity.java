package bg.uni_ruse.stoyanov.bsilentorganizer;

import android.os.Bundle;
import android.app.Activity;
import android.widget.EditText;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class HomeActivity extends Activity {
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        userDao = MainActivity.getDaoSession().getUserDao();

        QueryBuilder<User> qb = userDao.queryBuilder();
        qb.where(UserDao.Properties.FullName.isNotNull()).limit(1);
        List<User> users = qb.list();
        String text = users.get(0).getFullName();


        EditText editText = (EditText) findViewById(R.id.testBox);
        editText.setText(text);

    }



}
