package com.newdawn.gui;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

import java.awt.geom.Point2D;

public class UtilsTest {

    @Test
    public void testConvertCoordinate() throws Exception {
        Point2D.Double initialValue = new Point2D.Double(350045648,1545648745);
        Point2D.Double calculatedValue = Utils.convertCoordinateFromSpaceToScreen(initialValue, 20000);
        Point2D.Double calculatedValue2 = Utils.convertCoordinateFromScreenToSpace(calculatedValue, 20000);
        Assert.assertEquals(initialValue, calculatedValue2);
    }

    public void testConvertCoordinateFromScreenToSpace() throws Exception {

    }
}