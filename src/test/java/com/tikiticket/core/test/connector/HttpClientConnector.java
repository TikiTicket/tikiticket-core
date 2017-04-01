package com.tikiticket.core.test.connector;

import com.tikiticket.core.Context;
import com.tikiticket.core.Credentials;
import com.tikiticket.core.base.BaseConnector;
import com.tikiticket.core.exception.TikiTicketException;
import com.tikiticket.core.util.Util;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by veinhorn on 28.3.17.
 */
public class HttpClientConnector extends BaseConnector {
    private CloseableHttpClient httpClient;
    private Credentials credentials;

    public HttpClientConnector(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public HttpClientConnector(CloseableHttpClient httpClient, Credentials credentials) {
        this.httpClient = httpClient;
        this.credentials = credentials;
    }

    @Override
    public Context doGet(String url) throws TikiTicketException {
        HttpGet httpGet = new HttpGet(url);
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            return fromResponse(response);
        } catch (IOException e) {
            throw new TikiTicketException("Cannot execute GET request", e);
        }
    }

    @Override
    public Context doPost(String url, Map<String, String> params) throws TikiTicketException {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> pairs = fromMap(params);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(pairs));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            return fromResponse(response);
        } catch (IOException e) {
            throw new TikiTicketException("Cannot execute POST request", e);
        }
    }

    @Override
    public Credentials getCredentials() {
        return credentials == null ? new DefaultCredentials() : credentials;
    }

    private Context fromResponse(CloseableHttpResponse response) throws IOException {
        String res = EntityUtils.toString(response.getEntity());
        Header[] headers = response.getAllHeaders();
        return Util.newContext(res, toMap(headers));
    }

    private List<NameValuePair> fromMap(Map<String, String> params) {
        List<NameValuePair> pairs = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return pairs;
    }

    private Map<String, String> toMap(Header[] headers) {
        Map<String, String> mapHeaders = new HashMap<>();
        for (Header header : headers) mapHeaders.put(header.getName(), header.getValue());
        return mapHeaders;
    }
}
