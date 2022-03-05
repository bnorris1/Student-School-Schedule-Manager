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
import com.example.c196bnorris.src.entities.Term;
import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {
    class TermViewHolder extends RecyclerView.ViewHolder{
        private final TextView termItemView1;
        private final TextView termItemView2;
        private final TextView termItemView3;
        private final TextView termItemView4;
        private TermViewHolder(View view) {
            super(view);
            termItemView1 = view.findViewById(R.id.termItemView1);
            termItemView2 = view.findViewById(R.id.termItemView2);
            termItemView3 = view.findViewById(R.id.termItemView3);
            termItemView4 = view.findViewById(R.id.termItemView4);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Term term = termList.get(position);
                    Intent intent = new Intent(context,TermPage.class);
                    intent.putExtra("id",term.getTermID());
                    intent.putExtra("title",term.getTitle());
                    intent.putExtra("start",term.getStartDate());
                    intent.putExtra("end",term.getEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Term> termList;
    private final Context context;
    private final LayoutInflater layoutInflater;

    public TermAdapter(Context context){
        layoutInflater=LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.term_list_layout,parent,false);
        return new TermViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {
        if(termList!=null){
            Term term = termList.get(position);
            int id = term.getTermID();
            String title = term.getTitle();
            String start = term.getStartDate();
            String end = term.getEndDate();
            holder.termItemView1.setText(Integer.toString(id));
            holder.termItemView2.setText(title);
            holder.termItemView3.setText(start);
            holder.termItemView4.setText(end);
        }
        else{
            holder.termItemView1.setText("No Term ID");
            holder.termItemView2.setText("No Term Title");
            holder.termItemView3.setText("No Term Start");
            holder.termItemView4.setText("No Term End");
        }
    }

    public void setTerms(List<Term> terms){
        termList=terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {return termList.size();}
}
