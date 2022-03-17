package com.psbc.rpa.model;

import java.io.Serializable;

/**
 * @author dahua
 * @time 2022/3/10 9:36
 */
public class Result implements Serializable {

    private boolean state;
    private Object obj;

    public Result(boolean state) {
        this.state = state;
    }

    public Result(boolean state, Object obj) {
        this.state = state;
        this.obj = obj;
    }

    public static Result success() {
        return new Result(true);
    }

    public static Result success(Object obj) {
        return new Result(true, obj);
    }

    public static Result error() {
        return new Result(false);
    }

    public static Result error(Object obj) {
        return new Result(false, obj);
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
