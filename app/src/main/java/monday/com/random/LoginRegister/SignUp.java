package monday.com.random.LoginRegister;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import monday.com.random.API.LoginAPI;
import monday.com.random.APIresponse.SignUpResponse;
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

public class SignUp extends AppCompatActivity{

    @BindView(R.id.name)
    EditText etName;

    @BindView(R.id.email)
    EditText etEmail;

    @BindView(R.id.pswd)
    EditText etPass;

    @BindView(R.id.signUP)
    Button butSign;

    @BindView(R.id.loadingBar)
    ProgressBar bar;

    String e_mail,pass,name;
    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        //preference editor
        shared_preferences = getApplicationContext().getSharedPreferences("Article_Pref", MODE_PRIVATE);
        shared_preferences_editor  = shared_preferences.edit();

        bar.setVisibility(View.INVISIBLE);

        butSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etName.getText().toString().trim().length() != 0 && etPass.getText().toString().trim().length() != 0 && etEmail.getText().toString().trim().length() != 0){
                    //sign up
                    e_mail = etEmail.getText().toString();
                    pass = etPass.getText().toString();
                    name = etName.getText().toString();
                    bar.setVisibility(View.VISIBLE);
                    signUp();
                }else {
                    Toast.makeText(getApplication(),"Name/Password/Email cannot be left empty",Toast.LENGTH_SHORT);
                }
            }
        });
    }

    public void signUp(){
        //--instantiate retrofit and add the custom okhttp client
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BASE_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoginAPI service = retrofit.create(LoginAPI.class);
        Call<SignUpResponse> call = service.signup(e_mail,pass,"REGISTER","1",name);

        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                bar.setVisibility(View.INVISIBLE);
                SignUpResponse data = response.body();
                if(data != null){
                    try {
                        if(data.getStatus() == 1){
                            Toast.makeText(SignUp.this,data.getMsg(),Toast.LENGTH_SHORT).show();
                            shared_preferences_editor.putString("name",name);
                            shared_preferences_editor.putString("email",e_mail);
                            shared_preferences_editor.commit();
                            Intent intent = new Intent(SignUp.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                            finish();
                        }else {
                            Toast.makeText(SignUp.this,data.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){

                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                bar.setVisibility(View.INVISIBLE);

            }
        });
    }
}
