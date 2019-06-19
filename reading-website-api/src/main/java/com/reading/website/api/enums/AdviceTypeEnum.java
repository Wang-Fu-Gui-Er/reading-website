package com.reading.website.api.enums;

import java.util.Arrays;

/**
 * 反馈类型枚举类
 *
 * @xyang010 2019/3/21
 */
public enum AdviceTypeEnum {
    NOT_FOUND(0, "缺失"),
    REPORT(1, "举报"),
    PROPOSAL(2, "建议");

    private Integer code;
    private String desc;

    AdviceTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static AdviceTypeEnum getAdviceType(Integer code) {
        for (AdviceTypeEnum adviceTypeEnum : AdviceTypeEnum.values()) {
            if (adviceTypeEnum.getCode().equals(code)) {
                return adviceTypeEnum;
            }
        }

        return null;
    }
}
