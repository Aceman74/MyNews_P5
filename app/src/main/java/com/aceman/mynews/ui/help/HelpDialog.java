/*
 * Copyright (c) 2019. Aceman. All rights reserved.
 * Developed by Aceman.
 * Data provided by The New York Times API.
 * https://developer.nytimes.com/
 */

package com.aceman.mynews.ui.help;

import android.app.Dialog;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.aceman.mynews.R;

/**
 * Created by Lionel JOFFRAY - on 27/03/2019.
 * * This Class contain all CALL with API key for NYT <br>
 */
public class HelpDialog {

    private static Animation pulse;
    private static String mHelpTitle;

    public static void openHelp(final Context mContext) {
        mHelpTitle = mContext.getResources().getString(R.string.help_title);
// custom dialog
        final Dialog dialog = new Dialog(mContext);
        pulse = AnimationUtils.loadAnimation(dialog.getContext(), R.anim.pulse_effect);
        dialog.setContentView(R.layout.help_dialog);
        dialog.setTitle(mHelpTitle);
        TextView drawer = dialog.findViewById(R.id.textview_help_drawer);
        ImageView hamb = dialog.findViewById(R.id.imageview_hamburger_icon);
        ImageView search = dialog.findViewById(R.id.imageview_search_icon);
        ImageView menu = dialog.findViewById(R.id.imageview_menu_right);
        dialog.show();
        drawer.startAnimation(pulse);
        hamb.startAnimation(pulse);
        search.startAnimation(pulse);
        menu.startAnimation(pulse);
    }

}
