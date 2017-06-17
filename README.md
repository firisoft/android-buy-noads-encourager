# Encourage users to buy no-ads version of your app:

Encourage users to buy a NO-ADS in-app-product by showing them a message after X times they open the app.
In the shown encouraging message they can choose "Buy", "Later" or "Never".

<img src="https://github.com/firisoft/android-buy-noads-encourager/raw/master/screenshot1.png" width="250" ></img>

# HOW TO USE:

Open your root level build.gradle file and :

1. Add the following maven repository to your build.gradle

```java
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

2. Add the dependency for android-buy-noads-encourager :

```java
dependencies {
    compile 'com.github.firisoft:android-buy-noads-encourager:1.0.0'
}
```

3. Add the following lines to your 'onCreate' method of 'MainActivity.java'

```java
BuyNoAdsEncourager buyNoAdsEncourager = new BuyNoAdsEncourager(this);
buyNoAdsEncourager.setOnBuyClickListener(new OnBuyClickListener() {
    @Override
    public void onBuyClick() {
        //Add your buying process here
    }
});
buyNoAdsEncourager.EncourageToBuyAfterKTimes(3); //Ask user to buy after 3 times he opens the app
```

That's all
