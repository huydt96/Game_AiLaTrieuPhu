package com.techja.game_ailatrieuphu.view.dialog;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.techja.game_ailatrieuphu.MediaManager;
import com.techja.game_ailatrieuphu.R;
import com.techja.game_ailatrieuphu.databinding.ViewHelpCallDialogBinding;


public class HelpCallDialog extends BaseDialog<ViewHelpCallDialogBinding> {
    private int trueCase;

    public HelpCallDialog(@NonNull Context context, int trueCase) {
        super(context, new Object[]{trueCase});
    }

    @Override
    protected void initData(Object[] data) {
        this.trueCase = (int) data[0];
    }

    @Override
    protected void initView() {
        binding.ivDoctor.setOnClickListener(view -> showTrueAnswer(view, R.drawable.ic_doctor_help_call));
        binding.ivProfessor.setOnClickListener(view -> showTrueAnswer(view, R.drawable.ic_professor_help_call));
        binding.ivEngineer.setOnClickListener(view -> showTrueAnswer(view, R.drawable.ic_engineer_help_call));
        binding.ivReporter.setOnClickListener(view -> showTrueAnswer(view, R.drawable.ic_reporter_help_call));
    }

    private void showTrueAnswer(View view, int idImage) {
        String trueAnswer = getTrueAnswer();
        binding.lnChooseWhoHelp.setVisibility(View.GONE);
        MediaManager.getInstance().startSong(R.raw.song_call, false, mediaPlayer -> {
            binding.lnTrueAnswer.setVisibility(View.VISIBLE);
            MediaManager.getInstance().stopSong();
        });
        binding.ivWhoHelp.setImageResource(idImage);
        binding.tvJob.setText(view.getContentDescription().toString());
        binding.tvAnswer.setText(String.format("Theo tôi đáp án đúng là %s", trueAnswer));
        binding.btCloseDialog.setOnClickListener(view1 -> {
            dismiss();
            callBack.doSomeThing();
        });

    }

    private String getTrueAnswer() {
        if (trueCase == 1) {
            return "A";
        } else if (trueCase == 2) {
            return "B";
        } else if (trueCase == 3) {
            return "C";
        } else {
            return "D";
        }
    }

    @Override
    protected ViewHelpCallDialogBinding initViewBinding() {
        return ViewHelpCallDialogBinding.inflate(getLayoutInflater());
    }


}
