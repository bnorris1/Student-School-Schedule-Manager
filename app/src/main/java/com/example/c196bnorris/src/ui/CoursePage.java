package com.example.c196bnorris.src.ui;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.example.c196bnorris.R;
import com.example.c196bnorris.src.database.Repository;
import com.example.c196bnorris.src.entities.Assessment;
import com.example.c196bnorris.src.entities.Course;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@SuppressWarnings("ConstantConditions")
public class CoursePage extends AppCompatActivity {
    private Repository repository;
    private TextView courseID;
    private TextView titleBox;
    private TextView startBox;
    private TextView endBox;
    private TextView status;
    private TextView iname;
    private TextView iphone;
    private TextView iemail;
    private TextView note;
    private int courseIDint;
    private int termIDint;
    public static RecyclerView recyclerView;
    private ArrayList<Assessment> theseAssessments = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_page);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        repository= new Repository(getApplication());
        courseID = findViewById(R.id.courseIDValue);
        courseID.setText(Integer.toString(getIntent().getIntExtra("id",-1)));
        courseIDint = Integer.parseInt(courseID.getText().toString());
        titleBox = findViewById(R.id.courseTitleValue);
        titleBox.setText(getIntent().getStringExtra("title"));
        startBox = findViewById(R.id.courseStartValue);
        startBox.setText(getIntent().getStringExtra("start"));
        endBox = findViewById(R.id.courseEndValue);
        endBox.setText(getIntent().getStringExtra("end"));
        status = findViewById(R.id.courseStatusValue);
        status.setText(getIntent().getStringExtra("status"));
        iname = findViewById(R.id.instructorNameValue);
        iname.setText(getIntent().getStringExtra("iname"));
        iphone = findViewById(R.id.instructorPhoneNumberValue);
        iphone.setText(getIntent().getStringExtra("iphone"));
        iemail = findViewById(R.id.instructorEmailValue);
        iemail.setText(getIntent().getStringExtra("iemail"));
        note = findViewById(R.id.courseNoteValue);
        note.setText(getIntent().getStringExtra("note"));
        termIDint = getIntent().getIntExtra("termID",-1);
        List<Assessment> allAssessments = repository.getAllAssessments();
        for (Assessment assessment : allAssessments){
            if (assessment.getCourseID()==courseIDint){theseAssessments.add(assessment);}
        }
        recyclerView = findViewById(R.id.assessmentsInCourseList);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessments(theseAssessments);
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.share:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,"Note for Course ID: "+courseIDint+" = " +note.getText());
                Intent newIntent = Intent.createChooser(intent,null);
                intent.setType("text/plain");
                startActivity(newIntent);
                return true;

            case R.id.notify:
                String courseTitleStr = titleBox.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);

                String startDateStr = startBox.getText().toString();
                String endDateStr = endBox.getText().toString();

                Date startDate = null;
                Date endDate = null;

                try {startDate = sdf.parse(startDateStr);}
                catch (ParseException e) {e.printStackTrace();}
                try {endDate = sdf.parse(endDateStr);}
                catch (ParseException e) {e.printStackTrace();}

                Long startDateTrigger = startDate.getTime();
                Long endDateTrigger = endDate.getTime();

                Intent startIntent = new Intent(CoursePage.this,MyReceiver.class);
                Intent endIntent = new Intent(CoursePage.this,MyReceiver.class);

                startIntent.putExtra("a","Course '"+courseTitleStr+"' starts today:"+startDateStr);
                endIntent.putExtra("a","Course '"+courseTitleStr+"' ends today:"+endDateStr);

                PendingIntent pendingStartIntent = PendingIntent.getBroadcast(CoursePage.this, ++MainActivity.staticNumber,startIntent,PendingIntent.FLAG_IMMUTABLE);
                PendingIntent pendingEndIntent = PendingIntent.getBroadcast(CoursePage.this, ++MainActivity.staticNumber,endIntent,PendingIntent.FLAG_IMMUTABLE);

                AlarmManager startAlarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                AlarmManager endAlarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

                startAlarmManager.set(AlarmManager.RTC_WAKEUP, startDateTrigger, pendingStartIntent);
                endAlarmManager.set(AlarmManager.RTC_WAKEUP, endDateTrigger, pendingEndIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onRestart() {
        super.onRestart();
        List<Course> allCourses = repository.getAllCourses();
        Course updatedCourse = null;
        for (Course course : allCourses){
            if (course.getCourseID()==courseIDint){
                updatedCourse = course;
            }
        }
        titleBox.setText(updatedCourse.getTitle());
        startBox.setText(updatedCourse.getStartDate());
        endBox.setText(updatedCourse.getEndDate());
        status.setText(updatedCourse.getStatus());
        iname.setText(updatedCourse.getInstructorName());
        iphone.setText(updatedCourse.getInstructorPhoneNumber());
        iemail.setText(updatedCourse.getInstructorEmail());
        note.setText(updatedCourse.getCourseNote());
        theseAssessments.clear();
        List<Assessment> allAssessments = repository.getAllAssessments();
        for (Assessment assessment : allAssessments){
            if (assessment.getCourseID()==courseIDint){theseAssessments.add(assessment);}
        }
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessments(theseAssessments);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    public void navToCourseDetailsPage(View view) {
        Intent intent = new Intent(CoursePage.this,CourseDetails.class);
        intent.putExtra("id",courseID.getText().toString());
        intent.putExtra("title",titleBox.getText().toString());
        intent.putExtra("start",startBox.getText().toString());
        intent.putExtra("end",endBox.getText().toString());
        intent.putExtra("status",status.getText().toString());
        intent.putExtra("iname",iname.getText().toString());
        intent.putExtra("iphone",iphone.getText().toString());
        intent.putExtra("iemail",iemail.getText().toString());
        intent.putExtra("note",note.getText().toString());
        intent.putExtra("termID",termIDint);
        startActivity(intent);
    }
    public void deleteCourse(View view) {
        for(Course course : repository.getAllCourses()){
            if(course.getCourseID()==courseIDint) {
                for(Assessment assessment : theseAssessments){
                    if(assessment.getCourseID()==courseIDint){
                        repository.delete(assessment);
                    }
                }
                repository.delete(course);
                finish();
            }
        }
    }
    public void navToAddAssessmentPage(View view) {
        Intent intent = new Intent(CoursePage.this,AddAssessment.class);
        intent.putExtra("courseID",courseIDint);
        startActivity(intent);
    }
}