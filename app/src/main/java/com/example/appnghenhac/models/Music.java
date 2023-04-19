package com.example.appnghenhac.models;

public class Music {
    public int id;
    public String nameMusic;
    public String fileMusic;
    public Singer singer;
    public String musicPlaylists;

    public Music(int id, String nameMusic, String fileMusic, Singer singer, String musicPlaylists) {
        this.id = id;
        this.nameMusic = nameMusic;
        this.fileMusic = fileMusic;
        this.singer = singer;
        this.musicPlaylists = musicPlaylists;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNameMusic(String nameMusic) {
        this.nameMusic = nameMusic;
    }

    public void setFileMusic(String fileMusic) {
        this.fileMusic = fileMusic;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }

    public void setMusicPlaylists(String musicPlaylists) {
        this.musicPlaylists = musicPlaylists;
    }

    public int getId() {
        return id;
    }

    public String getNameMusic() {
        return nameMusic;
    }

    public String getFileMusic() {
        return fileMusic;
    }

    public Singer getSinger() {
        return singer;
    }

    public String getMusicPlaylists() {
        return musicPlaylists;
    }
}
