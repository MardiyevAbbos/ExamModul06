package com.example.exammodul06.utils

import android.util.Log
import com.example.exammodul06.network.RetrofitHttp

class Logger {
    companion object{
        fun d(tag: String, msg: String){
            if (RetrofitHttp.IS_TESTER) Log.d(tag, msg)
        }

        fun i(tag: String, msg: String){
            if (RetrofitHttp.IS_TESTER) Log.i(tag, msg)
        }

        fun v(tag: String, msg: String){
            if (RetrofitHttp.IS_TESTER) Log.v(tag, msg)
        }

        fun e(tag: String, msg: String){
            if (RetrofitHttp.IS_TESTER) Log.e(tag, msg)
        }

    }
}