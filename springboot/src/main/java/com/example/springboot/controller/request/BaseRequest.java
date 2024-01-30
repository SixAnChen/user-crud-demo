package com.example.springboot.controller.request;

import lombok.Data;

/**
 * 功能：
 * 作者：SixAn
 * 日期：2023-10-20 05:51
 */
@Data
public class BaseRequest {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
