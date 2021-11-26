package com.example.workwithtext.Scripts;

import android.app.Person;
import android.content.Context;
import android.media.MediaMuxer;
import android.net.LocalSocketAddress;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class xmlWorker {

    public static String ParseXML(Context context) {

        try
        {

        //creating a constructor of file class and parsing an XML file
            File file = new File(context.getFilesDir(), "user.xml");
        //an instance of factory that gives a document builder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        //an instance of builder to parse the specified xml file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            NodeList nodeList = doc.getElementsByTagName("file");

            String info = "";

            // nodeList is not iterable, so we are using for loop
            for (int itr = 0; itr < nodeList.getLength(); itr++)
            {
                Node node = nodeList.item(itr);
                Log.e("Node Name :",  node.getNodeName());
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element eElement = (Element) node;
                    info = "Student Name: "+ eElement.getElementsByTagName("name").item(0).getTextContent() + "\n";
                    info += "Sex: "+ eElement.getElementsByTagName("sex").item(0).getTextContent() + "\n" ;
                    info += "Ide: "+ eElement.getElementsByTagName("ide").item(0).getTextContent() + "\n" ;
                    info += "Lang: "+ eElement.getElementsByTagName("lang").item(0).getTextContent() + "\n" ;
                }
            }

            return "RETURN FROM XML\n\n" +  info;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
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
        xmlSerializer.startTag("", "file");

        xmlSerializer.startTag("", "user");
        xmlSerializer.attribute("", "ID", "000001");

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


}
