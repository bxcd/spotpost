package art.coded.spotpost.model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import art.coded.spotpost.model.entity.Event;
import art.coded.spotpost.model.local.Dao;
import art.coded.spotpost.model.local.RoomDatabase;

public class Repository {

    private static final String LOG_TAG = Repository.class.getSimpleName();
    Dao mDao;

    public Repository(Context context) {
        RoomDatabase db = RoomDatabase.getInstance(context);
        mDao = db.dao();
    }

    public LiveData<List<Event>> getAllEvents() { return mDao.getAll(); }
    public void request(String baseUrl, String getPath, String postPath) {
        new RequestAsyncTask(mDao).execute(baseUrl, getPath, postPath); }

    private static class RequestAsyncTask extends AsyncTask<String, Void, Void> {

        Dao mAsyncTaskDao;
        public RequestAsyncTask(Dao dao) { mAsyncTaskDao = dao; }

        @Override protected Void doInBackground(String... strings) {
            List<Event> events = DataUtils.spotPost(strings[0], strings[1], strings[2]);
            for (Event e : events) mAsyncTaskDao.insert(e);
            return null;
        }
    }
}
