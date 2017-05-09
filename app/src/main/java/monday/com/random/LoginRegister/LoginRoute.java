package monday.com.random.LoginRegister;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import monday.com.random.HomePage.HomeActivity;
import monday.com.random.R;

/**
 * Created by Saahil on 08/05/17.
 */

public class LoginRoute extends AppCompatActivity {

    @BindView(R.id.but_login)
    Button login;

    @BindView(R.id.but_signup)
    Button signup;

    SharedPreferences shared_preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_router);
        ButterKnife.bind(this);

        shared_preferences = getApplicationContext().getSharedPreferences("Article_Pref", MODE_PRIVATE);
        if(shared_preferences.getString("uid","0") != "0"){
            Intent intent = new Intent(LoginRoute.this,HomeActivity.class);
            startActivity(intent);
            finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginRoute.this,Login.class);
                startActivity(intent);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginRoute.this,SignUp.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
