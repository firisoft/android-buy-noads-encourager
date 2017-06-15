package com.firisoft.android_buy_noads_encourager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class BuyNoAdsEncourager {
    private Activity _activity;
    OnBuyClickListener _onBuyClickListener;

    public BuyNoAdsEncourager(Activity currentActivity) {
        _activity = currentActivity;
    }

    public void setOnBuyClickListener(OnBuyClickListener onBuyClickListener) {
        _onBuyClickListener = onBuyClickListener;
    }

    /**
     * This method encourages the user to buy a no ads version of the app.
     * This is a comulative method, working this way:
     *  1. If the memory variable  "DidUserBuy" equals "Never" or "Buyd", return.
     *  1. After called K times, the fifth time shows "Buy this app dialog" (Yes,Later or Never).
     *  2. If user clicked Never, we will set the memory variable "DidUserBuy" to "Never"
     *  3. If the user clicked Yes, we will set the memory variable "DidUserBuy" to "Buyd" and
     *     also open the rating window of play store with our app page.
     *  4. If the user clicked "Later" we will wait K more times (TODO: maybe wait two days).
     *  5. We remember the times with a memory variable called "BuyAskingCounter"
     */
    public void EncourageToBuyAfterKTimes(int k) {
        String didUserBuy = getDidUserBuyValue();

        if (didUserBuy.equals("Buyd") ||didUserBuy.equals("Never")) {
            return;
        }

        int buyAskingCounter = getBuyAskingCounter();
        buyAskingCounter++;
        setBuyAskingCounter(buyAskingCounter);
        if (buyAskingCounter < k) {
            return;
        }

        //Buy is k or more and user did not buy yet
        setBuyAskingCounter(0);
        askToBuy();
    }


    private void askToBuy() {
        final AlertDialog dialog = new AlertDialog.Builder(_activity)
                .setPositiveButton("Buy NO-ADS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do positive action
                        setDidUserBuyValue("Buyd");
                        _onBuyClickListener.onBuyClick();
                    }
                })
                .setNeutralButton("Later", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do neutral action
                        setDidUserBuyValue("Later");
                    }
                })
                .setNegativeButton("Never", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do negative action
                        setDidUserBuyValue("Never");
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .create();

        LayoutInflater inflater = _activity.getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.encourager, null);
        dialog.setView(dialogLayout);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.show();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
                ImageView image = (ImageView) dialog.findViewById(R.id.encourager_dialog_image);
                Bitmap icon = BitmapFactory.decodeResource(_activity.getResources(),
                        R.drawable.encourage_image);
                float imageWidthInPX = (float)image.getWidth();

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Math.round(imageWidthInPX),
                        Math.round(imageWidthInPX * (float)icon.getHeight() / (float)icon.getWidth()));
                image.setLayoutParams(layoutParams);


            }
        });
    }

    private void showPlaystoreAppPage() {
        Uri uri = Uri.parse("market://details?id=" + _activity.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            _activity.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            _activity.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + _activity.getPackageName())));
        }
    }

    private void setBuyAskingCounter(int counterValue) {
        SharedPreferences prefs = _activity.getSharedPreferences(_activity.getPackageName() + "BuyNoAdsEncourager",_activity.MODE_PRIVATE);
        prefs.edit().putInt("BuyAskingCounter", counterValue).commit();
    }

    private int getBuyAskingCounter() {
        SharedPreferences prefs = _activity.getSharedPreferences(_activity.getPackageName() + "BuyNoAdsEncourager",_activity.MODE_PRIVATE);
        return prefs.getInt("BuyAskingCounter",0);
    }

    private void setDidUserBuyValue(String didUserBuy) {
        SharedPreferences prefs = _activity.getSharedPreferences(_activity.getPackageName() + "BuyNoAdsEncourager",_activity.MODE_PRIVATE);
        prefs.edit().putString("DidUserBuy", didUserBuy).commit();
    }

    private String getDidUserBuyValue() {
        SharedPreferences prefs = _activity.getSharedPreferences(_activity.getPackageName() + "BuyNoAdsEncourager",_activity.MODE_PRIVATE);
        return prefs.getString("DidUserBuy", "Later");
    }
}
