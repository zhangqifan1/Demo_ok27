package com.as.demo_ok27.provice;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.as.demo_ok27.R;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

public class ProvinceDialog extends android.support.v4.app.DialogFragment {

    private int selectitem = 0;

    public void setSelectitem(int selectitem) {
        this.selectitem = selectitem;
    }

    public int getSelectitem() {
        return selectitem;
    }


    private List<String> provinces;

    public void setProvinces(List<String> provinces) {
        this.provinces = provinces;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.province_or_city, null);
        RecyclerView recyclerView = inflate.findViewById(R.id.pc_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        PcAdapter pcAdapter = new PcAdapter(R.layout.recycler_item_pc, provinces);
        recyclerView.setAdapter(pcAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        pcAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                setSelectitem(position);
                dismiss();
            }
        });

        return inflate;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);

        getDialog().setCanceledOnTouchOutside(true);
        //① 设置对话框内的内容为透明
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        //② . 将对话框外的内容设置为透明
//        Window window = getDialog().getWindow();
//        WindowManager.LayoutParams windowParams = window != null ? window.getAttributes() : null;
//        windowParams.dimAmount = 0.0f;
//        window.setAttributes(windowParams);
    }

    @Override
    public void onResume() {
        super.onResume();
        //动态设置宽高
        getDialog().getWindow().setLayout(1000, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (idismisslistener != null) {
            idismisslistener.listener();
        }
    }

    public interface Idismisslistener {
        void listener();
    }

    public Idismisslistener idismisslistener;

    public void setIdismisslistener(Idismisslistener idismisslistener) {
        this.idismisslistener = idismisslistener;
    }
}
