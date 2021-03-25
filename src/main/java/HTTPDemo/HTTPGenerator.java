package HTTPDemo;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HTTPGenerator {
    /*
     * Ok, so you can get a quickstart here: http://hc.apache.org/httpcomponents-client-4.5.x/quickstart.html
     * I would use the Apache library for any https stuff in java because java is one of the worst languages for net calls.
     * For your needs, I would recommend using the FLUENT API.  It allows simple one line calls like you see below.
     */
    private String connectionUrl;

    /* I recommend this way */
    public HTTPGenerator(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public String POST() {
        try {
            System.out.println(Request.Get(connectionUrl)
                    .execute().returnContent());
            System.out.println(Request.Post(connectionUrl)
                    .bodyForm(Form.form().add("first_name", "Matthew")
                            .add("last_name", "Adamson").build())
                    .execute().returnContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    /* I do not recommend this way but if you need the flexibility go for it */

    public String PostNOFluentAPI() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(connectionUrl);
        CloseableHttpResponse response1 = httpclient.execute(httpGet);
    // The underlying HTTP connection is still held by the response object
    // to allow the response content to be streamed directly from the network socket.
    // In order to ensure correct deallocation of system resources
    // the user MUST call CloseableHttpResponse#close() from a finally clause.
    // Please note that if response content is not fully consumed the underlying
    // connection cannot be safely re-used and will be shut down and discarded
    // by the connection manager.
        try {
            System.out.println(response1.getStatusLine());
            HttpEntity entity1 = response1.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity1);
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            try {
                response1.close();
            } catch(IOException IO2) {
                IO2.printStackTrace();
            }
        }

        HttpPost httpPost = new HttpPost(connectionUrl);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("first_name", "Matthew"));
        nvps.add(new BasicNameValuePair("last_name", "Adamson"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        CloseableHttpResponse response2 = httpclient.execute(httpPost);

        try {
            System.out.println(response2.getStatusLine());
            HttpEntity entity2 = response2.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity2);
        } finally {
            response2.close();
        }
        return "";
    }
}
