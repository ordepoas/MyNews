package com.aftebi.mynews.service;

import com.aftebi.mynews.model.TechNews;
import com.aftebi.mynews.model.Zapping;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ZappingParser {
    private List<Zapping> zappingList = new ArrayList<>();
    private XmlPullParserFactory parserFactory;
    private URL url;

    public ZappingParser(URL url){
        this.url = url;
    }

    public List<Zapping> getZappingList() {
        return zappingList;
    }

    public void parseXML() {

        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();

            InputStream is = url.openConnection().getInputStream();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            processParsing(parser);

        } catch (XmlPullParserException e) {


        } catch (IOException e) {

        }

    }

    private void processParsing(XmlPullParser parser) throws IOException, XmlPullParserException {

        int eventType = parser.getEventType();
        Zapping zapping = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String eltName = null;

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();
                    if ("item".equals(eltName)) {
                        zapping = new Zapping();
                        zappingList.add(zapping);
                    } else if (zapping != null) { //title, link, description, category, pubdate
                        if ("title".equals(eltName)) {
                            //currentTechNews.name = parser.nextText(); Para Referência
                            zapping.setTitle(parser.nextText());
                        } else if ("link".equals(eltName)) {
                            zapping.setLink(parser.nextText());
                        } else if ("description".equals(eltName)) {
                            zapping.setDescription(parser.nextText());
                        } else if("pubDate".equals(eltName)){
                            zapping.setPubDate(parser.nextText());
                        }
                    }
                    break;
            }
            eventType = parser.next();
        }
    }
}
