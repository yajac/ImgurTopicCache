package org.yajac.imgur.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpMethod.GET;

public class SubRedditListing {

    private static String CLIENT_ID = "Client-Id b80b1a8f464dbd9";
    private static String URL_BASE = "https://api.imgur.com/3/gallery/r/{subreddit}";
    private ObjectMapper mapper = new ObjectMapper();


    public Map<String, String> getListingForSubTopic(final String subtopic){
        try {
            final String subtopicJSON = getImgurResponse(subtopic);
            JsonNode node = mapper.readTree(subtopicJSON);
            return getListingData(node);
        } catch (IOException  e) {
            throw new ImgurException("Invalid return from Reddit");
        }
    }

    private String getImgurResponse(String subtopic) {
        final RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(URL_BASE, GET, new HttpEntity<byte[]>(getHeaders()), String.class, getURIParams(subtopic));
        if(!response.getStatusCode().is2xxSuccessful()){
            throw new ImgurException("Unable to connect to Imgur");
        }
        return response.getBody();
    }

    protected Map<String, String> getListingData(JsonNode node) throws JsonProcessingException {
        final Map<String, String>listings = new HashMap<>();
        final ArrayNode children = (ArrayNode) node.get("data");
        for(int i = 0; i < children.size(); i++){
            JsonNode listing = children.get(i);
            final String key = listing.get("id").textValue();
            final String value = mapper.writeValueAsString(listing);
            listings.put(key, value);
        }
        return listings;
    }

    private Map<String, String> getURIParams(final String subreddit){
        Map<String,String> myMap = new HashMap<String,String>();
        myMap.put("subreddit", subreddit);
        return myMap;
    }

    private HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, CLIENT_ID);
        return headers;
    }
}
