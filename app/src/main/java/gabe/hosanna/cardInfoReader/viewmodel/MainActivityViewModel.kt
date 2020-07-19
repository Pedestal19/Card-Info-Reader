package gabe.hosanna.cardInfoReader.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gabe.hosanna.cardInfoReader.model.Card
import gabe.hosanna.cardInfoReader.api.repository.CardInfoRepository
import gabe.hosanna.cardInfoReader.view.CardView

class MainActivityViewModel : ViewModel(), CardView {

    var presenter: CardInfoRepository =
        CardInfoRepository()



    //get success message from the server

    var _sucessfful= MutableLiveData<String>()


    //get error message from the server
    var _error= MutableLiveData<String>()




    //get card_view details  from the server
    var _card= MutableLiveData<Card>()


    //send card_view to server
    fun postData(card_num : String){
        presenter.getCardInfo(card_num,this)
    }

    override fun loadingFailed(msg: String) {
        _error.postValue(msg)
    }

    override fun loadingSuccessful(msg: String) {
        _sucessfful.postValue(msg)
    }

    override fun card(data: Card?) {
        _card.postValue(data)
    }


}