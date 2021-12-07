package com.example.parserapp;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class UserResourceParserXML {
    private final ArrayList<UserXML> users;

    public UserResourceParserXML(){
        users = new ArrayList<>();
    }

    public ArrayList<UserXML> getUsers(){
        return  users;
    }

    public static void CreateXMLString(User person, Context context) throws IllegalArgumentException, IllegalStateException, IOException
    {
        File newxmlfile = new File(context.getFilesDir(), "user.xml");

        try {
            newxmlfile.createNewFile();
        } catch (IOException e) {
            Log.e("IOException", "Exception in create new File(");
        }

        FileOutputStream fileos = null;
        try {
            fileos = new FileOutputStream(newxmlfile);

        } catch(FileNotFoundException e) {
            Log.e("FileNotFoundException",e.toString());
        }

        XmlSerializer xmlSerializer = Xml.newSerializer();
        //StringWriter writer = new StringWriter();

        xmlSerializer.setOutput(fileos, "UTF-8");

        //Start Document
        xmlSerializer.startDocument(null, Boolean.TRUE);
        xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

        //Open Tag <file>
        xmlSerializer.startTag("", "");

        xmlSerializer.startTag("", "user");

        xmlSerializer.startTag("", "name");
        xmlSerializer.text(person.getName());
        xmlSerializer.endTag("", "name");

        xmlSerializer.startTag("", "sex");
        xmlSerializer.text(person.getSex());
        xmlSerializer.endTag("", "sex");

        xmlSerializer.startTag("", "ide");
        xmlSerializer.text(person.getIde());
        xmlSerializer.endTag("", "ide");

        xmlSerializer.startTag("", "lang");
        xmlSerializer.text(person.getLang());
        xmlSerializer.endTag("", "lang");

        //end tag <file>
        xmlSerializer.endTag("", "user");

        //end tag <file>
        xmlSerializer.endTag("", "file");
        xmlSerializer.endDocument();

        //return writer.toString();
        if (fileos != null) {
            fileos.flush();
            fileos.close();
        }

    }

    public boolean parse(XmlPullParser xpp){
        boolean status = true;
        UserXML currentUser = null;
        boolean inEntry = false;
        String textValue = "";

        try{
            int eventType = xpp.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){

                String tagName = xpp.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if("user".equalsIgnoreCase(tagName)){
                            inEntry = true;
                            currentUser = new UserXML();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(inEntry){
                            if("user".equalsIgnoreCase(tagName)){
                                users.add(currentUser);
                                inEntry = false;
                            } else if("name".equalsIgnoreCase(tagName)){
                                currentUser.setName(textValue);
                            } else if("age".equalsIgnoreCase(tagName)){
                                currentUser.setAge(textValue);
                            }
                        }
                        break;
                    default:
                }
                eventType = xpp.next();
            }
        }
        catch (Exception e){
            status = false;
            e.printStackTrace();
        }
        return  status;
    }
}