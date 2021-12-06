package com.techja.game_ailatrieuphu.viewmodel;

import androidx.lifecycle.MutableLiveData;


import com.techja.game_ailatrieuphu.App;
import com.techja.game_ailatrieuphu.R;
import com.techja.game_ailatrieuphu.db.entities.Question;

import java.util.List;
import java.util.Random;

public class M001GamePlayVM extends BaseViewModel {
    public static final String KEY_GET_15_QUESTION = "KEY_GET_15_QUESTION";
    public static final int[] songQuestionCurrent = {R.raw.ques1, R.raw.ques2, R.raw.ques3, R.raw.ques4, R.raw.ques5, R.raw.ques6, R.raw.ques7, R.raw.ques8, R.raw.ques9, R.raw.ques10, R.raw.ques11, R.raw.ques12, R.raw.ques13, R.raw.ques14, R.raw.ques15};
    public static final int[] listTVBonusesCurrent = {R.id.tv_bonus_1, R.id.tv_bonus_2, R.id.tv_bonus_3, R.id.tv_bonus_4, R.id.tv_bonus_5, R.id.tv_bonus_6, R.id.tv_bonus_7, R.id.tv_bonus_8, R.id.tv_bonus_9, R.id.tv_bonus_10, R.id.tv_bonus_11, R.id.tv_bonus_12, R.id.tv_bonus_13, R.id.tv_bonus_14, R.id.tv_bonus_15};
    public static final int[] listTVAnswer = {R.id.bt_answer_a, R.id.bt_answer_b, R.id.bt_answer_c, R.id.bt_answer_d};
    private static final String KEY_GET_QUESTION_BY_LEVEL = "KEY_GET_QUESTION_BY_LEVEL";
    public static int[] listSongAns = {R.raw.song_ans_a, R.raw.song_ans_b, R.raw.song_ans_c, R.raw.song_ans_d};
    public static int[] listSongTrueAns = {R.raw.true_a, R.raw.true_b, R.raw.true_c, R.raw.true_d};
    public static int[] listSongWrongAns = {R.raw.lose_a, R.raw.lose_b, R.raw.lose_c, R.raw.lose_d};
    private final MutableLiveData<List<Question>> listQuestionLD = new MutableLiveData<>(null);
    private final MutableLiveData<Question> newQuestionLD = new MutableLiveData<>(null);
    private final MutableLiveData<Integer> timeRemaining = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoseLD = new MutableLiveData<>(false);
    private List<Question> listQuestion;
    private int indexCurrentQuestion = 0;
    private String money = "0";
    private int trueCase = 0;
    private int flagTrueCaseWhenChange = 0;
    private Thread thread;
    private boolean isPause = false;
    private boolean isNew = true;


    public void setPauseTimeRemeaning(boolean pause) {
        this.isPause = pause;
    }

    public void setNewTimeRemeaning(boolean isNew) {
        this.isNew = isNew;
    }

    public int getTrueCase() {
        if (flagTrueCaseWhenChange == listQuestion.get(indexCurrentQuestion).level) {
            return trueCase;
        }
        return listQuestion.get(indexCurrentQuestion).trueCase;
    }

    public void setTrueCase(int trueCase, int flag) {
        flagTrueCaseWhenChange = flag;
        this.trueCase = trueCase;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public MutableLiveData<Integer> getTimeRemaining() {
        return timeRemaining;
    }

    public MutableLiveData<Boolean> getIsLoseLD() {
        return isLoseLD;
    }

    public List<Question> getListQuestion() {
        return listQuestion;
    }

    public void setListQuestion(List<Question> listQuestion) {
        this.listQuestion = listQuestion;
    }

    public int getIndexCurrentQuestion() {
        return indexCurrentQuestion;
    }

    public MutableLiveData<List<Question>> getListQuestionLD() {
        return listQuestionLD;
    }

    public MutableLiveData<Question> getNewQuestionLD() {
        return newQuestionLD;
    }

    public void get15Question() {
        queryToDB(KEY_GET_15_QUESTION);
    }

    public void newQuestionByLevel() {
        queryToDB(KEY_GET_QUESTION_BY_LEVEL);
    }

    private void queryToDB(String key) {
        new Thread(() -> executeTask(key)).start();
    }

    private void executeTask(String key) {
        if (key.equals(KEY_GET_15_QUESTION)) {
            listQuestionLD.postValue(App.getInstance().getDbQuestion().questionDAO().getAllQuestion());
        } else if (key.equals(KEY_GET_QUESTION_BY_LEVEL)) {
            newQuestionLD.postValue(App.getInstance().getDbQuestion().questionDAO().getQuestionByLevel(listQuestion.get(indexCurrentQuestion).level));
        }
    }

    public void startTimeRemaining() {
        thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    if (isPause) continue;
                    if (isNew) {
                        isNew = false;
                        timeRemaining.postValue(30);
                        continue;
                    }
                    timeRemaining.postValue(timeRemaining.getValue() - 1);
                    if (timeRemaining.getValue() == 1) {
                        isLoseLD.postValue(true);
                        thread.interrupt();
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }

    public void stopTimeRemaining() {
        thread.interrupt();
        thread = null;
    }

    public void increaseIndex() {
        indexCurrentQuestion++;
    }


    public int[] randomPercentChart(int[] chart) {
        boolean isHaveFullAns = true;
        Random random = new Random();
        int indexTrueCaseHelp = getTrueCase() - 1;
        int[] perCentChart = new int[4];
        for (int i = 0; i < 4; i++) {
            if (chart[i] != 0) {
                isHaveFullAns = false;
                break;
            }
        }
        if (isHaveFullAns) {
            int lastIndex = -1;
            int percentTemp = 0;
            perCentChart[indexTrueCaseHelp] = random.nextInt(10) + 40;
            for (int i = 0; i < 4; i++) {
                if (i != indexTrueCaseHelp) {
                    perCentChart[i] = random.nextInt(10) + 10;
                    percentTemp += perCentChart[i];
                    lastIndex = i;
                }
            }
            perCentChart[lastIndex] = 100 - (percentTemp - perCentChart[lastIndex]) - perCentChart[indexTrueCaseHelp];
        } else {
            perCentChart[indexTrueCaseHelp] = random.nextInt(20) + 50;
            for (int i = 0; i < 4; i++) {
                if (chart[i] == 0 && i != indexTrueCaseHelp) {
                    perCentChart[i] = 100 - perCentChart[indexTrueCaseHelp];
                }
            }
        }

        return perCentChart;
    }
}
