/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.mainaer.wjokhttp.controller;


import com.mainaer.wjokhttp.comment.MyAppController;
import com.mainaer.wjokhttp.model.LoadRequest;
import com.mainaer.wjokhttp.model.LoadResponse;
import com.mainaer.wjokhttp.url.URLConst;
import com.mainaer.wjoklib.okhttp.IUrl;
import com.mainaer.wjoklib.okhttp.exception.OkException;


/**
 * 类/接口描述
 *
 * @author wangjian
 * @date 2016/3/23.
 */
public class LoadController extends MyAppController<LoadController.LoadListener> {

    public LoadController(LoadListener l) {
        mListener = l;
    }

    public void load(LoadRequest request) {
        Task task = new Task();
        task.load(request, LoadResponse.class);
    }

    /**
     * 执行加载任务的task，其回调都是在主线程中调用
     * 需要加入请求参数LoadRequest和相应参数LoadResponse
     */
    public class Task extends AppBaseTask<LoadRequest, LoadResponse> {

        @Override
        public IUrl getUrl() {
            // 后去url
            return URLConst.Product.PRODUCTLIST;
        }

        @Override
        public void onSuccess(LoadResponse loadResponse) {
            // 获得加载成功的相应数据，自动解析成LoadResponse
            mListener.onLoadSuccess(loadResponse);
        }

        @Override
        public void onError(OkException e) {
            // 加载失败回调
            mListener.onLoadFail(e);
        }
    }

    /**
     * 加载回调接口
     */
    public interface LoadListener {

        void onLoadSuccess(LoadResponse loadResponse);

        void onLoadFail(OkException e);
    }
}
