package com.example.imdbapp;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MovObj {
    private String title;
    private String image;
    private double rating;
    private int releaseYear;
    private List<String> genre;

    MovObj(String tt,String imj, double rate, int year, List<String> list) {
        this.title = tt;
        this.image = findImageURL(imj);
        this.rating = rate;
        this.releaseYear = year;
        this.genre = list;
    }

    public String getTitle(){
        return title;
    }

    public String getImage(){
        return image;
    }

    public double getRating(){
        return rating;
    }

    public int getReleaseYear(){
        return releaseYear;
    }

    public List<String> getGenre(){
        return genre;
    }

    public String stringtify_genre(){
        String rs = "";
        for (int i=0; i<this.genre.size();i++) {
            if (i == 0){
                rs = this.genre.get(i);
            }
            else
                rs = rs + ", " + this.genre.get(i);
        }
        rs.substring(1);
        return rs;
    }

    private String findImageURL (String page){
        String imageLink=null;
        if(page.endsWith("jpg")||page.endsWith("jpeg")||page.endsWith("png")||page.endsWith("gif")||page.endsWith("bmp"))
            return page;
        try {
            Log.d(TAG, "Connecting to [" + page + "]");
            Document doc = Jsoup.connect(page).get();
            // Get meta info
            Elements metaElems = doc.select("meta");

            for (Element metaElem : metaElems) {
                String property = metaElem.attr("property");
                if(property.equals("og:image"))// if find the line with the image
                {
                    imageLink = metaElem.attr("content");
                    Log.d(TAG, "Image URL" + imageLink );
                }
            }
            return imageLink;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
