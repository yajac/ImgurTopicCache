package org.yajac.imgur;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.yajac.imgur.handler.ImgurCacheReadHandler;
import org.yajac.persist.PersistCacheManager;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public abstract class BaseTestClass {

    public static final String TABLE = ImgurCacheReadHandler.table;

    protected AmazonDynamoDB dynamoDB;
    protected PersistCacheManager persistCacheManager;

    protected final String TEST = "{" +
            "            \"id\": \"YvlkTUn\",\n" +
            "            \"title\": \"Test\",\n" +
            "            \"description\": null,\n" +
            "            \"datetime\": 1536673218,\n" +
            "            \"type\": \"image/jpeg\",\n" +
            "            \"animated\": false,\n" +
            "            \"width\": 640,\n" +
            "            \"height\": 432,\n" +
            "            \"size\": 36215,\n" +
            "            \"views\": 351,\n" +
            "            \"bandwidth\": 12711465,\n" +
            "            \"vote\": null,\n" +
            "            \"favorite\": false,\n" +
            "            \"nsfw\": false,\n" +
            "            \"section\": \"test\",\n" +
            "            \"account_url\": null,\n" +
            "            \"account_id\": null,\n" +
            "            \"is_ad\": false,\n" +
            "            \"in_most_viral\": false,\n" +
            "            \"has_sound\": false,\n" +
            "            \"tags\": [],\n" +
            "            \"ad_type\": 0,\n" +
            "            \"ad_url\": \"\",\n" +
            "            \"in_gallery\": false,\n" +
            "            \"link\": \"https://i.imgur.com/YvlkTUn.jpg\",\n" +
            "            \"comment_count\": null,\n" +
            "            \"favorite_count\": null,\n" +
            "            \"ups\": null,\n" +
            "            \"downs\": null,\n" +
            "            \"points\": null,\n" +
            "            \"score\": 323,\n" +
            "            \"is_album\": false\n" +
            "}";

    @Before
    public void setupDatabase(){
        dynamoDB = DynamoDBEmbedded.create().amazonDynamoDB();
        createTable(dynamoDB, TABLE, PersistCacheManager.HASH);
        persistCacheManager = new PersistCacheManager(dynamoDB);
    }

    protected static CreateTableResult createTable(AmazonDynamoDB ddb, String tableName, String hashKeyName) {
        List<AttributeDefinition> attributeDefinitions = new ArrayList<>();
        attributeDefinitions.add(new AttributeDefinition(hashKeyName, ScalarAttributeType.S));

        List<KeySchemaElement> ks = new ArrayList<>();
        ks.add(new KeySchemaElement(hashKeyName, KeyType.HASH));

        ProvisionedThroughput provisionedthroughput = new ProvisionedThroughput(1000L, 1000L);

        CreateTableRequest request =
                new CreateTableRequest()
                        .withTableName(tableName)
                        .withAttributeDefinitions(attributeDefinitions)
                        .withKeySchema(ks)
                        .withProvisionedThroughput(provisionedthroughput);

        return ddb.createTable(request);
    }

    public Context getContext() {
        LambdaLogger logger = mock(LambdaLogger.class);
        Context context = mock(Context.class);
        when(context.getLogger()).thenReturn(logger);
        return context;
    }
}
