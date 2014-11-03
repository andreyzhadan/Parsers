import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import pojo.Gem;
import pojo.Visual;

import javax.xml.parsers.*;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final String GEMS_XML = "gems.xml";

    // http://www.javacodegeeks.com/2013/05/parsing-xml-using-dom-sax-and-stax-parser-in-java.html
    public static void main(String[] args) {
        List<Gem> gemList;
//          gemList = parseWithDom();
          gemList = parseWithSax();
//        gemList = parseWithStax();
        for (Gem gem : gemList) {
            System.out.println(gem);
        }
    }

    private static List<Gem> parseWithStax() {
        List<Gem> gemList = new ArrayList<>();
        Gem gem = new Gem();
        String tagContent = null;
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader;
        try {
            reader = factory.createXMLStreamReader(new FileReader(GEMS_XML));
            while (reader.hasNext()) {
                switch (reader.next()) {
                    case XMLStreamConstants.START_ELEMENT:
                        if (Gem.rootStr.equals(reader.getLocalName())) {
                            gem = new Gem();
                        }
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        tagContent = reader.getText().trim();
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        switch (reader.getLocalName()) {
                            case Gem.rootStr:
                                gemList.add(gem);
                                break;
                            case Gem.nameStr:
                                gem.setName(tagContent);
                                break;
                            case Gem.preciousnessStr:
                                gem.setPreciousness(Boolean.parseBoolean(tagContent));
                                break;
                        }
                        break;
                }

            }
        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return gemList;
    }

    private static List<Gem> parseWithSax() {
        final List<Gem> gemList = new ArrayList<>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(GEMS_XML, new DefaultHandler() {
                boolean bName = false;
                boolean bPreciousness = false;
                boolean bOrigin = false;
                boolean bVisual = false;
                boolean bValue = false;

                boolean bColor = false;
                boolean bTransparency = false;
                boolean bWay = false;
                String color;
                Integer transparency;
                Integer way;

                String name;
                boolean preciousness;
                String origin;
                Visual visual = new Visual(color, transparency, way);
                int value;

                    //visual:
    //                String color;
    //                Integer transparency;
    //                Integer way;
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if (qName.equals(Gem.nameStr)) {
                        bName = true;
                    }
                    if (qName.equals(Gem.preciousnessStr)) {
                        bPreciousness = true;
                    }
                    if (qName.equals(Gem.originStr)) {
                        bOrigin = true;
                    }
                    if (qName.equals(Gem.visualStr)) {
                        /*-----*/
                        //вложенные в Visual
                        if (qName.equals(Visual.colorStr) && qName.equals(Visual.transparencyStr) && qName.equals(Visual.wayStr)) {
                            bColor = true;
                            bTransparency = true;
                            bWay = true;
                        }
                        /*---*/
                        bVisual = true;
                    }
                    if (qName.equals(Gem.valueStr)) {
                        bValue = true;
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (Gem.rootStr.equals(qName)) {
                        Gem gem = new Gem(name, preciousness, origin, visual, value);
                        gemList.add(gem);
                    }
                }
//                Gem(String name, boolean preciousness, String origin, Visual visual, int value)

                @Override
                public void characters(char ch[], int start, int length) throws SAXException {
                    if (bName) {
                        name = new String(ch, start, length);
                        bName = false;
                    }
                    if (bPreciousness) {
                        preciousness = Boolean.parseBoolean(new String(ch, start, length));
                        bPreciousness = false;
                    }
                    if (bOrigin) {
                        origin = new String(ch, start, length);
                        bOrigin = false;
                    }
                    if (bVisual) {
                        visual = new Visual();
                        bVisual = false;
                    }
                    if (bValue) {
                        value = Integer.parseInt(new String(ch, start, length));
                        bValue = false;
                    }
                }
            });
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return gemList;
    }

    private static List<Gem> parseWithDom() {
        List<Gem> gemList = new ArrayList<>();
        try {
            File file = new File(GEMS_XML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("gem");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String name = element.getElementsByTagName(Gem.nameStr).item(0).getTextContent();
                    boolean preciousness = Boolean.parseBoolean(element.getElementsByTagName(Gem.preciousnessStr).item(0).getTextContent());
                    String origin = element.getElementsByTagName("origin").item(0).getTextContent();

                    Node visualNode = element.getElementsByTagName("visual").item(0);
                    Element visualElement = (Element) visualNode;
                    String color = visualElement.getElementsByTagName("color").item(0).getTextContent();
                    Integer transparency = Integer.valueOf(visualElement.getElementsByTagName("transparency").item(0).getTextContent());
                    Integer way = Integer.valueOf(visualElement.getElementsByTagName("way").item(0).getTextContent());
                    Visual visual = new Visual(color, transparency, way);

                    int value = Integer.parseInt(element.getElementsByTagName("value").item(0).getTextContent());
                    Gem gem = new Gem(name, preciousness, origin, visual, value);
                    gemList.add(gem);
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return gemList;
    }
}

