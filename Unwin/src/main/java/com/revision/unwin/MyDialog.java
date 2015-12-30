package com.revision.unwin;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 22/08/2014.
 */
public class MyDialog extends DialogFragment implements View.OnClickListener{

    Communicator communicator;
    Button button;
    DatabaseHandler db;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.dialog, null);
        setCancelable(false);
        getDialog().setTitle("Edit Subject");
        button = (Button) view.findViewById(R.id.butnDialog);
        button.setOnClickListener(this);
        Context context = getActivity();
        db = new DatabaseHandler(context);
        List<SubjectTopic> allSubjects = db.getAllSubjects();
        if(allSubjects.size() != 0){

            ArrayList<String> subjects = new ArrayList<String>();

            for(SubjectTopic subtop : allSubjects){
                final String subject = subtop.getName();
                final String topic = subtop.getTopics();

                subjects.add(subject);

            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.item, subjects);
            ListView listView = (ListView) view.findViewById(R.id.listViewDialog);
            listView.setAdapter(adapter);
        }
        return view;
    }

    public void onClick(View view){
        if(view.getId() == R.id.butnDialog){
            communicator.onDialogMessage("Button Clicked.");
            dismiss();
        }
    }

    interface Communicator{
        public void onDialogMessage(String message);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator = (Communicator) activity;
    }
}