package com.seakleang.dictionary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.seakleang.dictionary.adapter.DictionaryAdapter;
import com.seakleang.dictionary.data.dao.DictionaryDao;
import com.seakleang.dictionary.data.database.AppDatabase;
import com.seakleang.dictionary.entity.Dictionary;

import java.util.ArrayList;
import java.util.List;

public class DictionaryFragment extends Fragment {

    AppDatabase appDatabase;
    DictionaryDao dictionaryDao;

    public DictionaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dictionary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = view.findViewById(R.id.listWord);
        appDatabase = AppDatabase.getFileDatabase(getActivity());
        dictionaryDao = appDatabase.dictionaryDao();
        DictionaryAdapter adapter = new DictionaryAdapter(getActivity(), getListOfWord());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("id", position+1);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    List<String> getListOfWord(){
        List<String> list = new ArrayList<>();
        if (dictionaryDao.getNumberOfRow()==0) {
            addData();
        }
            list = dictionaryDao.getWord();
        return list;
    }

    private void addData() {
        List<Dictionary> dictionaries = new ArrayList<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            dictionaries.add(new Dictionary(c+"",c+""+c));
        }
        dictionaryDao.add(dictionaries);
    }

//    public void setListener(FragmentListener listener) {
//        this.listener = listener;
//    }
}
