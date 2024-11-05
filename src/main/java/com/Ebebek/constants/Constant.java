package com.Ebebek.constants;



import com.Ebebek.utils.ConfigManager;

import java.nio.file.Paths;

import static com.Ebebek.utils.DataManager.getData;

public final class Constant {

    private Constant(){}

    public static final String PARENT_DIR=System.getProperty("user.dir");
    public static final String TEST_RESOURCES_DIR= Paths.get(PARENT_DIR,"allure-results").toString();
    private static final String apkFolder = System.getProperty("user.dir") + "/src/test/resources/apk/";

}
