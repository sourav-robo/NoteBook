package com.andrious.notebook;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class NoteViewActivity extends AppCompatActivity {
    ExampleDBHelper mydb=new ExampleDBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_view);
        if(Build.VERSION.SDK_INT>=21){
            Window window=this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }
        TextView headdre=(TextView) findViewById(R.id.txt_headder);
        Typeface roboto = Typeface.createFromAsset(this.getAssets(),"Roboto-Regular.ttf"); //use this.getAssets if you are calling from an Activity
        headdre.setTypeface(roboto);

       final EditText titleview=(EditText) findViewById(R.id.title_view);
       final EditText textview=(EditText)findViewById(R.id.text_view);
        final EditText dateview=(EditText)findViewById(R.id.date_view);
        final String sub_id = getIntent().getStringExtra("id");
        String title=getIntent().getStringExtra("title");
        String text=getIntent().getStringExtra("text");
        String date=getIntent().getStringExtra("date");
        titleview.setText(title);
        titleview.setTypeface(roboto);
        headdre.setText("Task Details");
        headdre.setTextSize(15);
        textview.setText(text);
        dateview.setText(date);
        Button update=(Button) findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=titleview.getText().toString().trim();
                String text=textview.getText().toString().trim();
                String date=dateview.getText().toString().trim();
                    Log.d("progress","msg "+title+"---"+text+"----"+date+"-----"+sub_id);
               Boolean res=mydb.updateNote(sub_id,title,text,date);

                if(res==true){
                    Toast.makeText(getApplicationContext(),"Data Inserted",Toast.LENGTH_SHORT).show();
                }


                else
                    Toast.makeText(getApplicationContext(),"Something went wrong....Data not inserted",Toast.LENGTH_SHORT).show();

                Intent i1=new Intent(NoteViewActivity.this,MainActivity.class);
                startActivity(i1);

            }
        });


    }
}