package com.syt.easyexcel;

import com.alibaba.excel.EasyExcel;

/**
 * @Author: foofoo3
 */
public class TestRead {
    public static void main(String[] args) {

        String fileName = "E:\\excel\\01.xlsx";

        EasyExcel.read(fileName,UserData.class,new ExcelListener()).sheet().doRead();
    }
}
