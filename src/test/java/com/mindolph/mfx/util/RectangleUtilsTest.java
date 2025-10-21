package com.mindolph.mfx.util;

import javafx.geometry.Rectangle2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RectangleUtilsTest {
    final Rectangle2D r1 = new Rectangle2D(0, 0, 100, 100);
    final Rectangle2D r2 = new Rectangle2D(50, 50, 100, 100);
    final Rectangle2D r3 = new Rectangle2D(200, 200, 100, 100);

    @Test
    public void intersect() {
        Rectangle2D intersect = RectangleUtils.intersect(r1, r2);
        System.out.println(intersect);
        Assertions.assertEquals(new Rectangle2D(50, 50, 50, 50), intersect);
        intersect = RectangleUtils.intersect(r2, r3);
        Assertions.assertNull(intersect);
    }

    @Test
    public void union() {
        Rectangle2D union = RectangleUtils.union(r1, r2);
        System.out.println(union);
        Assertions.assertEquals(new Rectangle2D(0, 0, 150, 150), union);
        union = RectangleUtils.union(r2, r3);
        Assertions.assertEquals(new Rectangle2D(50, 50, 250, 250), union);
    }

    @Test
    public void enlarge() {
        Rectangle2D rectangle2D = new  Rectangle2D(50, 50, 100, 100);
        Assertions.assertEquals(new Rectangle2D(40, 40, 120, 120), RectangleUtils.enlarge(rectangle2D, 10));
        Assertions.assertEquals(new Rectangle2D(40, 30, 120, 140), RectangleUtils.enlarge(rectangle2D, 10, 20));
        Assertions.assertEquals(new Rectangle2D(45, 40, 120, 130), RectangleUtils.enlarge(rectangle2D, 5, 10, 15, 20));
    }
}
