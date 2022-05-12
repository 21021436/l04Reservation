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


public class MainActivity extends AppCompatActivity {

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



        nameField = findViewById(R.id.name_editText);
        phoneField = findViewById(R.id.phNumber_editText);
        groupSizeField = findViewById(R.id.size_editText);
        dp = findViewById(R.id.date_pick);
        tp = findViewById(R.id.time_pick);
        smoke = findViewById(R.id.smoking_Checkbox);
        reserve = findViewById(R.id.submit_button);
        reset = findViewById(R.id.reset_button);



        dp.updateDate(2020, 5, 1);
        tp.setCurrentHour(19);
        tp.setCurrentMinute(30);





        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = nameField.getText().toString().trim();
                String phone = phoneField.getText().toString().trim();
                String groupSize = groupSizeField.getText().toString().trim();
                String smoking = "Non-Smoking";
                if (smoke.isChecked()) smoking = "Smoking";

                int numericMonth = dp.getMonth()+1;
                String month = null;


                switch(numericMonth){
                    case 1: month = "January";
                        break;
                    case 2: month = "February";
                        break;
                    case 3: month = "March";
                        break;
                    case 4: month = "April";
                        break;
                    case 5: month = "May";
                        break;
                    case 6: month = "June";
                        break;
                    case 7: month = "July";
                        break;
                    case 8: month = "August";
                        break;
                    case 9: month = "September";
                        break;
                    case 10: month = "October";
                        break;
                    case 11: month = "November";
                        break;
                    case 12: month = "December";
                        break;
                }

                int hour;
                String timeOfDay = "AM";
                int numtimeOfDay;
                hour = tp.getCurrentHour();
                if (hour != 12) hour = (tp.getCurrentHour() % 12);
                numtimeOfDay = (tp.getCurrentHour()/12);
                if (numtimeOfDay == 1) timeOfDay = "PM";

                int day = dp.getDayOfMonth();
                int year = dp.getYear();

                String toastOut1 = "Dear " + name + " (" + phone + ")" + " your reservation for " + groupSize +"...";
                Toast.makeText(MainActivity.this,toastOut1,Toast.LENGTH_LONG).show();
                String toastOut2 = "...has been made on " + day + " " + month + " " + year + " at " + hour + ":" + tp.getCurrentMinute() + " " + timeOfDay + " in the " + smoking + " area.";
                Toast.makeText(MainActivity.this,toastOut2,Toast.LENGTH_LONG).show();

            }
        });

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