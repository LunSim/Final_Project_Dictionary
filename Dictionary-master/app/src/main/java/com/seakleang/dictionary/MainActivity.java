package com.seakleang.dictionary;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtClose;
    Dialog dialog;
    MenuItem menuLanguage;
    DictionaryFragment dictionaryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dictionaryFragment = new DictionaryFragment();
//        bookmarkFragment = new BookmarkFragment();
//        dictionaryFragment.setListener(new FragmentListener() {
//            @Override
//            public void onItemClick() {
//                Intent intent = new Intent(this, DetailActivity.class);
//            }
//        });

        openFragment(dictionaryFragment, R.id.fragment_container);
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

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    public void openFragment(Fragment fragment, int container){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(container, fragment);
        fragmentTransaction.commit();
    }
}
