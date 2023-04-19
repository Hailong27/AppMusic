package com.example.appnghenhac;


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appnghenhac.models.Music;
import com.example.appnghenhac.models.PlayList;

import java.util.List;

public class SongAdaper extends BaseAdapter {
    List<Music> musicList;

    public SongAdaper(List<Music> musicList) {
        this.musicList = musicList;
    }

    @Override
    public int getCount() {
        return musicList.size();
    }

    @Override
    public Object getItem(int i) {
        return musicList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return musicList.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewSong;
        if (convertView == null) {
            viewSong = View.inflate(parent.getContext(), R.layout.song_view, null);
        } else viewSong = convertView;

        //Bind sữ liệu phần tử vào View
        Music music = (Music) getItem(position);
        ((TextView) viewSong.findViewById(R.id.stt_song)).setText(String.valueOf(position+1));
        ((TextView) viewSong.findViewById(R.id.name_song)).setText(music.nameMusic);
        ((TextView) viewSong.findViewById(R.id.singer_song)).setText(music.singer.nameSinger);


        return viewSong;
    }
}
