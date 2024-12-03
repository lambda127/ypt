package com.pm.ypt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class ToDoListAdapter extends BaseAdapter {
    private ArrayList<ToDo> toDoList = new ArrayList<ToDo>();
    private Context context;
    private ViewHolder viewHolder;
    private int checked_num = 0;

    @Override
    public int getCount() {
        return toDoList.size();
    }

    @Override
    public Object getItem(int i) {
        return toDoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public int getChecked_num() {
        return checked_num;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        context = viewGroup.getContext();
        ToDo toDo = toDoList.get(i);

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_todo, viewGroup, false);

            viewHolder = new ViewHolder();

            viewHolder.setCheckBox((CheckBox) view.findViewById(R.id.todo_check_box));
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        //CheckBox checkBox = (CheckBox) view.findViewById(R.id.todo_check_box);
        TextView txt = (TextView) view.findViewById(R.id.todo_text);

        txt.setText(toDo.getTodo());


        //체크박스의 기본 이벤트리스너 제거
        viewHolder.checkBox.setClickable(false);
        viewHolder.checkBox.setFocusable(false);
        //체크상태 설정
        viewHolder.checkBox.setChecked(toDo.getState());

        return view;

    }

    public void checkedConfirm(int position) {
        ToDo todo = toDoList.get(position);
        checked_num += todo.getState() ? -1 : 1;
        todo.setState(!todo.getState());
    }

    public void addToDo(ToDo todo){
        toDoList.add(todo);
    }

}
