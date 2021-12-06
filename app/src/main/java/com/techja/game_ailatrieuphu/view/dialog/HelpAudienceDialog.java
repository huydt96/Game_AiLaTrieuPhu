package com.techja.game_ailatrieuphu.view.dialog;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.techja.game_ailatrieuphu.R;
import com.techja.game_ailatrieuphu.databinding.ViewHelpAudienceDialogBinding;


public class HelpAudienceDialog extends BaseDialog<ViewHelpAudienceDialogBinding> {
    private static final int KEY_CHART_A = 101;
    private static final int KEY_CHART_B = 102;
    private static final int KEY_CHART_C = 103;
    private static final int KEY_CHART_D = 104;
    private int percentA;
    private int percentB;
    private int percentC;
    private int percentD;
    private double chartHeight;
    private final Handler mHandler = new Handler(message -> {
        if (message.what == KEY_CHART_A) {
            updateUI(message);
        } else if (message.what == KEY_CHART_B) {
            updateUI(message);
        } else if (message.what == KEY_CHART_C) {
            updateUI(message);
        } else {
            updateUI(message);
        }
        return false;
    });

    public HelpAudienceDialog(@NonNull Context context, int percentA, int percentB, int percentC, int percentD) {
        super(context, new Object[]{percentA, percentB, percentC, percentD});
    }

    @Override
    protected void initData(Object[] data) {
        this.percentA = (int) data[0];
        this.percentB = (int) data[1];
        this.percentC = (int) data[2];
        this.percentD = (int) data[3];
    }

    @Override
    protected void initView() {
        binding.btCloseDialog.setOnClickListener(view -> {
            dismiss();
            callBack.doSomeThing();
        });
        chartHeight = binding.chartA.getResources().getDimension(R.dimen.d_200);
    }

    @Override
    protected ViewHelpAudienceDialogBinding initViewBinding() {
        return ViewHelpAudienceDialogBinding.inflate(getLayoutInflater());
    }

    private void updateUI(Message message) {
        ViewGroup.LayoutParams params = ((View) message.obj).getLayoutParams();
        params.height = ((int) chartHeight / 100 * message.arg1);
        ((View) message.obj).setLayoutParams(params);
        ((TextView) findViewById(message.arg2)).setText(String.format("%s%%", message.arg1));
    }

    public void startVote() {
        increaseChart(percentA, KEY_CHART_A, R.id.tv_percent_a, binding.chartA);
        increaseChart(percentB, KEY_CHART_B, R.id.tv_percent_b, binding.chartB);
        increaseChart(percentC, KEY_CHART_C, R.id.tv_percent_c, binding.chartC);
        increaseChart(percentD, KEY_CHART_D, R.id.tv_percent_d, binding.chartD);
    }

    public void showButtonCloseDialog() {
        binding.btCloseDialog.setVisibility(View.VISIBLE);
    }

    private void increaseChart(int percent, int key, int idTV, View view) {
        new Thread(() -> {
            for (int i = 1; i <= percent; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message msg = new Message();
                msg.what = key;
                msg.obj = view;
                msg.arg1 = i;
                msg.arg2 = idTV;
                msg.setTarget(mHandler);

                msg.sendToTarget();
            }
        }).start();
    }
}
