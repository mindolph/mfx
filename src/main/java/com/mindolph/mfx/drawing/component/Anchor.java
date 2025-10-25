package com.mindolph.mfx.drawing.component;

/**
 * Anchor to parent component.
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
