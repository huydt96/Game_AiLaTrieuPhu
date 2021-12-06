package com.techja.game_ailatrieuphu.view.fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.techja.game_ailatrieuphu.CommonUtils;
import com.techja.game_ailatrieuphu.MediaManager;
import com.techja.game_ailatrieuphu.R;
import com.techja.game_ailatrieuphu.databinding.M000MenuFrgBinding;
import com.techja.game_ailatrieuphu.view.callback.IOnTwoOptions;
import com.techja.game_ailatrieuphu.view.callback.OnAnimListener;
import com.techja.game_ailatrieuphu.view.dialog.BonusLevelDialog;
import com.techja.game_ailatrieuphu.view.dialog.InfoAppDialog;
import com.techja.game_ailatrieuphu.view.dialog.NotificationDialog;
import com.techja.game_ailatrieuphu.view.dialog.ScoreDialog;
import com.techja.game_ailatrieuphu.view.dialog.SettingDialog;
import com.techja.game_ailatrieuphu.viewmodel.M000MenuGameVM;

import java.util.Collections;


public class M000MenuFrg extends BaseFragment<M000MenuFrgBinding, M000MenuGameVM> {
    public static final String TAG = M000MenuFrg.class.getName();
    private BonusLevelDialog bonusLevelDialog;
    private NotificationDialog readyDialog;
    private ScoreDialog scoreDialog;
    private boolean wentToOnStart = false;

    @Override
    protected void initViews() {
        Animation animAlpha = AnimationUtils.loadAnimation(context, R.anim.anim_alpha);
        Animation animRotateCircle = AnimationUtils.loadAnimation(context, R.anim.anim_rotate_circle);
        Animation animRotateLoading = AnimationUtils.loadAnimation(context, R.anim.anim_rotate_loading);
        if (wentToOnStart) {

            viewModel.getScoreListFromDB();
            binding.ivLoading.setVisibility(View.GONE);
            binding.lnMain.setVisibility(View.VISIBLE);
            binding.ivCircular.startAnimation(animRotateCircle);
            playBgMedia();

        } else {
            viewModel.getScoreListFromDB();
            animRotateLoading.setAnimationListener(new OnAnimListener() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    binding.ivLoading.setVisibility(View.GONE);
                    binding.lnMain.setVisibility(View.VISIBLE);
                    binding.lnMain.startAnimation(animAlpha);
                    binding.ivCircular.startAnimation(animRotateCircle);
                    playBgMedia();
                }
            });
            binding.ivLoading.startAnimation(animRotateLoading);
        }


        viewModel.getScoreListLD().observe(this, scores -> {
            if (scores != null) {
                Collections.sort(scores, (s1, s2) -> s1.scoreUser >= s2.scoreUser ? -1 : 1);
                scoreDialog = new ScoreDialog(context, scores);
            }
        });

        binding.btPlay.setOnClickListener(view -> {
            MediaManager.getInstance().pauseBg();
            showBonusLevelDialog();
        });

        binding.btAbout.setOnClickListener(view -> showDialogInfoApp());
        binding.btArchivement.setOnClickListener(view -> showDialogScore());
        binding.btSetting.setOnClickListener(view -> showSettingGame());
    }

    private void playBgMedia() {
        new Thread(() -> {
            String stateBg = CommonUtils.getInstance().getPref(SettingDialog.STATE_BG);
            String stateSong = CommonUtils.getInstance().getPref(SettingDialog.STATE_SONG);
            if (stateBg != null) {
                Log.i(TAG, stateBg + ":" + stateSong);
                if (stateBg.equals("0")) {
                    MediaManager.getInstance().startBg(R.raw.song_intro, true);
                } else {
                    MediaManager.getInstance().setTurnOnBgMedia(false);
                }

                if (stateSong.equals("1")) {
                    MediaManager.getInstance().setTurnOnSongMedia(false);
                }
            } else {
                MediaManager.getInstance().startBg(R.raw.song_intro, true);
            }
        }).start();
    }

    private void showSettingGame() {
        SettingDialog settingDialog = new SettingDialog(context);
        settingDialog.show();
    }

    private void showDialogScore() {
        scoreDialog.show();
    }

    private void showDialogInfoApp() {
        InfoAppDialog dialog = new InfoAppDialog(context);
        dialog.show();
    }

    private void showBonusLevelDialog() {
        if (bonusLevelDialog == null) {
            bonusLevelDialog = new BonusLevelDialog(context);
            bonusLevelDialog.setCallBack(() -> {
                bonusLevelDialog.dismiss();
                MediaManager.getInstance().stopBg();
                MediaManager.getInstance().stopSong();
                callBack.showFragment(M001GamePlayFrg.TAG, null, true);
            });
        }
        bonusLevelDialog.show();
        MediaManager.getInstance().startSong(R.raw.song_rule, false, mediaPlayer -> MediaManager.getInstance().startSong(R.raw.song_ready, false, mediaPlayer1 -> showDialogReady()));

    }

    private void showDialogReady() {
        if (readyDialog == null) {
            readyDialog = new NotificationDialog(context, "Thông báo", "Bạn đã sẵn sàng chơi với chúng tôi ?", "Bỏ qua", "Sẵn sàng");
            readyDialog.setCanceledOnTouchOutside(false);
            readyDialog.setCancelable(false);
            readyDialog.setCallBackTwoOptions(new IOnTwoOptions() {
                @Override
                public void doPositiveAction() {
                    MediaManager.getInstance().stopBg();
                    MediaManager.getInstance().stopSong();
                    readyDialog.dismiss();
                    bonusLevelDialog.dismiss();
                    callBack.showFragment(M001GamePlayFrg.TAG, null, true);
                }

                @Override
                public void doNegativeAction() {
                    readyDialog.dismiss();
                    bonusLevelDialog.dismiss();
                    MediaManager.getInstance().continueBg();
                }
            });
        }
        readyDialog.show();
    }

    @Override
    public boolean onBackPressed() {
        NotificationDialog dialogCloseApp = new NotificationDialog(context, "THÔNG BÁO", "Bạn có muốn thoát trò chơi", "Hủy", "Đồng ý");
        dialogCloseApp.setCancelable(false);
        dialogCloseApp.setCanceledOnTouchOutside(false);
        dialogCloseApp.setCallBackTwoOptions(new IOnTwoOptions() {
            @Override
            public void doPositiveAction() {
                getActivity().finish();
            }

            @Override
            public void doNegativeAction() {
                dialogCloseApp.dismiss();
            }
        });
        dialogCloseApp.show();
        return true;
    }

    @Override
    protected Class<M000MenuGameVM> getClassViewModel() {
        return M000MenuGameVM.class;
    }

    @Override
    protected M000MenuFrgBinding initViewBinding(@NonNull LayoutInflater inflater,
                                                 @Nullable ViewGroup container) {
        return M000MenuFrgBinding.inflate(inflater, container, false);
    }

    @Override
    public void onStart() {
        wentToOnStart = true;
        super.onStart();
    }
}
