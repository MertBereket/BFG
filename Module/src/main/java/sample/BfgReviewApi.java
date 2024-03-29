package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BfgReviewApi {

    public static class UserMethods{

        public void SignUpUserJsonObject(JSONObject json) throws IOException {
            System.out.println("Deneme!");
            CloseableHttpClient httpClient = org.apache.hc.client5.http.impl.classic.HttpClientBuilder.create().build();

            try {
                HttpPost request = new HttpPost("https://bfg-review-api.herokuapp.com/user");
                StringEntity params = new StringEntity(json.toString());
                request.addHeader("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJQZXJzb25JRCI6MTAsImlhdCI6MTYxMTY2NTAzNywiZXhwIjoxNjEyODc0NjM3fQ.1awyRnGvQnkMd2mGO4VEXEbkH0ZMgT0p8tzG-0P8nKY");
                request.addHeader("content-type", "application/json");
                request.setEntity(params);
                httpClient.execute(request);
                // handle response here...
            } catch (Exception ex) {
                // handle exception here
            } finally {
                httpClient.close();
            }
        }
        public JSONObject SignInJsonObject(JSONObject json) throws IOException
        {
            JSONObject nll = null;
            CloseableHttpClient httpClient = org.apache.hc.client5.http.impl.classic.HttpClients.createDefault();

        try {

            HttpGet request = new org.apache.hc.client5.http.classic.methods.HttpGet("https://bfg-review-api.herokuapp.com/user");

            // add request headers
            request.addHeader("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJQZXJzb25JRCI6MTAsImlhdCI6MTYxMTY2NTAzNywiZXhwIjoxNjEyODc0NjM3fQ.1awyRnGvQnkMd2mGO4VEXEbkH0ZMgT0p8tzG-0P8nKY");
            request.addHeader("content-type", "application/json");
            StringEntity params = new StringEntity(json.toString());
            request.setEntity(params);


            CloseableHttpResponse response = httpClient.execute(request);
            try {

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // return it as a String

                    String result = EntityUtils.toString(entity);
                    JSONParser parser = new JSONParser();
                    JSONArray jsonArray = (JSONArray) parser.parse(result);
                    if (jsonArray.isEmpty()==false)
                    {
                        JSONObject object = (JSONObject) jsonArray.get(0);
                        if (object.containsValue(json.get("Pasword")))
                        {
                            return object;
                        }
                    }
                    else return nll;


                }

            } catch (ParseException | org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
        return nll;
        }
        public ObservableList<UserMovie> GetUserMoviesJsonObject(JSONObject user) throws IOException
        {
            ObservableList<UserMovie> results = FXCollections.observableArrayList();
            System.out.println("INGETUSERMOVIES");
            JSONObject userid = new JSONObject();
            userid.put("PersonID",user.get("PersonID"));
            CloseableHttpClient httpClient = org.apache.hc.client5.http.impl.classic.HttpClients.createDefault();

            try {
                System.out.println("IN1");
                HttpGet request = new org.apache.hc.client5.http.classic.methods.HttpGet("https://bfg-review-api.herokuapp.com/user/movies");

                // add request headers
                request.addHeader("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJQZXJzb25JRCI6MTAsImlhdCI6MTYxMTY2NTAzNywiZXhwIjoxNjEyODc0NjM3fQ.1awyRnGvQnkMd2mGO4VEXEbkH0ZMgT0p8tzG-0P8nKY");
                request.addHeader("content-type", "application/json");
                StringEntity params = new StringEntity(userid.toString());
                request.setEntity(params);


                CloseableHttpResponse response = httpClient.execute(request);
                try {
                    System.out.println("IN2");
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        // return it as a String
                        System.out.println("IN3");
                        String result = EntityUtils.toString(entity);
                        JSONParser parser = new JSONParser();
                        JSONArray jsonArray = (JSONArray) parser.parse(result);
                        System.out.println("IN4");

                        if (jsonArray.isEmpty()==false)
                        {
                            System.out.println("Kullanici filmleri bulundu");
                            for (int i=0;i< jsonArray.size();i++)
                            {
                                JSONObject bulunan = (JSONObject) jsonArray.get(i);
                                UserMovie eklenen = new UserMovie();
                                eklenen.Title=new SimpleStringProperty(bulunan.get("Title").toString());
                                eklenen.Director=new SimpleStringProperty(bulunan.get("Director").toString());
                                eklenen.Relase=new SimpleStringProperty(bulunan.get("Years").toString());
                                eklenen.story= new SimpleIntegerProperty(Integer.parseInt(bulunan.get("Story").toString()));
                                eklenen.acting= new SimpleIntegerProperty(Integer.parseInt(bulunan.get("Acting").toString()));
                                eklenen.editing= new SimpleIntegerProperty(Integer.parseInt(bulunan.get("Editing").toString()));
                                eklenen.music= new SimpleIntegerProperty(Integer.parseInt(bulunan.get("Music").toString()));
                                eklenen.DateWatched= new SimpleStringProperty(bulunan.get("DateWatched").toString());
                                eklenen.Review= new SimpleStringProperty(bulunan.get("Review").toString());
                                eklenen.tmdbid= Integer.parseInt(bulunan.get("TMDB_ID").toString());
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
        public void PostUserMovieJsonObject(JSONObject json) throws IOException {
            System.out.println("Deneme!");
            CloseableHttpClient httpClient = org.apache.hc.client5.http.impl.classic.HttpClientBuilder.create().build();

            try {
                HttpPost request = new HttpPost("https://bfg-review-api.herokuapp.com/user/movies");
                StringEntity params = new StringEntity(json.toString());
                request.addHeader("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJQZXJzb25JRCI6MTAsImlhdCI6MTYxMTY2NTAzNywiZXhwIjoxNjEyODc0NjM3fQ.1awyRnGvQnkMd2mGO4VEXEbkH0ZMgT0p8tzG-0P8nKY");
                request.addHeader("content-type", "application/json");
                request.setEntity(params);
                httpClient.execute(request);
                // handle response here...
            } catch (Exception ex) {
                // handle exception here
            } finally {
                httpClient.close();
            }
        }
        public ObservableList<UserMovie> GetUserSeriesJsonObject(JSONObject user) throws IOException
        {
            ObservableList<UserMovie> results = FXCollections.observableArrayList();
            System.out.println("INGETUSERSeries");
            JSONObject userid = new JSONObject();
            userid.put("PersonID",user.get("PersonID"));
            CloseableHttpClient httpClient = org.apache.hc.client5.http.impl.classic.HttpClients.createDefault();

            try {
                System.out.println("IN1");
                HttpGet request = new org.apache.hc.client5.http.classic.methods.HttpGet("https://bfg-review-api.herokuapp.com/user/series");

                // add request headers
                request.addHeader("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJQZXJzb25JRCI6MTAsImlhdCI6MTYxMTY2NTAzNywiZXhwIjoxNjEyODc0NjM3fQ.1awyRnGvQnkMd2mGO4VEXEbkH0ZMgT0p8tzG-0P8nKY");
                request.addHeader("content-type", "application/json");
                StringEntity params = new StringEntity(userid.toString());
                request.setEntity(params);


                CloseableHttpResponse response = httpClient.execute(request);
                try {
                    System.out.println("IN2");
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        // return it as a String
                        System.out.println("IN3");
                        String result = EntityUtils.toString(entity);
                        JSONParser parser = new JSONParser();
                        JSONArray jsonArray = (JSONArray) parser.parse(result);
                        System.out.println("IN4");

                        if (jsonArray.isEmpty()==false)
                        {
                            System.out.println("Kullanici dizileri bulundu");
                            for (int i=0;i< jsonArray.size();i++)
                            {
                                JSONObject bulunan = (JSONObject) jsonArray.get(i);
                                UserMovie eklenen = new UserMovie();
                                eklenen.Title=new SimpleStringProperty(bulunan.get("Title").toString());
                                eklenen.Director=new SimpleStringProperty(bulunan.get("Producer").toString());
                                eklenen.Relase=new SimpleStringProperty(bulunan.get("Years").toString());
                                eklenen.story= new SimpleIntegerProperty(Integer.parseInt(bulunan.get("Story").toString()));
                                eklenen.acting= new SimpleIntegerProperty(Integer.parseInt(bulunan.get("Acting").toString()));
                                eklenen.editing= new SimpleIntegerProperty(Integer.parseInt(bulunan.get("Editing").toString()));
                                eklenen.music= new SimpleIntegerProperty(Integer.parseInt(bulunan.get("Music").toString()));
                                eklenen.DateWatched= new SimpleStringProperty(bulunan.get("DateWatched").toString());
                                eklenen.Review= new SimpleStringProperty(bulunan.get("Review").toString());
                                eklenen.tmdbid= Integer.parseInt(bulunan.get("TMDB_ID").toString());
                                results.add(eklenen);
                            }
                            return results;

                        }
                        else
                        {
                            System.out.println("Kullanici dizi incelememis");
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
        public void PostUserSeriesJsonObject(JSONObject json) throws IOException {
            System.out.println("Deneme!");
            CloseableHttpClient httpClient = org.apache.hc.client5.http.impl.classic.HttpClientBuilder.create().build();

            try {
                HttpPost request = new HttpPost("https://bfg-review-api.herokuapp.com/user/series");
                StringEntity params = new StringEntity(json.toString());
                request.addHeader("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJQZXJzb25JRCI6MTAsImlhdCI6MTYxMTY2NTAzNywiZXhwIjoxNjEyODc0NjM3fQ.1awyRnGvQnkMd2mGO4VEXEbkH0ZMgT0p8tzG-0P8nKY");
                request.addHeader("content-type", "application/json");
                request.setEntity(params);
                httpClient.execute(request);
                // handle response here...
            } catch (Exception ex) {
                // handle exception here
            } finally {
                httpClient.close();
            }
        }
        public String SuggestJsonObject(String sgst) throws IOException
        {
            sgst = sgst.replaceAll(" ","%20");
            String movies="";
            CloseableHttpClient httpClient = org.apache.hc.client5.http.impl.classic.HttpClients.createDefault();

            try {

                HttpGet request = new org.apache.hc.client5.http.classic.methods.HttpGet("http://127.0.0.1:5000/title?word="+sgst);
                CloseableHttpResponse response = httpClient.execute(request);
                try {

                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        // return it as a String

                        String result = EntityUtils.toString(entity);
                        JSONParser parser = new JSONParser();
                        JSONObject jsonObject = (JSONObject) parser.parse(result);
                        for(Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
                            String key = (String) iterator.next();
                            movies+=jsonObject.get(key).toString()+"\n";
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
            return movies;
        }

    }
}
