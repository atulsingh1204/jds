package com.bpointer.rkofficial.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bpointer.rkofficial.Api.Api;
import com.bpointer.rkofficial.Api.Authentication;
import com.bpointer.rkofficial.Common.CustomDialog;
import com.bpointer.rkofficial.Common.PreferenceManager;
import com.bpointer.rkofficial.Model.RequestBody;
import com.bpointer.rkofficial.Model.Response.AddressDetailsResponseModel.AddressDetailsResponseModel;
import com.bpointer.rkofficial.Model.Response.BankDetailsResponseModel.BankDetailsResponseModel;
import com.bpointer.rkofficial.Model.Response.GPayResponseModel.GPayResponseModel;
import com.bpointer.rkofficial.Model.Response.GetBankDetailsResponseModel.Data;
import com.bpointer.rkofficial.Model.Response.GetBankDetailsResponseModel.GetBankDetailsResponseModel;
import com.bpointer.rkofficial.Model.Response.PaytmResponseModel.PaytmResponseModel;
import com.bpointer.rkofficial.Model.Response.PhonePeResponseModel.PhonePeResponseModel;
import com.bpointer.rkofficial.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bpointer.rkofficial.Common.AppConstant.ID;
import static com.bpointer.rkofficial.Common.AppConstant.MOBILE;
import static com.bpointer.rkofficial.Common.AppConstant.NAME;

public class ProfileDetailsFragment extends Fragment implements View.OnClickListener {
    View view;
    ImageView iv_back;
    TextView tv_title, tv_user_id, tv_mobile;
    PreferenceManager preferenceManager;
    CardView cv_address, cv_bank, cv_paytm, cv_gpay, cv_phonepe;
    int userId;
    AlertDialog alertDialog;
    CustomDialog customDialog;
    Data allBankDetails;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_details, container, false);
    
        initView();

        getBankDetailsAPI();
        return view;
    }

    private void getBankDetailsAPI() {
        customDialog.showLoader();

        RequestBody requestBody = new RequestBody();
        requestBody.setUser_id(String.valueOf(userId));

        Call<GetBankDetailsResponseModel> call = Api.getClient().create(Authentication.class).getBankDetails(requestBody);
        call.enqueue(new Callback<GetBankDetailsResponseModel>() {
            @Override
            public void onResponse(Call<GetBankDetailsResponseModel> call, Response<GetBankDetailsResponseModel> response) {
                customDialog.closeLoader();
                if (response.body() != null) {
                    if (response.body().getStatus().equals("true")) {
                        allBankDetails = response.body().getData();
                    } else {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetBankDetailsResponseModel> call, Throwable t) {
                customDialog.closeLoader();
                Log.e("LoginResponse", "onFailure: " + t.getMessage());
            }
        });
    }

    private void initView() {
        iv_back = view.findViewById(R.id.iv_back);
        tv_title = view.findViewById(R.id.tv_title);
        tv_user_id = view.findViewById(R.id.tv_user_id);
        tv_mobile = view.findViewById(R.id.tv_mobile);
        cv_address = view.findViewById(R.id.cv_address);
        cv_bank = view.findViewById(R.id.cv_bank);
        cv_paytm = view.findViewById(R.id.cv_paytm);
        cv_gpay = view.findViewById(R.id.cv_gpay);
        cv_phonepe = view.findViewById(R.id.cv_phonepe);

        preferenceManager = new PreferenceManager(getContext());
        userId = preferenceManager.getIntPreference(ID);
        customDialog = new CustomDialog(getContext());

        tv_title.setText("Profile Details");
        tv_user_id.setText("Name : " + preferenceManager.getStringPreference(NAME));
        tv_mobile.setText("Mobile No : " + preferenceManager.getStringPreference(MOBILE));

        iv_back.setOnClickListener(this);
        cv_address.setOnClickListener(this);
        cv_bank.setOnClickListener(this);
        cv_paytm.setOnClickListener(this);
        cv_gpay.setOnClickListener(this);
        cv_phonepe.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;

            case R.id.cv_address:
                showAddressDialog();
                break;

            case R.id.cv_bank:
                showBankDialog();
                break;

            case R.id.cv_paytm:
                showPaytmDialog();
                break;

            case R.id.cv_gpay:
                showGPayDialog();
                break;

            case R.id.cv_phonepe:
                showPhonePeDialog();
                break;
        }
    }

    private void showPhonePeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ViewGroup viewGroup = view.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_phonepe, viewGroup, false);
        builder.setView(dialogView);
        alertDialog = builder.create();

        Button bt_submit = dialogView.findViewById(R.id.bt_submit);
        EditText et_number = dialogView.findViewById(R.id.et_number);

        if (allBankDetails != null) {
            if (allBankDetails.getPhonepay() != null) {
                et_number.setText("" + allBankDetails.getPhonepay());
            }
        }

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_number.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Please fill details", Toast.LENGTH_SHORT).show();
                } else {
                    addPhonePeAPI(et_number.getText().toString());
                }
            }
        });
        alertDialog.show();
    }

    private void addPhonePeAPI(String number) {
        customDialog.showLoader();

        RequestBody requestBody = new RequestBody();
        requestBody.setUser_id(String.valueOf(userId));
        requestBody.setPhonepay(number);

        Call<PhonePeResponseModel> call = Api.getClient().create(Authentication.class).updatePhonePeDetails(requestBody);
        call.enqueue(new Callback<PhonePeResponseModel>() {
            @Override
            public void onResponse(Call<PhonePeResponseModel> call, Response<PhonePeResponseModel> response) {
                customDialog.closeLoader();
                if (response.body() != null) {
                    if (response.body().getStatus().equals("true")) {
                        alertDialog.dismiss();
                        customDialog.showSuccessDialog(response.body().getMessage());
                    } else {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PhonePeResponseModel> call, Throwable t) {
                customDialog.closeLoader();
                Log.e("LoginResponse", "onFailure: " + t.getMessage());
            }
        });
    }

    private void showGPayDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ViewGroup viewGroup = view.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_gpay, viewGroup, false);
        builder.setView(dialogView);
        alertDialog = builder.create();

        Button bt_submit = dialogView.findViewById(R.id.bt_submit);
        EditText et_number = dialogView.findViewById(R.id.et_number);

        if (allBankDetails != null) {
            if (allBankDetails.getGpay() != null) {
                et_number.setText("" + allBankDetails.getGpay());
            }
        }

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_number.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Please fill details", Toast.LENGTH_SHORT).show();
                } else {
                    addGPayAPI(et_number.getText().toString());
                }
            }
        });
        alertDialog.show();
    }

    private void addGPayAPI(String number) {
        customDialog.showLoader();

        RequestBody requestBody = new RequestBody();
        requestBody.setUser_id(String.valueOf(userId));
        requestBody.setGpay(number);

        Call<GPayResponseModel> call = Api.getClient().create(Authentication.class).updateGPayDetails(requestBody);
        call.enqueue(new Callback<GPayResponseModel>() {
            @Override
            public void onResponse(Call<GPayResponseModel> call, Response<GPayResponseModel> response) {
                customDialog.closeLoader();
                if (response.body() != null) {
                    if (response.body().getStatus().equals("true")) {
                        alertDialog.dismiss();
                        customDialog.showSuccessDialog(response.body().getMessage());
                    } else {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GPayResponseModel> call, Throwable t) {
                customDialog.closeLoader();
                Log.e("LoginResponse", "onFailure: " + t.getMessage());
            }
        });
    }

    private void showPaytmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ViewGroup viewGroup = view.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_paytm, viewGroup, false);
        builder.setView(dialogView);
        alertDialog = builder.create();

        Button bt_submit = dialogView.findViewById(R.id.bt_submit);
        EditText et_number = dialogView.findViewById(R.id.et_number);

        if (allBankDetails != null) {
            if (allBankDetails.getPaytm() != null) {
                et_number.setText(allBankDetails.getPaytm());
            }
        }

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_number.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Please fill details", Toast.LENGTH_SHORT).show();
                } else {
                    addPaytmAPI(et_number.getText().toString());
                }
            }
        });
        alertDialog.show();
    }

    private void addPaytmAPI(String number) {
        customDialog.showLoader();

        RequestBody requestBody = new RequestBody();
        requestBody.setUser_id(String.valueOf(userId));
        requestBody.setPaytm(number);

        Call<PaytmResponseModel> call = Api.getClient().create(Authentication.class).updatePayTmDetails(requestBody);
        call.enqueue(new Callback<PaytmResponseModel>() {
            @Override
            public void onResponse(Call<PaytmResponseModel> call, Response<PaytmResponseModel> response) {
                customDialog.closeLoader();
                if (response.body() != null) {
                    if (response.body().getStatus().equals("true")) {
                        alertDialog.dismiss();
                        customDialog.showSuccessDialog(response.body().getMessage());
                    } else {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PaytmResponseModel> call, Throwable t) {
                customDialog.closeLoader();
                Log.e("LoginResponse", "onFailure: " + t.getMessage());
            }
        });
    }

    private void showBankDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ViewGroup viewGroup = view.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_bank_details, viewGroup, false);
        builder.setView(dialogView);
        alertDialog = builder.create();

        Button bt_submit = dialogView.findViewById(R.id.bt_submit);
        EditText et_bank = dialogView.findViewById(R.id.et_bank);
        EditText et_ifsc = dialogView.findViewById(R.id.et_ifsc);
        EditText et_account_no = dialogView.findViewById(R.id.et_account_no);
        EditText et_holder = dialogView.findViewById(R.id.et_holder);

        if (allBankDetails != null) {
            if (allBankDetails.getBankName() != null) {
                et_bank.setText(allBankDetails.getBankName());
            }
            if (allBankDetails.getIfsc() != null) {
                et_ifsc.setText(allBankDetails.getIfsc());
            }
            if (allBankDetails.getAccountNumber() != null) {
                et_account_no.setText(allBankDetails.getAccountNumber());
            }
            if (allBankDetails.getHolderName() != null) {
                et_holder.setText(allBankDetails.getHolderName());
            }
        }

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_bank.getText().toString().trim().isEmpty() || et_ifsc.getText().toString().trim().isEmpty() || et_account_no.getText().toString().trim().isEmpty() || et_holder.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Please fill all details", Toast.LENGTH_SHORT).show();
                } else {
                    addBankAPI(et_bank.getText().toString(), et_ifsc.getText().toString(), et_account_no.getText().toString(), et_holder.getText().toString());
                }
            }
        });
        alertDialog.show();
    }

    private void addBankAPI(String bank, String ifsc, String account, String holder) {
        customDialog.showLoader();

        RequestBody requestBody = new RequestBody();
        requestBody.setUser_id(String.valueOf(userId));
        requestBody.setBank_name(bank);
        requestBody.setIfsc(ifsc);
        requestBody.setAccount_number(account);
        requestBody.setHolder_name(holder);

        Call<BankDetailsResponseModel> call = Api.getClient().create(Authentication.class).updateBankDetails(requestBody);
        call.enqueue(new Callback<BankDetailsResponseModel>() {
            @Override
            public void onResponse(Call<BankDetailsResponseModel> call, Response<BankDetailsResponseModel> response) {
                customDialog.closeLoader();
                if (response.body() != null) {
                    if (response.body().getStatus().equals("true")) {
                        alertDialog.dismiss();
                        customDialog.showSuccessDialog(response.body().getMessage());
                    } else {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BankDetailsResponseModel> call, Throwable t) {
                customDialog.closeLoader();
                Log.e("LoginResponse", "onFailure: " + t.getMessage());
            }
        });
    }

    private void showAddressDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ViewGroup viewGroup = view.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_address, viewGroup, false);
        builder.setView(dialogView);
        alertDialog = builder.create();

        Button bt_submit = dialogView.findViewById(R.id.bt_submit);
        EditText et_address = dialogView.findViewById(R.id.et_address);
        EditText et_city = dialogView.findViewById(R.id.et_city);
        EditText et_pincode = dialogView.findViewById(R.id.et_pincode);

        if (allBankDetails != null) {
            if (allBankDetails.getUser().getAddress() != null) {
                et_address.setText(allBankDetails.getUser().getAddress());
            }
            if (allBankDetails.getUser().getCity() != null) {
                et_city.setText(allBankDetails.getUser().getCity());
            }
            if (allBankDetails.getUser().getPincode() != null) {
                et_pincode.setText("" + allBankDetails.getUser().getPincode());
            }
        }

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_address.getText().toString().trim().isEmpty() || et_city.getText().toString().trim().isEmpty() || et_pincode.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Please fill all details", Toast.LENGTH_SHORT).show();
                } else {
                    addAddressAPI(et_address.getText().toString(), et_city.getText().toString(), et_pincode.getText().toString());
                }
            }
        });
        alertDialog.show();
    }

    private void addAddressAPI(String address, String city, String pincode) {
        customDialog.showLoader();

        RequestBody requestBody = new RequestBody();
        requestBody.setUser_id(String.valueOf(userId));
        requestBody.setAddress(address);
        requestBody.setCity(city);
        requestBody.setPincode(pincode);

        Call<AddressDetailsResponseModel> call = Api.getClient().create(Authentication.class).updateAddressDetails(requestBody);
        call.enqueue(new Callback<AddressDetailsResponseModel>() {
            @Override
            public void onResponse(Call<AddressDetailsResponseModel> call, Response<AddressDetailsResponseModel> response) {
                customDialog.closeLoader();
                if (response.body() != null) {
                    if (response.body().getStatus().equals("true")) {
                        alertDialog.dismiss();
                        customDialog.showSuccessDialog(response.body().getMessage());
                    } else {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddressDetailsResponseModel> call, Throwable t) {
                customDialog.closeLoader();
                Log.e("LoginResponse", "onFailure: " + t.getMessage());
            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
