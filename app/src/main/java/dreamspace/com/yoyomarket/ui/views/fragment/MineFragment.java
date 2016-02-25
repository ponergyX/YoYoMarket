package dreamspace.com.yoyomarket.ui.views.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.common.base.BaseLazyFragment;
import dreamspace.com.yoyomarket.common.untils.CommonUntil;
import dreamspace.com.yoyomarket.common.untils.ImageCaptureManager;
import dreamspace.com.yoyomarket.common.untils.ToastUntil;
import dreamspace.com.yoyomarket.common.untils.UploadImageUntil;
import dreamspace.com.yoyomarket.ui.presenter.fragment.MineFragmentPresenter;
import dreamspace.com.yoyomarket.ui.view.fragment.MineView;
import dreamspace.com.yoyomarket.ui.views.activity.ModifyPwdActivity;

/**
 * Created by Lx on 2016/1/28.
 */
public class MineFragment extends BaseLazyFragment implements MineView{

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.avatar_civ)
    CircleImageView avatarCiv;

    @Bind(R.id.phone_tv)
    TextView phoneTv;

    @Bind(R.id.change_pw_tv)
    TextView changePwdTv;

    @Bind(R.id.get_good_address_rl)
    RelativeLayout addressRl;

    @Bind(R.id.feedback_rl)
    RelativeLayout feedbackRl;

    @Bind(R.id.service_rl)
    RelativeLayout serviceRl;

    @Bind(R.id.problem_rl)
    RelativeLayout problemRl;

    @Bind(R.id.about_us_rl)
    RelativeLayout aboutRl;

    @Bind(R.id.logout)
    TextView logoutTv;

    @Inject
    MineFragmentPresenter mineFragmentPresenter;

    private ImageCaptureManager captureManager;
    private SweetAlertDialog sweetAlertDialog;
    private AlertDialog selectDialog;
    private static final int SELECT_PHOTO_REQUEST_CODE = 2;
    private static final int TAKE_PHOTO_REQUEST_CODE = 3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjector();
        initPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mineFragmentPresenter.onDestory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(captureManager != null){
            captureManager.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(captureManager != null){
            captureManager.onRestoreInstanceState(savedInstanceState);
        }
    }

    private void initInjector(){
        getApiComponent().inject(this);
    }

    private void initPresenter(){
        mineFragmentPresenter.attchView(this);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViewsAndEvents() {
        toolbar.setBackgroundColor(Color.parseColor("#F99C35"));
        avatarCiv.setImageDrawable(new ColorDrawable(Color.parseColor("#F99C35")));
    }

    @OnClick(R.id.logout)
    void logout(){

    }

    @OnClick(R.id.avatar_civ)
    void changeAvatar(){
        if(selectDialog == null){
            createSelectImageDialog();
        }

        selectDialog.show();
    }

    @OnClick(R.id.change_pw_tv)
    void changPwd(){
        navigator.navigateToModifyPwdActivity(getActivity(), ModifyPwdActivity.MODIFY_PASSWORD);
    }

    @OnClick(R.id.get_good_address_rl)
    void checkAddressInfo(){
        navigator.navigateToAddressActivity(getActivity());
    }

    @OnClick(R.id.feedback_rl)
    void feedback(){
        navigator.navigateToFeedbackActivity(getActivity());
    }

    @OnClick(R.id.service_rl)
    void servicePhone(){

    }

    @OnClick(R.id.problem_rl)
    void checkProblem(){

    }

    @OnClick(R.id.about_us_rl)
    void checkAboutUs(){

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_mine;
    }

    private void createSelectImageDialog(){
        captureManager = new ImageCaptureManager(getActivity());
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_select_image, null);
        selectDialog = new AlertDialog.Builder(getActivity())
                .setView(dialogView).create();
        LinearLayout selectImageLl = (LinearLayout) dialogView.findViewById(R.id.select_image_ll);
        LinearLayout takePhotoLl = (LinearLayout) dialogView.findViewById(R.id.take_photo_ll);
        selectImageLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.setType("image/*");
                selectDialog.dismiss();
                startActivityForResult(photoPickerIntent, SELECT_PHOTO_REQUEST_CODE);
            }
        });

        takePhotoLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = null;
                selectDialog.dismiss();
                try {
                    cameraIntent = captureManager.dispatchTakePictureIntent();
                    if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivityForResult(cameraIntent, TAKE_PHOTO_REQUEST_CODE);
                    } else {
                        ToastUntil.showToast("设备没有相机",getActivity());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == SELECT_PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Uri uri = data.getData();
            mineFragmentPresenter.uploadImage(CommonUntil.getRealPathFromURI(getActivity(), uri));
        }else if(requestCode == TAKE_PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            if(captureManager.getCurrentPhotoPath() != null){
                mineFragmentPresenter.uploadImage(captureManager.getCurrentPhotoPath());
            }
        }
    }

    @Override
    public void showNetCantUse() {
        ToastUntil.showNetCantUse(getActivity());
    }

    @Override
    public void showToast(String s) {
        ToastUntil.showToast(s,getActivity());
    }

    @Override
    public void showNetError() {
        ToastUntil.showNetError(getActivity());
    }

    @Override
    public void showUploadProcess() {
        sweetAlertDialog = new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("")
                .setContentText(getString(R.string.in_upload_image));
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#F99C35"));
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.show();
    }

    @Override
    public void showUploadError() {
        sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
        sweetAlertDialog.setTitleText("").setContentText(getString(R.string.upload_image_fail));
    }

    @Override
    public void showUploadSuccess(String path) {
        sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
        sweetAlertDialog.setTitleText("").setContentText(getString(R.string.upload_image_success));
        CommonUntil.showImageInCiv(getActivity(),path,avatarCiv);
    }
}
