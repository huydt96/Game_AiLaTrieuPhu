package com.techja.game_ailatrieuphu;

import android.media.MediaPlayer;

public class MediaManager {
    private static MediaManager instance;
    private MediaPlayer bgPlayer;
    private MediaPlayer songPlayer;
    private boolean isTurnOnBgMedia = true;
    private boolean isTurnOnSongMedia = true;

    public static MediaManager getInstance() {
        if (instance == null) {
            instance = new MediaManager();
        }
        return instance;
    }

    public void setTurnOnBgMedia(boolean turnOnBgMedia) {
        isTurnOnBgMedia = turnOnBgMedia;
    }

    public void setTurnOnSongMedia(boolean turnOnSongMedia) {
        isTurnOnSongMedia = turnOnSongMedia;
    }

    public void startBg(int song, boolean isLooping) {
        if (bgPlayer != null) {
            bgPlayer.reset();
        }
        bgPlayer = startMedia(song, isLooping);
        if (!isTurnOnBgMedia) {
            bgPlayer.setVolume(0, 0);
        }
    }

    public void startSong(int song, boolean isLooping, MediaPlayer.OnCompletionListener event) {
        if (songPlayer != null) {
            songPlayer.reset();
        }
        songPlayer = startMedia(song, isLooping);
        songPlayer.setOnCompletionListener(event);
        if (!isTurnOnSongMedia) {
            songPlayer.setVolume(0, 0);
        }
    }

    public void continueBg() {
        continueMedia(bgPlayer);
    }

    public void continueSong() {
        continueMedia(songPlayer);
    }

    public void pauseBg() {
        pauseMedia(bgPlayer);
    }

    public void pauseSong() {
        pauseMedia(songPlayer);
    }

    public void stopBg() {
        bgPlayer = stopMedia(bgPlayer);
    }

    public void stopSong() {
        songPlayer = stopMedia(songPlayer);
    }

    private MediaPlayer startMedia(int song, boolean isLooping) {
        MediaPlayer media = MediaPlayer.create(App.getInstance(), song);
        media.setLooping(isLooping);
        media.start();

        return media;
    }

    private void continueMedia(MediaPlayer media) {
        if (media != null && media.getCurrentPosition() == media.getDuration()) {
            media = null;
        }
        if (media != null) {
            media.start();
        }
    }

    private void pauseMedia(MediaPlayer media) {
        if (media != null && media.isPlaying()) {
            media.pause();
        }
    }

    private MediaPlayer stopMedia(MediaPlayer media) {
        if (media != null) {
            media.reset();
        }
        return null;
    }

}
