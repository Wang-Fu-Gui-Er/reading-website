package com.reading.website.api.enums;

/**
 * 反馈处理进度
 *
 * @xyang010 2019/3/21
 */
public enum AdviceHandleStatusEnum {
    PENDING(0, "待处理"),
    PROCESSING(1, "正在处理"),
    DONE(2, "已完成"),
    REJECTED(3, "被拒绝");


    private Integer code;
    private String desc;

    AdviceHandleStatusEnum(Integer code, String desc) {
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

    public static AdviceHandleStatusEnum getAdviceStatus(Integer code) {
        for (AdviceHandleStatusEnum handleStatusEnum : AdviceHandleStatusEnum.values()) {
            if (handleStatusEnum.getCode().equals(code)) {
                return handleStatusEnum;
            }
        }

        return null;
    }
}
