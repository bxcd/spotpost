package art.coded.spotpost.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import art.coded.spotpost.R;
import art.coded.spotpost.model.Repository;

public class MainViewModel extends ViewModel {

    private static final String LOG_TAG = MainViewModel.class.getSimpleName();

    String mBaseUrl;
    String mGetPath;
    String mPostPath;

    public void load(Context context) {
        mBaseUrl = context.getString(R.string.baseUrl);
        mGetPath = context.getString(R.string.getPath);
        mPostPath = context.getString(R.string.postPath);
    }

    public void spotPost() {
        Repository repository = new Repository();
        repository.request(mBaseUrl, mGetPath, mPostPath);
    }
}
