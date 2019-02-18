package com.seakleang.dictionary;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.seakleang.dictionary.data.dao.DictionaryDao;
import com.seakleang.dictionary.data.database.AppDatabase;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private TextView tvDetail;
    private Menu menu;
    private TextToSpeech TTS;
    private String title;
    private String detail;
    private int id;
    private boolean bookmark;
    private AppDatabase appDatabase;
    private DictionaryDao dictionaryDao;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        appDatabase = AppDatabase.getFileDatabase(this);
        dictionaryDao = appDatabase.dictionaryDao();
        tvDetail = findViewById(R.id.tvDetail);

        id = getIntent().getExtras().getInt("id");
        title = dictionaryDao.getWordById(id);
        detail = dictionaryDao.getDetailById(id);
        toolbar = findViewById(R.id.tbDetail);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int result = TTS.setLanguage(Locale.US);
                }
            }
        });

        getSupportActionBar().setTitle(title);
        tvDetail.setText(detail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        this.menu = menu;
        checkMenuBookmarkIcon();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menuSpeaker){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                TTS.speak(title, TextToSpeech.QUEUE_FLUSH, null, null);
            }
            else {
                TTS.speak(title, TextToSpeech.QUEUE_FLUSH, null);
            }
                Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
        }
        if (itemId == R.id.menuBookmark){
            bookmark = !bookmark;
            if (bookmark == false){
                Toast.makeText(this, "Removed", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
            }
            dictionaryDao.updateBookmarkById(id, bookmark);
            checkMenuBookmarkIcon();
        }
        if (itemId == android.R.id.home){
            Intent intent;
            if (getIntent().getStringExtra("Activity") == "MainActivity"){
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            if (getIntent().getStringExtra("Activity") == "BookmarkActivity"){
                intent = new Intent(this, BookmarkActivity.class);
                startActivity(intent);
            }
            if (getIntent().getStringExtra("Activity") == "HistoryActivity"){
                intent = new Intent(this, HistoryActivity.class);
                startActivity(intent);
            }
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if(TTS != null){
            TTS.stop();
            TTS.shutdown();
        }
        super.onDestroy();
    }

    private void checkMenuBookmarkIcon() {
        bookmark = dictionaryDao.getBookmarkById(id);
        if (bookmark == false){
            menu.findItem(R.id.menuBookmark).setIcon(R.drawable.ic_bookmark);
        }
        else {
            menu.findItem(R.id.menuBookmark).setIcon(R.drawable.ic_bookmark_full);
        }
    }
}
