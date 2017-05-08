package monday.com.random.APIresponse;

import java.util.ArrayList;

import monday.com.random.POJO.Article_Data;
import monday.com.random.POJO.Topic_Data;

/**
 * Created by Saahil on 08/05/17.
 */

public class HomeResponse {
    private int status;
    private String msg;
    ArrayList<Topic_Data> topic_data;
    ArrayList<Article_Data> article_data;

    public ArrayList<Article_Data> getArticle_data() {
        return article_data;
    }

    public void setArticle_data(ArrayList<Article_Data> article_data) {
        this.article_data = article_data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<Topic_Data> getTopic_data() {
        return topic_data;
    }

    public void setTopic_data(ArrayList<Topic_Data> topic_data) {
        this.topic_data = topic_data;
    }
}

