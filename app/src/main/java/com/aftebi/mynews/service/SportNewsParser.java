package com.aftebi.mynews.service;

import com.aftebi.mynews.model.SportNews;
import com.aftebi.mynews.model.TechNews;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SportNewsParser {

    private List<SportNews> sportNewsList = new ArrayList<>();
    private XmlPullParserFactory parserFactory;
    private URL url; //https://www.rtp.pt/noticias/rss/desporto

    public SportNewsParser(URL url){
        this.url = url;
    }

    public List<SportNews> getSportNewsList() {
        return sportNewsList;
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
        SportNews sn = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String eltName;

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();
                    if ("item".equals(eltName)) {
                        sn = new SportNews();
                        sportNewsList.add(sn);
                    } else if (sn != null) { //title, link, description, category, pubdate
                        if ("title".equals(eltName)) {
                            //currentTechNews.name = parser.nextText(); Para Referência
                            sn.setTitle(parser.nextText());
                        } else if ("link".equals(eltName)) {
                            sn.setLink(parser.nextText());
                        } else if ("content:encoded".equals(eltName)) {
                            sn.setDescription(parser.nextText());
                        } else if ("category".equals(eltName)) {
                            sn.setCategory(parser.nextText());
                        } else if("pubDate".equals(eltName)){
                            sn.setPubDate(parser.nextText());
                        }
                    }
                    break;
            }
            eventType = parser.next();
        }
    }

}
