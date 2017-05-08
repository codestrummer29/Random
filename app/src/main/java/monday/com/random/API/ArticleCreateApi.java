package monday.com.random.API;

import monday.com.random.APIresponse.ArticleCreateResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Saahil on 09/05/17.
 */

public interface ArticleCreateApi {

    @FormUrlEncoded
    @POST("article_upload")
    Call<ArticleCreateResponse> saveArticle(@Field("uid")String uid,@Field("status")String status,@Field("article_title")String article_title,@Field("article_topic")String article_topic,@Field("article_desc")String article_desc);
}
