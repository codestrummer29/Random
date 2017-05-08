package monday.com.random.LoginRegister;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import monday.com.random.API.LoginAPI;
import monday.com.random.APIresponse.Login_Response;
import monday.com.random.HomePage.HomeActivity;
import monday.com.random.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Saahil on 08/05/17.
 */

public class Login extends AppCompatActivity {

    @BindView(R.id.login)
    Button login;

    @BindView(R.id.pswd)
    EditText pswd;

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.loadingBar)
    ProgressBar bar;

    String e_mail,pass;
    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //preference editor
        shared_preferences = getApplicationContext().getSharedPreferences("Article_Pref", MODE_PRIVATE);
        shared_preferences_editor  = shared_preferences.edit();

        bar.setVisibility(View.INVISIBLE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().length() != 0 && pswd.getText().toString().length() != 0){
                    // login
                    bar.setVisibility(View.VISIBLE);
                    e_mail = email.getText().toString();
                    pass = pswd.getText().toString();
                    login();
                }else {
                    Toast.makeText(Login.this,"Email/Password cannot be left blank",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void login() {
        //--instantiate retrofit and add the custom okhttp client
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BASE_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoginAPI service = retrofit.create(LoginAPI.class);
        Call<Login_Response> call = service.login(e_mail,pass,"LOGIN","1");
        service.login(e_mail,pass,"LOGIN","1");


        call.enqueue(new Callback<Login_Response>() {
            @Override
            public void onResponse(Call<Login_Response> call, Response<Login_Response> response) {
                Login_Response data = response.body();
                bar.setVisibility(View.INVISIBLE);
                if(data != null){
                    try {
                        if(data.getStatus() == 1){
                            //success
                            Log.d("here","here");
                            Toast.makeText(Login.this,data.getMsg(),Toast.LENGTH_SHORT).show();
                            shared_preferences_editor.putString("name",data.getContent().getName());
                            shared_preferences_editor.putString("email",data.getContent().getEmail());
                            shared_preferences_editor.putString("uid",data.getContent().getUid());
                            shared_preferences_editor.commit();
                            Intent intent = new Intent(Login.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Log.d("here1","here1");
                            Toast.makeText(Login.this,data.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Login_Response> call, Throwable t) {
                bar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
