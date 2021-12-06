package com.techja.game_ailatrieuphu.view.dialog;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.techja.game_ailatrieuphu.CommonUtils;
import com.techja.game_ailatrieuphu.MediaManager;
import com.techja.game_ailatrieuphu.R;
import com.techja.game_ailatrieuphu.databinding.ViewSettingDialogBinding;

public class SettingDialog extends BaseDialog<ViewSettingDialogBinding> {
    public static final String STATE_BG = "STATE_BG";
    public static final String STATE_SONG = "STATE_SONG";
    private static final String TAG = SettingDialog.class.getName();

    public SettingDialog(@NonNull Context context) {
        super(context, null);
    }

    @Override
    protected void initData(Object[] data) {

    }

    @Override
    protected void initView() {
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        String stateBg = CommonUtils.getInstance().getPref(STATE_BG);
        String stateSong = CommonUtils.getInstance().getPref(STATE_SONG);
        if (stateBg != null && stateSong != null) {
            if (stateBg.equals("0")) {
                initStateSetting(binding.ivStateBgMedia, R.drawable.ic_button_on_media, 0, true);
            } else {
                initStateSetting(binding.ivStateBgMedia, R.drawable.ic_button_off_media, 1, false);
            }

            if (stateSong.equals("0")) {
                initStateSetting(binding.ivStateSong, R.drawable.ic_button_on_media, 0, true);
            } else {
                initStateSetting(binding.ivStateSong, R.drawable.ic_button_off_media, 1, false);
            }
        } else {
            binding.ivStateBgMedia.setTag(0);
            binding.ivStateSong.setTag(0);
        }

        binding.ivStateBgMedia.setOnClickListener(view -> controlBgMedia());
        binding.ivStateSong.setOnClickListener(view -> controlSongMedia());
    }

    private void controlSongMedia() {
        if (binding.ivStateSong.getTag().equals(0)) {
            binding.ivStateSong.setImageResource(R.drawable.ic_button_off_media);
            binding.ivStateSong.setTag(1);
            MediaManager.getInstance().setTurnOnSongMedia(false);
        } else {
            MediaManager.getInstance().setTurnOnSongMedia(true);
            binding.ivStateSong.setImageResource(R.drawable.ic_button_on_media);
            binding.ivStateSong.setTag(0);
        }
    }

    private void controlBgMedia() {
        if (binding.ivStateBgMedia.getTag().equals(0)) {
            binding.ivStateBgMedia.setImageResource(R.drawable.ic_button_off_media);
            binding.ivStateBgMedia.setTag(1);
            MediaManager.getInstance().stopBg();
            MediaManager.getInstance().setTurnOnBgMedia(false);
        } else {
            MediaManager.getInstance().setTurnOnBgMedia(true);
            MediaManager.getInstance().startBg(R.raw.song_intro, true);
            binding.ivStateBgMedia.setImageResource(R.drawable.ic_button_on_media);
            binding.ivStateBgMedia.setTag(0);
        }
    }

    private void initStateSetting(ImageView imageView, int idButton, Object tag, boolean isTurnOnMedia) {
        imageView.setImageResource(idButton);
        imageView.setTag(tag);
        MediaManager.getInstance().setTurnOnBgMedia(isTurnOnMedia);
    }

    @Override
    public void onDetachedFromWindow() {
        Log.i(TAG,binding.ivStateBgMedia.getTag().toString()+":bg");
        Log.i(TAG,binding.ivStateSong.getTag().toString()+":song");
        CommonUtils.getInstance().savePref(STATE_BG, binding.ivStateBgMedia.getTag().toString());
        CommonUtils.getInstance().savePref(STATE_SONG, binding.ivStateSong.getTag().toString());
        super.onDetachedFromWindow();
    }

    @Override
    protected ViewSettingDialogBinding initViewBinding() {
        return ViewSettingDialogBinding.inflate(getLayoutInflater());
    }
}
