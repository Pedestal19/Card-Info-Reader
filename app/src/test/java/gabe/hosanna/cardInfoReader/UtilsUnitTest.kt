package gabe.hosanna.cardInfoReader

import android.text.Editable
import com.hosanna.cardInfoReader.R
import gabe.hosanna.cardInfoReader.utils.greetings
import gabe.hosanna.cardInfoReader.utils.isOnline
import gabe.hosanna.cardInfoReader.utils.setCardNumber
import org.junit.Assert
import org.junit.Test

class UtilsUnitTest {

    @Test
    fun spaceSetCardNumber() {

       val s: Editable ?= null
        s?.equals(6).toString()
        assert(true) {
            if (s != null) {
                setCardNumber(s)
            }
        }

    }



    @Test
    fun checkDeviceOnline() {
        Assert.assertEquals(false, isOnline())
    }





}