package com.mindolph.mfx.drawing.component;

/**
 * Anchor to parent component.
 * If both sides are not set, the component should be centered in the parent container.
 *
 * @since 3.1
 */
public class Anchor {

    private Double top;
    private Double bottom;
    private Double left;
    private Double right;

    public Anchor() {
    }

    public Anchor(Double left, Double top, Double right, Double bottom) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    public static Anchor toLeft(double left) {
        return new Anchor(left, null, null, null);
    }

    public static Anchor toRight(double right) {
        return new Anchor(null, null, right, null);
    }

    public static Anchor toTop(double top) {
        return new Anchor(null, top, null, null);
    }

    public static Anchor toBottom(double bottom) {
        return new Anchor(null, null, null, bottom);
    }

    public static Anchor toLeftRight(double left, double right) {
        return new Anchor(left, null, right, null);
    }

    public static Anchor toLeftRight(double value) {
        return new Anchor(value, null, value, null);
    }

    public static Anchor toTopBottom(double top, double bottom) {
        return new Anchor(null, top, null, bottom);
    }

    public static Anchor toTopBottom(double value) {
        return new Anchor(null, value, null, value);
    }

    public static Anchor toLeftTop(double left, double top) {
        return new Anchor(left, top, null, null);
    }

    public static Anchor toLeftTop(double value) {
        return new Anchor(value, value, null, null);
    }

    public static Anchor toLeftBottom(double left, double bottom) {
        return new Anchor(left, null, null, bottom);
    }

    public static Anchor toLeftBottom(double value) {
        return new Anchor(value, null, null, value);
    }

    public static Anchor toRightTop(double right, double top) {
        return new Anchor(null, top, right, null);
    }

    public static Anchor toRightTop(double value) {
        return new Anchor(null, value, value, null);
    }

    public static Anchor toRightBottom(double right, double bottom) {
        return new Anchor(null, null, right, bottom);
    }

    public static Anchor toRightBottom(double value) {
        return new Anchor(null, null, value, value);
    }

    public Double getTop() {
        return top;
    }

    public Double getBottom() {
        return bottom;
    }

    public Double getTopBottom() {
        return (top == null ? 0 : top) + (bottom == null ? 0 : bottom);
    }

    public Double getLeft() {
        return left;
    }

    public Double getRight() {
        return right;
    }

    public Double getLeftRight() {
        return (left == null ? 0 : left) + (right == null ? 0 : right);
    }

    public void setTop(Double top) {
        this.top = top;
    }

    public void setBottom(Double bottom) {
        this.bottom = bottom;
    }

    public void setLeft(Double left) {
        this.left = left;
    }

    public void setRight(Double right) {
        this.right = right;
    }
}
