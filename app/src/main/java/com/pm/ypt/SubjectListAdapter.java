package com.pm.ypt;

import static com.pm.ypt.MainActivity.formatTime;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class SubjectListAdapter extends BaseAdapter {
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
            view = inflater.inflate(R.layout.list_subject, viewGroup, false);
        }

        TextView subject_name = (TextView) view.findViewById(R.id.subject_name);
        TextView subject_chronometer = (TextView) view.findViewById(R.id.subject_chrono);


        subject_name.setText(subject.getsName());
        subject_chronometer.setText(formatTime(subject.getTime()));

        return view;
    }


    public void addSubject(Subject subject){
        subjects.add(subject);
    }

}
