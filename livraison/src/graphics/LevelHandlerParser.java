/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Stack;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author quentindeme
 */
public class LevelHandlerParser extends DefaultHandler{
    
    Stack<String> balises= new Stack<String>();
    public String chemin;
    public ArrayList<String> listCase = new ArrayList<String>();
    String buildingChemin = "";
    public int x;
    public int y;
    public int nbLayer = 0;
    private boolean end = false;
    
    public LevelHandlerParser(){
    }
    
    Hashtable tags;
     @Override
    public void startDocument()
    {
        //System.out.println("Document started");
        //tags = new Hashtable();
    }
     @Override
     public void endDocument()
     {
         //System.out.println("Documents ended"); 
     }

     @Override
     public void startElement(String namespaceURI,String localName,String qname,Attributes atts) throws SAXException
     {
        // System.out.println("Element started");
        // if(qname.equals("Currency"))
        //System.out.print(qname+"-->");
        balises.push(qname);
        if(qname == "layer"){
            nbLayer++;
            x = Integer.parseInt(atts.getValue("width"));
            y = Integer.parseInt(atts.getValue("height"));
        }        
     }
     
     @Override
     public void endElement(String uri,String localName, String qname)
     {
        if(qname == "data"){
            listCase.add(",");
        } 
        balises.pop();
     }
     @Override
     public void characters(char[] ch, int start, int length)
     {
        String str =  new String(ch,start,length);
        //System.out.println(str);
        if(str.trim().length() > 0 ){
            listCase.add(str);
        }
        //System.out.println(str);
        //System.out.println();
       
     }  
}
