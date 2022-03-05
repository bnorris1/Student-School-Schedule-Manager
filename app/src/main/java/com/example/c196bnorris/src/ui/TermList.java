package com.example.c196bnorris.src.ui;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import com.example.c196bnorris.R;
import com.example.c196bnorris.src.database.Repository;
import com.example.c196bnorris.src.entities.Term;
import java.util.List;
import java.util.Objects;

@SuppressWarnings({"FieldCanBeLocal", "SwitchStatementWithTooFewBranches"})
public class TermList extends AppCompatActivity {
    private Repository repository;
    public static RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.term_list);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        repository= new Repository(getApplication());

        List<Term> allTerms = repository.getAllTerms();
        recyclerView = findViewById(R.id.listRecyclerView);
        final TermAdapter termAdapter= new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        repository= new Repository(getApplication());
        List<Term> allTerms = repository.getAllTerms();
        recyclerView = findViewById(R.id.listRecyclerView);
        final TermAdapter termAdapter= new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);
    }

    public void navToAddTermPage(View view) {
        Intent intent = new Intent(TermList.this,AddTerm.class);
        startActivity(intent);
    }
}