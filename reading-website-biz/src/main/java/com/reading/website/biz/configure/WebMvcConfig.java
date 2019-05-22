package com.reading.website.biz.configure;

import com.reading.website.biz.configure.handler.LoginInterceptorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * webConfig
 *
 * @xyang010 2019/3/28
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final LoginInterceptorHandler loginInterceptorHandler;

    @Autowired
    public WebMvcConfig(LoginInterceptorHandler loginInterceptorHandler) {
        this.loginInterceptorHandler = loginInterceptorHandler;
    }

    /**
     * 注册拦截器,自己写好的拦截器需要通过这里添加注册才能生效
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns("/**") 表示拦截所有的请求，
        // excludePathPatterns() 表示不拦截的请求
        registry.addInterceptor(loginInterceptorHandler)
                .addPathPatterns("/advice/query")
                .addPathPatterns("/advice/update")
                .addPathPatterns("/book/list/onShelf")
                .addPathPatterns("/review/addOrUpdate")
                .addPathPatterns("/review/del")
                .addPathPatterns("/book/addOrRemoveToShelf")
                .addPathPatterns("/book/addOrUpdateBook")
                .addPathPatterns("/book/del")
                .addPathPatterns("/transport/download")
                .addPathPatterns("/transport/getPicture")
                .addPathPatterns("/notes/add")
                .addPathPatterns("/notes/del")
                .addPathPatterns("/notes/queryByUserId")
                .addPathPatterns("/notes/queryByUserIdAndBookId")
                .addPathPatterns("/user/getUserInfo")
                .addPathPatterns("/user/updateUserInfo")
                .addPathPatterns("/reading/history")
                .addPathPatterns("/chapter/generatorChapters")
                .addPathPatterns("/chapter/insertChapterInfo")
                .addPathPatterns("/chapter/updateChapterInfo")
                .addPathPatterns("/author/addOrUpdate")
                .addPathPatterns("/author/del");
    }
}
