package dreamspace.com.yoyomarket.ui.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.common.base.BaseActivity;
import dreamspace.com.yoyomarket.ui.presenter.activity.ModifyAddressActivityPresenter;
import dreamspace.com.yoyomarket.ui.view.activity.ModifyAddressView;

/**
 * Created by Lx on 2016/2/3.
 */
public class ModifyAddressActivity extends BaseActivity implements ModifyAddressView{

    public static final String ADDRESS_MODE = "dreamspace.com.yoyomarket.ui.views.activity.ModifyAddressActivity.ADDRESS_MODE";
    public static final int MODIFY_ADDRESS = 1;
    public static final int ADD_ADDRESS = 2;

    @Bind(R.id.location_et)
    EditText locationEt;

    @Bind(R.id.phone_et)
    EditText phoneEt;

    @Bind(R.id.boy_iv)
    ImageView boyIv;

    @Bind(R.id.gril_iv)
    ImageView grilIv;

    @Bind(R.id.confirm_btn)
    Button confirmBtn;

    @Inject
    ModifyAddressActivityPresenter modifyAddressActivityPresenter;

    private boolean sex = true;//true = 男生，false = 女生

    public static Intent getCallingIntent(Context context,int mode){
        Intent intent =  new Intent(context,ModifyAddressActivity.class);
        intent.putExtra(ADDRESS_MODE,mode);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjector();
        initPresenter();
    }

    private void initInjector(){
        getApiComponent().inject(this);
    }

    private void initPresenter(){
        modifyAddressActivityPresenter.attchView(this);
    }

    @Override
    protected void initViewsAndEvents() {
        int mode = getIntent().getIntExtra(ADDRESS_MODE,MODIFY_ADDRESS);
        if(mode == MODIFY_ADDRESS){
            setTitle(getString(R.string.modify_address));
        }else{
            setTitle(getString(R.string.add_address));
        }
    }

    @OnClick(R.id.boy_iv)
    void boyClick(){
        if(!sex){
            sex = true;
            boyIv.setImageResource(R.drawable.ic_sex_checkbox_s);
            grilIv.setImageResource(R.drawable.ic_sex_checkbox);
        }
    }

    @OnClick(R.id.gril_iv)
    void grilClick(){
        if(sex){
            sex = false;
            boyIv.setImageResource(R.drawable.ic_sex_checkbox);
            grilIv.setImageResource(R.drawable.ic_sex_checkbox_s);
        }
    }

    @OnClick(R.id.confirm_btn)
    void confirm(){

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_modify_address;
    }
}
