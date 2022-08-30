package com.raf.models;

public class Approval {
    Integer accept;
    Integer id;

    public Approval() {
    }
    
    public Approval(Integer id, Integer accept) {
        this.accept = accept;
        this.id = id;
    }


    public Integer getAccept() {
        return this.accept;
    }

    public void setAccept(Integer accept) {
        this.accept = accept;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{" +
            " accept='" + getAccept() + "'" +
            ", id='" + getId() + "'" +
            "}";
    }
    


   
   
}


