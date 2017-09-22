package com.webxzen.ridersapp.shared;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import com.webxzen.ridersapp.R;
import com.webxzen.ridersapp.api.MyProfileAPI;
import com.webxzen.ridersapp.api.RetrofitService;
import com.webxzen.ridersapp.base.BaseActivity;
import com.webxzen.ridersapp.model.ProfileModel;
import com.webxzen.ridersapp.model.ProfileResponseModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends BaseActivity {


    @BindView(R.id.img_photo)
    ImageView imgProfilePhoto;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_curr_rating)
    TextView tvCurrRating;
    @BindView(R.id.tv_phone_no)
    TextView tvPhoneNo;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_address)
    TextView tvAddress;


    private ProfileModel profileModel;
    private MyProfileAPI myProfileAPI;
    private Call<ProfileResponseModel> profileResponseModelCall;
    private String companyId;

    protected BaseActivity thisContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);
        ButterKnife.bind(this);

        thisContext = this;

        showBackButton(true);

        myProfileAPI = RetrofitService.createService(MyProfileAPI.class, getString(R.string.api_server_url), false);




        // Load profileModel Detain from API
        loadCompany(companyId);

    }

    private void loadCompany(final String companyId) {
        this.companyId = companyId;

        if (isNetworkAvailable()) {
            dialogUtil.showProgressDialog();

            profileResponseModelCall = myProfileAPI.getProfile(
                    getLoginModel().accessToken
            );

            profileResponseModelCall.enqueue(new Callback<ProfileResponseModel>() {
                @Override
                public void onResponse(Call<ProfileResponseModel> call, Response<ProfileResponseModel> response) {
                    if (response.isSuccessful()) {
                        dialogUtil.dismissProgress();
                        ProfileResponseModel profileResponseModel = response.body();

                        if (profileResponseModel.status == 1) {
                            renderCompanyDetail(profileResponseModel.result.profile);
                        } else {
                            if (response.body().result != null) {

                                dialogUtil.showDialogOk(response.body().message, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        ProfileActivity.this.finish();
                                    }
                                });
                            } else {
                                dialogUtil.showDialogOk("Server data are corrupted!", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        ProfileActivity.this.finish();
                                    }
                                });
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ProfileResponseModel> call, Throwable t) {

                    dialogUtil.dismissProgress();
                    dialogUtil.showDialogOk(t.getLocalizedMessage(), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ProfileActivity.this.finish();
                        }
                    });
                }
            });
        } else {
            dialogUtil.showDialogOk(getString(R.string.no_internet), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    ProfileActivity.this.finish();
                }
            });
        }

    }

    private void renderCompanyDetail(final ProfileModel profile) {

        setTitle("My ProfileModel");





        if (profile.photo != null) {
            Picasso.with(this)
                    .load(getString(R.string.api_assets) + profile.photo)
                    .centerInside()
                    .fit()
                    .into(imgProfilePhoto);
        }
        tvName.setText(profile.name);
        tvCurrRating.setText(profile.currRating);
        tvEmail.setText(profile.email);
        tvPhoneNo.setText(profile.phone);
        tvAddress.setText(profile.address);



    }

}
