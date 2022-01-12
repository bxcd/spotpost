package art.coded.spotpost.model;

import android.os.AsyncTask;

public class Repository {

    private static final String LOG_TAG = Repository.class.getSimpleName();

    public void request(String baseUrl, String getPath, String postPath) {
        new RequestAsyncTask().execute(baseUrl, getPath, postPath); }

    private static class RequestAsyncTask extends AsyncTask<String, Void, Void> {

        @Override protected Void doInBackground(String... strings) {
            DataUtils.spotPost(strings[0], strings[1], strings[2]);
            return null;
        }
    }
}
