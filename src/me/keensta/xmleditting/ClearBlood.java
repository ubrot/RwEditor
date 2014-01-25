package me.keensta.xmleditting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class ClearBlood {

    private File xmlFile;
    private SAXBuilder builder;

    public ClearBlood(File xmlFile, SAXBuilder builder) {
        this.xmlFile = xmlFile;
        this.builder = builder;
    }

    public void activateCode() {
        try {
            Document doc = builder.build(xmlFile);
            Element rootNode = doc.getRootElement();

            Iterator<Element> c = rootNode.getDescendants(new ElementFilter("Def"));
            List<Element> markedToBeRemoved = new ArrayList<Element>();

            while(c.hasNext()) {
                Element e = c.next();

                if(e.getValue().equalsIgnoreCase("Blood")) {
                    if(e.getParentElement().getName().equalsIgnoreCase("Thing")) {
                        markedToBeRemoved.add(e.getParentElement());
                    }
                }
            }

            for(int i = 0; i < markedToBeRemoved.size(); i++) {
                markedToBeRemoved.get(i).getParentElement().removeContent(markedToBeRemoved.get(i));
            }

            XMLOutputter xmlOutput = new XMLOutputter();
            FileWriter fw = new FileWriter(xmlFile);

            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, fw);

            fw.close();

        } catch(IOException io) {
            io.printStackTrace();
        } catch(JDOMException e) {
            e.printStackTrace();
        }
    }

}