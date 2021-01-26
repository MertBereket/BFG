package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.util.Iterator;

public class TmdbApiFunctions {

    public static class moviedata {
        public String apikey="2389cb0185b69d2a8714d7c39c9f738c";
        public JSONObject getTmdbMovie(int movieid) throws IOException {

            JSONObject movie = new JSONObject();
            CloseableHttpClient httpClient = org.apache.hc.client5.http.impl.classic.HttpClients.createDefault();

            try {

                HttpGet request = new org.apache.hc.client5.http.classic.methods.HttpGet("https://api.themoviedb.org/3/movie/"+String.valueOf(movieid)+"?api_key="+ apikey);
                CloseableHttpResponse response = httpClient.execute(request);
                try {

                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        // return it as a String

                        String result = EntityUtils.toString(entity);
                        JSONParser parser = new JSONParser();
                        movie = (JSONObject) parser.parse(result);
                        return movie;
                    }

                } catch (ParseException | org.json.simple.parser.ParseException e) {
                    e.printStackTrace();
                } finally {
                    response.close();
                }
            } finally {
                httpClient.close();
            }
            return movie;
        }
        public JSONArray getTmdbMovieCast(int movieid) throws IOException {

            JSONArray cast = new JSONArray();
            CloseableHttpClient httpClient = org.apache.hc.client5.http.impl.classic.HttpClients.createDefault();

            try {

                HttpGet request = new org.apache.hc.client5.http.classic.methods.HttpGet("https://api.themoviedb.org/3/movie/"+String.valueOf(movieid)+"/credits"+"?api_key="+ apikey);
                CloseableHttpResponse response = httpClient.execute(request);
                try {

                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        // return it as a String

                        String result = EntityUtils.toString(entity);
                        JSONParser parser = new JSONParser();
                        JSONObject film = (JSONObject) parser.parse(result);
                        JSONArray array = (JSONArray) film.get("cast");
                        cast.add(array.get(0));
                        array = (JSONArray) film.get("crew");
                        Iterator<JSONObject> iterator = array.iterator();
                        while (iterator.hasNext())
                        {
                            JSONObject current = iterator.next();
                            if (current.containsKey("job") && current.get("job").toString().equals("Director"))
                            {
                                cast.add(current);
                            }
                        }
                        return cast;
                    }

                } catch (ParseException | org.json.simple.parser.ParseException e) {
                    e.printStackTrace();
                } finally {
                    response.close();
                }
            } finally {
                httpClient.close();
            }
            return cast;
        }
        public ObservableList<UserMovie> GetUserMovieSearch(String query) throws IOException
        {
            ObservableList<UserMovie> results = FXCollections.observableArrayList();
            System.out.println("INGETUSERMOVIES");
            CloseableHttpClient httpClient = org.apache.hc.client5.http.impl.classic.HttpClients.createDefault();

            try {
                System.out.println("IN1");
                HttpGet request = new org.apache.hc.client5.http.classic.methods.HttpGet("https://api.themoviedb.org/3/search/movie?api_key=2389cb0185b69d2a8714d7c39c9f738c&language=en-US&query="+query+"&page=1&include_adult=false");

                CloseableHttpResponse response = httpClient.execute(request);
                try {
                    System.out.println("IN2");
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        // return it as a String
                        System.out.println("IN3");
                        String result = EntityUtils.toString(entity);
                        JSONParser parser = new JSONParser();
                        JSONObject search = (JSONObject) parser.parse(result);
                        JSONArray jsonArray = (JSONArray) search.get("results");
                        System.out.println("IN4");

                        if (jsonArray.isEmpty()==false)
                        {
                            for (int i=0;i< jsonArray.size();i++)
                            {
                                JSONObject bulunan = (JSONObject) jsonArray.get(i);
                                UserMovie eklenen = new UserMovie();
                                eklenen.Title=new SimpleStringProperty(bulunan.get("title").toString());
                                eklenen.Relase=new SimpleStringProperty(bulunan.get("release_date").toString());
                                eklenen.tmdbid= Integer.parseInt(bulunan.get("id").toString());
                                results.add(eklenen);
                            }
                            return results;

                        }
                        else
                        {
                            System.out.println("Kullanici film incelememis");
                        }

                    }

                } catch (ParseException | org.json.simple.parser.ParseException e) {
                    e.printStackTrace();
                } finally {
                    response.close();
                }
            } finally {
                httpClient.close();
            }
            return results;
        }
        public JSONObject getTmdbSeries(int serieid) throws IOException {

            JSONObject series = new JSONObject();
            CloseableHttpClient httpClient = org.apache.hc.client5.http.impl.classic.HttpClients.createDefault();

            try {

                HttpGet request = new org.apache.hc.client5.http.classic.methods.HttpGet("https://api.themoviedb.org/3/tv/"+String.valueOf(serieid)+"?api_key="+ apikey);
                CloseableHttpResponse response = httpClient.execute(request);
                try {

                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        // return it as a String

                        String result = EntityUtils.toString(entity);
                        JSONParser parser = new JSONParser();
                        series = (JSONObject) parser.parse(result);
                        return series;
                    }

                } catch (ParseException | org.json.simple.parser.ParseException e) {
                    e.printStackTrace();
                } finally {
                    response.close();
                }
            } finally {
                httpClient.close();
            }
            return series;
        }
        public JSONArray getTmdbSeriesCast(int movieid) throws IOException {

            JSONArray cast = new JSONArray();
            CloseableHttpClient httpClient = org.apache.hc.client5.http.impl.classic.HttpClients.createDefault();

            try {

                HttpGet request = new org.apache.hc.client5.http.classic.methods.HttpGet("https://api.themoviedb.org/3/tv/"+String.valueOf(movieid)+"/credits"+"?api_key="+ apikey);
                CloseableHttpResponse response = httpClient.execute(request);
                try {

                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        // return it as a String

                        String result = EntityUtils.toString(entity);
                        JSONParser parser = new JSONParser();
                        JSONObject film = (JSONObject) parser.parse(result);
                        JSONArray array = (JSONArray) film.get("cast");
                        cast.add(array.get(0));
                        array = (JSONArray) film.get("crew");
                        Iterator<JSONObject> iterator = array.iterator();
                        while (iterator.hasNext())
                        {
                            JSONObject current = iterator.next();
                            if (current.containsKey("job") && current.get("job").toString().equals("Director"))
                            {
                                cast.add(current);
                            }
                        }
                        return cast;
                    }

                } catch (ParseException | org.json.simple.parser.ParseException e) {
                    e.printStackTrace();
                } finally {
                    response.close();
                }
            } finally {
                httpClient.close();
            }
            return cast;
        }
        public ObservableList<UserMovie> GetUserSeriesSearch(String query) throws IOException
        {
            ObservableList<UserMovie> results = FXCollections.observableArrayList();
            System.out.println("INGETUSERseriessearch");
            CloseableHttpClient httpClient = org.apache.hc.client5.http.impl.classic.HttpClients.createDefault();

            try {
                System.out.println("IN1");
                HttpGet request = new org.apache.hc.client5.http.classic.methods.HttpGet("https://api.themoviedb.org/3/search/tv?api_key=2389cb0185b69d2a8714d7c39c9f738c&language=en-US&query="+query+"&page=1&include_adult=false");

                CloseableHttpResponse response = httpClient.execute(request);
                try {
                    System.out.println("IN2");
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        // return it as a String
                        System.out.println("IN3");
                        String result = EntityUtils.toString(entity);
                        JSONParser parser = new JSONParser();
                        JSONObject search = (JSONObject) parser.parse(result);
                        JSONArray jsonArray = (JSONArray) search.get("results");
                        System.out.println("IN4");

                        if (jsonArray.isEmpty()==false)
                        {
                            for (int i=0;i< jsonArray.size();i++)
                            {
                                JSONObject bulunan = (JSONObject) jsonArray.get(i);
                                UserMovie eklenen = new UserMovie();
                                eklenen.Title=new SimpleStringProperty(bulunan.get("name").toString());
                                eklenen.Relase=new SimpleStringProperty(bulunan.get("first_air_date").toString());
                                eklenen.tmdbid= Integer.parseInt(bulunan.get("id").toString());
                                results.add(eklenen);
                            }
                            return results;

                        }
                        else
                        {
                            System.out.println("Series not found");
                        }

                    }

                } catch (ParseException | org.json.simple.parser.ParseException e) {
                    e.printStackTrace();
                } finally {
                    response.close();
                }
            } finally {
                httpClient.close();
            }
            return results;
        }

    }
}
