package com.reading.website.api.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 图书保存VO
 *
 * @xyang010 2019/4/24
 */
@Data
public class BookSaveDTO implements Serializable {

    // 图书
    private BookDO bookDO;

    // 章节列表
    private List<ChapterDO> chapterList;
}
