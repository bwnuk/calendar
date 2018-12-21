package pl.krakow.politechnika.io.wnuk.calendar.color;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pl.krakow.politechnika.io.wnuk.calendar.R;

public class ColorAdapter extends ArrayAdapter {
    public ColorAdapter(Context context, ArrayList<ColorItem> colorList){
        super(context, 0, colorList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
              R.layout.color_spinner_row, parent, false
            );
        }

        ImageView imageViewColor = convertView.findViewById(R.id.iv_color);
        TextView textViewColor = convertView.findViewById(R.id.tv_color);

        ColorItem colorItem = (ColorItem) getItem(position);

        if(colorItem != null){
            imageViewColor.setImageResource(colorItem.getmFlagItem());
            textViewColor.setText(colorItem.getColorName());
        }
        return convertView;
    }
}
