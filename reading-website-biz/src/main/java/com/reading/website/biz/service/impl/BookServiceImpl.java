package com.reading.website.biz.service.impl;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.Page;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.api.domain.BookDO;
import com.reading.website.api.domain.BookInfoQuery;
import com.reading.website.api.service.BookService;
import com.reading.website.api.vo.BookInfoVO;
import com.reading.website.biz.enums.BookRecommendType;
import com.reading.website.biz.logic.BookLogic;
import com.reading.website.biz.mapper.BookMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 图书服务实现类
 *
 * @xyang010 2019/3/12
 */
@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    /**
     * 根据图书id查询
     * @param bookId 图书id
     * @return 图书实体
     */
    @Override
    public BaseResult<BookInfoVO> selectByBookId(Integer bookId) {
        if (bookId == null || bookId < 0) {
            log.warn("getBookInfo param bookId is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param bookId is null");
        }

        try {
            BookInfoQuery query = new BookInfoQuery();
            query.setId(bookId);
            List<BookDO> list = bookMapper.selectSelective(query);
            if (!CollectionUtils.isEmpty(list)) {
                return BaseResult.rightReturn(BookLogic.convertDO2VO(list.get(0)));
            }
            return BaseResult.rightReturn(null);
        } catch (Exception e) {
            log.error("BookServiceImpl selectByBookId bookId {}, error {}", bookId, e);
            return BaseResult.errorReturn(StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }

    /**
     * 新增或更新
     * @param bookDO 图书实体
     * @return 图书id
     */
    @Override
    public BaseResult<Integer> insertOrUpdateBook(BookDO bookDO) {
        if (bookDO == null) {
            log.warn("insertOrUpdateBook param book is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param book is null");
        }

        try {
            bookMapper.insertOrUpdate(bookDO);
            return BaseResult.rightReturn(bookDO.getId());
        } catch (Exception e) {
            log.error("BookServiceImpl insertOrUpdateBook book {}, error {}", bookDO, e);
            return BaseResult.errorReturn(StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }

    /**
     * 删除图书
     * @param bookId 图书id
     * @return 是否成功
     */
    @Override
    public BaseResult<Boolean> delByBookId(Integer bookId) {
        if (bookId == null || bookId < 0) {
            log.warn("delByBookId param bookId is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param bookId is null");
        }

        try {
            BookDO bookDO = new BookDO();
            bookDO.setId(bookId);
            bookDO.setIsDeleted(true);
            int res = bookMapper.insertOrUpdate(bookDO);
            return BaseResult.rightReturn(res > 0);
        } catch (Exception e) {
            log.error("BookServiceImpl delByBookId bookId {}, error {}", bookId, e);
            return BaseResult.errorReturn(StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }

    /**
     * 推荐图书接口
     * @param recommendType 推荐类型
     * @param page 分页信息
     * @return 图书列表
     */
    @Override
    public BaseResult<List<BookInfoVO>> listRecommendBooks(String recommendType, Page page) {
        if (StringUtils.isEmpty(recommendType)) {
            log.warn("listRecommendBooks param recommendType is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param recommendType is null");
        }
        if (page == null) {
            page = new Page();
        }

        try {
            BaseResult<List<BookInfoVO>> result = new BaseResult<>();
            result.setSuccess(true);

            if (recommendType.equals(BookRecommendType.NEWLY.getType())) {
                result.setData(BookLogic.convertDOs2VOs(bookMapper.listNewlyBooks(page)));
            } else if (recommendType.equals(BookRecommendType.FAVORABLE.getType())) {
                result.setData(BookLogic.convertDOs2VOs(bookMapper.listFavorableBooks(page)));
            } else if (recommendType.equals(BookRecommendType.CLASSIC.getType())) {
                result.setData(BookLogic.convertDOs2VOs(bookMapper.listClassicBooks(page)));
            } else {
                log.warn("listRecommendBooks param recommendType is invalid");
                return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param recommendType is invalid");
            }
            page.setTotalNum(bookMapper.countSelective(new BookInfoQuery()));
            result.setPage(page);
            return result;
        } catch (Exception e) {
            log.error("BookServiceImpl listRecommendBooks error {}", e);
            return BaseResult.errorReturn(StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }

    /**
     * 分页查询
     * @param query 查询条件
     * @return
     */
    @Override
    public BaseResult<List<BookInfoVO>> pageQuery(BookInfoQuery query) {
        if (query == null) {
            log.warn("pageQuery param query is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param recommendType is null");
        }

        try {
            BaseResult<List<BookInfoVO>> result = new BaseResult<>();
            result.setSuccess(true);
            result.setData(BookLogic.convertDOs2VOs(bookMapper.pageQuery(query)));
            Page page = new Page();
            page.setTotalNum(bookMapper.countSelective(query));
            page.setPageNum(query.getPageNum());
            page.setPageSize(query.getPageSize());
            result.setPage(page);
            return result;
        } catch (Exception e) {
            log.error("BookServiceImpl listRecommendBooks error {}", e);
            return BaseResult.errorReturn(StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }
}
