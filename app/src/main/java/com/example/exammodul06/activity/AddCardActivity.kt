package com.example.exammodul06.activity

import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import com.example.exammodul06.R
import android.text.Editable
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.exammodul06.database.entity.CardRoom
import com.example.exammodul06.database.repository.RoomRepository
import com.example.exammodul06.model.CardResp
import com.example.exammodul06.network.RetrofitHttp
import com.example.exammodul06.utils.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddCardActivity : AppCompatActivity() {

    private lateinit var tv_card_number: TextView
    private lateinit var edt_card_number: EditText
    private lateinit var tv_card_month: TextView
    private lateinit var edt_card_month: EditText
    private lateinit var tv_card_year: TextView
    private lateinit var edt_card_year: EditText
    private lateinit var tv_holder_name: TextView
    private lateinit var edt_holder_name: EditText
    private lateinit var edt_card_cvv: EditText
    private lateinit var tv_add_card: TextView

    private val watcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            tv_card_number.text = edt_card_number.text.toString()
            tv_card_month.text = edt_card_month.text.toString()
            tv_card_year.text = edt_card_year.text.toString()
            tv_holder_name.text = edt_holder_name.text.toString()
        }

        override fun afterTextChanged(s: Editable?) {}

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        initViews()

    }

    private fun initViews() {
        tv_card_number = findViewById(R.id.tv_card_number)
        edt_card_number = findViewById(R.id.edtNumber)
        tv_card_month = findViewById(R.id.tv_month)
        edt_card_month = findViewById(R.id.edt_month)
        tv_card_year = findViewById(R.id.tv_year)
        edt_card_year = findViewById(R.id.edt_year)
        tv_holder_name = findViewById(R.id.tv_card_holder)
        edt_holder_name = findViewById(R.id.edtHolder)
        edt_card_cvv = findViewById(R.id.edt_cvv)
        tv_add_card = findViewById(R.id.tv_add_card)

        edtChangeListener()

        val iv_close: ImageView = findViewById(R.id.ivCancel)
        iv_close.setOnClickListener {
            finish()
        }

        tv_add_card.setOnClickListener {
            addCard()
            Toast.makeText(this, "Add Your Card", Toast.LENGTH_SHORT).show()
        }

    }

    private fun edtChangeListener(){
        edt_card_number.addTextChangedListener(watcher)
        edt_card_month.addTextChangedListener(watcher)
        edt_card_year.addTextChangedListener(watcher)
        edt_holder_name.addTextChangedListener(watcher)
    }

    private fun addCard(){
        val cardRoom: CardRoom
        if(isInternetAvailable()){
            cardRoom = CardRoom(card_number = edt_card_number.text.toString(), month = edt_card_month.text.toString(), year = edt_card_year.text.toString(), cvv = edt_card_cvv.text.toString(), holder_name = edt_holder_name.text.toString(), isOnline = true)
            val cardResp = CardResp(cardRoom.card_number, cardRoom.month, cardRoom.year, cardRoom.cvv, cardRoom.holder_name, "")
            RetrofitHttp.retrofitService.createCard(cardResp).enqueue(object :
                Callback<CardResp> {
                override fun onResponse(call: Call<CardResp>, response: Response<CardResp>) {
                    Logger.d("@@@CreateCardD", response.body().toString())
                }

                override fun onFailure(call: Call<CardResp>, t: Throwable) {
                    Logger.e("@@@CreateCardD", t.message.toString())
                }

            })
        }else{
            cardRoom = CardRoom(card_number = edt_card_number.text.toString(), month = edt_card_month.text.toString(), year = edt_card_year.text.toString(), cvv = edt_card_cvv.text.toString(), holder_name = edt_holder_name.text.toString(), isOnline = false)
        }
        RoomRepository(application).saveExamModel(cardRoom)

        tv_card_number.text = ""
        edt_card_number.text.clear()
        tv_card_month.text = ""
        edt_card_month.text.clear()
        tv_card_year.text = ""
        edt_card_year.text.clear()
        tv_holder_name.text = ""
        edt_holder_name.text.clear()
        edt_card_cvv.text.clear()
    }

    private fun isInternetAvailable(): Boolean {
        val manager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val infoMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val infoWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return infoMobile!!.isConnected || infoWifi!!.isConnected
    }

}