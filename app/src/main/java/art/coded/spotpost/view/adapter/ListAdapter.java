package art.coded.spotpost.view.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import art.coded.spotpost.R;
import art.coded.spotpost.model.entity.Event;

/**
 * Manages and formats ViewHolders from the corresponding entitys data
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private static final String LOG_TAG = ListAdapter.class.getSimpleName();

    // Member variables
    LayoutInflater mLayoutInflater;
    List<Event> mAllEvents;

    // Instantiates a LayoutInflator provided from the calling Activity
    public ListAdapter(Activity activity) {

        mLayoutInflater = LayoutInflater.from(activity);
    }

    // Inflates and instantiates a ViewHolder from the LayoutInflater provided from the calling Activity
    @NonNull @Override  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = mLayoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(itemView);
    }

    // Formats VieHolder on binding at the specified position
    @Override  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (mAllEvents != null) {
            Event event = mAllEvents.get(position);
            holder.bind(event);
        }
    }

    // Identifies itemcount
    @Override  public int getItemCount() {

        return mAllEvents == null ? 0 : mAllEvents.size();
    }

    // Defines the entitys data that will populate ViewHolders
    public void setEvents(List<Event> events) {
        mAllEvents = events;
        notifyDataSetChanged();
    }

    /**
     * Formats an individual ViewHolder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mUrlView;
        private final TextView mVisitorIdView;
        private final TextView mTimestampView;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            mUrlView = itemView.findViewById(R.id.text_url);
            mVisitorIdView = itemView.findViewById(R.id.text_visitorId);
            mTimestampView = itemView.findViewById(R.id.text_timestamp);
        }

        public void bind(Event event) {
            mUrlView.setText(event.getUrl());
            mVisitorIdView.setText(event.getVisitorId());
            mTimestampView.setText(String.format(Locale.getDefault(), "%d", event.getTimestamp()));
        }
    }
}