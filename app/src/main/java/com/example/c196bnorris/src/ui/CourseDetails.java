package com.example.c196bnorris.src.ui;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
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
import com.example.c196bnorris.src.entities.Course;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class CourseDetails extends AppCompatActivity {
    private Repository repository;

    private int id;
    private EditText title;
    private EditText start;
    private EditText end;
    private Spinner status;
    private EditText iname;
    private EditText iphone;
    private EditText iemail;
    private EditText note;
    private DatePickerDialog.OnDateSetListener startDate;
    private DatePickerDialog.OnDateSetListener endDate;
    private final Calendar calendarStart = Calendar.getInstance();
    private final Calendar calendarEnd = Calendar.getInstance();
    private int termID;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_details);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        repository= new Repository(getApplication());
        TextView idtv = findViewById(R.id.courseIDValue);
        idtv.setText(getIntent().getStringExtra("id"));
        id = Integer.parseInt(idtv.getText().toString());
        title = findViewById(R.id.courseTitleValue);
        title.setText(getIntent().getStringExtra("title"));
        start = findViewById(R.id.courseStartValue);
        start.setText(getIntent().getStringExtra("start"));
        end = findViewById(R.id.courseEndValue);
        end.setText(getIntent().getStringExtra("end"));
        iname = findViewById(R.id.instructorNameValue);
        iname.setText(getIntent().getStringExtra("iname"));
        iphone = findViewById(R.id.instructorPhoneNumberValue);
        iphone.setText(getIntent().getStringExtra("iphone"));
        iemail = findViewById(R.id.instructorEmailValue);
        iemail.setText(getIntent().getStringExtra("iemail"));
        note = findViewById(R.id.courseNoteValue);
        note.setText(getIntent().getStringExtra("note"));
        termID = getIntent().getIntExtra("termID",-1);
        startDate = (view, year, month, day) -> {
            calendarStart.set(year,month,day);
            updateLabelText(1);};
        start.setOnClickListener(view -> new DatePickerDialog(CourseDetails.this, startDate,
                calendarStart.get(Calendar.YEAR),
                calendarStart.get(Calendar.MONTH),
                calendarStart.get(Calendar.DAY_OF_MONTH)).show());
        endDate = (view, year, month, day) -> {
            calendarEnd.set(year,month,day);
            updateLabelText(2);};
        end.setOnClickListener(view -> new DatePickerDialog(CourseDetails.this, endDate,
                calendarEnd.get(Calendar.YEAR),
                calendarEnd.get(Calendar.MONTH),
                calendarEnd.get(Calendar.DAY_OF_MONTH)).show());
        status = findViewById(R.id.courseStatusValue);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        status.setAdapter(adapter);
        status.setSelection(spinnerNameToIndex(getIntent().getStringExtra("status")));
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
    private int spinnerNameToIndex(String name){
        int index = 0;
        switch (name){
            case "In Progress":
                index = 1;
                break;
            case "Completed":
                index = 2;
                break;
            case "Dropped":
                index = 3;
                break;
            case "Plan to take":
                index = 4;
                break;
        }
        return index;
    }
    public void updateCourse(View view) {
        Course course;
        if (title.getText().toString().isEmpty()) {
            Toast.makeText(CourseDetails.this, "Please enter a title.", Toast.LENGTH_LONG).show(); }
        else if (start.getText().toString().isEmpty()) {
            Toast.makeText(CourseDetails.this, "Please select a start date.", Toast.LENGTH_LONG).show(); }
        else if (end.getText().toString().isEmpty()) {
            Toast.makeText(CourseDetails.this, "Please select an end date.", Toast.LENGTH_LONG).show(); }
        else if (calendarStart.compareTo(calendarEnd) > 0) {
            Toast.makeText(CourseDetails.this, "End date cannot be before the start date.", Toast.LENGTH_LONG).show(); }
        else if (status.getSelectedItemPosition()==0) {
            Toast.makeText(CourseDetails.this, "Please enter a status.", Toast.LENGTH_LONG).show(); }
        else if (iname.getText().toString().isEmpty()) {
            Toast.makeText(CourseDetails.this, "Please select an instructor name.", Toast.LENGTH_LONG).show(); }
        else if (iphone.getText().toString().isEmpty()) {
            Toast.makeText(CourseDetails.this, "Please select an instructor name.", Toast.LENGTH_LONG).show(); }
        else if (iemail.getText().toString().isEmpty()) {
            Toast.makeText(CourseDetails.this, "Please enter an instructor name.", Toast.LENGTH_LONG).show(); }
        else {
            course = new Course(
                    id,
                    title.getText().toString(),
                    start.getText().toString(),
                    end.getText().toString(),
                    status.getSelectedItem().toString(),
                    iname.getText().toString(),
                    iphone.getText().toString(),
                    iemail.getText().toString(),
                    note.getText().toString(),
                    termID);
            repository.update(course);
            finish();
        }
    }
    public void navToCoursePage(View view) {finish();}
}
