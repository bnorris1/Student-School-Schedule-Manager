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
import com.example.c196bnorris.src.entities.Assessment;
import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    class AssessmentViewHolder extends RecyclerView.ViewHolder{

        private final TextView assessmentItemView1;
        private final TextView assessmentItemView2;
        private final TextView assessmentItemView3;
        private final TextView assessmentItemView4;

        private AssessmentViewHolder(View view) {
            super(view);
            assessmentItemView1 = view.findViewById(R.id.assessmentItemView1);
            assessmentItemView2 = view.findViewById(R.id.assessmentItemView2);
            assessmentItemView3 = view.findViewById(R.id.assessmentItemView3);
            assessmentItemView4 = view.findViewById(R.id.assessmentItemView4);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessment assessment = assessmentList.get(position);
                    Intent intent = new Intent(context, AssessmentDetails.class);
                    intent.putExtra("id", assessment.getAssessmentID());
                    intent.putExtra("type", assessment.getType());
                    intent.putExtra("title", assessment.getTitle());
                    intent.putExtra("start", assessment.getStartDate());
                    intent.putExtra("end", assessment.getEndDate());
                    intent.putExtra("courseID", assessment.getCourseID());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Assessment> assessmentList;
    private final Context context;
    private final LayoutInflater layoutInflater;

    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.assessment_list_layout,parent,false);
        return new AssessmentAdapter.AssessmentViewHolder(view);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if(assessmentList!=null){
            Assessment assessment=assessmentList.get(position);
            String type=assessment.getType();
            String title= assessment.getTitle();
            String start= assessment.getStartDate();
            String end= assessment.getEndDate();
            holder.assessmentItemView1.setText(type);
            holder.assessmentItemView2.setText(title);
            holder.assessmentItemView3.setText(start);
            holder.assessmentItemView4.setText(end);
        }
        else{
            holder.assessmentItemView1.setText("No Title");
            holder.assessmentItemView2.setText("No Status");
            holder.assessmentItemView3.setText("No Start");
            holder.assessmentItemView4.setText("No End");
        }
    }
    @Override
    public int getItemCount() {
        if (assessmentList!=null){return assessmentList.size();}
        else return 0;
    }
    public void setAssessments(List<Assessment> assessments){
        assessmentList=assessments;
        notifyDataSetChanged();
    }
    public AssessmentAdapter(Context context){
        layoutInflater=LayoutInflater.from(context);
        this.context=context;
    }














}
