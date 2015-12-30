package com.revision.unwin;

/**
 * Created by Sam on 20/08/2014.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class createSub extends Activity{

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_sub);
    }

    public void addSubject(View v){
        final EditText enter_sub = (EditText) findViewById(R.id.enter_sub);
        final EditText enter_top = (EditText) findViewById(R.id.enter_top);
        final String subject = enter_sub.getText().toString();
        final String topic = enter_top.getText().toString();
        if(subject.length() == 0 || topic.length() == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Empty field");
            builder.setMessage("You cannot have an empty field.");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }else{
            if(topic.contains(" ") && !topic.contains(",")){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Warning");
                builder.setMessage("There are spaces in the topics. Click yes to add subject, or click no to edit and add a comma.");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
                        SubjectTopic subjTop = new SubjectTopic(subject, topic);
                        DatabaseHandler db = new DatabaseHandler(createSub.this);
                        db.addSubject(subjTop);
                        Toast.makeText(createSub.this, "Added: " + subject, Toast.LENGTH_SHORT).show();
                        enter_sub.setText("");
                        enter_top.setText("");
                    }
                });
            }
            else{
                SubjectTopic subjTop = new SubjectTopic(subject, topic);
                DatabaseHandler db = new DatabaseHandler(this);
                db.addSubject(subjTop);
                Toast.makeText(this, "Added: " + subject, Toast.LENGTH_SHORT).show();
                enter_sub.setText("");
                enter_top.setText("");
            }
        }

    }

    public void deleteAll(View v){
        final DatabaseHandler db = new DatabaseHandler(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure?")
                .setMessage("This is irreversible, are you sure you want to delete all subjects?")
                .setCancelable(true)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.deleteAllSubjects();
                        Toast.makeText(createSub.this, "Deleted.", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}