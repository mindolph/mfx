package com.mindolph.mfx.drawing.constant;

import java.util.EnumSet;
import java.util.Set;

/**
 * @since 3.1
 */
public enum ExtendDirection {
    LEFT,
    RIGHT,
    UP,
    DOWN;

    public static final Set<ExtendDirection> LEFT_UP = EnumSet.of(LEFT, UP);
    public static final Set<ExtendDirection> LEFT_DOWN = EnumSet.of(LEFT, DOWN);
    public static final Set<ExtendDirection> RIGHT_UP = EnumSet.of(RIGHT, UP);
    public static final Set<ExtendDirection> RIGHT_DOWN = EnumSet.of(RIGHT, DOWN);

//    public static final Set<ExtendDirection> LEFT_ = LEFT_DOWN;
}
