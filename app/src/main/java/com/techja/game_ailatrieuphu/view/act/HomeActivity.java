package com.techja.game_ailatrieuphu.view.act;


import com.techja.game_ailatrieuphu.databinding.ActivityHomeBinding;
import com.techja.game_ailatrieuphu.view.fragment.M000MenuFrg;
import com.techja.game_ailatrieuphu.viewmodel.CommonVM;

public class HomeActivity extends BaseAct<ActivityHomeBinding, CommonVM> {

    @Override
    protected void initViews() {
        showFragment(M000MenuFrg.TAG, null, false);
    }

    @Override
    protected ActivityHomeBinding initViewBinding() {
        return ActivityHomeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<CommonVM> initViewModel() {
        return CommonVM.class;
    }
}