package com.example.c196bnorris.src.ui;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.c196bnorris.R;
import com.example.c196bnorris.src.database.Repository;
import com.example.c196bnorris.src.entities.Assessment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AssessmentDetails extends AppCompatActivity {
    private Repository repository;
    private TextView assessmentID;
    private Spinner assessmentType;
    private EditText assessmentTitle;
    private EditText assessmentStart;
    private EditText assessmentEnd;
    private int courseIDint;
    private int assessmentIDint;
    private DatePickerDialog.OnDateSetListener startDate;
    private DatePickerDialog.OnDateSetListener endDate;
    private final Calendar calendarStart = Calendar.getInstance();
    private final Calendar calendarEnd = Calendar.getInstance();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessment_details);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        repository= new Repository(getApplication());
        assessmentID = findViewById(R.id.assessmentIDValue);
        assessmentID.setText(Integer.toString(getIntent().getIntExtra("id",-1)));
        assessmentIDint = Integer.parseInt(assessmentID.getText().toString());
        assessmentTitle = findViewById(R.id.assessmentTitleValue);
        assessmentTitle.setText(getIntent().getStringExtra("title"));
        assessmentStart= findViewById(R.id.assessmentStartValue);
        assessmentStart.setText(getIntent().getStringExtra("start"));
        assessmentEnd = findViewById(R.id.assessmentEndValue);
        assessmentEnd.setText(getIntent().getStringExtra("end"));
        courseIDint = getIntent().getIntExtra("courseID",-1);
        startDate = (view, year, month, day) -> {
            calendarStart.set(year,month,day);
            updateLabelText(1);};
        assessmentStart.setOnClickListener(view -> new DatePickerDialog(AssessmentDetails.this, startDate,
                calendarStart.get(Calendar.YEAR),
                calendarStart.get(Calendar.MONTH),
                calendarStart.get(Calendar.DAY_OF_MONTH)).show());
        endDate = (view, year, month, day) -> {
            calendarEnd.set(year,month,day);
            updateLabelText(2);};
        assessmentEnd.setOnClickListener(view -> new DatePickerDialog(AssessmentDetails.this, endDate,
                calendarEnd.get(Calendar.YEAR),
                calendarEnd.get(Calendar.MONTH),
                calendarEnd.get(Calendar.DAY_OF_MONTH)).show());
        assessmentType = findViewById(R.id.assessmentTypeValue);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        assessmentType.setAdapter(adapter);
        assessmentType.setSelection(spinnerTypeToIndex(getIntent().getStringExtra("type")));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home: this.finish();
                return true;

            case R.id.notify:
                String assessmentTitleStr = assessmentTitle.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);

                String startDateStr = assessmentStart.getText().toString();
                String endDateStr = assessmentEnd.getText().toString();

                Date startDate = null;
                Date endDate = null;

                try {startDate = sdf.parse(startDateStr);}
                catch (ParseException e) {e.printStackTrace();}
                try {endDate = sdf.parse(endDateStr);}
                catch (ParseException e) {e.printStackTrace();}

                Long startDateTrigger = startDate.getTime();
                Long endDateTrigger = endDate.getTime();

                Intent startIntent = new Intent(AssessmentDetails.this,MyReceiver.class);
                Intent endIntent = new Intent(AssessmentDetails.this,MyReceiver.class);

                startIntent.putExtra("a","Assessment '"+assessmentTitleStr+"' starts today:"+startDateStr);
                endIntent.putExtra("a","Assessment '"+assessmentTitleStr+"' ends today:"+endDateStr);

                PendingIntent pendingStartIntent = PendingIntent.getBroadcast(AssessmentDetails.this, ++MainActivity.staticNumber,startIntent,PendingIntent.FLAG_IMMUTABLE);
                PendingIntent pendingEndIntent = PendingIntent.getBroadcast(AssessmentDetails.this, ++MainActivity.staticNumber,endIntent,PendingIntent.FLAG_IMMUTABLE);

                AlarmManager startAlarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                AlarmManager endAlarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

                startAlarmManager.set(AlarmManager.RTC_WAKEUP, startDateTrigger, pendingStartIntent);
                endAlarmManager.set(AlarmManager.RTC_WAKEUP, endDateTrigger, pendingEndIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menualt,menu);
        return true;
    }
    private void updateLabelText(int i) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
        switch (i){
            case 1: assessmentStart.setText(sdf.format(calendarStart.getTime()));
                break;
            case 2: assessmentEnd.setText(sdf.format(calendarEnd.getTime()));
                break;
        }
    }
    public void deleteAssessment(View view) {
        for(Assessment assessment : repository.getAllAssessments()){
            if(assessment.getAssessmentID()==assessmentIDint) {
                repository.delete(assessment);
                finish();
            }
        }
    }
    private int spinnerTypeToIndex(String name){
        int index = 0;
        switch (name){
            case "Performance":
                index = 1;
                break;
            case "Objective":
                index = 2;
                break;
        }
        return index;
    }
    public void updateAssessment(View view) {
        Assessment assessment;
        if (assessmentType.getSelectedItemPosition()==0) {
            Toast.makeText(AssessmentDetails.this, "Please select a type.", Toast.LENGTH_LONG).show(); }
        else if (assessmentTitle.getText().toString().isEmpty()) {
            Toast.makeText(AssessmentDetails.this, "Please enter a title.", Toast.LENGTH_LONG).show(); }
        else if (assessmentStart.getText().toString().isEmpty()) {
            Toast.makeText(AssessmentDetails.this, "Please select a start date.", Toast.LENGTH_LONG).show(); }
        else if (assessmentEnd.getText().toString().isEmpty()) {
            Toast.makeText(AssessmentDetails.this, "Please select an end date.", Toast.LENGTH_LONG).show(); }
        else if (calendarStart.compareTo(calendarEnd) > 0) {
            Toast.makeText(AssessmentDetails.this, "End date cannot be before the start date.", Toast.LENGTH_LONG).show(); }
        else {
            assessment = new Assessment(
                    assessmentIDint,
                    assessmentType.getSelectedItem().toString(),
                    assessmentTitle.getText().toString(),
                    assessmentStart.getText().toString(),
                    assessmentEnd.getText().toString(),
                    courseIDint);
                    repository.update(assessment);
                    finish();
        }
    }
}
