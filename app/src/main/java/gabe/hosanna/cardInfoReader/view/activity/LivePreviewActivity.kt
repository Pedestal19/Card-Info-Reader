
package gabe.hosanna.cardInfoReader.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.annotation.KeepName
import com.hosanna.cardInfoReader.R
import gabe.hosanna.cardInfoReader.mlkit.CameraSource
import gabe.hosanna.cardInfoReader.mlkit.TextRecognitionProcessor
import kotlinx.android.synthetic.main.activity_live_preview.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import timber.log.Timber
import java.io.IOException


@KeepName
class LivePreviewActivity : AppCompatActivity() {

    private var cameraSource: CameraSource? = null
    private val textRecognitionProcessor = TextRecognitionProcessor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")
        setContentView(R.layout.activity_live_preview)
        createCameraSource()
        observeText()

    }

    @SuppressLint("MissingPermission")
    private fun observeText() {
        textRecognitionProcessor.readingText{
            if (it.isNotEmpty()) {
                cameraSource?.stop()
                alert {
                    message = it
                    okButton {
                        cameraSource?.start()
                    }
                }.show()
            }
        }
    }


    private fun createCameraSource() {
        // If there's no existing cameraSource, create one.
        if (cameraSource == null) {
            cameraSource = CameraSource(this, fireFaceOverlay)
            cameraSource?.setMachineLearningFrameProcessor(textRecognitionProcessor)
        }
    }

    /**
     * Starts or restarts the camera source, if it exists. If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private fun startCameraSource() {
        cameraSource?.let {
            try {
                if (firePreview == null) {
                    Timber.d("resume: Preview is null")
                }
                if (fireFaceOverlay == null) {
                    Timber.d("resume: graphOverlay is null")
                }
                firePreview?.start(it, fireFaceOverlay)
            } catch (e: IOException) {
                Timber.e(e)
                cameraSource?.release()
                cameraSource = null
            }
        }
    }

    public override fun onResume() {
        super.onResume()
        Timber.d("onResume")
        startCameraSource()
    }

    /** Stops the camera.  */
    override fun onPause() {
        super.onPause()
        firePreview?.stop()
    }

    public override fun onDestroy() {
        super.onDestroy()
        cameraSource?.release()
    }


}
