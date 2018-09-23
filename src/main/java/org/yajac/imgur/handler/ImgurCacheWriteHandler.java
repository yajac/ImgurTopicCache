package org.yajac.imgur.handler;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.yajac.imgur.model.ImgurGalleryRequest;
import org.yajac.imgur.client.SubRedditListing;
import org.yajac.imgur.model.GatewayResponse;
import org.yajac.persist.PersistCacheManager;

import java.util.Map;

/**
 * Handler for requests to Lambda function.
 */
public class ImgurCacheWriteHandler implements RequestHandler<ImgurGalleryRequest, GatewayResponse> {

    static final String table = "imgurGallery";

    public GatewayResponse handleRequest(final ImgurGalleryRequest input, final Context context) {
        final SubRedditListing client = new SubRedditListing();
        final String subreddit = input.getPathParameters().get("subreddit");
        context.getLogger().log("subreddit: " + subreddit);
        setCache(client, subreddit);
        return new GatewayResponse("{'Output': 'Success'}", 200);
    }

    private void setCache(SubRedditListing client, String subtopic) {
        AmazonDynamoDB amazonDynamoDB = getAmazonDynamoDB();
        PersistCacheManager persistCacheManager = new PersistCacheManager(amazonDynamoDB);
        Map<String, String> subtopics = client.getListingForSubTopic(subtopic);
        persistCacheManager.putEvents(table, subtopics.values());
    }

    protected AmazonDynamoDB getAmazonDynamoDB() {
        return AmazonDynamoDBClientBuilder.standard().build();
    }
}
