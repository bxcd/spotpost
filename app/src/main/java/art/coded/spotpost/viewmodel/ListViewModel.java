package art.coded.spotpost.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import art.coded.spotpost.model.Repository;
import art.coded.spotpost.model.entity.Event;

/**
 * Interacts with Repository to sync datasource with database and provide entity LiveData to the UI
 */
public class ListViewModel extends ViewModel {

    private static final String LOG_TAG = ListViewModel.class.getSimpleName();

    // Member variables
    private Repository mRepository;
    private LiveData<List<Event>> mData;

    // From application argument instantiates Repository from which LiveData is retrieved from EventDao
    public void load(Context context) {
        mRepository = new Repository(context);
        mData = mRepository.getAllEvents();
    }

    public LiveData<List<Event>> getData() {
        return mData;
    }
}