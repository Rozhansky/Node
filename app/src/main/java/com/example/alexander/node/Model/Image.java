package com.example.alexander.node.Model;

import java.io.Serializable;

/**
 * Created by W11B on 29.10.2017.
 */

public class Image implements Serializable {
    int id;
    String path;
    int pos;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
