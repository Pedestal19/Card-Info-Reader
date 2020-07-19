package gabe.hosanna.cardInfoReader.view

import gabe.hosanna.cardInfoReader.model.Card

interface CardView{
    fun loadingFailed(msg: String)
    fun loadingSuccessful(msg: String)
    fun card (data : Card?)
}