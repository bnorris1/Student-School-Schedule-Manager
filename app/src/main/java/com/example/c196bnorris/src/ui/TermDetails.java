package com.example.c196bnorris.src.ui;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.c196bnorris.R;
import com.example.c196bnorris.src.database.Repository;
import com.example.c196bnorris.src.entities.Term;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class TermDetails extends AppCompatActivity {
    private Repository repository;
    private TextView idtv;
    private int id;
    private EditText title;
    private EditText start;
    private EditText end;
    private DatePickerDialog.OnDateSetListener startDate;
    private DatePickerDialog.OnDateSetListener endDate;
    final Calendar calendarStart = Calendar.getInstance();
    final Calendar calendarEnd = Calendar.getInstance();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.term_details);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        repository= new Repository(getApplication());
        idtv = findViewById(R.id.termIDValue);
        idtv.setText(getIntent().getStringExtra("id"));
        id = Integer.parseInt(idtv.getText().toString());
        title = findViewById(R.id.termTitleValue);
        title.setText(getIntent().getStringExtra("title"));
        start = findViewById(R.id.termStartValue);
        start.setText(getIntent().getStringExtra("start"));
        end = findViewById(R.id.termEndValue);
        end.setText(getIntent().getStringExtra("end"));
        startDate = (view, year, month, day) -> {
            calendarStart.set(year,month,day);
            updateLabelText(1);};
        start.setOnClickListener(view -> new DatePickerDialog(
                TermDetails.this,
                startDate,
                calendarStart.get(Calendar.YEAR),
                calendarStart.get(Calendar.MONTH),
                calendarStart.get(Calendar.DAY_OF_MONTH)).show());
        endDate = (view, year, month, day) -> {
            calendarEnd.set(year,month,day);
            updateLabelText(2);};
        end.setOnClickListener(view -> new DatePickerDialog(
                TermDetails.this,
                endDate,
                calendarEnd.get(Calendar.YEAR),
                calendarEnd.get(Calendar.MONTH),
                calendarEnd.get(Calendar.DAY_OF_MONTH)).show());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home: this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void navToTermPage(View view) {finish();}
    public void updateTerm(View view) {
        Term term;
        if (title.getText().toString().isEmpty()) {
            Toast.makeText(TermDetails.this, "Please enter a title.", Toast.LENGTH_LONG).show(); }
        else if (start.getText().toString().isEmpty()) {
            Toast.makeText(TermDetails.this, "Please select a start date.", Toast.LENGTH_LONG).show(); }
        else if (end.getText().toString().isEmpty()) {
            Toast.makeText(TermDetails.this, "Please select an end date.", Toast.LENGTH_LONG).show(); }
        else if (calendarStart.compareTo(calendarEnd) > 0) {
            Toast.makeText(TermDetails.this, "End date cannot be before the start date.", Toast.LENGTH_LONG).show(); }
        else {
            term = new Term(id, title.getText().toString(), start.getText().toString(), end.getText().toString());
            repository.update(term);
            finish();
        }
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
}