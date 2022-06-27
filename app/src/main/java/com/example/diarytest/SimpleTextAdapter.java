package com.example.diarytest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SimpleTextAdapter extends BaseAdapter {

    LayoutInflater layoutInflater = null;
    private ArrayList<Diary> listViewData = null;
    private int count = 0;

    public SimpleTextAdapter(ArrayList<Diary> listData)
    {
        listViewData = listData;
        count = listViewData.size();
    }

    @Override
    public int getCount()
    {
        return count;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            final Context context = parent.getContext();
            if (layoutInflater == null)
            {
                layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = layoutInflater.inflate(R.layout.activity_list_item, parent, false);
        }

        TextView body_1 = convertView.findViewById(R.id.Datetext);
        TextView body_2 = convertView.findViewById(R.id.Diatext);

        body_1.setText(listViewData.get(position).wrtieTime);
        body_2.setText(listViewData.get(position).diaryText);

        return convertView;
    }


}
