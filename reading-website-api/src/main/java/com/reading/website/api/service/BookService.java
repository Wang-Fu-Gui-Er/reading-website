package com.reading.website.api.service;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.Page;
import com.reading.website.api.domain.BookDO;
import com.reading.website.api.domain.BookInfoQuery;

import java.util.List;

/**
 * 图书服务
 *
 * @xyang010 2019/3/12
 */
public interface BookService {

    /**
     * 根据图书id查询
     * @param bookId 图书id
     * @return 图书实体
     */
    BaseResult<BookDO> selectByBookId(Integer bookId);

    /**
     * 新增或更新
     * @param bookDO 图书实体
     * @return 图书id
     */
    BaseResult<Integer> insertOrUpdateBook(BookDO bookDO);

    /**
     * 删除图书
     * @param bookId 图书id
     * @return 是否成功
     */
    BaseResult<Boolean> delByBookId(Integer bookId);

    /**
     * 推荐图书接口
     * @param recommendType 推荐类型
     * @param page 分页信息
     * @return 图书列表
     */
    BaseResult<List<BookDO>> listRecommendBooks(String recommendType, Page page);

    /**
     * 分页查询
     * @param query 查询条件
     * @return
     */
    BaseResult<List<BookDO>> pageQuery(BookInfoQuery query);


}
