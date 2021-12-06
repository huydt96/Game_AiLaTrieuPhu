package com.techja.game_ailatrieuphu.view.dialog;

import android.content.Context;

import androidx.annotation.NonNull;

import com.techja.game_ailatrieuphu.databinding.ViewScoreDialogBinding;
import com.techja.game_ailatrieuphu.db.entities.HighScore;
import com.techja.game_ailatrieuphu.view.adapter.ListScoreAdapter;

import java.util.List;


public class ScoreDialog extends BaseDialog<ViewScoreDialogBinding> {
    private Context context;
    private List<HighScore> highScoreList;
    private ListScoreAdapter adapter;

    public ScoreDialog(@NonNull Context context, List<HighScore> highScoreList) {
        super(context, new Object[]{context, highScoreList});
    }

    @Override
    protected void initData(Object[] data) {
        this.context = (Context) data[0];
        this.highScoreList = (List<HighScore>) data[1];
    }

    @Override
    protected void initView() {
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        if (adapter == null) {
            adapter = new ListScoreAdapter(context, highScoreList);
            binding.rvScore.setAdapter(adapter);
        }
    }

    @Override
    protected ViewScoreDialogBinding initViewBinding() {
        return ViewScoreDialogBinding.inflate(getLayoutInflater());
    }
}
