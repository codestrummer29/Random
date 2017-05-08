package monday.com.random.API;

import monday.com.random.APIresponse.HomeResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Saahil on 08/05/17.
 */

public interface HomePageApi {

    @FormUrlEncoded
    @POST("home")
    Call<HomeResponse> getHomeData(@Field("filter")String filter);
}
