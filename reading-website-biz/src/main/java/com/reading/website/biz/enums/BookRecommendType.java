package com.reading.website.biz.enums;

/**
 * 图书推荐分类
 *
 * @xyang010 2019/3/12
 */
public enum BookRecommendType {
    NEWLY("newly", "最新推荐"),
    FAVORABLE("favorable", "最受欢迎"),
    CLASSIC("classic", "经典推荐");

    private String type;
    private String desc;

    BookRecommendType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
