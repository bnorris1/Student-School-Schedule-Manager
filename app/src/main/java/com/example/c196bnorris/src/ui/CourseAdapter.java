package com.example.c196bnorris.src.ui;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.c196bnorris.R;
import com.example.c196bnorris.src.entities.Course;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    class CourseViewHolder extends RecyclerView.ViewHolder{

        private final TextView courseItemView1;
        private final TextView courseItemView2;
        private final TextView courseItemView3;
        private final TextView courseItemView4;
        private final TextView courseItemView5;
        private final TextView courseItemView6;
        private final TextView courseItemView7;

        private CourseViewHolder(View view) {
            super(view);
            courseItemView1 = view.findViewById(R.id.courseItemView1);
            courseItemView2 = view.findViewById(R.id.courseItemView2);
            courseItemView3 = view.findViewById(R.id.courseItemView3);
            courseItemView4 = view.findViewById(R.id.courseItemView4);
            courseItemView5 = view.findViewById(R.id.courseItemView5);
            courseItemView6 = view.findViewById(R.id.courseItemView6);
            courseItemView7 = view.findViewById(R.id.courseItemView7);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course course = courseList.get(position);
                    Intent intent = new Intent(context,CoursePage.class);
                    intent.putExtra("id",course.getCourseID());
                    intent.putExtra("title",course.getTitle());
                    intent.putExtra("status",course.getStatus());
                    intent.putExtra("start",course.getStartDate());
                    intent.putExtra("end",course.getEndDate());
                    intent.putExtra("iname",course.getInstructorName());
                    intent.putExtra("iphone",course.getInstructorPhoneNumber());
                    intent.putExtra("iemail",course.getInstructorEmail());
                    intent.putExtra("note",course.getCourseNote());
                    intent.putExtra("termID",course.getTermID());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Course> courseList;
    private final Context context;
    private final LayoutInflater layoutInflater;

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.course_list_layout,parent,false);
        return new CourseAdapter.CourseViewHolder(view);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if(courseList!=null){
            Course course=courseList.get(position);
            String title=course.getTitle();
            String status=course.getStatus();
            String start=course.getStartDate();
            String end=course.getEndDate();
            String iname=course.getInstructorName();
            String iphone=course.getInstructorPhoneNumber();
            String iemail=course.getInstructorEmail();
            holder.courseItemView1.setText(title);
            holder.courseItemView2.setText(status);
            holder.courseItemView3.setText(start);
            holder.courseItemView4.setText(end);
            holder.courseItemView5.setText(iname);
            holder.courseItemView6.setText(iphone);
            holder.courseItemView7.setText(iemail);
        }
        else{
            holder.courseItemView1.setText("No Title");
            holder.courseItemView2.setText("No Status");
            holder.courseItemView3.setText("No Start");
            holder.courseItemView4.setText("No End");
            holder.courseItemView5.setText("No I.Name");
            holder.courseItemView6.setText("No I.Phone");
            holder.courseItemView7.setText("No I.Email");
        }
    }
    @Override
    public int getItemCount() {
        if (courseList!=null){return courseList.size();}
        else return 0;
    }
    public void setCourses(List<Course> courses){
        courseList=courses;
        notifyDataSetChanged();
    }
    public CourseAdapter(Context context){
        layoutInflater=LayoutInflater.from(context);
        this.context=context;
    }
}
