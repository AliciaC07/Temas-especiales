package com.example.my_first_app;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
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

    private RadioButton rButtonYes;
    private RadioButton rButtonNo;

    private RadioGroup radioGroup;


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

        radioGroup = findViewById(R.id.radioGroup);
        rButtonYes = findViewById(R.id.rb_yes);
        rButtonNo = findViewById(R.id.rb_no);


        //CB
        gettingCheckboxes();



    }

    public void getInformation(View view){
        String message = "";
        StringBuilder languages= new StringBuilder();

        if (!verificationFields())return;

        if (rButtonNo.isChecked()){
            message = "Hola! Mi nombre es: "+name.getText().toString()+" "+lastName.getText().toString()+".\n\n Soy "+spinner.getSelectedItem().toString()+", y nací en la fecha:" +
                    "\n "+dateButton.getText().toString()+"\n\n No me gusta programar.";
        }else if (rButtonYes.isChecked()){
            for (CheckBox cb: checkBoxesLanguages
                 ) {
                if(cb.isChecked()){
                    languages.append(cb.getText().toString()).append(", ");
                }
            }
            languages.deleteCharAt(languages.length() -2);
            message = "Hola! Mi nombre es: "+name.getText().toString()+" "+lastName.getText().toString()+".\n\n Soy "+spinner.getSelectedItem().toString()+", y nací en la fecha:" +
                    "\n "+dateButton.getText().toString()+"\n\n Me gusta programar. Mis lenguajes favoritos son:\n"+languages;
        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra("Message", message);
        startActivity(intent);


    }

    public boolean verificationFields(){
        boolean verificationFailed = false;
        if (name.getText().toString().isEmpty() || lastName.getText().toString().isEmpty() || spinner.getSelectedItem().toString().equals("Select")){
            Toast.makeText(this, "Debe llenar todos los campos de información", Toast.LENGTH_LONG).show();
            return verificationFailed;
        }
        if (rButtonYes.isChecked()){
            for (CheckBox cb: checkBoxesLanguages) {
                if (cb.isChecked()){
                    return true;
                }
            }
            Toast.makeText(this, "Debe seleccionar al menos un lenguaje de Programación", Toast.LENGTH_LONG).show();
            return verificationFailed;

        }

        return true;
    }

    public void cleanInputs(View view){
        name.setText("");
        lastName.setText("");
        dateButton.setText(getTodaysDate());
        spinner.setSelection(0);
        radioGroup.clearCheck();
        radioGroup.check(R.id.rb_yes);
        for (CheckBox cb: checkBoxesLanguages) {
            cb.setChecked(false);
        }
        Toast.makeText(this, "Cleared", Toast.LENGTH_SHORT).show();

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