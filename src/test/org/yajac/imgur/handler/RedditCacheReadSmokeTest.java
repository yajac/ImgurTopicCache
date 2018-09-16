package org.yajac.imgur.handler;

import org.junit.Assert;
import org.junit.Test;
import org.yajac.imgur.BaseTestClass;
import org.yajac.imgur.model.GatewayResponse;
import org.yajac.imgur.model.ImgurGalleryRequest;


public class RedditCacheReadSmokeTest extends BaseTestClass {

    private ImgurCacheWriteHandler writeHandler = new ImgurCacheWriteHandler();
    private ImgurCacheReadHandler handler = new ImgurCacheReadHandler();

    @Test
    public void empty() throws Exception {

    }

    @Test
    public void handleRequest() throws Exception {
        ImgurGalleryRequest request = new ImgurGalleryRequest();
        request.getPathParameters().put("subreddit", "fluffy");
        GatewayResponse response = handler.handleRequest(request, getContext());
        Assert.assertNotNull(response);
        String bodies = response.getBody();
        Assert.assertNotNull(bodies);
    }

    @Test
    public void handleWriteRequest() throws Exception {
        ImgurGalleryRequest request = new ImgurGalleryRequest();
        request.getPathParameters().put("subreddit", "fluffy");
        GatewayResponse response = writeHandler.handleRequest(request, getContext());
        Assert.assertNotNull(response);
        String bodies = response.getBody();
        Assert.assertNotNull(bodies);
    }

}