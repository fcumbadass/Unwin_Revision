package com.revision.unwin;

/**
 * Created by Sam on 20/08/2014.
 */
public class SubjectTopic {
    //private variables
    int _id;
    String _subject;
    String _topics;

    // Empty constructor
    public SubjectTopic(){

    }

    // constructor
    public SubjectTopic(int id, String subject, String _topics){
        this._id = id;
        this._subject = subject;
        this._topics = _topics;
    }

    // constructor
    public SubjectTopic(String subject, String topics){
        this._subject = subject;
        this._topics = topics;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getName(){
        return this._subject;
    }

    // setting name
    public void setName(String subject){
        this._subject = subject;
    }

    // getting phone number
    public String getTopics(){
        return this._topics;
    }

    // setting phone number
    public void setPhoneNumber(String topics){
        this._topics = topics;
    }
}