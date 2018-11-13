package graphics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PlayerImageParser extends DefaultHandler {
    
    public String chemin;
    public ArrayList<String> listCase = new ArrayList<>();
    String buildingChemin = "";
    public int x;
    public int y;
    private final boolean end = false;
    //Nom, x, y, width, height;
    HashMap<String, ArrayList<Integer>> playerImages = new HashMap<>();
    ArrayList<String> playerNames = new ArrayList<>();
    
    public PlayerImageParser(){        
    }
    
    Hashtable tags;
    @Override
    public void startDocument() {
        //System.out.println("Document started");
        //tags = new Hashtable();
    }
    
    @Override
    public void endDocument() {
        //System.out.println("Documents ended"); 
    }

    @Override
    public void startElement(String namespaceURI,String localName,String qname,Attributes atts) throws SAXException {
        // System.out.println("Element started");
       // if(qname.equals("Currency"))
       //System.out.print(qname+"-->");
       if(qname == "SubTexture"){
           x = Integer.parseInt(atts.getValue("x"));
           y = Integer.parseInt(atts.getValue("y"));
           int width = Integer.parseInt(atts.getValue("width"));
           int height = Integer.parseInt(atts.getValue("height"));
           playerNames.add(atts.getValue("name"));
           playerImages.put(atts.getValue("name"), new ArrayList<>(Arrays.asList(
                   x,y,width,height
           )));
       }        
    }

    @Override
    public void endElement(String uri,String localName, String qname) {
       if(qname == "data"){
           listCase.add(",");
       }
    }
    
    @Override
    public void characters(char[] ch, int start, int length) {
       String str =  new String(ch,start,length);
       //System.out.println(str);
       if(str.trim().length() > 0 ){
           listCase.add(str);
       }
       //System.out.println(str);
       //System.out.println();
    }
}
