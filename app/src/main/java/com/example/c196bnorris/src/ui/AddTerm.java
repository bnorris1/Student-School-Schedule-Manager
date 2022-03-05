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
import com.example.c196bnorris.src.entities.Term;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AddTerm extends AppCompatActivity {
    private Repository repository;
    private EditText title;
    private EditText start;
    private EditText end;
    private DatePickerDialog.OnDateSetListener startDate;
    private DatePickerDialog.OnDateSetListener endDate;
    private final Calendar calendarStart = Calendar.getInstance();
    private final Calendar calendarEnd = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_term);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        repository= new Repository(getApplication());
        title = findViewById(R.id.termTitleValue);
        start = findViewById(R.id.termStartValue);
        end = findViewById(R.id.termEndValue);

        startDate = (view, year, month, day) -> {
            calendarStart.set(year,month,day);
            updateLabelText(1);};

        start.setOnClickListener(view -> new DatePickerDialog(AddTerm.this, startDate,
                calendarStart.get(Calendar.YEAR),
                calendarStart.get(Calendar.MONTH),
                calendarStart.get(Calendar.DAY_OF_MONTH)).show()
        );

        endDate = (view, year, month, day) -> {
            calendarEnd.set(year,month,day);
            updateLabelText(2);};

        end.setOnClickListener(view -> new DatePickerDialog(AddTerm.this, endDate,
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
    private void updateLabelText(int i) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
        switch (i){
            case 1: start.setText(sdf.format(calendarStart.getTime()));
                break;
            case 2: end.setText(sdf.format(calendarEnd.getTime()));
                break;
        }
    }
    public void saveTerm(View view) {
        Term term;
        if (title.getText().toString().isEmpty()) {
            Toast.makeText(AddTerm.this, "Please enter a title.", Toast.LENGTH_LONG).show(); }
        else if (start.getText().toString().isEmpty()) {
            Toast.makeText(AddTerm.this, "Please select a start date.", Toast.LENGTH_LONG).show(); }
        else if (end.getText().toString().isEmpty()) {
            Toast.makeText(AddTerm.this, "Please select an end date.", Toast.LENGTH_LONG).show(); }
        else if (calendarStart.compareTo(calendarEnd) > 0) {
            Toast.makeText(AddTerm.this, "End date cannot be before the start date.", Toast.LENGTH_LONG).show(); }
        else {
            int bookmark = repository.getAllTerms().size();
            if (bookmark != 0) {
                int id = repository.getAllTerms().get(bookmark - 1).getTermID() + 1;
                term = new Term(id, title.getText().toString(), start.getText().toString(), end.getText().toString());
            }
            else{
                term = new Term(0, title.getText().toString(), start.getText().toString(), end.getText().toString());
            }
            repository.insert(term);
            finish();
            }
        }
}

