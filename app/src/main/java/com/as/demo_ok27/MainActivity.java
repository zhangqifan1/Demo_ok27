package com.as.demo_ok27;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.as.demo_ok27.city.ProvinceCityDialog;
import com.as.demo_ok27.provice.ProvinceDialog;
import com.as.demo_ok27.rili.RiliDialog;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int provinceselect = 0;
    int provincecityselect = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tvprovince = findViewById(R.id.tvprovince);
        final TextView tvcity = findViewById(R.id.tvcity);
        final TextView tvmain2 = findViewById(R.id.tvmain2);

        String s = readTextFromSDcard();
        PcBean pcBean = new Gson().fromJson(s, PcBean.class);
        List<PcBean.ProvincesBean> provincesdata = pcBean.getProvinces();

        //让他们第一条都是忽略
        final List<String> provincesList = new ArrayList<>();
        provincesList.add("忽略");


        final List<List<PcBean.ProvincesBean.CitiesBean>> citiesList = new ArrayList<>();
        final PcBean.ProvincesBean.CitiesBean citiesBean = new PcBean.ProvincesBean.CitiesBean();
        citiesBean.setName("忽略");
        citiesList.add(new ArrayList<PcBean.ProvincesBean.CitiesBean>() {{
            add(citiesBean);
        }});
        for (int i = 0; i < provincesdata.size(); i++) {
            PcBean.ProvincesBean provincesBean = provincesdata.get(i);

            String name = provincesBean.getName();
            provincesList.add(name);
            citiesList.add(provincesBean.getCities());
        }


        tvprovince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProvinceDialog provinceDialog = new ProvinceDialog();
                provinceDialog.setProvinces(provincesList);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                provinceDialog.show(ft, "ft");

                provinceDialog.setIdismisslistener(new ProvinceDialog.Idismisslistener() {
                    @Override
                    public void listener() {

                        int selectitem = provinceDialog.getSelectitem();
                        if(selectitem != 0){
                            tvprovince.setText(provincesList.get(selectitem));
                            if(selectitem != provinceselect){
                                tvcity.setText("忽略");
                            }
                            provinceselect = selectitem;
                        }
                    }
                });
            }
        });

        tvcity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tvprovincestr = tvprovince.getText().toString();
                if (TextUtils.equals("省份", tvprovincestr) || TextUtils.equals("忽略", tvprovincestr)) {
                    Toast.makeText(MainActivity.this, "请先选择省份", Toast.LENGTH_SHORT).show();
                    return;
                }
                final List<PcBean.ProvincesBean.CitiesBean> citiesBeans = citiesList.get(provinceselect);
                final ProvinceCityDialog provinceCityDialog = new ProvinceCityDialog();
                provinceCityDialog.setPcities(citiesBeans);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                provinceCityDialog.show(ft, "ft");

                provinceCityDialog.setIdismisslistener(new ProvinceDialog.Idismisslistener() {
                    @Override
                    public void listener() {
                        int selectitem = provinceCityDialog.getProcinceCityselect();
                        tvcity.setText(citiesBeans.get(selectitem).getName());
                        provincecityselect = selectitem;
                    }
                });
            }
        });

        //去第二个看日历
        tvmain2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RiliDialog riliDialog = new RiliDialog();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                riliDialog.show(ft,"rili");
            }
        });


    }


    //将传入的is一行一行解析读取出来出来
    private String readTextFromSDcard() {
        InputStreamReader inputStreamReader;
        try {
            inputStreamReader = new InputStreamReader(getAssets().open("province_city.json"), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            inputStreamReader.close();
            bufferedReader.close();

            return stringBuilder.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
