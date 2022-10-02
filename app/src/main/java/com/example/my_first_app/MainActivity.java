package com.example.my_first_app;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private TextView name;
    private TextView lastName;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private Spinner spinner;

    private ArrayList<CheckBox> checkBoxesLanguages = new ArrayList<>();

    private Button rButtonYes;
    private Button rButtonNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.txt_name);
        lastName = findViewById(R.id.txt_lastname);


        //DP
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
        //SP
        spinner = findViewById(R.id.sp_gender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        rButtonYes = findViewById(R.id.rb_yes);
        rButtonNo = findViewById(R.id.rb_no);

        //CB
        gettingCheckboxes();



    }

    private void gettingCheckboxes(){
        checkBoxesLanguages.add(findViewById(R.id.cb_java));
        checkBoxesLanguages.add(findViewById(R.id.cb_cplus));
        checkBoxesLanguages.add(findViewById(R.id.cb_csharp));
        checkBoxesLanguages.add(findViewById(R.id.cb_goland));
        checkBoxesLanguages.add(findViewById(R.id.cb_js));
        checkBoxesLanguages.add(findViewById(R.id.cb_python));
        System.out.println(checkBoxesLanguages.get(1).getText());
    }

    //Datepicker extraido de https://github.com/codeWithCal/DatePickerTutorial.git
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }


    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }
    private String makeDateString(int day, int month, int year)
    {
        return month + " /" + day + " /" + year;
    }
    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }


}