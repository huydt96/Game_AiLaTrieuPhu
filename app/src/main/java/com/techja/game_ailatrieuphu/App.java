package com.techja.game_ailatrieuphu;

import android.app.Application;

import androidx.room.Room;

import com.techja.game_ailatrieuphu.db.AppDBQuestion;


public class App extends Application {
    private static App INSTANCE;
    private Storage storage;
    private AppDBQuestion dbQuestion;

    public static App getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        storage = new Storage();
        dbQuestion = Room.databaseBuilder(getApplicationContext(), AppDBQuestion.class, "Question")
                .createFromAsset("db/Question.db")
                .build();
    }

    public Storage getStorage() {
        return storage;
    }

    public AppDBQuestion getDbQuestion() {
        return dbQuestion;
    }

}
