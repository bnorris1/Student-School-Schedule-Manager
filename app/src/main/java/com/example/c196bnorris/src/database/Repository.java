package com.example.c196bnorris.src.database;
import android.app.Application;
import com.example.c196bnorris.src.dao.AssessmentDAO;
import com.example.c196bnorris.src.dao.CourseDAO;
import com.example.c196bnorris.src.dao.TermDAO;
import com.example.c196bnorris.src.entities.Assessment;
import com.example.c196bnorris.src.entities.Course;
import com.example.c196bnorris.src.entities.Term;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings({"FieldMayBeFinal", "CodeBlock2Expr", "unused"})
public class Repository {
    private TermDAO termDAO;
    private CourseDAO courseDAO;
    private AssessmentDAO assessmentDAO;
    private List<Term> allTerms;
    private List<Course> allCourses;
    private List<Assessment> allAssessments;
    private static int NUMBER_OF_THREADS = 4;
    public static final ExecutorService dbExe = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        DBBuilder db = DBBuilder.getDB(application);
        termDAO = db.termDAO();
        courseDAO = db.courseDAO();
        assessmentDAO = db.assessmentDAO();
    }

    //terms
    public List<Term> getAllTerms(){dbExe.execute(()->{allTerms= termDAO.getAllTerms();});
    try {Thread.sleep(1000);}
        catch (InterruptedException e) {e.printStackTrace();}
        return allTerms;}
    public void insert(Term term){dbExe.execute(()->{termDAO.insert(term);});
        try {Thread.sleep(1000);}
        catch (InterruptedException e) {e.printStackTrace();} }
    public void update(Term term){
        dbExe.execute(()->{termDAO.update(term);});
        try {Thread.sleep(1000);}
        catch (InterruptedException e) {e.printStackTrace();} }
    public void delete(Term term){dbExe.execute(()->{termDAO.delete(term);});
        try {Thread.sleep(1000);}
        catch (InterruptedException e) {e.printStackTrace();} }

    //courses
    public List<Course> getAllCourses(){dbExe.execute(()->{allCourses = courseDAO.getAllCourses();});
        try {Thread.sleep(1000);}
        catch (InterruptedException e) {e.printStackTrace();}
        return allCourses;}
   public void insert(Course course){dbExe.execute(()->{courseDAO.insert(course);});
        try {Thread.sleep(1000);}
        catch (InterruptedException e) {e.printStackTrace();}}
    public void update(Course course){dbExe.execute(()->{courseDAO.update(course);});
        try {Thread.sleep(1000);}
        catch (InterruptedException e) {e.printStackTrace();}}
    public void delete(Course course){dbExe.execute(()->{courseDAO.delete(course);});
        try {Thread.sleep(1000);}
        catch (InterruptedException e) {e.printStackTrace();}}

    //assessments
    public List<Assessment> getAllAssessments(){dbExe.execute(()->{allAssessments=assessmentDAO.getAllAssessments();});
        try {Thread.sleep(1000);}
        catch (InterruptedException e) {e.printStackTrace();}
        return allAssessments;}
    public void insert(Assessment assessment){dbExe.execute(()->{assessmentDAO.insert(assessment);});
        try {Thread.sleep(1000);}
        catch (InterruptedException e) {e.printStackTrace();}}
    public void update(Assessment assessment){dbExe.execute(()->{assessmentDAO.update(assessment);});
        try {Thread.sleep(1000);}
        catch (InterruptedException e) {e.printStackTrace();}}
    public void delete(Assessment assessment){dbExe.execute(()->{assessmentDAO.delete(assessment);});
        try {Thread.sleep(1000);}
        catch (InterruptedException e) {e.printStackTrace();}}
}
