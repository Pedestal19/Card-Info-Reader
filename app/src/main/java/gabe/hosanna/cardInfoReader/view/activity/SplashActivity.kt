package gabe.hosanna.cardInfoReader.view.activity

import android.content.Intent
import android.os.Bundle

class SplashActivity : ParentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, MainActivity::class.java))
    }
}
