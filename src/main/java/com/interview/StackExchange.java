package com.interview;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StackExchange {

    public List<String> getSiteNames() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().build());
        //add Connection Timeout
        clientHttpRequestFactory.setConnectTimeout(60*1000);
        //add Read Timeout
        clientHttpRequestFactory.setReadTimeout(60*1000);

        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);

        String url = "https://api.stackexchange.com/2.3/sites?filter=!2*t)slhVyGhlozCwxcHAU";
        SitesName response = null;
        try {
            response = restTemplate.getForObject(new URI(url),SitesName.class);
        } catch (URISyntaxException e) {
            //no handled exception
        }

        return response != null ? response.getItems().stream().map(ObjName::getName).collect(Collectors.toList()) : null;
    }

    //For Test
    public static void main(String[] args) {
        StackExchange stackExchange = new StackExchange();
        System.out.println(stackExchange.getSiteNames());
    }


}
