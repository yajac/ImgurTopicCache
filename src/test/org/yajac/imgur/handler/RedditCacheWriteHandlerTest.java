package org.yajac.imgur.handler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.yajac.imgur.BaseTestClass;
import org.yajac.imgur.model.GatewayResponse;
import org.yajac.imgur.model.ImgurGalleryRequest;

public class RedditCacheWriteHandlerTest extends BaseTestClass {

    @Spy
    ImgurCacheWriteHandler handler;

    @Before
    public void setup(){
        Mockito.when(handler.getAmazonDynamoDB()).thenReturn(dynamoDB);
    }

    @Test
    public void handleRequest() throws Exception {
        ImgurGalleryRequest request = new ImgurGalleryRequest();
        request.getPathParameters().put("subreddit", "dogpictures");
        GatewayResponse response = handler.handleRequest(request, getContext());
        Assert.assertNotNull(response);
    }

}