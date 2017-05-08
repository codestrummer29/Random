package monday.com.random.ArticleViewer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import monday.com.random.R;

/**
 * Created by Saahil on 09/05/17.
 */

public class ViewArticle extends AppCompatActivity {

    @BindView(R.id.webView)
    WebView webView;

    String data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articleview);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                data= null;
            } else {
                data= extras.getString("article_desc");
            }
        } else {
            data= (String) savedInstanceState.getSerializable("article_desc");
        }
        Log.d("here",data);
        WebSettings settings = webView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        webView.loadData(data, "text/html; charset=utf-8",null);
    }
}
