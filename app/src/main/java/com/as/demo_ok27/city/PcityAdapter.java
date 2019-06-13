package com.as.demo_ok27.city;

import android.support.annotation.Nullable;

import com.as.demo_ok27.PcBean;
import com.as.demo_ok27.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * -----------------------------
 * Created by zqf on 2019/6/11.
 * ---------------------------
 */
public class PcityAdapter extends BaseQuickAdapter<PcBean.ProvincesBean.CitiesBean, BaseViewHolder> {

    public PcityAdapter(int layoutResId, @Nullable List<PcBean.ProvincesBean.CitiesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PcBean.ProvincesBean.CitiesBean item) {
        helper.setText(R.id.item_pc_text, item.getName());
    }
}
