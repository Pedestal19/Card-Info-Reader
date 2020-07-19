import gabe.hosanna.cardInfoReader.model.Card
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiInterface {


    @GET("{bin}")
    fun getCard(@Path("bin") id: String?): Call<Card>
}