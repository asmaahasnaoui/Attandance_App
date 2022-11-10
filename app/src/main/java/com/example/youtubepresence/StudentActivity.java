package com.example.youtubepresence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {
    Toolbar toolbar;
    ExtendedFloatingActionButton fab2;
    private String className;
    private String subjectName;
    private int position;
    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<StudentItem> studentItems = new ArrayList<>();
    private DbHelper dbHelper;
    private long cid;
    private MyCalendar calendar;
    private TextView subtitle;
    int present,absent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        fab2=findViewById(R.id.fab2);

        calendar = new MyCalendar();
        dbHelper = new DbHelper(this);
        Intent intent = getIntent();
        className = intent.getStringExtra("className");
        subjectName = intent.getStringExtra("subjectName");
        position = intent.getIntExtra("position", -1);
        cid = intent.getLongExtra("cid", -1);


        setToolbar();
        loadData();
        recyclerView = findViewById(R.id.student_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new StudentAdapter(this, studentItems);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> changeStatus(position));
        loadStatusData();
      //  loadPresentCount();
       // loadAbsentCount();
        loadPresenceData();
    }
    private void animation(){
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.set);
        fab2.startAnimation(animation);
    }
    private void loadData() {
        Cursor cursor = dbHelper.getStudentTable(cid);
        Log.i("1234567890", "loadData: " + cid);
        studentItems.clear();
        while (cursor.moveToNext()) {
            long sid = cursor.getLong(cursor.getColumnIndex(DbHelper.S_ID));
            String SpecialitéAnn = cursor.getString(cursor.getColumnIndex(DbHelper.STUDENT_SPECALITE_ANN));
            String n_ligne = cursor.getString(cursor.getColumnIndex(DbHelper.STUDENT_N_LIGNE));

            String name = cursor.getString(cursor.getColumnIndex(DbHelper.STUDENT_NAME_KEY));

            studentItems.add(new StudentItem(sid, SpecialitéAnn, n_ligne,name));
        }
        cursor.close();
    }

    private void changeStatus(int position) {
        String status = studentItems.get(position).getStatus();


        if (status.equals("P")) status = "A";
          //  absent=1;
            //present=0;
        //}
        else status = "P";
            //present=1;
            //absent=0;
       // }
       // int presentCount=studentItems.get(position).getPresent();
       // int AbsentCount=studentItems.get(position).getAbsent();

       // presentCount=presentCount+present;
       // AbsentCount=AbsentCount+absent;



        studentItems.get(position).setStatus(status);
      //  studentItems.get(position).setPresent(present);
        //studentItems.get(position).setAbsent(absent);
        adapter.notifyItemChanged(position);
        animation();
    }

    private void setToolbar() {

        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title_toolbar);



        subtitle = toolbar.findViewById(R.id.subtitle_toolbar);
        ImageButton back = toolbar.findViewById(R.id.back);

        fab2.setOnClickListener(v -> saveStatus());
        title.setText(className);


        subtitle.setText(calendar.getDate());


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(StudentActivity.this,MainActivity.class);
                startActivity(intent2);

            }
        });
        toolbar.inflateMenu(R.menu.student_menu);
        toolbar.setOnMenuItemClickListener(menuItem -> onMenuItemClick(menuItem));
    }

    private void saveStatus() {
        for (StudentItem studentItem : studentItems) {
            String status = studentItem.getStatus();
           // studentItems.get(position).setPresent(present);
            //studentItems.get(position).setAbsent(absent);
          //  int presentCount=studentItem.getPresent()+present;
           // int absentCount=studentItem.getAbsent()+absent;
            //studentItems.get(position).setPresent(presentCount);
            //studentItems.get(position).setAbsent(absentCount);

            //if (status != "P")
              //  status = "A";

            long value = dbHelper.addStatus(studentItem.getSid(), cid, calendar.getDate(), status);

            if (value == -1)
                dbHelper.updateStatus(studentItem.getSid(), calendar.getDate(), status);
        }
        loadPresenceData();
        loadStatusData();

    }

    private void loadStatusData() {
        for (StudentItem studentItem : studentItems) {
            String status = dbHelper.getStatus(studentItem.getSid(), calendar.getDate());
            if (status != null) studentItem.setStatus(status);
            else studentItem.setStatus("");
        }
        adapter.notifyDataSetChanged();
    }
    private void loadPresenceData() {
        for (StudentItem studentItem : studentItems) {
            ArrayList<String> absentCount = dbHelper.getAllStatus(studentItem.getSid());
            present=0;
            absent=0;
            for (int  i= 0;  i<absentCount.size() ; i++) {
                if ((absentCount.get(i)).equals("P")){
                    present++;
                }
                if ((absentCount.get(i)).equals("A")){
                    absent++;
                }
            }
            studentItem.setAbsent( String.valueOf(absent));
            studentItem.setPresent(String.valueOf(present));



        }
        adapter.notifyDataSetChanged();

    }

    private boolean onMenuItemClick(MenuItem menuItem) {


        if (menuItem.getItemId() == R.id.add_student) {
            showAddStudentDialog();
        } else if (menuItem.getItemId() == R.id.show_Calendar) {
            showCalendar();
        } else if (menuItem.getItemId() == R.id.show_attendance_sheet) {
            openSheetList();
        }
        return true;
    }

    private void openSheetList() {
        long[] idArray = new long[studentItems.size()];
        String[] nameArray = new String[studentItems.size()];
        String[] specialitéAnnArray = new String[studentItems.size()];
        String[] n_ligneArray = new String[studentItems.size()];


        for (int i = 0; i < idArray.length; i++)
            idArray[i] = studentItems.get(i).getSid();
        for (int i = 0; i < specialitéAnnArray.length; i++)
            specialitéAnnArray[i] = studentItems.get(i).getSpecialité_anné();
        for (int i = 0; i < nameArray.length; i++)
            nameArray[i] = studentItems.get(i).getName();
        for (int i = 0; i < nameArray.length; i++)
            n_ligneArray[i] = studentItems.get(i).getN_ligne();

        Intent intent = new Intent(this, SheetListActivity.class);
        intent.putExtra("cid", cid);
        intent.putExtra("nameClass", className);
        intent.putExtra("idArray",idArray);
        intent.putExtra("spacialitéAnnArray",specialitéAnnArray);
        intent.putExtra("nameArray",nameArray);
        intent.putExtra("n_ligneArray",n_ligneArray);

        startActivity(intent);
    }

    private void showCalendar() {

        calendar.show(getSupportFragmentManager(), "");
        calendar.setOnCalendarOkClickListener(this::onCalendarOkClicked);
    }

    private void onCalendarOkClicked(int year, int month, int day) {
        calendar.setDate(year, month, day);
        subtitle.setText(calendar.getDate());
        loadStatusData();
    }

    private void showAddStudentDialog() {
        MyDialog dialog = new MyDialog();
        dialog.show(getSupportFragmentManager(), MyDialog.STUDENT_ADD_Dialog);
        dialog.setListener((specailitéAnn, n_ligne,name) -> addStudent(specailitéAnn, n_ligne,name));
    }

    private void addStudent(String specialitéAnn, String n_ligne,String name) {
       // int roll = Integer.parseInt(roll_string);
        long sid = dbHelper.addStudent(cid, specialitéAnn, n_ligne,name);
        StudentItem studentItem = new StudentItem(sid, specialitéAnn, n_ligne,name);
        studentItems.add(studentItem);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case 0:
                showUpdateStudentDialog(item.getGroupId());
                break;
            case 1:
                deleteStudent(item.getGroupId());
        }
        return super.onContextItemSelected(item);
    }

    private void showUpdateStudentDialog(int position) {
        MyDialog dialog = new MyDialog(studentItems.get(position).getSpecialité_anné(),studentItems.get(position).getN_ligne(),studentItems.get(position).getName());
        dialog.show(getSupportFragmentManager(), MyDialog.STUDENT_UPDATE_DIALOG);
        dialog.setListener((specialitéAnn,n_ligne,name) -> updateStudent(position,specialitéAnn,n_ligne,name));
    }

    private void updateStudent(int position, String specialitéAnn,String n_ligne,String name) {
        dbHelper.updateStudent(studentItems.get(position).getSid(), specialitéAnn,n_ligne,name);
        studentItems.get(position).setName(name);
        studentItems.get(position).setN_ligne(n_ligne);
        studentItems.get(position).setSpecialité_anné(specialitéAnn);


        adapter.notifyItemChanged(position);
    }

    private void deleteStudent(int position) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Vous etes sur de supprimer l'etudiant ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Oui",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dbHelper.deleteStudent(studentItems.get(position).getSid());
                        studentItems.remove(position);
                        adapter.notifyItemRemoved(position);

                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "Non",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }


}
