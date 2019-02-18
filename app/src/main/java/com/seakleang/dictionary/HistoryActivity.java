package com.seakleang.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.seakleang.dictionary.adapter.HistoryAdapter;
import com.seakleang.dictionary.data.dao.DictionaryDao;
import com.seakleang.dictionary.data.dao.HistoryDao;
import com.seakleang.dictionary.data.database.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;
    AppDatabase appDatabase;
    HistoryDao historyDao;
    DictionaryDao dictionaryDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        appDatabase = AppDatabase.getFileDatabase(this);
        historyDao =  appDatabase.historyDao();
        dictionaryDao = appDatabase.dictionaryDao();
        listView = findViewById(R.id.lvHistory);
        toolbar = findViewById(R.id.tbHistory);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("History");

        refreshListView();

        //Toast.makeText(this, historyDao.getNumberOfRow()+"", Toast.LENGTH_SHORT).show();

        listView.setOnItemClickListener(((parent, view, position, id) -> {
            TextView textView = view.findViewById(R.id.tvHistoryWord);

            int wordId = dictionaryDao.getIdByWord(textView.getText().toString());
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("id", wordId);
            intent.putExtra("activity", "HistoryActivity");
            startActivity(intent);
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.clear_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuClear){
            historyDao.deleteAll();
            refreshListView();
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshListView() {
        HistoryAdapter adapter = new HistoryAdapter(historyDao.getWord(), historyDao, dictionaryDao);
        listView.setAdapter(adapter);

    }
}
