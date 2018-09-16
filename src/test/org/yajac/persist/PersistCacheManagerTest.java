package org.yajac.persist;

import com.amazonaws.services.dynamodbv2.document.Item;
import org.junit.Assert;
import org.junit.Test;
import org.yajac.imgur.BaseTestClass;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.yajac.persist.PersistCacheManager.INSERT_TIME;
import static org.yajac.persist.PersistCacheManager.TTL_TIME;

public class PersistCacheManagerTest extends BaseTestClass {


    private final String[] FIELDNAMES = {"section", "created_utc", "animated", "url", "nsfw"};


    @Test
    public void persist() throws Exception {
        List<String> items = new ArrayList<>();
        items.add(TEST);
        persistCacheManager.putEvents(TABLE, items);
        Item eventItem = Item.fromJSON(TEST);
        List<String> events = persistCacheManager.getEvents(TABLE, "test" );
        Assert.assertNotNull(events);
        Item eventItemReturn = Item.fromJSON(events.get(0));
        for(String field : FIELDNAMES) {
            Assert.assertEquals(eventItem.get(field), eventItemReturn.get(field));
        }
        Assert.assertTrue(((BigDecimal)eventItemReturn.get(INSERT_TIME)).longValue()<= System.currentTimeMillis() / 1000 + TTL_TIME);
    }


}