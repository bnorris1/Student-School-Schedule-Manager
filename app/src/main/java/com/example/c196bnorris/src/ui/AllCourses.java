package com.example.c196bnorris.src.ui;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.c196bnorris.R;
import com.example.c196bnorris.src.database.Repository;
import com.example.c196bnorris.src.entities.Course;
import com.example.c196bnorris.src.entities.Term;

import java.util.List;
import java.util.Objects;


@SuppressWarnings({"FieldCanBeLocal", "SwitchStatementWithTooFewBranches"})
public class AllCourses extends AppCompatActivity {
    private Repository repository;
    public static RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_courses);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        repository = new Repository(getApplication());
        List<Course> allCourses = repository.getAllCourses();
        recyclerView = findViewById(R.id.listRecyclerView);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourses(allCourses);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        repository = new Repository(getApplication());
        List<Course> allCourses = repository.getAllCourses();
        recyclerView = findViewById(R.id.listRecyclerView);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourses(allCourses);
    }
}
