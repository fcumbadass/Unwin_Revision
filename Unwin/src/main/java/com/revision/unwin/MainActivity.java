package com.revision.unwin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import java.util.List;

public class MainActivity extends Activity {

    DatabaseHandler db;
    private View ref;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_subjects);
        db = new DatabaseHandler(this);

        refreshMain(ref);
    }

    protected void onResume(){
        super.onResume();
        refreshMain(ref);
    }

    public void refreshMain(View v){
        setContentView(R.layout.all_subjects);
        LinearLayout layout = (LinearLayout) findViewById(R.id.LinearContainer);
        layout.removeAllViews();
        String all = null;
        List<SubjectTopic> subtop = db.getAllSubjects();

        if(subtop.size() > 0){
            for(SubjectTopic sub : subtop){
                final String subject = sub.getName();
                final int id = sub.getID();
                final String topic = sub.getTopics();

                Button buttonSubject = new Button(this);
                buttonSubject.setText(subject);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                layoutParams.setMargins(5, 5, 5, 5);


                buttonSubject.setLayoutParams(layoutParams);
                buttonSubject.setTextColor(Color.WHITE);
                buttonSubject.setBackgroundResource(R.drawable.button_resource);

                layout.addView(buttonSubject);
                buttonSubject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), template.class);
                        intent.putExtra("topics", topic);
                        intent.putExtra("id", id);
                        intent.putExtra("subject", subject);
                        startActivity(intent);
                    }
                });
            }
        }
        else if(subtop.size() == 0){
            setContentView(R.layout.nosubjects);
        }
    }

    //Create the settings menu drop down.
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Checks OptionItemsSelection, and does code according to which one is pressed
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            //Checks to see if the action_settings option is pressed
            case R.id.add_sub:
                Intent intent = new Intent(this, createSub.class);
                startActivity(intent);
                return true;
            case R.id.help_menu:
                //Some help box here
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.help);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setTitle("Help");
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            //Anything else - Not possible with this
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            v = inflater.inflate(R.layout.all_subjects, parent, false);
        } else {
            //super.onCreateView(inflater, parent, savedInstanceState);
        }
        return v;
    }

    public void startAddSub(View view){
        Intent intent = new Intent(this, createSub.class);
        startActivity(intent);
    }
}