package gabe.hosanna.cardInfoReader.api.repository

import gabe.hosanna.cardInfoReader.model.Card
import gabe.hosanna.cardInfoReader.api.RetrofitClient
import gabe.hosanna.cardInfoReader.view.CardView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardInfoRepository {



    fun getCardInfo(cardNumber : String, callback : CardView){

        RetrofitClient.getInstance()?.getApi()?.getCard(cardNumber)?.enqueue(object :
            Callback<Card> {
            override fun onFailure(call: Call<Card>, t: Throwable) {
                callback.loadingFailed(t.toString())
            }

            override fun onResponse(call: Call<Card>, response: Response<Card>) {

                if(response.isSuccessful && response.body()!= null){
                    when (response.code()){
                        200->{
                            response.body()?.let { callback.card(it)
                            }

                            callback.loadingSuccessful("valid card_view")

                        }

                        else ->{
                            callback.loadingFailed("Invalid request.. please try again")
                        }
                    }
                }else{
                    callback.loadingFailed("Invalid request..Check your card_view details and try again")
                }
            }
        })
    }
}