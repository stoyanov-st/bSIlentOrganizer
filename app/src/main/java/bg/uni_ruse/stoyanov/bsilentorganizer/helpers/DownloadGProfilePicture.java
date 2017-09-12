package bg.uni_ruse.stoyanov.bsilentorganizer.helpers;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import bg.uni_ruse.stoyanov.bsilentorganizer.HomeActivity;
import bg.uni_ruse.stoyanov.bsilentorganizer.R;
import bg.uni_ruse.stoyanov.bsilentorganizer.profile.ProfileFragment;

/**
 * Created by stoyanovst on 9/5/17.
 */

public class DownloadGProfilePicture extends AsyncTask<URL, Void, Bitmap> {
    Object activity;
    Object view;

    public DownloadGProfilePicture(Activity activity) {
        this.activity = activity;
    }

    public DownloadGProfilePicture(View view) {
        this.view = view;
    }

    private Object getView() {
        return view;
    }


    private Object getActivity() {
        return activity;
    }

    @Override
    protected Bitmap doInBackground(URL... urls) {
        Bitmap googleProfilePicture = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) urls[0].openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            googleProfilePicture = BitmapFactory.decodeStream(inputStream);
            connection.disconnect();
        } catch ( IOException ioe) {
            ioe.printStackTrace();
        }

        return googleProfilePicture;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (this.activity != null) {
            Activity mActivity = (Activity) getActivity();

            ImageView googleImageView = mActivity.findViewById(R.id.g_profile_picture);
            googleImageView.setVisibility(View.VISIBLE);
            googleImageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 50, 55, false));

            ImageView googleNavImageView = mActivity.findViewById(R.id.g_profile_picture_nav);
            googleNavImageView.setVisibility(View.VISIBLE);
            googleNavImageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 90, 90, false));
        }
        if (this.view != null) {
            View view = (View) getView();
            ImageView googleProfileImageView = view.findViewById(R.id.g_picture);
            googleProfileImageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 180, 180, false));
            googleProfileImageView.setVisibility(View.VISIBLE);
        }
    }

}
