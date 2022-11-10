package com.example.youtubepresence;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;

public class MyDialog extends DialogFragment {
    public static final String CLASS_ADD_Dialog="addClass";
    public static final String CLASS_UPDATE_Dialog="updateClass";

    public static final String STUDENT_ADD_Dialog="addStudent";
    public static final String STUDENT_UPDATE_DIALOG = "updateStudent";

    private onClickListener listener;
    private String Specialité_anné;
    private String name;
    private String n_ligne;

    public MyDialog(String name) {

        this.name=name;
    }
    public MyDialog(String Specialité_anné, String n_ligne, String name) {
        this.Specialité_anné = Specialité_anné;
        this.n_ligne = n_ligne;

        this.name=name;
    }
    public MyDialog(){

    }

    public interface onClickListener{
        void onClick(String text1,String Text2,String Text3);
    }


    public void setListener(onClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog=null;
        if(getTag().equals(CLASS_ADD_Dialog))dialog=getAddclassDialog();
        if(getTag().equals(STUDENT_ADD_Dialog))dialog=getAddstudentDialog();
        if(getTag().equals(CLASS_UPDATE_Dialog))dialog=getUpdateClassDialog();
        if(getTag().equals(STUDENT_UPDATE_DIALOG))dialog=getUpdateStudentDialog();


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return dialog;
    }

    private Dialog getUpdateStudentDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.class_dialog,null);
        builder.setView(view);
        TextView title=view.findViewById(R.id.titledialog);
        title.setText("Modifier un etudiant");

        EditText specialitéann_edt=view.findViewById(R.id.edt01);
        EditText n_ligne_edt=view.findViewById(R.id.edt02);
        EditText name_edt=view.findViewById(R.id.edt03);
        TextInputLayout edt1_lay=view.findViewById(R.id.edt1_ly);
        TextInputLayout edt2_ly=view.findViewById(R.id.edt2_ly);
        TextInputLayout edt3_ly=view.findViewById(R.id.edt3_ly);




        edt1_lay.setHint("Specialité et Année");
        edt2_ly.setHint("N° du ligne");
        edt3_ly.setHint("Nom et Prenom");



        Button cancel= view.findViewById(R.id.cancel_btn);
        Button add=  view.findViewById(R.id.add_btn);
        add.setText("Modifier");
         specialitéann_edt.setText(Specialité_anné);
        name_edt.setText(name);

        //roll_edt.setEnabled(false);
        n_ligne_edt.setText(n_ligne);
        specialitéann_edt.setSelection(Specialité_anné.length());
        n_ligne_edt.setSelection(n_ligne_edt.length());
        name_edt.setSelection(name_edt.length());
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String specialitéAnn=specialitéann_edt.getText().toString();
                String n_ligne=n_ligne_edt.getText().toString();
                String name=name_edt.getText().toString();


                listener.onClick(specialitéAnn,n_ligne,name);
                dismiss();



            }
        });


        return builder.create();
    }

    private Dialog getUpdateClassDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.class_dialog,null);
        builder.setView(view);
        TextView title=view.findViewById(R.id.titledialog);
        title.setText("Modifier un Group");

        EditText class_edt=view.findViewById(R.id.edt01);
        EditText subject_edt=view.findViewById(R.id.edt02);
        EditText None=view.findViewById(R.id.edt03);
        TextInputLayout edt1_ly=view.findViewById(R.id.edt1_ly);

        None.setVisibility(View.GONE);
        class_edt.setText(class_edt.getText().toString());
        class_edt.setSelection(class_edt.getText().length());


        edt1_ly.setHint("Nom du Group");
        class_edt.setText(name);
        class_edt.setSelection(name.length());

        subject_edt.setVisibility(View.GONE);

        Button cancel= view.findViewById(R.id.cancel_btn);
        Button add=  view.findViewById(R.id.add_btn);
        add.setText("Modifier");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String className=class_edt.getText().toString();
               // String subName=subject_edt.getText().toString();
                listener.onClick(className,null,null);
                dismiss();

            }
        });


        return builder.create();
    }

    private Dialog getAddstudentDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.class_dialog,null);
        builder.setView(view);
        TextView title=view.findViewById(R.id.titledialog);
        title.setText("Ajouter un etudiant");

        EditText specialitéAnn_edt=view.findViewById(R.id.edt01);
        EditText n_ligne_edt=view.findViewById(R.id.edt02);
        EditText name_edt=view.findViewById(R.id.edt03);
        TextInputLayout edt1_lay=view.findViewById(R.id.edt1_ly);
        TextInputLayout edt2_ly=view.findViewById(R.id.edt2_ly);
        TextInputLayout edt3_ly=view.findViewById(R.id.edt3_ly);





        edt1_lay.setHint("Specialité et Année");
        edt2_ly.setHint("N° du ligne ");

        edt3_ly.setHint("Nom et prenom");

        Button cancel= view.findViewById(R.id.cancel_btn);
        Button add=  view.findViewById(R.id.add_btn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String specailitéAnn=specialitéAnn_edt.getText().toString();
                String n_ligne=n_ligne_edt.getText().toString();
                String name=name_edt.getText().toString();


                //roll_edt.setText(String.valueOf(Integer.parseInt(roll)+1));
                n_ligne_edt.setText("");



                specialitéAnn_edt.setText("");


                name_edt.setText("");
                specialitéAnn_edt.requestFocusFromTouch();




                listener.onClick(specailitéAnn,n_ligne,name);



            }
        });


        return builder.create();
    }

    private Dialog getAddclassDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.class_dialog,null);
        builder.setView(view);
        TextView title=view.findViewById(R.id.titledialog);
        title.setText("Ajouter un Group");

       EditText class_edt=view.findViewById(R.id.edt01);
        TextInputLayout class_lay=view.findViewById(R.id.edt1_ly);
       EditText subject_edt=view.findViewById(R.id.edt02);
        EditText None=view.findViewById(R.id.edt03);
        None.setVisibility(View.GONE);
        subject_edt.setVisibility(View.GONE);

        class_lay.setHint("Nom du Group");
        subject_edt.setHint("Class Name");

        Button cancel= view.findViewById(R.id.cancel_btn);
        Button add=  view.findViewById(R.id.add_btn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String className=class_edt.getText().toString();
                //String subName=subject_edt.getText().toString();
                listener.onClick(className,null,null);
                dismiss();

            }
        });


        return builder.create();
    }
}
