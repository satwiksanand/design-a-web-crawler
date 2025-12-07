package com.github.satwiksanand;

public class Pair implements Comparable<Pair>{
    private final Integer key;
    private Long value;

    Pair(Integer key, Long val){
        this.key = key;
        this.value = val;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Integer getKey(){
        return this.key;
    }

    public Long getValue(){
        return this.value;
    }


    @Override
    public int compareTo(Pair o) {
        return Long.compare(this.getValue(), o.getValue());
    }
}
