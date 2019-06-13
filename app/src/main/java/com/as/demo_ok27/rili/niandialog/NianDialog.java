package com.as.demo_ok27.rili.niandialog;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.as.demo_ok27.R;
import com.as.demo_ok27.customwheel.WheelView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NianDialog extends android.support.v4.app.DialogFragment {

    private String nianStr="1900年";

    public String getNianStr() {
        return nianStr;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.dialog_nian, null);
        // 1900 - 2100 年
        List<String> list=new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            list.add(1900+i+"年");
        }

        WheelView wheelView = inflate.findViewById(R.id.nian_rv);
        TextView tv_sure = inflate.findViewById(R.id.tv_sure);
        wheelView.setOffset(2);
        wheelView.setItems(list);
        wheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                nianStr=item;
            }
        });


//        pcAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                setSelectitem(position);
//                dismiss();
//            }
//        });

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return inflate;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);

        getDialog().setCanceledOnTouchOutside(false);
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
        getDialog().getWindow().setLayout(700, ViewGroup.LayoutParams.WRAP_CONTENT);
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
