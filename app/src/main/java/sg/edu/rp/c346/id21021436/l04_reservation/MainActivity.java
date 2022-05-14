package sg.edu.rp.c346.id21021436.l04_reservation;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    //define field variables

    EditText nameField;
    EditText phoneField;
    EditText groupSizeField;
    DatePicker dp;
    TimePicker tp;
    CheckBox smoke;
    Button reserve;
    Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //link variables to view elements

        nameField = findViewById(R.id.name_editText);
        phoneField = findViewById(R.id.phNumber_editText);
        groupSizeField = findViewById(R.id.size_editText);
        dp = findViewById(R.id.date_pick);
        tp = findViewById(R.id.time_pick);
        smoke = findViewById(R.id.smoking_Checkbox);
        reserve = findViewById(R.id.submit_button);
        reset = findViewById(R.id.reset_button);


        //default date and time set in view

        dp.updateDate(2020, 5, 1);
        tp.setCurrentHour(19);
        tp.setCurrentMinute(30);

        //block the user from choosing times before 0800 and after 2059 and reset timepicker to the earliest/latest permissible times, as appropriate

        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                int hourCheck = tp.getCurrentHour();


                if (hourCheck < 8){
                    tp.setCurrentHour(8);
                    tp.setCurrentMinute(0);
                }
                else if (hourCheck >= 21){
                    tp.setCurrentHour(20);
                    tp.setCurrentMinute(59);
                }

            }
        });

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = nameField.getText().toString().trim();
                String phone = phoneField.getText().toString().trim();
                String groupSize = groupSizeField.getText().toString().trim();

                //check for null inputs and display a toast to complain if any required text fields are left empty

                if (name.equals("")||phone.equals("")||groupSize.equals("")){
                    Toast.makeText(MainActivity.this, "One or more required fields not filled!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String smoking_Status = getString(R.string.non_Smoking);
                if (smoke.isChecked()) smoking_Status = getString(R.string.Smoking);

                //months in getMonth method are indexed from 0

                int numericMonth = dp.getMonth()+1;
                String month = null;

                //to get English names for months

                switch(numericMonth){
                    case 1: month = getString(R.string.jan);
                        break;
                    case 2: month = getString(R.string.feb);
                        break;
                    case 3: month = getString(R.string.mar);
                        break;
                    case 4: month = getString(R.string.apr);
                        break;
                    case 5: month = getString(R.string.may);
                        break;
                    case 6: month = getString(R.string.jun);
                        break;
                    case 7: month = getString(R.string.jul);
                        break;
                    case 8: month = getString(R.string.aug);
                        break;
                    case 9: month = getString(R.string.sep);
                        break;
                    case 10: month = getString(R.string.oct);
                        break;
                    case 11: month = getString(R.string.nov);
                        break;
                    case 12: month = getString(R.string.dec);
                        break;
                }

                //hourTwentyFour captures the 24-hour clock hour from the timepicker, while hour is the 12-hour format. Modulo, integer division operations used to get 12-hour format and extract AM/PM information

                int hourTwentyFour;
                int hour = 12;
                String timeOfDay = getString(R.string.day_code);
                int numtimeOfDay;
                hourTwentyFour = tp.getCurrentHour();
                if (hourTwentyFour != 12) hour = (hourTwentyFour % 12);
                numtimeOfDay = (hourTwentyFour/12);
                if (numtimeOfDay == 1) timeOfDay = "@string/night_code";

                int min = tp.getCurrentMinute();
                String minute = String.valueOf(min);

                //to make the display of single digit minutes nicer and more standardised

                if (Integer.parseInt(minute)<10) minute = "0" + minute;

                int day = dp.getDayOfMonth();
                int year = dp.getYear();

                //Using SimpleDateFormat and Date from imported Java CLasses to handle system date and time capture

                SimpleDateFormat yearSDF = new SimpleDateFormat("y");
                int actualYear = Integer.parseInt(yearSDF.format(new Date()));

                SimpleDateFormat monthSDF = new SimpleDateFormat("M");
                int actualMonth = Integer.parseInt(monthSDF.format(new Date()));

                SimpleDateFormat dateSDF = new SimpleDateFormat("d");
                int actualDate = Integer.parseInt(dateSDF.format(new Date()));

                SimpleDateFormat hourSDF = new SimpleDateFormat("H");
                int actualHour = Integer.parseInt(hourSDF.format(new Date()));

                SimpleDateFormat minuteSDF = new SimpleDateFormat("m");
                int actualMinute = Integer.parseInt(minuteSDF.format(new Date()));

                //the following logic checks if the date and time are "OK" in the sense of the reservation date/time being in the future. Checking proceeds from year down to minute in order of decreasing significance

                boolean dateTimeOK = false;

                if (actualYear > year) dateTimeOK = false;
                else if (actualYear < year) dateTimeOK = true;
                else {
                    if (actualMonth > numericMonth) dateTimeOK = false;
                    else if (actualMonth < numericMonth) dateTimeOK = true;
                    else {
                        if (actualDate > day) dateTimeOK = false;
                        else if (actualDate < day) dateTimeOK = true;
                        else{
                            if (actualHour > hourTwentyFour) dateTimeOK = false;
                            else if (actualHour < hourTwentyFour) dateTimeOK = true;
                            else{
                                if (actualMinute >= min) dateTimeOK = false;
                                else if (actualMinute < min) dateTimeOK = true;

                            }
                        }
                    }
                }

                //only output successful reservation message if the date and time are OK and if not, output an unsuccessful reservation message

                if (dateTimeOK) {
                    String toastOut1 = "Dear " + name + " (" + phone + ")" + " your reservation for " + groupSize + "...";
                    Toast.makeText(MainActivity.this, toastOut1, Toast.LENGTH_LONG).show();
                    String toastOut2 = "...has been made on " + day + " " + month + " " + year + " at " + hour + ":" + minute + " " + timeOfDay + " in the " + smoking_Status + " area.";
                    Toast.makeText(MainActivity.this, toastOut2, Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(MainActivity.this, R.string.wrong_reservation_time, Toast.LENGTH_SHORT).show();
                }

            }
        });

        //resets the fields and the date and time spinners back to default

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameField.setText(null);
                phoneField.setText(null);
                groupSizeField.setText(null);
                dp.updateDate(2020, 5, 1);
                tp.setCurrentHour(19);
                tp.setCurrentMinute(30);

            }
        });
    }
}