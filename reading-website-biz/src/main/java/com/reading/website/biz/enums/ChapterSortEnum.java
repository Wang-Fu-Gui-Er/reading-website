package com.reading.website.biz.enums;

/**
 * 章节排序枚举类
 *
 * @xyang010 2019/4/24
 */
public enum  ChapterSortEnum {
    ASC("asc", "正序"),
    DESC("desc", "倒序");

    private String type;
    private String desc;

    ChapterSortEnum(String type, String desc) {
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
