package com.reading.website.api.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserGradeInfoDO implements Serializable {
    private Integer id;

    private Date created;

    private Date updated;

    private Boolean isDeleted;

    private Integer userId;

    private Integer bookId;

    private Integer grade;

}