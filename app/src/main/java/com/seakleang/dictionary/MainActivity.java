package com.seakleang.dictionary;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.seakleang.dictionary.adapter.DictionaryAdapter;
import com.seakleang.dictionary.data.dao.DictionaryDao;
import com.seakleang.dictionary.data.dao.HistoryDao;
import com.seakleang.dictionary.data.database.AppDatabase;
import com.seakleang.dictionary.entity.Dictionary;
import com.seakleang.dictionary.entity.History;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    AppDatabase appDatabase;
    DictionaryDao dictionaryDao;
    HistoryDao historyDao;
    Time time;
    ListView listView;
    DictionaryAdapter adapter;
    List<String> list = new ArrayList<>();
    TextView txtClose;
    Dialog dialog;
    EditText edit_search;
    MenuItem menuLanguage;
    List<String> words = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        listView = findViewById(R.id.lvMainActivity);

        appDatabase =AppDatabase.getFileDatabase(this);
        dictionaryDao = appDatabase.dictionaryDao();
        historyDao = appDatabase.historyDao();
        time = new Time();
        list = getListOfWord();
        refreshList(list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                time.setToNow();
                String t = time.format("%H:%M:%S");
                String d = time.format("%Y/%h/%d");
                historyDao.add(new History(position+1, d, t));

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("id", position+1);
                intent.putExtra("activity", "MainActivity");
                startActivity(intent);
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        edit_search = findViewById(R.id.edit_search);
        edit_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence keys, int start, int before, int count) {
                if (keys.toString().equals("")){
                    list = getListOfWord();
                    refreshList(list);
                }else {
                    words.clear();
                    for (String word: list){
                        if (word.contains(keys.toString())){
                            words.add(word);

                        }
                    }
                    refreshList(words);
                }
            }

            @Override
            public void afterTextChanged(Editable keys) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menuLanguage = menu.findItem(R.id.language);
        String id = StateLanguage.getSate(this, "language");
        if (id == null) {
            onOptionsItemSelected(menu.findItem(R.id.en_kh));
        }else {
            onOptionsItemSelected(menu.findItem(Integer.parseInt(id)));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        StateLanguage.saveState(this, "language", String.valueOf(id));
        //noinspection SimplifiableIfStatement
        if (id == R.id.en_kh) {
            menuLanguage.setIcon(getDrawable(R.drawable.english_khmer));
        } else if (id == R.id.kh_en) {
            menuLanguage.setIcon(getDrawable(R.drawable.khmer_english));
        } else if (id == R.id.kh_kh) {
            menuLanguage.setIcon(getDrawable(R.drawable.khmer_khmer));
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bookmark){
            Intent intent = new Intent(this, BookmarkActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_history){
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_help) {
            showDialog();
        } else if (id == R.id.nav_about) {
            showDialog_about();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public List<String> getListOfWord(){
        List<String> list = new ArrayList<>();
        if (dictionaryDao.getNumberOfRow()== 0) {
            addData();
        }
        list = dictionaryDao.getWord();
        return list;
    }

    private void addData() {
        List<Dictionary> dictionaries = new ArrayList<>();
        dictionaries.add(new Dictionary("accurate","1.ត្រឹម\n2.ត្រង់ទៀងទាត់"));
        dictionaries.add(new Dictionary("acknowledge","1.ទទួលស្គាល់\n2.ចំណេះដឺងឮន"));
        dictionaries.add(new Dictionary("acidulous ","1.ដែលមានរសជាតិល្វីង"));
        dictionaries.add(new Dictionary("Acorn ","1.ផ្លែសែន ឬ ផ្លែអូក"));

        dictionaries.add(new Dictionary("Bookseller  ","1.អ្នកលក់សៀវភៅ"));
        dictionaries.add(new Dictionary("booklet","1.សៀវភៅតូច"));

        dictionaries.add(new Dictionary("Exercise","1.ការិយកម្\n2.ការហាត់ប្រាណ.លំហាត់"));

        dictionaries.add(new Dictionary("Hegemony","1.អនុត្តរភាព"));
        dictionaries.add(new Dictionary("heifer","1.មេគោ (មិនទាន់កូន)\n" +"2.មេគោ (មិនទាន់មានកូ"));

        dictionaries.add(new Dictionary("Improve","1.ធ្វើអោយប្រសើរឡើង\n2.ធ្វើឲ្យប្រសើរ"));

        dictionaries.add(new Dictionary("Life","1.ជីវិត {\tnoun }\n3.អាយុកាល"));
        dictionaries.add(new Dictionary("Love","1.សេចក្ដីស្រឡាញ់\n2.ចូលចិត្\n3.ប៉ងប្រាថ្នា"));

        dictionaries.add(new Dictionary("Position","1.ឋានៈ\n2.ងារ\n3.តំណែង\n4.ទីតាំង ស្ថាន ភាព ទីកន្លែង របៀបប្រើ ទំលាប់\n5.ផ្នែក {\tnoun }\n6.មុខតំណែង"));
        dictionaries.add(new Dictionary("Provide","1.ផ្ដល់\n2.ផ្ដល់អោយ"));

        dictionaries.add(new Dictionary("Qualification","1គុណសម្បត្ដិ\n"));

        dictionaries.add(new Dictionary("Study","1.សិក្សា\n2.ការសិក្សា"));





        dictionaryDao.add(dictionaries);
    }

    private void refreshList(List<String> list) {
        adapter = new DictionaryAdapter(this, list);
        listView.setAdapter(adapter);
    }

    private void showDialog_about() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.alertdialog_about);
        txtClose = dialog.findViewById(R.id.txt_close);
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void showDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.alertdialog_help);
        txtClose = dialog.findViewById(R.id.txt_close);
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
