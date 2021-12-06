package com.techja.game_ailatrieuphu.view.dialog;

import android.content.Context;

import androidx.annotation.NonNull;

import com.techja.game_ailatrieuphu.databinding.ViewInfoAppDialogBinding;


public class InfoAppDialog extends BaseDialog<ViewInfoAppDialogBinding> {
    public InfoAppDialog(@NonNull Context context) {
        super(context, null);
    }

    @Override
    protected void initData(Object[] data) {

    }

    @Override
    protected void initView() {
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected ViewInfoAppDialogBinding initViewBinding() {
        return ViewInfoAppDialogBinding.inflate(getLayoutInflater());
    }
}
