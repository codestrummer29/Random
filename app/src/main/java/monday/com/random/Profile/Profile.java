package monday.com.random.Profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import monday.com.random.LoginRegister.LoginRoute;
import monday.com.random.R;

/**
 * Created by Saahil on 09/05/17.
 */

public class Profile extends AppCompatActivity {

    @BindView(R.id.tvEmail)
    TextView tv1;

    @BindView(R.id.tvName)
    TextView tv2;

    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        shared_preferences = getApplicationContext().getSharedPreferences("Article_Pref", MODE_PRIVATE);
        shared_preferences_editor  = shared_preferences.edit();

        tv1.setText(shared_preferences.getString("email", "0").toString());
        tv2.setText(shared_preferences.getString("name", "0").toString());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.logout){
            //Logout
            shared_preferences_editor.clear();
            shared_preferences_editor.commit();
            Intent intent = new Intent(getApplicationContext(), LoginRoute.class);
            startActivity(intent);
            finishAffinity();
        }
        return super.onOptionsItemSelected(item);
    }
}
