package com.example.exammodul06.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exammodul06.R
import com.example.exammodul06.adapter.CardAdapter
import com.example.exammodul06.database.entity.CardRoom
import com.example.exammodul06.database.repository.RoomRepository
import com.example.exammodul06.model.CardResp
import com.example.exammodul06.network.RetrofitHttp
import com.example.exammodul06.utils.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cardAdapter: CardAdapter
    private var cardRooms: ArrayList<CardRoom> = ArrayList()
    private var cardResp: ArrayList<CardResp> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

    }

    private fun initViews() {
        val iv_add_card: ImageView = findViewById(R.id.iv_add_card)

        iv_add_card.setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        cardAdapter = CardAdapter(this)
        recyclerView.adapter = cardAdapter

        if (isInternetAvailable()){
            openRVOnline()
        }else{
            openRVOffline()
        }

    }

    override fun onRestart() {
        super.onRestart()
        cardRooms.clear()
        if (isInternetAvailable()){
            openRVOnline()
        }else{
            openRVOffline()
        }
    }

    private fun getAllCard(){
        cardRooms.clear()
        cardRooms = RoomRepository(application).getCardRoomList() as ArrayList<CardRoom>
    }

    private fun openRVOnline(){
        getAllCard()
        if (cardRooms.size > 0){
            for (cardRoom in cardRooms){
                if (!cardRoom.isOnline){
                    val cardResp = CardResp(cardRoom.card_number, cardRoom.month, cardRoom.year, cardRoom.cvv, cardRoom.holder_name, "")
                    RetrofitHttp.retrofitService.createCard(cardResp).enqueue(object : Callback<CardResp>{
                        override fun onResponse(call: Call<CardResp>, response: Response<CardResp>) {
                            Logger.d("@@@CreateCardD", response.body().toString())
                        }

                        override fun onFailure(call: Call<CardResp>, t: Throwable) {
                            Logger.e("@@@CreateCardD", t.message.toString())
                        }

                    })
                    RoomRepository(application).updateCardRoom(cardRoom.id!!, false)
                }
            }
        }

        RetrofitHttp.retrofitService.listPhotos().enqueue(object : Callback<ArrayList<CardResp>>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<ArrayList<CardResp>>,
                response: Response<ArrayList<CardResp>>
            ) {
                Logger.d("@@@CreateCardD", response.body().toString())
                cardAdapter.items = response.body()!!
                cardAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<ArrayList<CardResp>>, t: Throwable) {
                Logger.e("@@@CreateCardD", t.message.toString())
            }

        })

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun openRVOffline(){
        getAllCard()
        for (cardRoom in cardRooms){
            cardResp.add(CardResp(cardRoom.card_number, cardRoom.month, cardRoom.year, cardRoom.cvv, cardRoom.holder_name, ""))
        }
        cardAdapter.items = cardResp
        cardAdapter.notifyDataSetChanged()
    }

    private fun isInternetAvailable(): Boolean {
        val manager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val infoMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val infoWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return infoMobile!!.isConnected || infoWifi!!.isConnected
    }

}