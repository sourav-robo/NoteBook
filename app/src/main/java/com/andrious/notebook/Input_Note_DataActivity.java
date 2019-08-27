package com.andrious.notebook;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class Input_Note_DataActivity extends AppCompatActivity {
    ExampleDBHelper db;

    TextView headdre;
    static TextView txt_date_set;
    ImageView back,img_datepick;
    EditText n_title;
    EditText n_text;
    String title,text,date;
    public static SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_note_data);

        if(Build.VERSION.SDK_INT>=21){
            Window window=this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.back));
        }
        db=new ExampleDBHelper(getApplicationContext());
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        headdre=(TextView) findViewById(R.id.txt_headder);

        headdre.setText("Daily UI Task");
        Typeface roboto = Typeface.createFromAsset(this.getAssets(),"Roboto-Regular.ttf"); //use this.getAssets if you are calling from an Activity
        headdre.setTypeface(roboto);



        back=(ImageView) findViewById(R.id.img_choose);
        back.setImageResource(R.drawable.left_arrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backintent = new Intent(Input_Note_DataActivity.this,MainActivity.class);
                startActivity(backintent);
                finish();
            }
        });

        n_title=(EditText) findViewById(R.id.title);
        n_text=(EditText) findViewById(R.id.text);
        n_title.setTypeface(roboto);
        n_text.setTypeface(roboto);


        txt_date_set=(TextView) findViewById(R.id.txt_date_set);
        txt_date_set.setTypeface(roboto);
        img_datepick=(ImageView) findViewById(R.id.img_datepick);
        img_datepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datepicker();
            }
        });



        Button clickButton = (Button) findViewById(R.id.clickButton);
        clickButton.setTypeface(roboto);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                title = n_title.getText().toString();
                text = n_text.getText().toString();
                date= txt_date_set.getText().toString();

                if(title.length() == 0){
                    SharedPreferences.Editor editor = pref.edit();

                    int idName = pref.getInt("name", 0);
                    idName++;
                    title="new document "+idName ;
                    editor.putInt("name",idName);
                    editor.commit();

                };

                if( text.length() == 0){
                    Toast.makeText(getApplicationContext(), "title or text box is empty !!!",
                            Toast.LENGTH_SHORT).show();
                }
                else if (date.length() == 0){
                    Toast.makeText(getApplicationContext(), "Select the date",
                            Toast.LENGTH_SHORT).show();

                }
                else
                {
                    db.insertPerson(title,text,date);
                    Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
                    finish();}
            }
        });
    }

    private void datepicker() {

        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener  {


        private String dat;

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it

            DatePickerDialog dialog=new DatePickerDialog(getActivity(), this, year, month, day);


            Calendar c1 = Calendar.getInstance();
            c1.add(Calendar.YEAR, 20);
            // c1.set(2000, 0, 1);//Year,Mounth -1,Day
            dialog.getDatePicker().setMaxDate(c1.getTimeInMillis());

            c1 = Calendar.getInstance();
            //  c1.add(Calendar.YEAR, -80);
            dialog.getDatePicker().setMinDate(c1.getTimeInMillis());

            return dialog;

        }

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {

            String years = "" + year;
            String months = "" + (month + 1);
            String days = "" + day;

            if (month >= 0 && month < 9) {
                months = "0" + (month + 1);
            }

            if (day > 0 && day < 10) {
                days = "0" + day;
            }
            //collday = years + "-" + months + "-" + days;
//            collday = days + "-" + months + "-" + years;
            dat = days + "-" + months + "-" + years;

            txt_date_set.setText(dat);




        }
    }

}

