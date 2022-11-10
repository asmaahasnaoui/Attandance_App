package com.example.youtubepresence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ExtendedFloatingActionButton fab;
    RecyclerView recyclerView;
    classAdapter classAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ClassItem>classItems=new ArrayList<>();
    Toolbar toolbar;
    DbHelper dbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper=new DbHelper(this);
        fab=findViewById(R.id.fab_main);
         loadData();
         loadNumberStudent();
        recyclerView=findViewById(R.id.recyclerview);
        setToolbar();
        recyclerView.setHasFixedSize(true);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        classAdapter =new classAdapter(this,classItems);
        recyclerView.setAdapter(classAdapter);
        classAdapter.setOnItemClickListener(position -> gotoItemActivity(position));
    }
    private void loadNumberStudent() {
        for (ClassItem classItem : classItems) {
            String number = dbHelper.getAllStudent(classItem.getCid());
            classItem.setNumberStudent(number);

        }
        //classAdapter.notifyDataSetChanged();
    }

    private void loadData() {
        Cursor cursor=dbHelper.getClassTable();
        classItems.clear();
        if(cursor !=null && cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(DbHelper.C_ID));
                String className = cursor.getString(cursor.getColumnIndex(DbHelper.CLASS_NAME_KEY));

                classItems.add(new ClassItem(id, className));
            }
            while (cursor.moveToNext());
        }



    }

    private void setToolbar() {
        toolbar=findViewById(R.id.toolbar);
        TextView title=toolbar.findViewById(R.id.title_toolbar);
        TextView subtitle=toolbar.findViewById(R.id.subtitle_toolbar);
        ImageButton back=toolbar.findViewById(R.id.back);

        //title.setText("Attendence App");
        subtitle.setVisibility(View.GONE);
        back.setVisibility(View.INVISIBLE);

        toolbar.setTitle("");
       // toolbar.setBackgroundResource(R.drawable.ic_wave__2_);
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setOnMenuItemClickListener(menuItem -> onMenuItemClick(menuItem));



    }

    private boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.New_trimestre) {
            showTrimestreDialog();}
        return true;
    }

    private void showTrimestreDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Nouveau Trimestre ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Oui",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Toast.makeText(MainActivity.this,"car modify succefully",Toast.LENGTH_LONG).show();
                        dbHelper.remouveStatusTable();
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

    private void gotoItemActivity(int position) {
        Intent intent=new Intent(this,StudentActivity.class);
        intent.putExtra("className",classItems.get(position).getClassName());

        intent.putExtra("position",position);
        intent.putExtra("cid",classItems.get(position).getCid());


        startActivity(intent);

    }

    private void showDialog(){
       MyDialog dialog=new MyDialog();
       dialog.show(getSupportFragmentManager(),MyDialog.CLASS_ADD_Dialog);
       dialog.setListener((className,subjectName,code)->addClass(className,subjectName,code));


    }

    private void addClass(String className,String subjectName,String code) {
        long cid=dbHelper.addClass(className,subjectName);
        ClassItem classItem=new ClassItem(cid,className);
        classItems.add(classItem);
        classAdapter.notifyDataSetChanged();



    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 0:
                showUpdateDialog(item.getGroupId());
                break;
            case 1:
                deleteClass(item.getGroupId());
        }
        return super.onContextItemSelected(item);
    }

    private void showUpdateDialog(int position) {
        MyDialog dialog=new MyDialog(classItems.get(position).getClassName());
        dialog.show(getSupportFragmentManager(),MyDialog.CLASS_UPDATE_Dialog);
        dialog.setListener((className,subjectName,code)->updateClass(position,className,subjectName,code));
    }
    private void updateClass(int position,String className,String subjectName,String code){
        dbHelper.updateClass(classItems.get(position).getCid(),className,subjectName);
        classItems.get(position).setClassName(className);

        classAdapter.notifyItemChanged(position);


    }

    private void deleteClass(int position) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Vous etes sur de supprimer le groupe ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Oui",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       // Toast.makeText(MainActivity.this,"car modify succefully",Toast.LENGTH_LONG).show();
                        dbHelper.deleteClass(classItems.get(position).getCid());
                        classItems.remove(position);
                        classAdapter.notifyItemRemoved(position);

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

    @Override
    protected void onStart() {
        super.onStart();
        loadNumberStudent();
    }
}