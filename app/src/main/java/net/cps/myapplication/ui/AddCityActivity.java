package net.cps.myapplication.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;

import net.cps.myapplication.BApp;
import net.cps.myapplication.R;
import net.cps.myapplication.entity.greendao.CityEntityDao;
import net.cps.myapplication.entity.greendao.DaoSession;
import net.cps.myapplication.entity.greendao.ProvinceEntityDao;
import net.cps.myapplication.model.CityEntity;
import net.cps.myapplication.model.ProvinceEntity;

import java.util.List;

public class AddCityActivity extends Activity {
    private DaoSession daoSession;
    private ProvinceEntityDao provinceDao;
    private CityEntityDao cityDao;
    private EditText etprovince,etprovincepid;
    private Button btsaveProvince,tvresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        initView();
        initDao();
        btsaveProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityEntity provinceEntity=new CityEntity();
                provinceEntity.setCid(Long.valueOf(etprovincepid.getText().toString()));
                provinceEntity.setCname(etprovince.getText().toString());
                cityDao.save(provinceEntity);
            }
        });

        findViewById(R.id.btfind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<CityEntity> joes = cityDao.queryBuilder()
                        .list();
                tvresult.setText(JSON.toJSONString(joes,true));
            }
        });
    }

    private void initView() {
        etprovince=(EditText)findViewById(R.id.etprovince);
        btsaveProvince=findViewById(R.id.btsaveProvince);
        tvresult=findViewById(R.id.tvresult);
        etprovincepid=(EditText)findViewById(R.id.etprovincepid);
    }


    private void initDao() {
        daoSession = ((BApp) getApplication()).getDaoSession();
        // 课程
        provinceDao=daoSession.getProvinceEntityDao();
        cityDao=daoSession.getCityEntityDao();
    }

}
