package com.seakleang.dictionary.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.seakleang.dictionary.MainActivity;
import com.seakleang.dictionary.R;
import com.seakleang.dictionary.data.dao.BookmarkDao;
import com.seakleang.dictionary.data.dao.DictionaryDao;
import com.seakleang.dictionary.data.database.AppDatabase;
import com.seakleang.dictionary.entity.Bookmark;

import java.util.List;

public class BookmarkAndHistoryAdapter extends BaseAdapter {
    List<String>  wordList;

    public BookmarkAndHistoryAdapter(List<String>  wordList){
        this.wordList = wordList;
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_and_history_layout_item, parent, false);
        else
            view = convertView;

        //create view Object
        TextView word = view.findViewById(R.id.tvBookmarkAndHistory);
        ImageView imageDelete = view.findViewById(R.id.ivBookmarkAndHistoryDelete);

        word.setText(wordList.get(position));

        return view ;
    }
}
