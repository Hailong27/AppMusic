package com.example.appnghenhac;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appnghenhac.models.Music;

import java.util.List;

public class MusicF4Adapter extends BaseAdapter {
    final List<Music> musics;

    ImageView btn_add;

    public MusicF4Adapter(List<Music> musics) {
        this.musics = musics;
    }

    @Override
    public int getCount() {
        return musics.size();
    }

    @Override
    public Object getItem(int i) {
        return musics.get(i);
    }

    @Override
    public long getItemId(int i) {
        return musics.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewMusic;
        if (convertView == null) {
            viewMusic = View.inflate(parent.getContext(), R.layout.music_frame4, null);
        } else viewMusic = convertView;

        Context context = viewMusic.getContext();

        //Bind sữ liệu phần tử vào View
        Music music = (Music) getItem(position);
        ((TextView) viewMusic.findViewById(R.id.name_music_f4)).setText(music.nameMusic);
        ((TextView) viewMusic.findViewById(R.id.name_singer_music_f4)).setText(music.singer.nameSinger);

        btn_add = (ImageView) viewMusic.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModalListManager modalManager = new ModalListManager();
                modalManager.showModal(view.getContext(), music.id);
            }
        });
//        if(music.singer.imageSinger != "No file selected."){
//            ((ShapeableImageView) viewMusic.findViewById(R.id.image_music_f4)).setImageResource(music.singer.imageSinger);
//        }



        return viewMusic;
    }
}
