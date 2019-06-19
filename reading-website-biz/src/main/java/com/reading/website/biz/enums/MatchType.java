package com.reading.website.biz.enums;

/**
 * 敏感词查询匹配类型
 *
 * @xyang010 2019/6/19
 */
public enum MatchType {

    MIN_MATCH("最小匹配规则"),
    MAX_MATCH("最大匹配规则");

    String desc;

    MatchType(String desc) {
        this.desc = desc;
    }
}
