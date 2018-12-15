package pl.krakow.politechnika.io.wnuk.calendar;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.ArrayList;

public class EventsAdapter extends ArrayAdapter<Event> {

    private ArrayList<Event> eventsList;
    public EventsAdapter(@NonNull Context context, ArrayList<Event> eventsList) {
        super(context, R.layout.events_list_row, eventsList);
        this.eventsList = eventsList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater eventsListInflater = LayoutInflater.from(getContext());
        View customView = eventsListInflater.inflate(R.layout.events_list_row, parent, false);
        String eventString =(String) getItem(position).getData();
        int color = getItem(position).getColor();
        TextView eventTextView = (TextView) customView.findViewById(R.id.tvEvent);

        ImageButton eventImageButton = (ImageButton) customView.findViewById(R.id.ibDelete);

        eventTextView.setBackgroundColor(color);
        if(Color.BLACK == color){
            eventTextView.setTextColor(Color.WHITE);
        }
        eventTextView.setText(eventString);
        eventImageButton.setImageResource(R.drawable.delete);

        eventImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventsList.remove(position);
            }
        });

        return customView;
    }
}
