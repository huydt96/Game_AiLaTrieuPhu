package com.techja.game_ailatrieuphu.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.techja.game_ailatrieuphu.App;
import com.techja.game_ailatrieuphu.db.entities.HighScore;

import java.util.List;


public class M000MenuGameVM extends BaseViewModel {
    private final MutableLiveData<List<HighScore>> scoreListLD = new MutableLiveData<>(null);

    public void getScoreListFromDB() {
        new Thread(() -> scoreListLD.postValue((App.getInstance().getDbQuestion().highScoreDAO().getAllScore()))).start();
    }

    public MutableLiveData<List<HighScore>> getScoreListLD() {
        return scoreListLD;
    }

}
