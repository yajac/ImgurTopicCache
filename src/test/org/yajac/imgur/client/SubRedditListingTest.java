package org.yajac.imgur.client;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class SubRedditListingTest {

    @Test
    public void getListingForSubTopic() throws Exception {
        SubRedditListing listing = new SubRedditListing();
        Map<String, String> listings = listing.getListingForSubTopic("test");
        Assert.assertNotNull(listings);
        Assert.assertTrue(listings.size() == 100);
    }

}