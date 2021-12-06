package com.techja.game_ailatrieuphu.view.dialog;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.techja.game_ailatrieuphu.R;
import com.techja.game_ailatrieuphu.view.callback.IOnDoSomeThing;
import com.techja.game_ailatrieuphu.view.callback.IOnTwoOptions;


public abstract class BaseDialog<T extends ViewBinding> extends Dialog {
    protected T binding;
    protected IOnDoSomeThing callBack;
    protected IOnTwoOptions callBackTwoOptions;

    public BaseDialog(@NonNull Context context, Object[] data) {
        super(context, R.style.BaseDialogCustom);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        initData(data);
        binding = initViewBinding();
        setContentView(binding.getRoot());
        initView();
    }

    protected abstract void initData(Object[] data);

    public void setCallBack(IOnDoSomeThing callBack) {
        this.callBack = callBack;
    }

    public void setCallBackTwoOptions(IOnTwoOptions callBackTwoOptions) {
        this.callBackTwoOptions = callBackTwoOptions;
    }

    protected abstract void initView();

    protected abstract T initViewBinding();

}
