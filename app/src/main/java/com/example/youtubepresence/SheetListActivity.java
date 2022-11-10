package com.example.youtubepresence;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SheetListActivity extends AppCompatActivity {
    private ListView sheetList;
    private ArrayAdapter adapter;
    private ArrayList<String> listItems=new ArrayList();
    private long cid;
    Toolbar toolbar;
    private String nameClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_list);
        cid=getIntent().getLongExtra("cid",-1);
        nameClass=getIntent().getStringExtra("nameClass");

        loadListItems();
        sheetList=findViewById(R.id.sheetList);

        adapter=new ArrayAdapter(this,R.layout.sheet_list,R.id.date_list_item,listItems);
        sheetList.setAdapter(adapter);
        sheetList.setOnItemClickListener((parent,view,position,id) -> openSheetActivity(position));

        setToolbar();
    }
    private void setToolbar() {

        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title_toolbar);





        TextView subtitle = toolbar.findViewById(R.id.subtitle_toolbar);

        title.setText(nameClass);
        ImageButton back = toolbar.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();

            }
        });




    }


    private void openSheetActivity(int position) {
        long[] idArray=getIntent().getLongArrayExtra("idArray");
        String[] spacialitéAnnArrays=getIntent().getStringArrayExtra("spacialitéAnnArray");
        String[] nameArray=getIntent().getStringArrayExtra("nameArray");
        String[] n_ligneArrays=getIntent().getStringArrayExtra("n_ligneArray");

        Intent intent=new Intent(this,SheetActivity.class);
        intent.putExtra("idArray",idArray);
        intent.putExtra("spacialitéAnnArray",spacialitéAnnArrays);
        intent.putExtra("nameArray",nameArray);
        intent.putExtra("n_ligneArray",n_ligneArrays);
        intent.putExtra("nameClass", nameClass);


        intent.putExtra("month",listItems.get(position));
        startActivity(intent);

    }

    private void loadListItems() {
        Cursor cursor=new DbHelper(this).getDistinctMonths(cid);
        while (cursor.moveToNext()){
            String date=cursor.getString(cursor.getColumnIndex(DbHelper.DATE_KEY));
            listItems.add(date.substring(3));
        }
    }
}