package com.techja.game_ailatrieuphu.view.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.techja.game_ailatrieuphu.databinding.ViewNotificationDialogBinding;

public class OutOfTimeDialog extends BaseDialog<ViewNotificationDialogBinding> {

    public OutOfTimeDialog(@NonNull Context context) {
        super(context, null);
    }

    @Override
    protected void initData(Object[] data) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        binding.btNegativeDialog.setVisibility(View.GONE);
        binding.btPositiveDialog.setText("Đóng");
        binding.tvMessageDialog.setText("Hết giờ!");

        binding.btPositiveDialog.setOnClickListener(view -> callBack.doSomeThing());
    }

    @Override
    protected ViewNotificationDialogBinding initViewBinding() {
        return ViewNotificationDialogBinding.inflate(getLayoutInflater());
    }
}
