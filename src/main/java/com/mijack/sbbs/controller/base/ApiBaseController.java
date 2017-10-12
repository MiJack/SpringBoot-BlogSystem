package com.mijack.sbbs.controller.base;

import com.mijack.sbbs.vo.Response;

/**
 * @author Mr.Yuan
 * @since 2017/10/12
 */
public class ApiBaseController extends BaseController {
    public <T> Response<T> apiNoAuthenticatedResponse() {
        return Response.failed("未认证请求，无权访问");
    }
}
