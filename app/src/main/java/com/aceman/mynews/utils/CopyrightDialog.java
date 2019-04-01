/*
 * Copyright (c) 2019. Aceman. All rights reserved.
 * Developed by Aceman.
 * Data provided by The New York Times API.
 * https://developer.nytimes.com/
 */

package com.aceman.mynews.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aceman.mynews.R;
import com.aceman.mynews.ui.navigations.activities.WebviewActivity;

/**
 * Created by Lionel JOFFRAY - on 27/03/2019.
 */
public class CopyrightDialog {

static String mCopyrightTitle;


    public static void openCopyright(final Context mContext){
        mCopyrightTitle = mContext.getResources().getString(R.string.copyright_text);
// custom dialog
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.copyright_dialog);
        dialog.setTitle(mCopyrightTitle);
        TextView textView = dialog.findViewById(R.id.text_copyright_textview);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent webView = new Intent(mContext, WebviewActivity.class);
                webView.putExtra("UrlWebview", "https://developer.nytimes.com" );
                mContext.startActivity(webView);
                dialog.dismiss();
            }
        });

        Button dialogButton =  dialog.findViewById(R.id.dialogButton);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
