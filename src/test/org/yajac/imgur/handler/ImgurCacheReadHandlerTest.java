package org.yajac.imgur.handler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.yajac.imgur.BaseTestClass;
import org.yajac.imgur.model.GatewayResponse;
import org.yajac.imgur.model.ImgurGalleryRequest;

import java.util.ArrayList;
import java.util.List;


public class ImgurCacheReadHandlerTest extends BaseTestClass {




    @Spy
    ImgurCacheReadHandler handler;

    @Before
    public void setup(){
        Mockito.when(handler.getAmazonDynamoDB()).thenReturn(dynamoDB);
        List<String> items = new ArrayList<>();
        items.add(TEST);
        persistCacheManager.putEvents(TABLE, items);
    }

    @Test
    public void handleRequest() throws Exception {
        ImgurGalleryRequest request = new ImgurGalleryRequest();
        request.getPathParameters().put("subreddit", "dogpictures");
        GatewayResponse response = handler.handleRequest(request, getContext());
        Assert.assertNotNull(response);
        String bodies = response.getBody();
        Assert.assertNotNull(bodies);
    }

}