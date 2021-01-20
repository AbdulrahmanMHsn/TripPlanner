package amo.tripplanner.Helper;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.victor.loading.rotate.RotateLoading;

import amo.tripplanner.R;

public class Dialogs {



    public static Dialog createProgressBarDialog(Context mContext, String text) {
        Dialog mDialog;
        mDialog = new Dialog(mContext, R.style.Theme_Dialog);
        mDialog.setContentView(R.layout.dialog_progress_bar);

        //find Views
        RotateLoading rotateLoading =(RotateLoading)mDialog.findViewById(R.id.rotateloading);
        rotateLoading.start();

        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.setCancelable(false);

        return mDialog;
    }
}

