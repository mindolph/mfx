package com.mindolph.mfx.util;

/**
 * Hold object for lambda.
 * TODO This should be moved to more basic module.
 *
 * @author allen
 */
public class ObjectHolder<T> {

    private T value;

    public ObjectHolder() {
    }

    public ObjectHolder(T value) {
        this.value = value;
    }

    public T get(){
        return this.value;
    }

    public void set(T value){
        this.value = value;
    }

}
