package com.pm.ypt;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import android.widget.Spinner;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    private boolean dd_pressed = false;

    private String sName = null;
    private SubjectListAdapter subject_adapter;
    private SubjectDropAdapter drop_subject_adapter;

    private String todoContent = null;
    private ToDoListAdapter todo_adapter;

    private boolean running = false;
    private long pauseOffset = 0;
    private long tmpTime = 0;


    private float todo_rate;

    private float tmp = 0;



    public static int ConvertDPtoPX(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public static String formatTime(long millis) {
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60;

        return String.format("%02d  :  %02d  :  %02d", hours, minutes, seconds);
    }


    private void animateHeightChange(View view, int startHeight, int endHeight, int duration) {
        ValueAnimator animator = ValueAnimator.ofInt(startHeight, endHeight);
        animator.addUpdateListener(animation -> {
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = (int) animation.getAnimatedValue();
            view.setLayoutParams(params);
        });
        animator.setDuration(duration); // 애니메이션 지속 시간 (ms 단위)
        animator.start();
    }

    private void animateWidthChange(View view, int startWidth, int endWidth, int duration) {
        ValueAnimator animator = ValueAnimator.ofInt(startWidth, endWidth);
        animator.addUpdateListener(animation -> {
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.width = (int) animation.getAnimatedValue();
            view.setLayoutParams(params);
        });
        animator.setDuration(duration); // 애니메이션 지속 시간 (ms 단위)
        animator.start();
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        LinearLayout box_shadow = (LinearLayout) findViewById(R.id.box_shadow);
        LinearLayout subject_list_box = (LinearLayout) findViewById(R.id.subject_list_box);
        ListView subject_list = (ListView) findViewById(R.id.subject_list);
        ImageButton dd_button = (ImageButton) findViewById(R.id.dd_button);


        FrameLayout todo_frame = (FrameLayout) findViewById(R.id.todo_frame);
        ListView todo_list = (ListView) findViewById(R.id.todolist);

        LinearLayout progress_fill = (LinearLayout) findViewById(R.id.progress_fill);
        LinearLayout progress_back = (LinearLayout) findViewById(R.id.progress_back);
        TextView progress_txt = (TextView) findViewById(R.id.progress_txt) ;

        ImageButton btn_subject_add = (ImageButton) findViewById(R.id.btn_subject_add);

        ImageButton btn_todo_add = (ImageButton) findViewById(R.id.btn_todo_add);

        Chronometer chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.setText(formatTime(pauseOffset));

        ImageButton chrono_button = (ImageButton) findViewById(R.id.chrono_button);

        Spinner subject_spinner = (Spinner) findViewById(R.id.subject_spinner);


        subject_adapter = new SubjectListAdapter();
        drop_subject_adapter = new SubjectDropAdapter();


        todo_adapter = new ToDoListAdapter();



        int expandedHeight = ConvertDPtoPX(this, 400);
        int collapsedHeight = ConvertDPtoPX(this, 133);

        int px25 = ConvertDPtoPX(this, 25);





        subject_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);

            }
            public void onNothingSelected(AdapterView<?> parent) {
                Object item = parent.getItemAtPosition(0);
            }
        });


        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            public void onChronometerTick(Chronometer cArg) {
                long t = SystemClock.elapsedRealtime() - cArg.getBase();
                cArg.setText(formatTime(t));
                if(sName != null){
                    subject_adapter.subjects.get(subject_spinner.getSelectedItemPosition()).setTime(t-tmpTime);
                    subject_list.setAdapter(subject_adapter);
                }
            }
        });



        chrono_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!running){

                    chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    tmpTime = pauseOffset;
                    chronometer.start();
                    running = true;
                    chrono_button.setBackgroundResource(R.drawable.pause);
                }
                else{
                    chronometer.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                    running = false;
                    chrono_button.setBackgroundResource(R.drawable.play_arrow);
                    // 지정한 과목에 변화량을 더한다. ->
                }
            }
        });

        dd_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!dd_pressed) {
                    animateHeightChange(box_shadow, collapsedHeight, expandedHeight, 500);
                    subject_list_box.setVisibility(View.VISIBLE);
                    subject_list.setVisibility(View.VISIBLE);
                    dd_button.setBackgroundResource(R.drawable.arrow_drop_up);
                } else {
                    animateHeightChange(box_shadow, expandedHeight, collapsedHeight, 500);
                    subject_list_box.setVisibility(View.INVISIBLE);
                    subject_list.setVisibility(View.INVISIBLE);
                    dd_button.setBackgroundResource(R.drawable.arrow_drop_down);
                }
                dd_pressed = !dd_pressed;

            }
        });

        btn_subject_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                View view2 = layoutInflater.inflate(R.layout.activity_dialog, (LinearLayout)findViewById(R.id.popup));


                AlertDialog ad = new AlertDialog.Builder(MainActivity.this, R.style.CustomAlertDialog)
                        .setView(view2)
                        .create();

                TextView dlg_title = (TextView) view2.findViewById(R.id.dialog_title);
                TextView dlg_txt = (TextView) view2.findViewById(R.id.dialog_txt);
                EditText et = (EditText) view2.findViewById(R.id.et);

                dlg_title.setText("Add Subject");
                dlg_txt.setText("Subject name : ");
                et.setHint("Input subject name");
                view2.findViewById(R.id.btn_condfirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sName = et.getText().toString();

                        if(sName != null){
                            subject_adapter.addSubject(new Subject(sName));
                            drop_subject_adapter.addSubject(new Subject(sName));

                            subject_list.setAdapter(subject_adapter);
                            subject_spinner.setAdapter(drop_subject_adapter);
                        }

                        ad.dismiss();
                    }
                });


                ad.show();


            }
        });


        btn_todo_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                View view2 = layoutInflater.inflate(R.layout.activity_dialog, (LinearLayout)findViewById(R.id.popup));


                AlertDialog ad = new AlertDialog.Builder(MainActivity.this, R.style.CustomAlertDialog)
                        .setView(view2)
                        .create();

                TextView dlg_title = (TextView) view2.findViewById(R.id.dialog_title);
                TextView dlg_txt = (TextView) view2.findViewById(R.id.dialog_txt);
                EditText et = (EditText) view2.findViewById(R.id.et);

                dlg_title.setText("Add To-Do");
                dlg_txt.setText("To-Do name : ");
                et.setHint("Input to-do");
                view2.findViewById(R.id.btn_condfirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        todoContent = et.getText().toString();


                        if(todoContent != null){
                            todo_adapter.addToDo(new ToDo(todoContent));

                            todo_list.setAdapter(todo_adapter);
                        }

                        todo_rate = (float) todo_adapter.getChecked_num() / todo_adapter.getCount();
                        progress_txt.setText((int) (todo_rate * 100) + "%");


                        animateWidthChange(progress_fill,
                                progress_fill.getWidth(),
                                px25 + (int)((  progress_back.getWidth() - px25) * todo_rate), 1200);

                        ad.dismiss();
                    }
                });


                ad.show();
            }
        });

        todo_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("onItemClick", "클릭->"+position);
                todo_adapter.checkedConfirm(position);
                todo_adapter.notifyDataSetChanged();

                tmp = todo_rate;

                todo_rate = (float) todo_adapter.getChecked_num() / todo_adapter.getCount();
                progress_txt.setText((int) (todo_rate * 100) + "%");


                animateWidthChange(progress_fill,
                        progress_fill.getWidth(),
                        px25 + (int)((  progress_back.getWidth() - px25) * todo_rate), 1200);



            }
        });



    }
}