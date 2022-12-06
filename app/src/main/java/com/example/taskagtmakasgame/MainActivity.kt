package com.example.taskagtmakasgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity()
{

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