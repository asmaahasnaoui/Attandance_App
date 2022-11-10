package com.example.youtubepresence;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Calendar;

public class SheetActivity extends AppCompatActivity {
    Toolbar toolbar;
    private String nameClass;
    String month;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet);
        month=getIntent().getStringExtra("month");
        nameClass=getIntent().getStringExtra("nameClass");


        setToolbar();
        showTable();
        //setToolbar();





    }
    private void setToolbar() {

        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title_toolbar);
        title.setText(nameClass);





       TextView subtitle = toolbar.findViewById(R.id.subtitle_toolbar);
       subtitle.setText(month);
       //subtitle.setText(month);
        ImageButton back = toolbar.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });



    }

    private void showTable() {
        DbHelper dbHelper=new DbHelper(this);
        TableLayout tableLayout=findViewById(R.id.tableLayout);
        long[] idArray=getIntent().getLongArrayExtra("idArray");
        String[] specialitéAnnArrays=getIntent().getStringArrayExtra("spacialitéAnnArray");
        String[] nameArray=getIntent().getStringArrayExtra("nameArray");
        String[] n_ligneArray=getIntent().getStringArrayExtra("n_ligneArray");


        //int DAY_IN_MONTH=getDayInMonth(month);
        int DAY_IN_MONTH=getDayInMonth(month);
        int rowSize=idArray.length+1;
        TableRow[] rows =new TableRow[rowSize];
        TextView[] specialitéAnn_tvs=new TextView[rowSize];
        TextView[] n_ligne_tvs=new TextView[rowSize];

        TextView[] name_tvs=new TextView[rowSize];

        TextView[][] status_tvs=new TextView[rowSize][DAY_IN_MONTH+1];
        for (int i=0;i<rowSize;i++){
            specialitéAnn_tvs[i]=new TextView(this);
            name_tvs[i]=new TextView(this);
            n_ligne_tvs[i]=new TextView(this);

            for (int j=0;j <= DAY_IN_MONTH;j++){
                status_tvs[i][j]=new TextView(this);
            }
        }

        specialitéAnn_tvs[0].setText("Specialité et Année");
        specialitéAnn_tvs[0].setTypeface(specialitéAnn_tvs[0].getTypeface(), Typeface.BOLD);
        n_ligne_tvs[0].setText("N° du ligne");
        n_ligne_tvs[0].setTypeface(n_ligne_tvs[0].getTypeface(), Typeface.BOLD);

        name_tvs[0].setText("Nom et Prenom");
        name_tvs[0].setTypeface(name_tvs[0].getTypeface(), Typeface.BOLD);

        for (int i=0;i <= DAY_IN_MONTH;i++){
            status_tvs[0][i].setText(String.valueOf(i));
            status_tvs[0][i].setTypeface(status_tvs[0][i].getTypeface(),Typeface.BOLD);
        }
        for (int i=1;i < rowSize;i++) {
            specialitéAnn_tvs[i].setText(String.valueOf(specialitéAnnArrays[i-1]));
            name_tvs[i].setText(nameArray[i-1]);
            n_ligne_tvs[i].setText(n_ligneArray[i-1]);

            for (int j=1;j <= DAY_IN_MONTH;j++) {
                String day=String.valueOf(j);
                if(day.length()==1) day="0"+day;
                String date=day+"."+month;
                String status=dbHelper.getStatus(idArray[i-1],date);
                status_tvs[i][j].setText(status);
            }

            }
        for (int i=0;i < rowSize;i++) {
            rows[i]=new TableRow(this);
            if (i%2==0)
                rows[i].setBackgroundColor(Color.parseColor("#EEEEEE"));
            else
                rows[i].setBackgroundColor(Color.parseColor("#E4E4E4"));

            specialitéAnn_tvs[i].setPadding(16,16,16,16);
            n_ligne_tvs[i].setPadding(16,16,16,16);

            name_tvs[i].setPadding(16,16,16,16);


            rows[i].addView(specialitéAnn_tvs[i]);

            rows[i].addView(n_ligne_tvs[i]);
            rows[i].addView(name_tvs[i]);


            for (int j=1;j <= DAY_IN_MONTH;j++) {
                status_tvs[i][j].setPadding(16,16,16,16);

                rows[i].addView(status_tvs[i][j]);

            }
            tableLayout.addView(rows[i]);
        }
        tableLayout.setShowDividers(TableLayout.SHOW_DIVIDER_MIDDLE);





        }

    private int getDayInMonth(String month) {
        int monthIndex=Integer.parseInt(month.substring(0,2))-1;
        int year=Integer.parseInt(month.substring(3));
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.MONTH,monthIndex);
        calendar.set(Calendar.YEAR,year);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}