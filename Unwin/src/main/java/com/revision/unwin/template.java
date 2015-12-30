package com.revision.unwin;

/**
 * Created by Sam on 20/08/2014.
 */


import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class template extends Activity {

    private String topic, subject;
    private int id;
    private String[] topics;
    private View v;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.templatelayout);
        Intent intent = getIntent();
        topic = intent.getExtras().getString("topics");
        subject = intent.getExtras().getString("subject");
        id = intent.getExtras().getInt("id");
        this.setTitle(subject);
        topics = topic.split(",");
        for(int i = 0; i < topics.length; i++){
            Log.d("TOPICS", topics[i]);
        }

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        refresh_template(v);

    }

    //Create the settings menu drop down.
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.template_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                Intent intent = new Intent(template.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.editthisSub:
                //Edit this subject
                editThisSubject();
                break;
            case R.id.deletethisSub:
                //Delete this subject.
                deleteThisSubject();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void refresh_template(View v){
        DatabaseHandler db = new DatabaseHandler(this);
        SubjectTopic subjectTopics = db.getSubject(id);
        final String subject = subjectTopics.getName();
        final String topic = subjectTopics.getTopics();

        topics = topic.split(",");
        TextView label = (TextView) findViewById(R.id.labelTemplate);
        Random random = new Random();
        int randNum = random.nextInt(topics.length);

        String current = label.getText().toString();
        if (topics[randNum] == current) {
            refresh_template(v);
        }
        label.setText(topics[randNum]);
    }

    private void deleteThisSubject(){
        //Deletes all of the subject.
        //Currently only deletes ALL subjects, not just this one.
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure?");
        builder.setMessage("You will be unable to get this subject back should you continue.");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHandler db = new DatabaseHandler(template.this);
                db.deleteSubject(new SubjectTopic(id, subject, topic));
                Toast.makeText(template.this, "Deleted.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(template.this, MainActivity.class);
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void editThisSubject(){
        setContentView(R.layout.edit_sub);
        EditText topicPlacement = (EditText) findViewById(R.id.topicEditLayout);
        topicPlacement.setText(topic);
        TextView subjectNamePlacement = (TextView) findViewById(R.id.editTextLayout);
        subjectNamePlacement.setText("Edit " + subject);
    }

    public void updatethisSubject(View v){
        DatabaseHandler db = new DatabaseHandler(this);
        EditText topicPlacement = (EditText) findViewById(R.id.topicEditLayout);
        String newTopic = topicPlacement.getText().toString();
        SubjectTopic subjectTopic = new SubjectTopic();
        subjectTopic._subject = subject;
        subjectTopic._topics = newTopic;
        subjectTopic._id = id;
        db.updateSubject(subjectTopic);
        Toast.makeText(this, "Updated.", Toast.LENGTH_SHORT).show();
        //finish();
        setContentView(R.layout.templatelayout);
        refresh_template(v);
    }
}