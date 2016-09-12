package net.mrlatte.khanjar.locations;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;

/**
 * Created by Jongha on 2/14/16.
 */
public class LocationKnifeTest extends TestCase {

    @Test
    public void testReadableDistance() throws Exception {
        Assert.assertEquals(LocationKnife.readableDistance(-1), "0m");
        Assert.assertEquals(LocationKnife.readableDistance(-100), "0m");
        Assert.assertEquals(LocationKnife.readableDistance(0), "0m");
        Assert.assertEquals(LocationKnife.readableDistance(999), "999m");
        Assert.assertEquals(LocationKnife.readableDistance(1000), "1km");
        Assert.assertEquals(LocationKnife.readableDistance(1500), "1.5km");
        Assert.assertEquals(LocationKnife.readableDistance(2000), "2km");
        Assert.assertEquals(LocationKnife.readableDistance(10000), "10km");
        Assert.assertEquals(LocationKnife.readableDistance(100000), "100km");
        Assert.assertEquals(LocationKnife.readableDistance(1000000), "1,000km");
    }
}