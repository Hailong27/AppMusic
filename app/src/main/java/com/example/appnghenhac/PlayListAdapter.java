package com.example.appnghenhac;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appnghenhac.models.PlayList;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class PlayListAdapter extends BaseAdapter {
    final ArrayList<PlayList> playLists;

    public PlayListAdapter(ArrayList<PlayList> playLists) {
        this.playLists = playLists;
    }

    @Override
    public int getCount() {
        return playLists.size();
    }

    @Override
    public Object getItem(int i) {
        return playLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return playLists.get(i).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewPlaylist;
        if (convertView == null) {
            viewPlaylist = View.inflate(parent.getContext(), R.layout.music_view, null);
        } else viewPlaylist = convertView;

        //Bind sữ liệu phần tử vào View
        PlayList playList = (PlayList) getItem(position);
        ((TextView) viewPlaylist.findViewById(R.id.name_playlist)).setText(playList.name);


        return viewPlaylist;
    }
}
