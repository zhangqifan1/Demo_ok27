package com.as.demo_ok27.rili;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.as.demo_ok27.R;
import com.as.demo_ok27.provice.PcAdapter;
import com.as.demo_ok27.provice.ProvinceDialog;
import com.as.demo_ok27.rili.niandialog.NianDialog;
import com.as.demo_ok27.rili.niandialog.YueDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.othershe.calendarview.bean.DateBean;
import com.othershe.calendarview.listener.CalendarViewAdapter;
import com.othershe.calendarview.listener.OnPagerChangeListener;
import com.othershe.calendarview.listener.OnSingleChooseListener;
import com.othershe.calendarview.utils.CalendarUtil;
import com.othershe.calendarview.weiget.CalendarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RiliDialog extends android.support.v4.app.DialogFragment {


    private CalendarView calendarView;
    private TextView tv_choose_date;
    private TextView tv_choose_date_nongli;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        int[] cDate = CalendarUtil.getCurrentDate();
        View inflate = inflater.inflate(R.layout.rili_custom, null);
        tv_choose_date = inflate.findViewById(R.id.tv_choose_date);
        tv_choose_date_nongli = inflate.findViewById(R.id.tv_choose_date_nongli);
        final TextView title = (TextView) inflate.findViewById(R.id.title);
        final TextView tv_nian = (TextView) inflate.findViewById(R.id.tv_nian);
        final TextView tv_yue = (TextView) inflate.findViewById(R.id.tv_yue);

        final ImageView iv_last = (ImageView) inflate.findViewById(R.id.iv_last);
        final ImageView iv_next = (ImageView) inflate.findViewById(R.id.iv_next);


        initListener(iv_last,iv_next,tv_nian,tv_yue);

        calendarView = (CalendarView) inflate.findViewById(R.id.calendar);
//        HashMap<String, String> map = new HashMap<>();
//        map.put("2017.10.30", "qaz");
//        map.put("2017.10.1", "wsx");
//        map.put("2017.11.12", "yhn");
//        map.put("2017.9.15", "edc");
//        map.put("2017.11.6", "rfv");
//        map.put("2017.11.11", "tgb");
        calendarView
//                .setSpecifyMap(map)
                .setStartEndDate("1900.1", "2100.1")
//                .setDisableStartEndDate("2016.10.10", "2028.10.10")//设置禁用范围
                .setInitDate(cDate[0] + "." + cDate[1])
                .setSingleDate(cDate[0] + "." + cDate[1] + "." + cDate[2])
                .init();

        //这里可以切入 自定义 布局
//       .setOnCalendarViewAdapter(R.layout.item_layout, new CalendarViewAdapter() {
//            @Override
//            public TextView[] convertView(View view, DateBean date) {
//                TextView solarDay = (TextView) view.findViewById(R.id.solar_day);
//                TextView lunarDay = (TextView) view.findViewById(R.id.lunar_day);
//                return new TextView[]{solarDay, lunarDay};
//            }
//        }).init();

        title.setText(cDate[0] + "年" + cDate[1] + "月");
        tv_choose_date.setText("当前选中的日期：" + cDate[0] + "年" + cDate[1] + "月" + cDate[2] + "日");

        calendarView.setOnPagerChangeListener(new OnPagerChangeListener() {
            @Override
            public void onPagerChanged(int[] date) {
                title.setText(date[0] + "年" + date[1] + "月");
                tv_nian.setText(date[0] + "年" );
                tv_yue.setText(date[1] + "月");
            }
        });

        calendarView.setOnSingleChooseListener(new OnSingleChooseListener() {
            @Override
            public void onSingleChoose(View view, DateBean date) {
                title.setText(date.getSolar()[0] + "年" + date.getSolar()[1] + "月");
                tv_nian.setText(date.getSolar()[0] + "年" );
                tv_yue.setText(date.getSolar()[1] + "月");

                if (date.getType() == 1) {
                    tv_choose_date.setText("当前选中的日期：" + date.getSolar()[0] + "年" + date.getSolar()[1] + "月" + date.getSolar()[2] + "日");
                    tv_choose_date_nongli.setText("当前选中的日期：" + date.getSolar()[0] + "年" + date.getLunar()[0] + date.getLunar()[1]);
                }
            }
        });




        return inflate;

    }

    private void initListener(ImageView iv_last, ImageView iv_next, final TextView tvnian, final TextView tvyue) {
        iv_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.lastMonth();
            }
        });

        iv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.nextMonth();
            }
        });

        tvnian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final NianDialog nianDialog = new NianDialog();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                nianDialog.show(ft, "nian");

                nianDialog.setIdismisslistener(new NianDialog.Idismisslistener() {
                    @Override
                    public void listener() {
                        String yue = tvyue.getText().toString().split("月")[0];
                        int yueInt = Integer.parseInt(yue);
                        String nianStr = nianDialog.getNianStr();
                        int nian = Integer.parseInt(nianStr.split("年")[0]);
                        tvnian.setText(nianStr);
                        calendarView.toSpecifyDate(nian,yueInt,1);

                        DateBean singleDate = calendarView.getSingleDate();
                        tv_choose_date.setText("当前选中的日期：" + singleDate.getSolar()[0] + "年" + singleDate.getSolar()[1] + "月" + singleDate.getSolar()[2] + "日");
                        tv_choose_date_nongli.setText("当前选中的日期：" + singleDate.getSolar()[0] + "年" + singleDate.getLunar()[0] + singleDate.getLunar()[1]);
                    }
                });
            }
        });



        tvyue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final YueDialog yueDialog = new YueDialog();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                yueDialog.show(ft, "yue");

                yueDialog.setIdismisslistener(new YueDialog.Idismisslistener() {
                    @Override
                    public void listener() {

                        String nian = tvnian.getText().toString().split("年")[0];
                        int nianInt = Integer.parseInt(nian);
                        String nianStr = yueDialog.getYueStr();
                        int yue = Integer.parseInt(nianStr.split("月")[0]);
                        tvyue.setText(nianStr);
                        calendarView.toSpecifyDate(nianInt,yue,1);

                        DateBean singleDate = calendarView.getSingleDate();
                        tv_choose_date.setText("当前选中的日期：" + singleDate.getSolar()[0] + "年" + singleDate.getSolar()[1] + "月" + singleDate.getSolar()[2] + "日");
                        tv_choose_date_nongli.setText("当前选中的日期：" + singleDate.getSolar()[0] + "年" + singleDate.getLunar()[0] + singleDate.getLunar()[1]);

                    }
                });
            }
        });
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
