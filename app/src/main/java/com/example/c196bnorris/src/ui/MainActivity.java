package com.example.c196bnorris.src.ui;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.c196bnorris.R;
import com.example.c196bnorris.src.database.Repository;
import com.example.c196bnorris.src.entities.Assessment;
import com.example.c196bnorris.src.entities.Course;
import com.example.c196bnorris.src.entities.Term;

@SuppressWarnings("CommentedOutCode")
public class MainActivity extends AppCompatActivity {
    public static int staticNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Repository repository = new Repository(getApplication());
    }

    public void goToTerms(View view) {
        Intent intent = new Intent(MainActivity.this,TermList.class);
        startActivity(intent);
    }

    public void navToAllCourses(View view) {
        Intent intent = new Intent(MainActivity.this,AllCourses.class);
        startActivity(intent);
    }

    public void navToAllAssignments(View view) {
        Intent intent = new Intent(MainActivity.this,AllAssessments.class);
        startActivity(intent);
    }
}