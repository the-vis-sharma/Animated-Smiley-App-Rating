package com.techbuddy.animatedsmileyrating;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hsalf.smilerating.SmileRating;

public class AnimatedSmileyRating {

    private Context context;
    private Dialog dialog;
    private CardView ratingDialogCard;
    private LinearLayout header;
    private TextView tvTitle;
    private TextView tvMsg;
    private TextView tvNever;
    private TextView tvLater;
    private TextView tvSubmit;
    private SmileRating smileyRatingBar;
    private SharedPreferences sharedpreferences;
    private String MyPrefs = "AnimatedSmileyRating";
    private static final String SESSION_COUNT = "session_count";
    private static final String SHOW_NEVER = "show_never";
    private int session = 1;
    private float threshold = 1;

    public AnimatedSmileyRating(final Context context) {
        this.context = context;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_rating_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ratingDialogCard = dialog.findViewById(R.id.rating_dialog_card);
        header = dialog.findViewById(R.id.header);
        tvTitle = dialog.findViewById(R.id.tv_title);
        tvMsg = dialog.findViewById(R.id.tv_msg);
        tvNever = dialog.findViewById(R.id.tv_never);
        tvLater = dialog.findViewById(R.id.tv_later);
        tvSubmit = dialog.findViewById(R.id.tv_submit);
        smileyRatingBar = dialog.findViewById(R.id.smiley_rating_bar);

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rating = smileyRatingBar.getSelectedSmile();
                if(rating>0) {
                    if (rating >= threshold) {
                        openPlayStore(context);
                    } else {
                        openFeedbackMail(context);
                    }

                    showNever();
                    dialog.dismiss();
                }
                else {
                    Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
                    ratingDialogCard.startAnimation(shake);
                    Toast.makeText(context, "Select a rating.", Toast.LENGTH_LONG).show();
                }
            }
        });

        tvLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLater();
                dialog.dismiss();
            }
        });

        tvNever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNever();
                dialog.dismiss();
            }
        });
    }

    public void openFeedbackMail(Context context) {
        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ "cs.techbuddy@gmail.com" });
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Recovery Bin - Feedback");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "Write your feedback/issue here");
        context.startActivity(Intent.createChooser(intent, "Choose Email App -"));
    }

    public void openPlayStore(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }

    private void showLater() {
        sharedpreferences = context.getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(SESSION_COUNT, 2);
        editor.commit();
    }

    private void showNever() {
        sharedpreferences = context.getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(SHOW_NEVER, true);
        editor.commit();
    }

    public void show() {
        if(checkIfSessionMatches(session)) {
            dialog.show();
        }
    }

    //The radius in pixels of the corners of the rectangle shape
    public void setCornRadius(float radius) {
        ratingDialogCard.setRadius(radius);
    }

    public void setHeaderBackground(Drawable drawable) {
        header.setBackground(drawable);
    }

    public void setTitleText(String title) {
        tvTitle.setText(title);
    }

    public void setTitleTextColor(int color) {
        tvTitle.setTextColor(context.getResources().getColor(color));
    }

    //Set the default text size to the given value, interpreted as "scaled pixel" units. This size
    // is adjusted based on the current density and user font size preference.
    public void setTitleTextSize(float size) {
        tvTitle.setTextSize(size);
    }

    public void setMsgText(String msg) {
        tvMsg.setText(msg);
    }

    public void setMsgTextColor(int color) {
        tvMsg.setTextColor(context.getResources().getColor(color));
    }

    public void setMsgTextSize(float size) {
        tvMsg.setTextSize(size);
    }

    public void setNeverText(String text) {
        tvNever.setText(text);
    }

    public void setNeverTextColor(int color) {
        tvNever.setTextColor(context.getResources().getColor(color));
    }

    public void setNeverTextSize(float size) {
        tvNever.setTextSize(size);
    }

    public void setLaterText(String text) {
        tvLater.setText(text);
    }

    public void setLaterTextColor(int color) {
        tvLater.setTextColor(context.getResources().getColor(color));
    }

    public void setLaterTextSize(float size) {
        tvLater.setTextSize(size);
    }

    public void setSubmitText(String text) {
        tvSubmit.setText(text);
    }

    public void setSubmitTextColor(int color) {
        tvSubmit.setTextColor(context.getResources().getColor(color));
    }

    public void setSubmitTextSize(float size) {
        tvSubmit.setTextSize(size);
    }

    public void setSession(int session) {
        this.session = session;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    private boolean checkIfSessionMatches(int session) {

        if (session == 1) {
            return true;
        }

        sharedpreferences = context.getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);

        if (sharedpreferences.getBoolean(SHOW_NEVER, false)) {
            return false;
        }

        int count = sharedpreferences.getInt(SESSION_COUNT, 1);

        if (session == count) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt(SESSION_COUNT, 1);
            editor.commit();
            return true;
        } else if (session > count) {
            count++;
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt(SESSION_COUNT, count);
            editor.commit();
            return false;
        } else {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt(SESSION_COUNT, 2);
            editor.commit();
            return false;
        }
    }


    /*
    1. card radius
    2. header background color
    3. title color, size
    4. msg color, size
    5. never, later and submit color and size
    6. play store and email feedback
    7. session
     */
}
