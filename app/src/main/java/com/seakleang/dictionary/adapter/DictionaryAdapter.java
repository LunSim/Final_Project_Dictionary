package com.seakleang.dictionary.adapter;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.seakleang.dictionary.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DictionaryAdapter extends BaseAdapter {
    List<String>  wordList;
    Context context;
    private TextToSpeech TTS;

    public DictionaryAdapter(Context context, List<String>  wordList){
        this.context = context;
        this.wordList = wordList;
    }

    public void setWordList(List<String> wordList) {
        this.wordList = wordList;
    }

    public void searchWord(CharSequence key) {
        List<String> search = new ArrayList<>();
        for (String word: search) {
            if (word.toLowerCase().contains(key)) {
                search.add(word.toLowerCase());
            }
        }

        wordList = search;
        notifyDataSetChanged();
    }
    // count item in array or arrayList
    @Override
    public int getCount() {
        return wordList.size();
    }
    //return item
    @Override
    public Object getItem(int position) {
        return wordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //convert layout from xml and bind data
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = null;
        if(convertView == null)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dictionary_layout_item, parent, false);
        else
            view = convertView;
        //create view Object
        TextView word = view.findViewById(R.id.tvDictionary);

        word.setText(wordList.get(position));

        return view ;
    }

}
