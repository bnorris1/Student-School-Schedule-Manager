package com.example.c196bnorris.src.ui;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.c196bnorris.R;
import com.example.c196bnorris.src.database.Repository;
import com.example.c196bnorris.src.entities.Course;
import com.example.c196bnorris.src.entities.Term;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings({"unused", "SwitchStatementWithTooFewBranches", "FieldCanBeLocal"})
public class TermPage extends AppCompatActivity {
    private Repository repository;
    private TextView idBox;
    private int termID;
    private TextView titleBox;
    private TextView startBox;
    private TextView endBox;
    public static RecyclerView recyclerView;
    private ArrayList<Course> theseCourses = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.term_page);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        repository= new Repository(getApplication());
        idBox = findViewById(R.id.termIDValue);
        idBox.setText(Integer.toString(getIntent().getIntExtra("id",-1)));
        termID = Integer.parseInt(idBox.getText().toString());
        titleBox = findViewById(R.id.termTitleValue);
        titleBox.setText(getIntent().getStringExtra("title"));
        startBox = findViewById(R.id.termStartValue);
        startBox.setText(getIntent().getStringExtra("start"));
        endBox = findViewById(R.id.termEndValue);
        endBox.setText(getIntent().getStringExtra("end"));
        List<Course> allCourses = repository.getAllCourses();
        for (Course course : allCourses){
            if (course.getTermID()==termID){theseCourses.add(course);}
        }
        recyclerView = findViewById(R.id.coursesInTermList);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourses(theseCourses);
    }

    @Override
    public void onRestart() {
        //resets term info
        super.onRestart();
        List<Term> allTerms = repository.getAllTerms();
        Term updatedTerm = null;
        for (Term term : allTerms){
            if (term.getTermID()==termID){
                updatedTerm = term;
            }
        }
        idBox.setText(String.valueOf(updatedTerm.getTermID()));
        titleBox.setText(updatedTerm.getTitle());
        startBox.setText(updatedTerm.getStartDate());
        endBox.setText(updatedTerm.getEndDate());

        //resets the course list for the term
        theseCourses.clear();
        List<Course> allCourses = repository.getAllCourses();
        for (Course course : allCourses){
            if (course.getTermID()==termID){theseCourses.add(course);}
        }
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourses(theseCourses);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home: this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void navToTermDetailsPage(View view) {
        Intent intent = new Intent(TermPage.this,TermDetails.class);
        intent.putExtra("id",idBox.getText().toString());
        intent.putExtra("title",titleBox.getText().toString());
        intent.putExtra("start",startBox.getText().toString());
        intent.putExtra("end",endBox.getText().toString());
        startActivity(intent);
    }

    public void navToAddCoursePage(View view) {
        Intent intent = new Intent(TermPage.this,AddCourse.class);
        intent.putExtra("termID",termID);
        startActivity(intent);
    }

    public void deleteTerm(View view) {
        if (theseCourses.size() != 0){
            Toast.makeText(TermPage.this, "Must delete all courses first.", Toast.LENGTH_LONG).show();
        }
        else {
            for(Term term : repository.getAllTerms()){
                if(term.getTermID()==termID) {
                    repository.delete(term);
                    finish();
                }
            }
        }
    }
}