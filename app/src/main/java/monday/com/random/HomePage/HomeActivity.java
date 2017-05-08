package monday.com.random.HomePage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import monday.com.random.API.HomePageApi;
import monday.com.random.APIresponse.HomeResponse;
import monday.com.random.ArticleCreator.ArticleCreator;
import monday.com.random.ArticleViewer.ViewArticle;
import monday.com.random.POJO.Article_Data;
import monday.com.random.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Saahil on 08/05/17.
 */

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.article_listView)
    RecyclerView article_list_view;

    @BindView(R.id.fab_article)
    FloatingActionButton fabBut;

    @BindView(R.id.loadingBar)
    ProgressBar loadingBar;

    ArrayList<Article_Data> homelist;
    LinearLayoutManager manager;
    int currentPosition;
    RecyclerView.Adapter listAdapter;
    String filter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        homelist = new ArrayList<>();

        article_list_view.setHasFixedSize(true);
        manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        article_list_view.setLayoutManager(manager);

        //initialising custom Adapter Class
        listAdapter = new CustomAdapter(getApplicationContext());
        listAdapter.setHasStableIds(false);
        article_list_view.setAdapter(listAdapter);
        filter = "";

        loadingBar.setVisibility(View.INVISIBLE);

        getHomePageData(filter);

        fabBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ArticleCreator.class);
                startActivity(intent);
            }
        });

    }

    public void getHomePageData(String filter) {
        loadingBar.setVisibility(View.VISIBLE);
        article_list_view.setVisibility(View.INVISIBLE);
        //--instantiate retrofit and add the custom okhttp client
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BASE_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HomePageApi service = retrofit.create(HomePageApi.class);
        Call<HomeResponse> call = service.getHomeData(filter);

        call.enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                Log.d("here","here");
                homelist = new ArrayList<>();
                HomeResponse data = response.body();
                loadingBar.setVisibility(View.INVISIBLE);
                article_list_view.setVisibility(View.VISIBLE);
                homelist.addAll(data.getArticle_data());
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                Log.d("here1","here1");
                loadingBar.setVisibility(View.INVISIBLE);
                article_list_view.setVisibility(View.VISIBLE);
            }
        });
    }

    //custom Adapter to add own cardView
    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.cardViewHolder>{
        //intialising context to give context to cardViewHolder OnCreate
        Context context;
        public CustomAdapter(Context mContext) {
            this.context = mContext;
        }

        @Override
        public cardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_article_element,parent,false);
            return new cardViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final cardViewHolder holder, int position) {
            holder.tvAuthor.setText("Posted by " + homelist.get(holder.getAdapterPosition()).getUsr());
            holder.tvDate.setText(homelist.get(holder.getAdapterPosition()).getDate());
            holder.tvTopic.setText(homelist.get(holder.getAdapterPosition()).getTopic_name());
            holder.tvTitle.setText(homelist.get(holder.getAdapterPosition()).getTitle());
            if(homelist.get(holder.getAdapterPosition()).getArticle_img() != "" ){
                Picasso.with(context).load(homelist.get(holder.getAdapterPosition()).getArticle_img()).into(holder.imageArt);
                holder.imageArt.setVisibility(View.VISIBLE);
            }else {
                holder.imageArt.setVisibility(View.GONE);
            }
            holder.tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, ViewArticle.class);
                    intent.putExtra("article_desc",homelist.get(holder.getAdapterPosition()).getDesc());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return homelist.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public class cardViewHolder extends  RecyclerView.ViewHolder{

            @BindView(R.id.topic_name)
            TextView tvTopic;

            @BindView(R.id.author)
            TextView tvAuthor;

            @BindView(R.id.date)
            TextView tvDate;

            @BindView(R.id.imageArticle)
            ImageView imageArt;

            @BindView(R.id.title)
            TextView tvTitle;

            public cardViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getHomePageData("");
    }
}

