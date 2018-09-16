package org.yajac.imgur.handler;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.yajac.imgur.model.ImgurGalleryRequest;
import org.yajac.persist.PersistCacheManager;
import org.yajac.imgur.model.GatewayResponse;

import java.util.List;

/**
 * Handler for requests to Lambda function.
 */
public class ImgurCacheReadHandler implements RequestHandler<ImgurGalleryRequest, GatewayResponse> {

    public static final String table = "imgurGallery";

    public GatewayResponse handleRequest(final ImgurGalleryRequest input, final Context context) {
        final String subtopic = input.getPathParameters().get("subreddit");
        context.getLogger().log("subreddit: " + subtopic);
        try {
            String body = getBody(subtopic);
            context.getLogger().log("Body: " + body);
            return new GatewayResponse(body, 200);
        } catch (Exception e){
            context.getLogger().log("Error: " + e);
            return new GatewayResponse("{'Error':'" + e.getMessage() + "'}", 500);
        }
    }

    private String getBody(final String subreddit) {
        List<String> cacheValues = getCacheValues(subreddit);
        StringBuffer body = new StringBuffer("[");
        body.append(String.join(",", cacheValues));
        body.append("]");
        return body.toString();
    }

    protected List<String> getCacheValues(String subreddit) {
        AmazonDynamoDB amazonDynamoDB = getAmazonDynamoDB();
        PersistCacheManager persistCacheManager = new PersistCacheManager(amazonDynamoDB);
        return persistCacheManager.getEvents(table, subreddit);
    }

    protected AmazonDynamoDB getAmazonDynamoDB() {
        return AmazonDynamoDBClientBuilder.standard().build();
    }
}
