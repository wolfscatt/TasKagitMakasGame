package com.example.taskagtmakasgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity()
{

    private var mInterstitialAd: InterstitialAd? = null
    private final var TAG = "MainActivity"

    lateinit var mAdView : AdView

    val arr = arrayOf("TAŞ","KAĞIT","MAKAS")
    var isBlueButtonClicked = false
    var isRedButtonClicked = false
    var blueScore = 0
    var redScore = 0
    var sayac : Long = 0
    //var runnable : Runnable = Runnable{}
    //var handler : Handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        blueScoreText.text = "Mavi Oyuncu: 0"
        redScoreText.text = "Kırmızı Oyuncu: 0"

        blueButton.text = "MAVİ OYUNCU\n\n(TAP) "
        redButton.text = "KIRMIZI OYUNCU\n\n(TAP) "

        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        InterstitialAd.load(this,"ca-app-pub-5495185379101554/9363404066", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError.toString())
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })
        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d(TAG, "Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                Log.d(TAG, "Ad dismissed fullscreen content.")
                mInterstitialAd = null
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                // Called when ad fails to show.
                Log.e(TAG, "Ad failed to show fullscreen content.")
                mInterstitialAd = null
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d(TAG, "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad showed fullscreen content.")
            }
        }
    }

    fun temizle(view: View){

        redScore = 0
        blueScore = 0
        redScoreText.text = "Kırmızı Oyuncu: 0"
        blueScoreText.text = "Mavi Oyuncu: 0"
        isBlueButtonClicked = false
        isRedButtonClicked = false
        redButton.text = "KIRMIZI OYUNCU"
        blueButton.text = "MAVİ OYUNCU"

        mInterstitialAd?.show(this)
    }

    fun scoreCalc(){

        if (blueButton.text == "TAŞ" && redButton.text == "KAĞIT"){
            redScore+=1
            redScoreText.text = "Kırmızı Oyuncu: ${redScore} "
        }else if (blueButton.text == "TAŞ" && redButton.text == "MAKAS"){
            blueScore+=1
            blueScoreText.text = "Mavi Oyuncu: ${blueScore} "

        }else if (blueButton.text == "KAĞIT" && redButton.text == "TAŞ"){
            blueScore+=1
            blueScoreText.text = "Mavi Oyuncu: ${blueScore} "

        }else if (blueButton.text == "KAĞIT" && redButton.text == "MAKAS"){
            redScore+=1
            redScoreText.text = "Kırmızı Oyuncu: ${redScore}"
        }else if(blueButton.text == "MAKAS" && redButton.text == "TAŞ"){
            redScore+=1
            redScoreText.text = "Kırmızı Oyuncu: ${redScore}"
        }else if(blueButton.text == "MAKAS" && redButton.text == "KAĞIT"){
            blueScore+=1
            blueScoreText.text = "Mavi Oyuncu: ${blueScore} "
        }
        if (blueScore == 3 || redScore == 3) {
            mInterstitialAd?.show(this)
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.")
        }
    }

    fun blueClick(view : View) {

        isBlueButtonClicked = true
        //sayac = 4
        butonlaraBasıldı()

    }

    fun redClick(view : View){

        isRedButtonClicked = true
        //sayac = 4
        butonlaraBasıldı()

    }

    fun butonlaraBasıldı(){

        if (isBlueButtonClicked && isRedButtonClicked) {

            object : CountDownTimer(4000,1000){
                override fun onTick(p0: Long) {
                    //sayac -= 1
                    sayac = p0/1000
                    redButton.text = sayac.toString()
                    blueButton.text = sayac.toString()
                }

                override fun onFinish() {
                    val blueRandom = Random.nextInt(0, 3)
                    blueButton.text = arr[blueRandom]
                    val redRandom = Random.nextInt(0, 3)
                    redButton.text = arr[redRandom]
                    scoreCalc()
                    isBlueButtonClicked = false
                    isRedButtonClicked = false
                }
            }.start()
            /*
            runnable = object : Runnable{
                override fun run() {
                    sayac-=1
                    redButton.text = sayac.toString()
                    blueButton.text = sayac.toString()
                    handler.postDelayed(this,1000)
                }
            }
            handler.post(runnable)

             */
            //handler.removeCallbacks(runnable)
        }
    }
}