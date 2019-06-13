package com.as.demo_ok27.provice;

import android.support.annotation.Nullable;

import com.as.demo_ok27.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * -----------------------------
 * Created by zqf on 2019/6/11.
 * ---------------------------
 */
public class PcAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public PcAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.item_pc_text, item);
    }
}
