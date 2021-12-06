package com.techja.game_ailatrieuphu.view.fragment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;


import com.techja.game_ailatrieuphu.MediaManager;
import com.techja.game_ailatrieuphu.R;
import com.techja.game_ailatrieuphu.databinding.M001GamePlayFrgBinding;
import com.techja.game_ailatrieuphu.db.entities.Question;
import com.techja.game_ailatrieuphu.view.callback.IOnTwoOptions;
import com.techja.game_ailatrieuphu.view.dialog.HelpAudienceDialog;
import com.techja.game_ailatrieuphu.view.dialog.HelpCallDialog;
import com.techja.game_ailatrieuphu.view.dialog.NotificationDialog;
import com.techja.game_ailatrieuphu.view.dialog.OutOfTimeDialog;
import com.techja.game_ailatrieuphu.view.dialog.SaveScoreDialog;
import com.techja.game_ailatrieuphu.viewmodel.M001GamePlayVM;

import java.util.Collections;

public class M001GamePlayFrg extends BaseFragment<M001GamePlayFrgBinding, M001GamePlayVM> {
    public static final String TAG = M001GamePlayFrg.class.getName();
    private SaveScoreDialog dialogScore;

    @Override
    protected Class<M001GamePlayVM> getClassViewModel() {
        return M001GamePlayVM.class;
    }

    @Override
    protected M001GamePlayFrgBinding initViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return M001GamePlayFrgBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initViews() {
        viewModel.get15Question();
        viewModel.startTimeRemaining();
        viewModel.getListQuestionLD().observe(this, questions -> {
            if (questions != null) {
                Collections.sort(questions, (q1, q2) -> q1.level < q2.level ? -1 : 1);
                viewModel.setListQuestion(questions);
            }
        });

        binding.includeBonusLevel.btHide.setVisibility(View.GONE);
        MediaManager.getInstance().startSong(R.raw.song_start_game_play, false, mediaPlayer -> newQuestion());
        binding.btAnswerA.setOnClickListener(view -> checkResult(view, 1));
        binding.btAnswerB.setOnClickListener(view -> checkResult(view, 2));
        binding.btAnswerC.setOnClickListener(view -> checkResult(view, 3));
        binding.btAnswerD.setOnClickListener(view -> checkResult(view, 4));

        viewModel.getIsLoseLD().observe(this, isLose -> {
            if (isLose) {
                binding.progressTime.setVisibility(View.GONE);
                MediaManager.getInstance().stopBg();
                MediaManager.getInstance().startSong(R.raw.song_out_of_time, false, null);
                OutOfTimeDialog outTimeDialog = new OutOfTimeDialog(context);
                outTimeDialog.setCallBack(() -> {
                    outTimeDialog.dismiss();
                    hasMoney();
                });
                outTimeDialog.show();
            }
        });

        binding.btStopGame.setOnClickListener(view -> onBackPressed());
        binding.btChangeQuestion.setOnClickListener(this::doHelpChangeQuestion);
        binding.btHelp5050.setOnClickListener(this::doHelp5050);
        binding.btHelpAudience.setOnClickListener(this::doHelpFromAudience);
        binding.btHelpCall.setOnClickListener(this::doHelpCall);


    }

    private void doHelpCall(View view) {
        HelpCallDialog helpCallDialog = new HelpCallDialog(context, viewModel.getTrueCase());
        helpCallDialog.setCallBack(this::doneHelp);

        whenClickHelp(view, R.drawable.ic_help_call_disable);
        MediaManager.getInstance().startSong(R.raw.song_help_call, false, mediaPlayer -> {
            helpCallDialog.show();
            MediaManager.getInstance().startSong(R.raw.song_start_time_help_call, false, null);
        });
    }

    @SuppressLint("WrongConstant")
    private void doHelpFromAudience(View view) {
        int tvAnsA = getView().findViewById(M001GamePlayVM.listTVAnswer[0]).getVisibility();
        int tvAnsB = getView().findViewById(M001GamePlayVM.listTVAnswer[1]).getVisibility();
        int tvAnsC = getView().findViewById(M001GamePlayVM.listTVAnswer[2]).getVisibility();
        int tvAnsD = getView().findViewById(M001GamePlayVM.listTVAnswer[3]).getVisibility();
        int[] percentChart = viewModel.randomPercentChart(new int[]{tvAnsA, tvAnsB, tvAnsC, tvAnsD});

        HelpAudienceDialog helpAudienceDialog = new HelpAudienceDialog(context, percentChart[0], percentChart[1], percentChart[2], percentChart[3]);
        helpAudienceDialog.setCallBack(this::doneHelp);
        helpAudienceDialog.show();

        whenClickHelp(view, R.drawable.ic_help_audience_disable);
        MediaManager.getInstance().startSong(R.raw.song_help_from_audience, false, mediaPlayer -> {
            helpAudienceDialog.startVote();
            MediaManager.getInstance().startSong(R.raw.song_vote_from_audience, false, mediaPlayer1 -> helpAudienceDialog.showButtonCloseDialog());
        });

    }

    private void doHelp5050(View view) {
        whenClickHelp(view, R.drawable.ic_help_5050_disable);
        MediaManager.getInstance().startSong(R.raw.song_help_5050, false, mediaPlayer -> {
            removeTwoWrongAnswer();
            doneHelp();
        });

    }

    private void doneHelp() {
        viewModel.setPauseTimeRemeaning(false);
        binding.progressTime.setVisibility(View.VISIBLE);
        MediaManager.getInstance().continueBg();
    }

    private void whenClickHelp(View view, int idDisable) {
        binding.progressTime.setVisibility(View.INVISIBLE);
        ((ImageView) view).setImageResource(idDisable);
        view.setEnabled(false);
        viewModel.setPauseTimeRemeaning(true);
        MediaManager.getInstance().pauseBg();
    }

    private void removeTwoWrongAnswer() {
        if (viewModel.getTrueCase() == 1) {
            binding.btAnswerB.setVisibility(View.INVISIBLE);
            binding.btAnswerC.setVisibility(View.INVISIBLE);
        } else if (viewModel.getTrueCase() == 2) {
            binding.btAnswerA.setVisibility(View.INVISIBLE);
            binding.btAnswerC.setVisibility(View.INVISIBLE);
        } else if (viewModel.getTrueCase() == 3) {
            binding.btAnswerA.setVisibility(View.INVISIBLE);
            binding.btAnswerB.setVisibility(View.INVISIBLE);
        } else {
            binding.btAnswerB.setVisibility(View.INVISIBLE);
            binding.btAnswerC.setVisibility(View.INVISIBLE);
        }
    }

    private void doHelpChangeQuestion(View view) {
        viewModel.getNewQuestionLD().observe(this, question -> {
            if (question != null) {
                viewModel.setTrueCase(question.trueCase, question.level);

                binding.btAnswerA.setVisibility(View.VISIBLE);
                binding.btAnswerB.setVisibility(View.VISIBLE);
                binding.btAnswerC.setVisibility(View.VISIBLE);
                binding.btAnswerD.setVisibility(View.VISIBLE);

                binding.tvQuestion.setText(question.question);
                binding.btAnswerA.setText(String.format("A: %s", question.caseA));
                binding.btAnswerB.setText(String.format("B: %s", question.caseB));
                binding.btAnswerC.setText(String.format("C: %s", question.caseC));
                binding.btAnswerD.setText(String.format("D: %s", question.caseD));
            }
        });

        viewModel.setPauseTimeRemeaning(true);
        NotificationDialog changeQuestionDialog = new NotificationDialog(context, "Thông báo", "Bạn thực sự muốn đổi câu hỏi ?", "Hủy bỏ", "Đồng ý");
        changeQuestionDialog.setCallBackTwoOptions(new IOnTwoOptions() {
            @Override
            public void doPositiveAction() {
                viewModel.newQuestionByLevel();
                changeQuestionDialog.dismiss();
                viewModel.setPauseTimeRemeaning(false);
                ((ImageView) view).setImageResource(R.drawable.ic_change_question_disable);
                view.setEnabled(false);
            }

            @Override
            public void doNegativeAction() {
                changeQuestionDialog.dismiss();
                viewModel.setPauseTimeRemeaning(false);
            }
        });
        changeQuestionDialog.show();
    }

    private void checkResult(View view, int answer) {
        binding.btAnswerA.setEnabled(false);
        binding.btAnswerB.setEnabled(false);
        binding.btAnswerC.setEnabled(false);
        binding.btAnswerD.setEnabled(false);
        int trueCase = viewModel.getTrueCase();
        MediaManager.getInstance().pauseBg();
        viewModel.setPauseTimeRemeaning(true);
        binding.progressTime.setVisibility(View.INVISIBLE);
        view.setBackgroundResource(R.drawable.ic_bg_selected_answer);
        MediaManager.getInstance().startSong(M001GamePlayVM.listSongAns[answer - 1], false, mediaPlayer -> {
            if (trueCase == answer) {
                whenTrueAnswer(view, answer);
            } else {
                whenWrongAnswer(view, trueCase);
            }
        });

    }

    private void whenTrueAnswer(View view, int answer) {
        TextView tvBonusMoney = requireView().findViewById(M001GamePlayVM.listTVBonusesCurrent[viewModel.getIndexCurrentQuestion()]);
        viewModel.setMoney((String) tvBonusMoney.getText());
        binding.tvMoney.setText(viewModel.getMoney());
        view.setBackgroundResource(R.drawable.ic_bg_true_answer);
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_blink_true_answer));
        MediaManager.getInstance().startSong(M001GamePlayVM.listSongTrueAns[answer - 1], false, mediaPlayer1 -> {
            if (viewModel.getIndexCurrentQuestion() != 14) {
                viewModel.increaseIndex();
                view.setBackgroundResource(R.drawable.ic_bg_answer);
                newQuestion();
            } else {
                MediaManager.getInstance().startSong(R.raw.song_winner, false, mediaPlayer -> hasMoney());
            }
        });
    }

    private void whenWrongAnswer(View view, int trueCase) {
        TextView tvTrueAns = requireView().findViewById(M001GamePlayVM.listTVAnswer[trueCase - 1]);
        view.setBackgroundResource(R.drawable.ic_bg_wrong_answer);
        tvTrueAns.setBackgroundResource(R.drawable.ic_bg_true_answer);
        tvTrueAns.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_blink_true_answer));
        MediaManager.getInstance().startSong(M001GamePlayVM.listSongWrongAns[trueCase - 1], false, mediaPlayer12 -> hasMoney());
    }

    private void hasMoney() {
        if (!viewModel.getMoney().equals("0")) {
            showDialogSaveScore();
        } else {
            MediaManager.getInstance().startSong(R.raw.song_thank_you, false, mediaPlayer13 -> backToPreScreen());
        }
    }

    private void showDialogSaveScore() {
        dialogScore = new SaveScoreDialog(context, viewModel.getMoney());
        dialogScore.setCallBack(() -> {
            dialogScore.dismiss();
            MediaManager.getInstance().startSong(R.raw.song_thank_you, false, mediaPlayer13 -> backToPreScreen());
        });
        dialogScore.show();
    }

    private void backToPreScreen() {
        MediaManager.getInstance().stopBg();
        MediaManager.getInstance().stopSong();
        callBack.backToPrevious();
    }

    private void newQuestion() {
        int currentIndex = viewModel.getIndexCurrentQuestion();
        if (currentIndex > 0) {
            requireView().findViewById(M001GamePlayVM.listTVBonusesCurrent[currentIndex - 1]).setBackgroundResource(0);
            if (currentIndex == 5 || currentIndex == 10) {
                requireView().findViewById(M001GamePlayVM.listTVBonusesCurrent[currentIndex - 1]).setBackgroundResource(R.drawable.ic_bg_money_milestone);
            }
        }
        requireView().findViewById(M001GamePlayVM.listTVBonusesCurrent[currentIndex]).setBackgroundResource(R.drawable.ic_bg_money_curent);
        binding.drawer.openDrawer(GravityCompat.START);
        MediaManager.getInstance().startSong(M001GamePlayVM.songQuestionCurrent[currentIndex], false, mediaPlayer -> {
            MediaManager.getInstance().startBg(R.raw.song_bg_game_play, true);
            binding.drawer.closeDrawers();
            binding.lnMain.setVisibility(View.VISIBLE);
            viewModel.setNewTimeRemeaning(true);
            viewModel.setPauseTimeRemeaning(false);
            viewModel.getTimeRemaining().observe(this, timeRemaining -> binding.tvTime.setText(String.format("%s", timeRemaining)));
        });
        updateQuestionUI();
    }

    private void updateQuestionUI() {
        binding.progressTime.setVisibility(View.VISIBLE);
        binding.btAnswerA.setVisibility(View.VISIBLE);
        binding.btAnswerB.setVisibility(View.VISIBLE);
        binding.btAnswerC.setVisibility(View.VISIBLE);
        binding.btAnswerD.setVisibility(View.VISIBLE);
        binding.btAnswerA.setEnabled(true);
        binding.btAnswerB.setEnabled(true);
        binding.btAnswerC.setEnabled(true);
        binding.btAnswerD.setEnabled(true);

        Question questionCurrent = viewModel.getListQuestion().get(viewModel.getIndexCurrentQuestion());
        binding.tvNumberQuestion.setText(String.format("Câu: %s", questionCurrent.level));
        binding.tvQuestion.setText(questionCurrent.question);
        binding.btAnswerA.setText(String.format("A: %s", questionCurrent.caseA));
        binding.btAnswerB.setText(String.format("B: %s", questionCurrent.caseB));
        binding.btAnswerC.setText(String.format("C: %s", questionCurrent.caseC));
        binding.btAnswerD.setText(String.format("D: %s", questionCurrent.caseD));
    }

    @Override
    public boolean onBackPressed() {
        viewModel.setPauseTimeRemeaning(true);
        NotificationDialog dialogCloseApp = new NotificationDialog(context, "THÔNG BÁO", "Bạn thực sự muốn dừng cuộc chơi ?", "Hủy bỏ", "Đồng ý");
        dialogCloseApp.setCallBackTwoOptions(new IOnTwoOptions() {
            @Override
            public void doPositiveAction() {
                dialogCloseApp.dismiss();
                binding.progressTime.setVisibility(View.GONE);
                MediaManager.getInstance().stopBg();
                hasMoney();
            }

            @Override
            public void doNegativeAction() {
                dialogCloseApp.dismiss();
                viewModel.setPauseTimeRemeaning(false);
            }
        });
        dialogCloseApp.show();
        return true;
    }

    @Override
    public void onPause() {
        viewModel.setPauseTimeRemeaning(true);
        super.onPause();
    }

    @Override
    public void onStart() {
        viewModel.setPauseTimeRemeaning(false);
        super.onStart();
    }

    @Override
    public void onDestroy() {
        viewModel.stopTimeRemaining();
        super.onDestroy();
    }
}
