package com.reading.website.api.base;

/**
 * 分页工具类
 *
 * @xyang010 2019/1/21
 */
public class Page {

    /**
     * 当前页
     */
    private Integer pageNum = 1;

    /**
     * 每页显示的总条数
     */
    private Integer pageSize = 10;

    /**
     * 总条数
     */
    private Integer totalNum;

    /**
     * mybatis分页查询
     */
    private Integer offset = 0;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
        this.offset = (pageNum > 0 ? pageNum - 1 : 0) * pageSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        this.offset = (pageNum > 0 ? pageNum - 1 : 0) * pageSize;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", totalNum=" + totalNum +
                ", offset=" + offset +
                '}';
    }
}
