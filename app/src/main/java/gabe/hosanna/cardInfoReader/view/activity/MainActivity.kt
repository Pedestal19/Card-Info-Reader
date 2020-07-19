package gabe.hosanna.cardInfoReader.view.activity

import gabe.hosanna.cardInfoReader.MainApplication
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cards.pay.paycardsrecognizer.sdk.Card
import cards.pay.paycardsrecognizer.sdk.ScanCardIntent
import cards.pay.paycardsrecognizer.sdk.ScanCardIntent.CancelReason
import com.hosanna.cardInfoReader.R
import gabe.hosanna.cardInfoReader.viewmodel.MainActivityViewModel
import gabe.hosanna.cardInfoReader.utils.getCartLogo
import gabe.hosanna.cardInfoReader.utils.getGreetingIcon
import gabe.hosanna.cardInfoReader.utils.greetings
import gabe.hosanna.cardInfoReader.utils.setCardNumber
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card_view.*
import org.jetbrains.anko.intentFor


class MainActivity : ParentActivity() {


    private  var activityViewModel : MainActivityViewModel?=null

    val REQUEST_CODE_SCAN_CARD = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view()

    }
    //to keep the oncreate page clean
    fun view(){
        CardNumberListner()

        //instantiate view model
        activityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)


        btn.setOnClickListener {
            scanCard()
        }

        btn_mlkit.setOnClickListener {
            startActivity(intentFor<LivePreviewActivity>())
            Toast.makeText(this, "Still in Development...", Toast.LENGTH_SHORT).show()
        }

        btn_upload_photo.setOnClickListener {
            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
        }

        //handled in utils
        getGreetingIcon()?.let { icon_img.setImageResource(it) }

        //handled in utils
        greeting_.text = greetings()


        activityViewModel?._error?.observe(this, Observer {
            toastError(it)
            pb.visibility = View.GONE

//            progressDialog(false)

        })

        activityViewModel?._sucessfful?.observe(this, Observer {
            toastSuccess(it)
//            progressDialog(false)
            pb.visibility = View.GONE

        })


        // to observe result from server and send through intent to display page
        activityViewModel?._card?.observe(this , Observer {
            if (it != null ){
                val i = Intent(
                    MainApplication.instance?.applicationContext,
                    CardInfoActivity::class.java)
                i.putExtra("data",it)
                startActivity(i)

            }
            pb.visibility = View.GONE

//            progressDialog(false)

        })

    }



    //card_view number edit text listner
    private fun CardNumberListner() {

        edtCardNumber.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable) { // Remove all spacing char


                // to append logo to card_view ui
                if(s.isNotEmpty()) {
                    providerLogo.visibility = View.VISIBLE
                    //handled in utils to set card_view logo
                    providerLogo.setImageResource(
                        getCartLogo(
                            s
                        )
                    )
                }else{
                    providerLogo.visibility = View.GONE
                }




                //logic to space card_view number
                setCardNumber(s)
                if(s.isNotEmpty()){
                    tv_card_number.text =  s.toString()
                }else{
                    tv_card_number.text = getString(R.string.card_number)
                }



                //get card_view details from server when edit text completed
                postCardDetailsToServer(s)


            }

        })

    }





    //to call the ocr scan
    private fun scanCard() {
        val intent: Intent =  ScanCardIntent.Builder(this).build()
        startActivityForResult(intent, REQUEST_CODE_SCAN_CARD)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SCAN_CARD) {
            if (resultCode == Activity.RESULT_OK) {
                val card: Card? = data?.getParcelableExtra(ScanCardIntent.RESULT_PAYCARDS_CARD)

                setCard(card)
            } else if (resultCode == Activity.RESULT_CANCELED) {
                @CancelReason val reason: Int = data?.getIntExtra(
                    ScanCardIntent.RESULT_CANCEL_REASON,
                    ScanCardIntent.BACK_PRESSED
                )
                    ?: ScanCardIntent.BACK_PRESSED


            } else if (resultCode == ScanCardIntent.RESULT_CODE_ERROR) {
//                Log.i(cards.pay.sample.demo.CardDetailsActivity.TAG, "Scan failed")
            }
        }
    }

    //result from ocr and send to view model to post to the server
    private fun setCard(card : Card?){
        if(card!= null){
            //show user progress bar before posting to the server
            pb.visibility = View.VISIBLE
//            progressDialog(true)
            activityViewModel?.postData(card.cardNumber.toString())
        }
    }



    private fun postCardDetailsToServer(s: Editable){

        if(s.length == 19) {
            val k: String = tv_card_number.text.toString().replace(" ", "")

            //show user progress bar before posting to the server
//            progressDialog(true)
            pb.visibility = View.VISIBLE

            activityViewModel?.postData(k)
        }

    }



    fun progressDialog(bol : Boolean){

        if(bol){
            frame_layout.setBackgroundColor(ContextCompat.getColor(this, R.color.grey))
            progress_bar.visibility = View.VISIBLE
            btn.isEnabled = false
            edtCardNumber.isEnabled = false

        }
        else{
            frame_layout.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            progress_bar.visibility = View.GONE
            btn.isEnabled = true
            edtCardNumber.isEnabled = true
            edtCardNumber.text.clear()
        }

    }


}
