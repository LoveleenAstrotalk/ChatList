package com.astrotalk.sdk.api.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.astrotalk.sdk.R
import com.astrotalk.sdk.api.adapter.ChatAstrologerListAdapter

class AstrologerListActivity : AppCompatActivity() {

    private var context = this
    private lateinit var chatAstrologerListAdapter: ChatAstrologerListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_astrologer_list)

        astrologerList()

    }

    private fun astrologerList() {

//        (RetrofitClient.client.create(RetrofitInterface::class.java).getAstrologerListSorting(
//            1, 1,1, "Asia/Calcutta", -1, 0,
//            1000, "1.1.197", 4, false, false, false,
//            1, false, "0614aa461b06c7ac", "+91", false, false,
//            true, 0).enqueue(object :
//
//            Callback<AstrologerListResponseModel> {
//            override fun onResponse(call: Call<AstrologerListResponseModel>, response: Response<AstrologerListResponseModel>){
//
//                Log.d("response", Gson().toJson(response.body()))
//                Log.d("response", response.errorBody().toString())
//
//                when {
//                    response.code() == 200 -> {
//                        if(response.body()?.status == "success") {
//                            chatAstrologerListAdapter = ChatAstrologerListAdapter(
//                                context,
//                                astrologerListModelArrayList,
//                                this,
//                                "list",
//                                this,
//                                this
//                            )
//                            recyclerView.setAdapter(chatAstrologerListAdapter)
//                        }
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<AstrologerListResponseModel>, th: Throwable) {
//                Log.e("Response", th.message.toString())
//            }
//        }))
    }
}