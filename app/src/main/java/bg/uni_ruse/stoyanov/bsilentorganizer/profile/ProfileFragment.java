package bg.uni_ruse.stoyanov.bsilentorganizer.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import bg.uni_ruse.stoyanov.bsilentorganizer.MainActivity;
import bg.uni_ruse.stoyanov.bsilentorganizer.R;
import bg.uni_ruse.stoyanov.bsilentorganizer.user.User;
import bg.uni_ruse.stoyanov.bsilentorganizer.user.UserDao;

import static bg.uni_ruse.stoyanov.bsilentorganizer.helpers.SocialId.getUserId;
import static bg.uni_ruse.stoyanov.bsilentorganizer.helpers.ToolbarTitle.setToolbarTitle;
import static com.facebook.FacebookSdk.getApplicationContext;

public class ProfileFragment extends Fragment {

    private UserDao userDao;
    private User user;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userDao = MainActivity.getDaoSession().getUserDao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        setToolbarTitle((AppCompatActivity) getActivity(), R.string.profile);

        String userId = getUserId(getApplicationContext());

        QueryBuilder<User> qb = userDao.queryBuilder();
        qb.where(UserDao.Properties.SocialId.like(userId));
        List<User> users = qb.list();
        user = users.get(0);

        if (user.isGoogleProfile()) {
            ImageView googleImageView = getActivity().findViewById(R.id.g_profile_picture);
            googleImageView.setVisibility(View.GONE);
            ImageView gImageView = view.findViewById(R.id.g_picture);
            gImageView.setVisibility(View.VISIBLE);
        }
        else {
            ProfilePictureView profilePictureView = getActivity().findViewById(R.id.fb_profile_picture);
            profilePictureView.setVisibility(View.GONE);
            ProfilePictureView profilePicture = view.findViewById(R.id.fb_picture);
            profilePicture.setProfileId(userId);
            FrameLayout fbFrameLayout = view.findViewById(R.id.frame_layout_profile);
            fbFrameLayout.setVisibility(View.VISIBLE);
        }

        TextView userName = view.findViewById(R.id.username_label);
        userName.setText(user.getFullName());
        //TextView emailTextView = view.findViewById(R.id.user_profile_link);
        //emailTextView.setText(user.getEmail());

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        setToolbarTitle((AppCompatActivity) getActivity(), user.getFullName());
        if (user.isGoogleProfile()) {
            ImageView googleImageView = getActivity().findViewById(R.id.g_profile_picture);
            googleImageView.setVisibility(View.VISIBLE);
        } else {
            ProfilePictureView profilePictureView = getActivity().findViewById(R.id.fb_profile_picture);
            profilePictureView.setVisibility(View.VISIBLE);
        }
    }
}
