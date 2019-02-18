package com.seakleang.dictionary;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.seakleang.dictionary.data.dao.BookmarkDao;
import com.seakleang.dictionary.data.dao.DictionaryDao;
import com.seakleang.dictionary.data.database.AppDatabase;
import com.seakleang.dictionary.entity.Bookmark;

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
    private BookmarkDao bookmarkDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appDatabase = AppDatabase.getFileDatabase(this);
        dictionaryDao = appDatabase.dictionaryDao();
        bookmarkDao = appDatabase.bookmarkDao();
        tvDetail = findViewById(R.id.tvDetail);

        id = getIntent().getExtras().getInt("id");
        title = dictionaryDao.getWordById(id);
        detail = dictionaryDao.getDetailById(id);
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

    private void checkMenuBookmarkIcon() {
        bookmark = dictionaryDao.getBookmarkById(id);
        Toast.makeText(this, bookmark+"", Toast.LENGTH_SHORT).show();
        if (bookmark == false){
            menu.findItem(R.id.menuBookmark).setIcon(R.drawable.ic_bookmark);
        }
        else {
            menu.findItem(R.id.menuBookmark).setIcon(R.drawable.ic_bookmark_full);
        }
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
            dictionaryDao.updateBookmarkById(id, bookmark);
            if (bookmark == true) {
                bookmarkDao.add(new Bookmark(id));
            }else{
                bookmarkDao.delete(new Bookmark(id));
            }
            checkMenuBookmarkIcon();
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
}
