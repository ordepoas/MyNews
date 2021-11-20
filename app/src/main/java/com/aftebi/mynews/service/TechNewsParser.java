package com.aftebi.mynews.service;

import com.aftebi.mynews.model.TechNews;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TechNewsParser {

    private List<TechNews> techNewsList = new ArrayList<>();
    private XmlPullParserFactory parserFactory;
    private URL url;

    public TechNewsParser(URL url){
        this.url = url;
    }

    public List<TechNews> getTechNewsList() {
        return techNewsList;
    }

    public void parseXML() {

        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();

            //URL url = new URL("https://4gnews.pt/feed/");
            InputStream is = url.openConnection().getInputStream();
            //InputStream is = getAssets().open("data.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            processParsing(parser);

        } catch (XmlPullParserException e) {


        } catch (IOException e) {

        }

    }

    private void processParsing(XmlPullParser parser) throws IOException, XmlPullParserException {

        int eventType = parser.getEventType();
        TechNews currentTechNews = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String eltName = null;

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();
                    if ("item".equals(eltName)) {
                        currentTechNews = new TechNews();
                        techNewsList.add(currentTechNews);
                    } else if (currentTechNews != null) { //title, link, description, category, pubdate
                        if ("title".equals(eltName)) {
                            //currentTechNews.name = parser.nextText(); Para Referência
                            currentTechNews.setTitle(parser.nextText());
                        } else if ("link".equals(eltName)) {
                            currentTechNews.setLink(parser.nextText());
                        } else if ("content:encoded".equals(eltName)) {
                            currentTechNews.setDescription(parser.nextText());
                        } else if ("category".equals(eltName)) {
                            currentTechNews.setCategory(parser.nextText());
                        } else if("pubDate".equals(eltName)){
                            currentTechNews.setPubDate(parser.nextText());
                        }
                    }
                    break;
            }
            eventType = parser.next();
        }
    }

}
