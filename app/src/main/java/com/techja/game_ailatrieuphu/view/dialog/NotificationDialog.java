package com.techja.game_ailatrieuphu.view.dialog;

import android.content.Context;

import androidx.annotation.NonNull;

import com.techja.game_ailatrieuphu.databinding.ViewNotificationDialogBinding;


public class NotificationDialog extends BaseDialog<ViewNotificationDialogBinding> {
    String title, message, textBtNegative, textBtPositive;


    public NotificationDialog(@NonNull Context context, String title, String message, String textBtNegative, String textBtPositive) {
        super(context, new Object[]{title, message, textBtNegative, textBtPositive});
    }

    @Override
    protected void initData(Object[] data) {
        this.title = (String) data[0];
        this.message = (String) data[1];
        this.textBtNegative = (String) data[2];
        this.textBtPositive = (String) data[3];
    }

    @Override
    protected void initView() {
        binding.tvTitleDialog.setText(title);
        binding.tvMessageDialog.setText(message);
        binding.btNegativeDialog.setText(textBtNegative);
        binding.btPositiveDialog.setText(textBtPositive);

        binding.btNegativeDialog.setOnClickListener(view -> callBackTwoOptions.doNegativeAction());
        binding.btPositiveDialog.setOnClickListener(view -> callBackTwoOptions.doPositiveAction());
    }

    @Override
    protected ViewNotificationDialogBinding initViewBinding() {
        return ViewNotificationDialogBinding.inflate(getLayoutInflater());
    }
}
