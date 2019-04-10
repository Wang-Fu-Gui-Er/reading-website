package com.reading.website.biz.enums;

/**
 * 图书评论方式枚举类
 *
 * @xyang010 2019/4/10
 */
public enum  BookReviewSortTypeEnum {

    TIME("time", "时间倒序"),
    HOT("hot", "热度倒序");

    private String type;
    private String desc;

    BookReviewSortTypeEnum(String type, String desc) {
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
