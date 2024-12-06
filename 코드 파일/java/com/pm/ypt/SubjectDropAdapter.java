package com.pm.ypt;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SubjectDropAdapter extends BaseAdapter {
    ArrayList<Subject> subjects = new ArrayList<Subject>();
    Context context;

    @Override
    public int getCount() {
        return subjects.size();
    }

    @Override
    public Object getItem(int i) {
        return subjects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        context = viewGroup.getContext();
        Subject subject = subjects.get(i);

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.subject_drop_menu, viewGroup, false);
        }

        TextView subject_name = (TextView) view.findViewById(R.id.subject_name);

        subject_name.setText(subject.getsName());

        return view;
    }


    public void addSubject(Subject subject){
        subjects.add(subject);
    }

}
