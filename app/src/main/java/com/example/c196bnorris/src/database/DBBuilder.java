package com.example.c196bnorris.src.database;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.c196bnorris.src.dao.AssessmentDAO;
import com.example.c196bnorris.src.dao.CourseDAO;
import com.example.c196bnorris.src.dao.TermDAO;
import com.example.c196bnorris.src.entities.Assessment;
import com.example.c196bnorris.src.entities.Course;
import com.example.c196bnorris.src.entities.Term;

@Database(entities={Term.class, Course.class, Assessment.class},version=1,exportSchema=false)
public abstract class DBBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();
    private static volatile DBBuilder INSTANCE;
    static DBBuilder getDB (final Context context){
        if (INSTANCE==null){
            synchronized (DBBuilder.class){
                if(INSTANCE==null) {
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(), DBBuilder.class,"C196DB")
                    .fallbackToDestructiveMigration()
                    .build();
                }
            }
        }
        return INSTANCE;
    }
}

