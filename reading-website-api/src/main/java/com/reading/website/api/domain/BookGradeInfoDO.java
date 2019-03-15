package com.reading.website.api.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 图书评分信息
 *
 * @yx8102 2019/3/15
 */
@Data
@ApiModel(value = "图书评分实体对象")
public class BookGradeInfoDO implements Serializable {
    // 主键
    private Integer id;

    private Date created;

    private Date updated;

    private Boolean isDeleted;

    // 图书id
    private Integer bookId;

    // 阅读次数
    private Integer readCount;

    // 下载次数
    private Integer downloadCount;

    // 评分为1次数
    private Integer oneNum;

    // 评分为2次数
    private Integer twoNum;

    // 评分为3次数
    private Integer threeNum;

    // 评分为4次数
    private Integer fourNum;

    // 评分为5次数
    private Integer fiveNum;

    // 平均分
    private Integer avgScore;

    public Integer getAvgScore() {
        int sum = oneNum + twoNum + threeNum + fourNum + fiveNum;
        this.avgScore = sum == 0 ? 0 : (int)Math.ceil((oneNum * 1 + twoNum * 2 + threeNum * 3 + fourNum * 4 + fiveNum * 5) / sum);
        return avgScore;
    }
}