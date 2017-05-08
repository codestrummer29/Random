package monday.com.random.ArticleCreator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.irshulx.Editor;
import com.github.irshulx.models.EditorTextStyle;

import java.io.IOException;

import monday.com.random.API.ArticleCreateApi;
import monday.com.random.APIresponse.ArticleCreateResponse;
import monday.com.random.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Saahil on 09/05/17.
 */

public class ArticleCreator extends AppCompatActivity {
    Editor editor;
    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;
    String desc,uid,title,topic;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_edit);
        shared_preferences = getApplicationContext().getSharedPreferences("Article_Pref", MODE_PRIVATE);
        uid = shared_preferences.getString("uid","0");
        editor = (Editor) findViewById(R.id.editor);
        CreateEditor();
    }

        private void CreateEditor(){
            findViewById(R.id.action_h1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.UpdateTextStyle(EditorTextStyle.H1);
                }
            });

            findViewById(R.id.action_h2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.UpdateTextStyle(EditorTextStyle.H2);
                }
            });

            findViewById(R.id.action_h3).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.UpdateTextStyle(EditorTextStyle.H3);
                }
            });

            findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.UpdateTextStyle(EditorTextStyle.BOLD);
                }
            });

            findViewById(R.id.action_Italic).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.UpdateTextStyle(EditorTextStyle.ITALIC);
                }
            });

            findViewById(R.id.action_indent).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.UpdateTextStyle(EditorTextStyle.INDENT);
                }
            });

            findViewById(R.id.action_outdent).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.UpdateTextStyle(EditorTextStyle.OUTDENT);
                }
            });

            findViewById(R.id.action_bulleted).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.InsertList(false);
                }
            });

            findViewById(R.id.action_unordered_numbered).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.InsertList(true);
                }
            });

            findViewById(R.id.action_hr).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.InsertDivider();
                }
            });

//            findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    editor.OpenImagePicker();
//                }
//            });

            findViewById(R.id.action_insert_link).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.insertLink();
                }
            });

            findViewById(R.id.action_erase).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.clearAllContents();
                }
            });

            //  editor.dividerBackground=R.drawable.divider_background_dark;
           // editor.setImageUploaderUri("http://192.168.43.239/Laser-Editor-WebApi/api/ImageUploaderApi/PostImage");
            editor.setFontFace(R.string.fontFamily__serif);
            editor.setDividerLayout(R.layout.tmpl_divider_layout);
            editor.setEditorImageLayout(R.layout.tmpl_image_view);
            editor.setListItemLayout(R.layout.tmpl_list_item);

            editor.Render();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.authoring_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.draft){
            //draft
            String x = editor.getContentAsHTML();
            Log.d("contentd",x);
        }else if(id == R.id.publish){
            //publish
            String x = editor.getContentAsHTML();
            Log.d("contentp",x);
            desc = x;
            showAlert();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == editor.PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK&& data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                editor.InsertImage(bitmap);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            //Write your code if there's no result
            Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            // editor.RestoreState();
        }
    }

    public void showAlert(){
        LayoutInflater factory = LayoutInflater.from(this);
        final View textEntryView = factory.inflate(R.layout.view_edit_text, null);
        final EditText input1 = (EditText) textEntryView.findViewById(R.id.et1);
        final EditText input2 = (EditText) textEntryView.findViewById(R.id.et2);
        input1.setText("", TextView.BufferType.EDITABLE);
        input2.setText("", TextView.BufferType.EDITABLE);
        input1.setHint("Enter Article Title");
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Enter the Text:")
                .setView(textEntryView)
                .setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Log.i("AlertDialog","TextEntry 1 Entered "+input1.getText().toString());
                                Log.i("AlertDialog","TextEntry 2 Entered "+input2.getText().toString());
                                title = input1.getText().toString();
                                topic = input2.getText().toString();
                                saveArticle();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                            }
                        });
        alert.show();
    }

    public void saveArticle(){
        //--instantiate retrofit and add the custom okhttp client
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BASE_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ArticleCreateApi service = retrofit.create(ArticleCreateApi.class);
        Call<ArticleCreateResponse> call = service.saveArticle(uid,"2",title,topic,desc);

        call.enqueue(new Callback<ArticleCreateResponse>() {
            @Override
            public void onResponse(Call<ArticleCreateResponse> call, Response<ArticleCreateResponse> response) {
                ArticleCreateResponse data = response.body();
                if(data != null){
                    if(data.getStatus() == 1){
                        Toast.makeText(getApplicationContext(),data.getMsg(),Toast.LENGTH_SHORT).show();
                        editor.clearAllContents();
                    }else{
                        Toast.makeText(getApplicationContext(),data.getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArticleCreateResponse> call, Throwable t) {

            }
        });
    }
}
