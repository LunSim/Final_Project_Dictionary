package com.seakleang.dictionary.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seakleang.dictionary.R;
import com.seakleang.dictionary.data.dao.DictionaryDao;

import java.util.List;

public class BookmarkAdapter extends BaseAdapter {
    List<String>  wordList;
    DictionaryDao dictionaryDao;

    public BookmarkAdapter(List<String>  wordList, DictionaryDao dictionaryDao){
        this.wordList = wordList;
        this.dictionaryDao = dictionaryDao;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        if(convertView == null)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_layout_item, parent, false);
        else
            view = convertView;

        //create view Object
        TextView word = view.findViewById(R.id.tvBookmarkWord);
        ImageView imageDelete = view.findViewById(R.id.ivBookmarkDelete);

        imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dictionaryDao.updateBookmarkByWord(wordList.get(position), false);
                wordList = dictionaryDao.getBookmarkWord();
                notifyDataSetChanged();
            }
        });

        word.setText(wordList.get(position));

        return view ;
    }
}
