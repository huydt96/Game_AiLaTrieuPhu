package com.techja.game_ailatrieuphu.view.dialog;

import android.content.Context;

import androidx.annotation.NonNull;

import com.techja.game_ailatrieuphu.App;
import com.techja.game_ailatrieuphu.databinding.ViewSaveScoreDialogBinding;
import com.techja.game_ailatrieuphu.db.entities.HighScore;


public class SaveScoreDialog extends BaseDialog<ViewSaveScoreDialogBinding> {
    private String score;
    private String intScore = "";

    public SaveScoreDialog(@NonNull Context context, String score) {
        super(context, new Object[]{score});
    }

    @Override
    protected void initData(Object[] data) {
        this.score = (String) data[0];
    }

    @Override
    protected void initView() {
        binding.tvScore.setText(String.format("%s VNĐ", score));
        binding.btSubmit.setOnClickListener(view -> {
            String name = binding.edtName.getText().toString().trim();
            if (!name.equals("")) {
                String[] splitScore = score.split(",");
                for (String s : splitScore) {
                    intScore += s;
                }
                callBack.doSomeThing();
                saveScoreToBD(name);
            }
        });
    }

    private void saveScoreToBD(String name) {
        new Thread(() -> {
            HighScore highScoreInsert = new HighScore();
            highScoreInsert.name = name;
            highScoreInsert.scoreUser = Integer.parseInt(intScore);
            App.getInstance().getDbQuestion().highScoreDAO().insertScore(highScoreInsert);
        }).start();
    }

    @Override
    protected ViewSaveScoreDialogBinding initViewBinding() {
        return ViewSaveScoreDialogBinding.inflate(getLayoutInflater());
    }
}
