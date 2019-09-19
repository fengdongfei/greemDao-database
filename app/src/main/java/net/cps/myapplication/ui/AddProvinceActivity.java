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
import net.cps.myapplication.model.ProvinceEntity;
import java.util.List;

public class AddProvinceActivity extends Activity {
    private DaoSession daoSession;
    private ProvinceEntityDao provinceDao;
    private CityEntityDao cityDao;
    private EditText etprovince,etprovincepid;
    private Button btsaveProvince,tvresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_province);
        initView();
        initDao();
        btsaveProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProvinceEntity provinceEntity=new ProvinceEntity();
                provinceEntity.setPid(Long.valueOf(etprovincepid.getText().toString()));
                provinceEntity.setPname(etprovince.getText().toString());
                provinceDao.save(provinceEntity);
                // 清除缓存,保证数据不因为缓存无法查询
                daoSession.clear();

            }
        });

        findViewById(R.id.btfind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ProvinceEntity> joes = provinceDao.queryBuilder()
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
