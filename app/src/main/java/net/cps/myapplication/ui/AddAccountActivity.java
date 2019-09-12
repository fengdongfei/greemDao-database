package net.cps.myapplication.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;

import net.cps.myapplication.BApp;
import net.cps.myapplication.R;
import net.cps.myapplication.entity.greendao.AccountEntityDao;
import net.cps.myapplication.entity.greendao.DaoSession;
import net.cps.myapplication.entity.greendao.UserEntityDao;
import net.cps.myapplication.model.AccountEntity;
import net.cps.myapplication.model.UserEntity;

import java.util.List;

public class AddAccountActivity extends Activity {
    private DaoSession daoSession;
    private EditText etcontent, etaid,etlassfy,etcmoney;
    private Button btsaveuser,tvresult;
    private AccountEntityDao accountDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        initView();
        initDao();
        btsaveuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountEntity accountEntity=new AccountEntity();
                accountEntity.setAccount_id(Long.valueOf(etaid.getText().toString()));
                accountEntity.setAccount_name(etcontent.getText().toString());
                accountEntity.setCreateTime(System.currentTimeMillis()/1000);
                accountEntity.setMoney(Long.valueOf(etcmoney.getText().toString()));
                accountEntity.setClassfy(etlassfy.getText().toString());
                accountDao.insert(accountEntity);
            }
        });

        findViewById(R.id.btfind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AccountEntity> joes = accountDao.queryBuilder()
                        .list();
                tvresult.setText(JSON.toJSONString(joes,true));
            }
        });
    }

    private void initView() {
        etcontent =(EditText)findViewById(R.id.etcontent);
        etaid =(EditText)findViewById(R.id.etaid);
        etlassfy =(EditText)findViewById(R.id.etlassfy);
        etcmoney =(EditText)findViewById(R.id.etcmoney);
        btsaveuser=findViewById(R.id.btsaveuser);
        tvresult=findViewById(R.id.tvresult);

    }


    private void initDao() {
        daoSession = ((BApp) getApplication()).getDaoSession();
        // 课程
        accountDao=daoSession.getAccountEntityDao();
    }

}
