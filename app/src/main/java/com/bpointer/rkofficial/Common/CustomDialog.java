package com.bpointer.rkofficial.Common;

import android.app.Dialog;
import android.content.Context;

import androidx.core.content.ContextCompat;

import com.bpointer.rkofficial.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CustomDialog {
    Dialog dialog;
    Context context;

    public CustomDialog(Context context) {
        this.context = context;
    }

    public void showSuccessDialog(String msg) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setTitleText(msg)
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    public void showFailureDialog(String msg) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setTitleText(msg)
                .setConfirmText("OK")
                .setConfirmButtonBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    public void showLoader() {
        Dialog dialog2 = new Dialog(context, R.style.alert_dialog_light);
        this.dialog = dialog2;
        dialog2.setContentView(R.layout.loading_layout);
        this.dialog.setCancelable(false);
        this.dialog.setCanceledOnTouchOutside(false);
        this.dialog.show();
    }

    public void closeLoader() {
        this.dialog.dismiss();
    }
}
