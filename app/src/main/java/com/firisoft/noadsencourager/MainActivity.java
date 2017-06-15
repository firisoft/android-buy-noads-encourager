package com.firisoft.noadsencourager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.firisoft.android_buy_noads_encourager.BuyNoAdsEncourager;
import com.firisoft.android_buy_noads_encourager.OnBuyClickListener;

public class MainActivity extends AppCompatActivity {

    BuyNoAdsEncourager mBuyNoAdsEncourager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBuyNoAdsEncourager = new BuyNoAdsEncourager(this);
        mBuyNoAdsEncourager.setOnBuyClickListener(new OnBuyClickListener() {
            @Override
            public void onBuyClick() {
                //Call here your method for buying the NO-ADS version
                Log.d("FirisoftNoAdsEncourager", "User clicked buy no ads version");
            }
        });
        mBuyNoAdsEncourager.EncourageToBuyAfterKTimes(3);

    }
}
