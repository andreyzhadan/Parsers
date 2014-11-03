import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import pojo.Gem;
import pojo.Visual;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;
import static javax.xml.stream.XMLStreamConstants.*;
import static org.w3c.dom.Node.ELEMENT_NODE;
import static pojo.Gem.*;
import static pojo.Visual.*;

public class Main {
    public static final String GEMS_XML = "gems.xml";

    public static void main(String[] args) {
        List<Gem> gemList;
//        gemList = parseWithDom();
//        gemList = parseWithSax();
        gemList = parseWithStax();
        for (Gem gem : gemList) {
            System.out.println(gem);
        }
    }

    private static List<Gem> parseWithStax() {
        final List<Gem> gemList = new ArrayList<>();
        Gem gem = new Gem();
        Visual visual = new Visual();
        String tagContent = null;
        try {
            XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(new FileReader(GEMS_XML));
            while (reader.hasNext()) {
                switch (reader.next()) {
                    case START_ELEMENT:
                        switch (reader.getLocalName()) {
                            case ROOT:
                                gem = new Gem();
                                break;
                            case VISUAL:
                                visual = new Visual();
                                break;
                        }
                        break;

                    case CHARACTERS:
                        tagContent = reader.getText().trim();
                        break;

                    case END_ELEMENT:
                        switch (reader.getLocalName()) {
                            case ROOT:
                                gemList.add(gem);
                                break;
                            case NAME:
                                gem.setName(tagContent);
                                break;
                            case PRECIOUSNESS:
                                gem.setPreciousness(parseBoolean(tagContent));
                                break;
                            case ORIGIN:
                                gem.setOrigin(tagContent);
                                break;
                            case COLOR:
                                visual.setColor(tagContent);
                                break;
                            case TRANSPARENCY:
                                if (tagContent != null) {
                                    visual.setTransparency(valueOf(tagContent));
                                }
                                break;
                            case WAY:
                                if (tagContent != null) {
                                    visual.setWay(valueOf(tagContent));
                                }
                                break;
                            case VISUAL:
                                gem.setVisual(visual);
                                break;
                            case VALUE:
                                if (tagContent != null) {
                                    gem.setValue(parseInt(tagContent));
                                }
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
            SAXParserFactory.newInstance().newSAXParser().parse(GEMS_XML, new DefaultHandler() {
                boolean bName, bPreciousness, bOrigin, bValue, bColor, bTransparency, bWay;
                Gem gem = new Gem();
                Visual visual = new Visual();

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    switch (qName) {
                        case COLOR:
                            bColor = true;
                            break;
                        case TRANSPARENCY:
                            bTransparency = true;
                            break;
                        case WAY:
                            bWay = true;
                            break;
                        case NAME:
                            bName = true;
                            break;
                        case PRECIOUSNESS:
                            bPreciousness = true;
                            break;
                        case VALUE:
                            bValue = true;
                            break;
                        case VISUAL:
                            visual = new Visual();
                            break;
                        case ROOT:
                            gem = new Gem();
                            break;
                        case ORIGIN:
                            bOrigin = true;
                            break;
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (ROOT.equals(qName)) {
                        gem.setVisual(visual);
                        gemList.add(gem);
                    }
                }

                @Override
                public void characters(char ch[], int start, int length) throws SAXException {
                    if (bName) {
                        gem.setName(new String(ch, start, length));
                        bName = false;
                    }
                    if (bPreciousness) {
                        gem.setPreciousness(parseBoolean(new String(ch, start, length)));
                        bPreciousness = false;
                    }
                    if (bOrigin) {
                        gem.setOrigin(new String(ch, start, length));
                        bOrigin = false;
                    }
                    if (bColor) {
                        visual.setColor(new String(ch, start, length));
                        bColor = false;
                    }
                    if (bTransparency) {
                        visual.setTransparency(parseInt(new String(ch, start, length)));
                        bTransparency = false;
                    }
                    if (bWay) {
                        visual.setWay(parseInt(new String(ch, start, length)));
                        bWay = false;
                    }
                    if (bValue) {
                        gem.setValue(parseInt(new String(ch, start, length)));
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
        final List<Gem> gemList = new ArrayList<>();
        try {
            NodeList nodeList = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(GEMS_XML)).getElementsByTagName(ROOT);
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == ELEMENT_NODE) {
                    Element element = (Element) node;
                    String name = element.getElementsByTagName(NAME).item(0).getTextContent();
                    boolean preciousness = parseBoolean(element.getElementsByTagName(PRECIOUSNESS).item(0).getTextContent());
                    String origin = element.getElementsByTagName(ORIGIN).item(0).getTextContent();

                    Node visualNode = element.getElementsByTagName(VISUAL).item(0);
                    Element visualElement = (Element) visualNode;
                    String color = visualElement.getElementsByTagName(COLOR).item(0).getTextContent();
                    Integer transparency = valueOf(visualElement.getElementsByTagName(TRANSPARENCY).item(0).getTextContent());
                    Integer way = valueOf(visualElement.getElementsByTagName(WAY).item(0).getTextContent());
                    Visual visual = new Visual(color, transparency, way);

                    int value = parseInt(element.getElementsByTagName(VALUE).item(0).getTextContent());
                    gemList.add(new Gem(name, preciousness, origin, visual, value));
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return gemList;
    }
}

