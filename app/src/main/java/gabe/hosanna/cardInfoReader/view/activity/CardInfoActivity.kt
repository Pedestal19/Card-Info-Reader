package gabe.hosanna.cardInfoReader.view.activity

import android.os.Bundle
import com.hosanna.cardInfoReader.R
import gabe.hosanna.cardInfoReader.model.Card
import kotlinx.android.synthetic.main.activity_card_info.*

class CardInfoActivity : ParentActivity() {

    var card : Card? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_info)


        if (intent != null) {
            card = intent.getSerializableExtra("data") as Card
            if (card == null) {
                finish()

                //if the data is null, it should close the page, nothing to display
            }

            displayData(card)
        }

        cancl.setOnClickListener { finish() }
    }

    private fun displayData(card : Card?){
        crd_brand.text = card?.brand ?: "Not available"
        crd_type.text = card?.type ?: "Not available"
        bank.text = card?.bank?.name ?: "Not available"
        country.text = card?.country?.name ?: "Not available"
        currency.text = card?.country?.currency  ?: "Not available"
    }
}
