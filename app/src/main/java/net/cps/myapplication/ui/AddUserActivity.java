package net.cps.myapplication.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;

import net.cps.myapplication.BApp;
import net.cps.myapplication.R;
import net.cps.myapplication.entity.greendao.DaoSession;
import net.cps.myapplication.entity.greendao.UserEntityDao;
import net.cps.myapplication.model.CityEntity;
import net.cps.myapplication.model.UserEntity;

import java.util.List;

public class AddUserActivity extends Activity {
    private DaoSession daoSession;
    private EditText etuname, etuid,etcourid,provinceid,cityid;
    private Button btsaveuser,tvresult;
    private UserEntityDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        initView();
        initDao();
        btsaveuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserEntity userEntity=new UserEntity();
                userEntity.setUid(Long.valueOf(etuid.getText().toString()));
                userEntity.setName(etuname.getText().toString());
                userEntity.setCourseId(Long.parseLong(etcourid.getText().toString()));
                userEntity.setAddressProvinceID(Long.parseLong(provinceid.getText().toString()));
                userEntity.setAddressCityId(Long.parseLong(cityid.getText().toString()));
                userEntity.setNick("nick_name");
                userDao.save(userEntity);
            }
        });

        findViewById(R.id.btfind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<UserEntity> joes = userDao.queryBuilder()
                        .list();
                tvresult.setText(JSON.toJSONString(joes,true));
            }
        });
    }

    private void initView() {
        etuname =(EditText)findViewById(R.id.etname);
        etuid =(EditText)findViewById(R.id.etuid);
        etcourid =(EditText)findViewById(R.id.etcourid);
        provinceid =(EditText)findViewById(R.id.provinceid);
        cityid =(EditText)findViewById(R.id.cityid);
        btsaveuser=findViewById(R.id.btsaveuser);
        tvresult=findViewById(R.id.tvresult);

    }


    private void initDao() {
        daoSession = ((BApp) getApplication()).getDaoSession();
        // 课程
        userDao=daoSession.getUserEntityDao();
    }

}
