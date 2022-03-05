package com.example.c196bnorris.src.ui;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.c196bnorris.R;
import com.example.c196bnorris.src.database.Repository;
import com.example.c196bnorris.src.entities.Assessment;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AddAssessment extends AppCompatActivity {
    private Repository repository;
    private Spinner type;
    private EditText title;
    private EditText start;
    private EditText end;
    private DatePickerDialog.OnDateSetListener startDate;
    private DatePickerDialog.OnDateSetListener endDate;
    private final Calendar calendarStart = Calendar.getInstance();
    private final Calendar calendarEnd = Calendar.getInstance();
    private int courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_assessment);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        repository= new Repository(getApplication());
        title = findViewById(R.id.assessmentTitleValue);
        start = findViewById(R.id.assessmentStartValue);
        end = findViewById(R.id.assessmentEndValue);
        courseID = getIntent().getIntExtra("courseID",-1);

        startDate = (view, year, month, day) -> {
            calendarStart.set(year,month,day);
            updateLabelText(1); };

        start.setOnClickListener(view -> new DatePickerDialog(com.example.c196bnorris.src.ui.AddAssessment.this, startDate,
                calendarStart.get(Calendar.YEAR),
                calendarStart.get(Calendar.MONTH),
                calendarStart.get(Calendar.DAY_OF_MONTH)).show());

        endDate = (view, year, month, day) -> {
            calendarEnd.set(year,month,day);
            updateLabelText(2); };

        end.setOnClickListener(view -> new DatePickerDialog(com.example.c196bnorris.src.ui.AddAssessment.this, endDate,
                calendarEnd.get(Calendar.YEAR),
                calendarEnd.get(Calendar.MONTH),
                calendarEnd.get(Calendar.DAY_OF_MONTH)).show());

        type = findViewById(R.id.assessmentTypeValue);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        type.setAdapter(adapter);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home: this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void updateLabelText(int i) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
        switch (i){
            case 1: start.setText(sdf.format(calendarStart.getTime()));
                break;
            case 2: end.setText(sdf.format(calendarEnd.getTime()));
                break;
        }
    }
    public void saveAssessment(View view) {
        Assessment assessment;
        if (type.getSelectedItemPosition()==0) {
            Toast.makeText(com.example.c196bnorris.src.ui.AddAssessment.this, "Please select a type.", Toast.LENGTH_LONG).show(); }
        else if (title.getText().toString().isEmpty()) {
            Toast.makeText(com.example.c196bnorris.src.ui.AddAssessment.this, "Please enter a title.", Toast.LENGTH_LONG).show(); }
        else if (start.getText().toString().isEmpty()) {
            Toast.makeText(com.example.c196bnorris.src.ui.AddAssessment.this, "Please select a start date.", Toast.LENGTH_LONG).show(); }
        else if (end.getText().toString().isEmpty()) {
            Toast.makeText(com.example.c196bnorris.src.ui.AddAssessment.this, "Please select an end date.", Toast.LENGTH_LONG).show(); }
        else if (calendarStart.compareTo(calendarEnd) > 0) {
            Toast.makeText(com.example.c196bnorris.src.ui.AddAssessment.this, "End date cannot be before the start date.", Toast.LENGTH_LONG).show(); }
        else {
            int bookmark = repository.getAllAssessments().size();
            if (bookmark != 0) {
                int id = repository.getAllAssessments().get(bookmark - 1).getAssessmentID() + 1;
                assessment = new Assessment(
                        id,
                        type.getSelectedItem().toString(),
                        title.getText().toString(),
                        start.getText().toString(),
                        end.getText().toString(),
                        courseID);
            }
            else{
                assessment = new Assessment(
                        0,
                        type.getSelectedItem().toString(),
                        title.getText().toString(),
                        start.getText().toString(),
                        end.getText().toString(),
                        courseID);
            }
            repository.insert(assessment);
            finish();
        }
    }
}