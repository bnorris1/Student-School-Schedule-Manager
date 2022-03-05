package com.example.c196bnorris.src.ui;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.c196bnorris.R;
import com.example.c196bnorris.src.database.Repository;
import com.example.c196bnorris.src.entities.Course;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddCourse extends AppCompatActivity {
    private Repository repository;
    private EditText title;
    private Spinner status;
    private EditText start;
    private EditText end;
    private DatePickerDialog.OnDateSetListener startDate;
    private DatePickerDialog.OnDateSetListener endDate;
    private final Calendar calendarStart = Calendar.getInstance();
    private final Calendar calendarEnd = Calendar.getInstance();
    private EditText instructorName;
    private EditText instructorPhoneNumber;
    private EditText instructorEmail;
    private EditText note;
    private int termID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_course);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        repository= new Repository(getApplication());
        title = findViewById(R.id.courseTitleValue);
        start = findViewById(R.id.courseStartValue);
        end = findViewById(R.id.courseEndValue);
        instructorName = findViewById(R.id.instructorNameValue);
        instructorPhoneNumber = findViewById(R.id.instructorPhoneNumberValue);
        instructorEmail = findViewById(R.id.instructorEmailValue);
        note = findViewById(R.id.courseNoteValue);
        termID = getIntent().getIntExtra("termID",-1);

        startDate = (view, year, month, day) -> {
            calendarStart.set(year,month,day);
            updateLabelText(1); };

        start.setOnClickListener(view -> new DatePickerDialog(AddCourse.this, startDate,
                calendarStart.get(Calendar.YEAR),
                calendarStart.get(Calendar.MONTH),
                calendarStart.get(Calendar.DAY_OF_MONTH)).show());

        endDate = (view, year, month, day) -> {
            calendarEnd.set(year,month,day);
            updateLabelText(2); };

        end.setOnClickListener(view -> new DatePickerDialog(AddCourse.this, endDate,
                calendarEnd.get(Calendar.YEAR),
                calendarEnd.get(Calendar.MONTH),
                calendarEnd.get(Calendar.DAY_OF_MONTH)).show());

        status = findViewById(R.id.courseStatusValue);
        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(
                this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        status.setAdapter(adapter);
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
    public void saveCourse(View view) {
        Course course;
        if (title.getText().toString().isEmpty()) {
            Toast.makeText(AddCourse.this, "Please enter a title.", Toast.LENGTH_LONG).show(); }
        else if (status.getSelectedItemPosition()==0) {
            Toast.makeText(AddCourse.this, "Please select a status.", Toast.LENGTH_LONG).show(); }
        else if (start.getText().toString().isEmpty()) {
            Toast.makeText(AddCourse.this, "Please select a start date.", Toast.LENGTH_LONG).show(); }
        else if (end.getText().toString().isEmpty()) {
            Toast.makeText(AddCourse.this, "Please select an end date.", Toast.LENGTH_LONG).show(); }
        else if (instructorName.getText().toString().isEmpty()) {
            Toast.makeText(AddCourse.this, "Please enter an instructor name.", Toast.LENGTH_LONG).show(); }
        else if (instructorPhoneNumber.getText().toString().isEmpty()) {
            Toast.makeText(AddCourse.this, "Please enter an instructor phone.", Toast.LENGTH_LONG).show(); }
        else if (instructorEmail.getText().toString().isEmpty()) {
            Toast.makeText(AddCourse.this, "Please enter an instructor email.", Toast.LENGTH_LONG).show(); }
        else if (note.getText().toString().isEmpty()) {
            Toast.makeText(AddCourse.this, "Please enter a note for the class.", Toast.LENGTH_LONG).show(); }
        else if (calendarStart.compareTo(calendarEnd) > 0) {
            Toast.makeText(AddCourse.this, "End date cannot be before the start date.", Toast.LENGTH_LONG).show(); }
        else {
            int bookmark = repository.getAllCourses().size();
            if (bookmark != 0) {
                int id = repository.getAllCourses().get(bookmark - 1).getCourseID() + 1;
                course = new Course(id,
                        title.getText().toString(),
                        start.getText().toString(),
                        end.getText().toString(),
                        status.getSelectedItem().toString(),
                        instructorName.getText().toString(),
                        instructorPhoneNumber.getText().toString(),
                        instructorEmail.getText().toString(),
                        note.getText().toString(),
                        termID);
            }
            else{
                course = new Course(0,
                        title.getText().toString(),
                        start.getText().toString(),
                        end.getText().toString(),
                        status.getSelectedItem().toString(),
                        instructorName.getText().toString(),
                        instructorPhoneNumber.getText().toString(),
                        instructorEmail.getText().toString(),
                        note.getText().toString(),
                        termID);
            }
            repository.insert(course);
            finish();
        }
    }
}
