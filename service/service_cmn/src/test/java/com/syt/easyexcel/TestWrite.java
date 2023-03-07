package com.syt.easyexcel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: foofoo3
 */
public class TestWrite {
    public static void main(String[] args) {

        String fileName = "E:\\excel\\01.xlsx";

        List<UserData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserData userData = new UserData();
            userData.setUid(i);
            userData.setUsername("kano" + i);
            list.add(userData);
        }
        EasyExcel.write(fileName,UserData.class).sheet("用户信息")
                .doWrite(list);
    }
}
