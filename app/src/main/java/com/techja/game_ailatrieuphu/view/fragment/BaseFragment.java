package com.techja.game_ailatrieuphu.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.techja.game_ailatrieuphu.MediaManager;
import com.techja.game_ailatrieuphu.view.callback.IOnBackPressed;
import com.techja.game_ailatrieuphu.view.callback.IOnMainCallBack;


public abstract class BaseFragment<B extends ViewBinding, V extends ViewModel>
        extends Fragment implements View.OnClickListener, IOnBackPressed {
    public static final String TAG = BaseFragment.class.getName();
    protected Context context;
    protected B binding;
    protected V viewModel;
    protected IOnMainCallBack callBack;
    protected Object mData;

    @Override
    public final void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater,
                                   @Nullable ViewGroup container,
                                   @Nullable Bundle savedInstanceState) {

        binding = initViewBinding(inflater, container);
        viewModel = new ViewModelProvider(this).get(getClassViewModel());
        initViews();
        return binding.getRoot();
    }

    @Override
    public final void onClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in));
        clickView(view);
    }

    protected void clickView(View view) {
        //do nothing
    }

    public final void setCallBack(IOnMainCallBack callBack) {
        this.callBack = callBack;
    }

    protected abstract void initViews();

    protected abstract Class<V> getClassViewModel();

    protected abstract B initViewBinding(@NonNull LayoutInflater inflater,
                                         @Nullable ViewGroup container);

    public void setData(Object data) {
        mData = data;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onPause() {
        MediaManager.getInstance().pauseBg();
        MediaManager.getInstance().pauseSong();
        super.onPause();
    }

    @Override
    public void onStart() {
        MediaManager.getInstance().continueBg();
        MediaManager.getInstance().continueSong();
        super.onStart();
    }

    @Override
    public void onDestroy() {
        MediaManager.getInstance().stopBg();
        MediaManager.getInstance().stopSong();
        super.onDestroy();
    }
}
