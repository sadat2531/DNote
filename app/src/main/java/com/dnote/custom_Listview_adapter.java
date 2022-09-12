package com.dnote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class custom_Listview_adapter extends BaseAdapter {

    Context context;
    ArrayList<Note_type> arrayList;

    // Function for receiving Note data from Database
    custom_Listview_adapter(Context context, ArrayList<Note_type> arrayList){

        this.context = context;
        this.arrayList = arrayList;

    }



    // For knowing the size of the note list received from Database
    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    // For knowing the position of clicked note on list view
    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    // Passing the id of note for indentifing note when clicked on note a on listview
    @Override
    public long getItemId(int position) {

        Note_type note_id = arrayList.get(position);
        long id = note_id.getId();

        return id;
    }

    // This is for adding notes to list view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.sample_view_for_listview, null);


            TextView Title_textView = (TextView) convertView.findViewById(R.id.title_textviewId);
            TextView Note_textView = (TextView) convertView.findViewById(R.id.note_textviewId);

            Note_type noteg = arrayList.get(position);    // Reading note by position on list

            Title_textView.setText(noteg.getTitle());    // Set note Title on view
            Note_textView.setText(noteg.getNote());      // Set Note on view


        return convertView;     // Returning view to list view
    }
}



