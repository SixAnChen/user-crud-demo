package com.example.springboot.controller.request;

import lombok.Data;

/**
 * 功能：
 * 作者：SixAn
 * 日期：2023-10-20 05:50
 */
@Data
public class UserPageRequest extends BaseRequest {
    private String name;
}
