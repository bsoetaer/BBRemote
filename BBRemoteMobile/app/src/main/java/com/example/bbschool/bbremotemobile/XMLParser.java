package com.example.bbschool.bbremotemobile;

import android.content.Context;
import android.util.Xml;
import android.widget.RelativeLayout;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlSerializer;

import java.io.*;
import java.util.ArrayList;

import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Braeden on 3/30/2016.
 */
public class XMLParser {
    private class LayoutXML {
        private static final String ROOT_NODE = "GamepadLayout";
        private static final String NAME = "Name";
        private static final String ROTATE_ENABLED = "RotateEnabled";
        private static final String ORIENTATION = "Orientation";
        private static final String INPUTS = "GamepadInputs";
        private static final String GAMEPAD_INPUT = "GamepadInput";
        private static final String INPUT_ID = "InputID";
        private static final String LEFT_MARGIN = "LeftMargin";
        private static final String TOP_MARGIN = "TopMargin";
        private static final String SCALE_X = "ScaleX";
        private static final String SCALE_Y = "ScaleY";
    }

    private XMLParser() {};

    public static void save(Context context, GamepadLayout layout) {
        try {
            FileOutputStream fos = context.openFileOutput(layout.getName() + ".xml", Context.MODE_PRIVATE);
            XmlSerializer xmlSerializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();
            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.startTag(null, LayoutXML.ROOT_NODE);

            xmlSerializer.startTag(null, LayoutXML.NAME);
            xmlSerializer.text(layout.getName());
            xmlSerializer.endTag(null, LayoutXML.NAME);

            xmlSerializer.startTag(null, LayoutXML.ROTATE_ENABLED);
            xmlSerializer.text(String.valueOf(layout.getRotateAsInput()));
            xmlSerializer.endTag(null, LayoutXML.ROTATE_ENABLED);

            xmlSerializer.startTag(null, LayoutXML.ORIENTATION);
            xmlSerializer.text(String.valueOf(layout.getPortrait()));
            xmlSerializer.endTag(null, LayoutXML.ORIENTATION);

            xmlSerializer.startTag(null, LayoutXML.INPUTS);
            ArrayList<GamepadInputView> inputs = layout.getGamepadInputs();
            for( GamepadInputView input : inputs) {
                xmlSerializer.startTag(null, LayoutXML.GAMEPAD_INPUT);

                xmlSerializer.startTag(null, LayoutXML.INPUT_ID);
                xmlSerializer.text(String.valueOf(input.getGamepadInput().getVal()));
                xmlSerializer.endTag(null, LayoutXML.INPUT_ID);

                xmlSerializer.startTag(null, LayoutXML.LEFT_MARGIN);
                xmlSerializer.text(String.valueOf(input.getLeft()));
                xmlSerializer.endTag(null, LayoutXML.LEFT_MARGIN);

                xmlSerializer.startTag(null, LayoutXML.TOP_MARGIN);
                xmlSerializer.text(String.valueOf(input.getTop()));
                xmlSerializer.endTag(null, LayoutXML.TOP_MARGIN);

                xmlSerializer.startTag(null, LayoutXML.SCALE_X);
                xmlSerializer.text(String.valueOf(input.getScaleX()));
                xmlSerializer.endTag(null, LayoutXML.SCALE_X);

                xmlSerializer.startTag(null, LayoutXML.SCALE_Y);
                xmlSerializer.text(String.valueOf(input.getScaleY()));
                xmlSerializer.endTag(null, LayoutXML.SCALE_Y);

                xmlSerializer.endTag(null, LayoutXML.GAMEPAD_INPUT);
            }

            xmlSerializer.endTag(null, LayoutXML.INPUTS);
            xmlSerializer.endTag(null, LayoutXML.ROOT_NODE);
            xmlSerializer.endDocument();
            String dataWrite = writer.toString();
            fos.write(dataWrite.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<String> getSavedLayouts(Context context) {
        ArrayList<String> layouts = new ArrayList<String>();
        layouts.add("DefaultSimple");
        layouts.add("DefaultComplex");
        for( File file : context.getFilesDir().listFiles() ) {
            layouts.add(file.getName().substring(0, file.getName().length() -4));
        }
        return layouts;
    }

    public static GamepadLayout load(Context context, String layoutName) {

        InputStream fis = null;
        GamepadLayout returnLayout = null;
        try {
            if(layoutName.equals("DefaultSimple"))
                fis = context.getResources().openRawResource(R.raw.layout_default_simple);
            else if (layoutName.equals("DefaultComplex"))
                fis = context.getResources().openRawResource(R.raw.layout_default_complex);
            else {
                String filename = layoutName + ".xml";
                fis = context.openFileInput(filename);
            }
            // create a XMLReader from SAXParser
            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            // create a SAXXMLHandler
            GamepadLayoutXMLHandler saxHandler = new GamepadLayoutXMLHandler(context);
            // store handler in XMLReader
            xmlReader.setContentHandler(saxHandler);
            // the process starts
            xmlReader.parse(new InputSource(fis));
            // get the `Employee list`
            returnLayout = saxHandler.getGamepadLayout();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fis != null) {
                try { fis.close(); }
                catch (IOException ignored) {}
            }
        }

        // return Employee list
        return returnLayout;

    }

    private static class GamepadLayoutXMLHandler extends DefaultHandler {

        private GamepadLayout layout;
        private String tempVal;
        private GamepadInputView tempInput;

        public GamepadLayoutXMLHandler(Context context) {
            layout = new GamepadLayout(context);
        }

        public GamepadLayout getGamepadLayout() {
            return layout;
        }

        // Event Handlers
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {
            // reset
            tempVal = "";
            if (qName.equalsIgnoreCase(LayoutXML.GAMEPAD_INPUT)) {
                tempInput = new GamepadInputView(layout.getContext());
                layout.addGamepadInput(tempInput);
            }
        }

        public void characters(char[] ch, int start, int length)
                throws SAXException {
            tempVal = new String(ch, start, length);
        }

        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            if (qName.equalsIgnoreCase(LayoutXML.NAME)) {
                layout.setName(tempVal);
            } else if (qName.equalsIgnoreCase(LayoutXML.ROTATE_ENABLED)) {
                layout.setRotateAsInput(Boolean.parseBoolean(tempVal));
            } else if (qName.equalsIgnoreCase(LayoutXML.ORIENTATION)) {
                layout.setPortrait(Boolean.parseBoolean(tempVal));
            } else if (qName.equalsIgnoreCase(LayoutXML.INPUT_ID)) {
                tempInput.setGamepadInput(GamepadInput.values()[Integer.parseInt(tempVal)]);
            } else if (qName.equalsIgnoreCase(LayoutXML.LEFT_MARGIN)) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tempInput.getLayoutParams();
                layoutParams.leftMargin = Integer.parseInt(tempVal);
                layoutParams.rightMargin = -250;
                layoutParams.bottomMargin = -250;
                tempInput.setLayoutParams(layoutParams);
            } else if (qName.equalsIgnoreCase(LayoutXML.TOP_MARGIN)) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tempInput.getLayoutParams();
                layoutParams.topMargin = Integer.parseInt(tempVal);
                layoutParams.rightMargin = -250;
                layoutParams.bottomMargin = -250;
                tempInput.setLayoutParams(layoutParams);
            } else if (qName.equalsIgnoreCase(LayoutXML.SCALE_X)) {
                tempInput.setScaleX(Float.parseFloat(tempVal));
            } else if (qName.equalsIgnoreCase(LayoutXML.SCALE_Y)) {
                tempInput.setScaleY(Float.parseFloat(tempVal));
            }
        }
    }
}
