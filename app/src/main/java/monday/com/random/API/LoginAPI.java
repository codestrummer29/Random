package monday.com.random.API;

import monday.com.random.APIresponse.Login_Response;
import monday.com.random.APIresponse.SignUpResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Saahil on 08/05/17.
 */

public interface LoginAPI {

    @FormUrlEncoded
    @POST("login")
    Call<Login_Response> login(@Field("email")String email,@Field("pswd")String pswd,@Field("mode")String mode,@Field("user_type")String user_type);

    @FormUrlEncoded
    @POST("login")
    Call<SignUpResponse> signup(@Field("email")String email,@Field("pswd")String pswd,@Field("mode")String mode,@Field("user_type")String user_type,@Field("name")String name);
}
