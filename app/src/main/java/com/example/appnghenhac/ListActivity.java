package com.example.appnghenhac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.appnghenhac.models.PlayList;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    ImageView button_back;
    ListView list_playlist;
    PlayListAdapter playListAdapter;
    ArrayList<PlayList> playlists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist);
        button_back = (ImageView) findViewById(R.id.button_back);

//        Fake data
        playlists = new ArrayList<>();
        PlayList playList = new PlayList(5,"Danh sach bai hat","@drawable/hat");
        playlists.add(playList);

        playList = new PlayList(1,"Danh sach bai hat","@drawable/hat");
        playlists.add(playList);

        playList = new PlayList(2,"Danh sach bai hat","@drawable/hat");
        playlists.add(playList);

        playList = new PlayList(3,"Danh sach bai hat","@drawable/hat");
        playlists.add(playList);

        playList = new PlayList(4,"Danh sach bai hat","@drawable/hat");
        playlists.add(playList);

        playList = new PlayList(7,"Danh sach bai hat","@drawable/hat");
        playlists.add(playList);

        playList = new PlayList(8,"Danh sach bai hat","@drawable/hat");
        playlists.add(playList);


//        End fake data

        playListAdapter = new PlayListAdapter(playlists);
        list_playlist = (ListView) findViewById(R.id.list_playlist);
        list_playlist.setAdapter(playListAdapter);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListActivity.this,MenuSecond.class);
                startActivity(i);
            }
        });
        list_playlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PlayList playList_temp = (PlayList) playListAdapter.getItem(i);
                Intent intent = new Intent(ListActivity.this,ListSong.class);
                //based on item add info to intent
                startActivity(intent);


            }
        });
    }
}