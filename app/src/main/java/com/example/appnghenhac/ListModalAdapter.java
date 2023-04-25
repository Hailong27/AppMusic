package com.example.appnghenhac;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appnghenhac.models.PlayList;

import java.util.List;

public class ListModalAdapter extends BaseAdapter{
    final List<PlayList> playLists;

    public ListModalAdapter(List<PlayList> playLists) {
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
            viewPlaylist = View.inflate(parent.getContext(), R.layout.view_list_modal, null);
        } else viewPlaylist = convertView;

        //Bind sữ liệu phần tử vào View
        PlayList playList = (PlayList) getItem(position);
        ((TextView) viewPlaylist.findViewById(R.id.stt_song)).setText(playList.name);


        return viewPlaylist;
    }
}
