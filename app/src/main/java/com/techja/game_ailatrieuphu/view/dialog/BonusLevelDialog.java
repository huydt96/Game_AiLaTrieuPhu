package com.techja.game_ailatrieuphu.view.dialog;

import android.content.Context;

import androidx.annotation.NonNull;

import com.techja.game_ailatrieuphu.databinding.ViewBonusLevelBinding;


public class BonusLevelDialog extends BaseDialog<ViewBonusLevelBinding> {
    public BonusLevelDialog(@NonNull Context context) {
        super(context, null);
    }


    @Override
    protected void initData(Object[] data) {

    }

    @Override
    protected void initView() {
        binding.btHide.setOnClickListener(view -> goToGamePlay());
    }

    @Override
    protected ViewBonusLevelBinding initViewBinding() {
        return ViewBonusLevelBinding.inflate(getLayoutInflater());
    }

    private void goToGamePlay() {
        callBack.doSomeThing();
    }
}
