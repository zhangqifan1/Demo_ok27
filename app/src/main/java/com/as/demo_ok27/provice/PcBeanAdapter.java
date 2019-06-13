package com.as.demo_ok27.provice;

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
public class PcBeanAdapter extends BaseQuickAdapter<PcBean.ProvincesBean, BaseViewHolder> {


    public PcBeanAdapter(int layoutResId, @Nullable List<PcBean.ProvincesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PcBean.ProvincesBean item) {
        helper.setText(R.id.item_pc_text, item.getName());
    }
}
