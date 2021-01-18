package sample;

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

import java.io.IOException;

public class BfgReviewApi {

    public static class SignupUser{

        public void SignUpUserJsonObject(JSONObject json) throws IOException {
            System.out.println("Deneme!");
            CloseableHttpClient httpClient = org.apache.hc.client5.http.impl.classic.HttpClientBuilder.create().build();

            try {
                HttpPost request = new HttpPost("https://bfg-review-api.herokuapp.com/user");
                StringEntity params = new StringEntity(json.toString());
                request.addHeader("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJQZXJzb25JRCI6MTAsImlhdCI6MTYxMDg0MDkwMSwiZXhwIjoxNjExNDQ1NzAxfQ.FwOWFbnZP75XNXshzJyMq9tbTdiVW38ql5jr_vyB5n8");
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

            CloseableHttpClient httpClient = org.apache.hc.client5.http.impl.classic.HttpClients.createDefault();

        try {

            HttpGet request = new org.apache.hc.client5.http.classic.methods.HttpGet("https://bfg-review-api.herokuapp.com/user");

            // add request headers
            request.addHeader("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJQZXJzb25JRCI6MTAsImlhdCI6MTYxMDg0MDkwMSwiZXhwIjoxNjExNDQ1NzAxfQ.FwOWFbnZP75XNXshzJyMq9tbTdiVW38ql5jr_vyB5n8");
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
                    JSONObject object = (JSONObject) jsonArray.get(0);
                    if (object.containsValue(json.get("Pasword")))
                    {
                        return object;
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
        return null;
        }

    }
}
