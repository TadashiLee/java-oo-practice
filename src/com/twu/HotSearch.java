package com.twu;

public class HotSearch implements Comparable<HotSearch>{
    private String name;
    private Integer poll=0;
    private int level;
    private Integer price=0;
    private Integer rank=0;

    public HotSearch() {
    }

    public HotSearch(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPoll() {
        return poll;
    }

    public void setPoll(Integer poll) {
        this.poll = poll;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Override
    public int compareTo(HotSearch hotSearch) {
        return new Integer((hotSearch.getPoll() - this.poll)).intValue();
    }
}
