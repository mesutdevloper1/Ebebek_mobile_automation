package com.Ebebek.utils;

import io.appium.java_client.MobileBy;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.openqa.selenium.By;

import java.io.File;
import java.io.FileReader;
import java.util.Map;


public class ElementManager {

    private ElementManager() {
    }
    public static Map<String, String> getElement(String elementName) throws Exception {
        File folder = new File("src/test/java/objectRepository");
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));

        for (File file : files) {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(new FileReader(file));
            if (obj.containsKey(elementName)) {
                JSONObject element = (JSONObject) obj.get(elementName);
                String value = (String) element.get("value");
                String type = (String) element.get("type");
                return Map.of("value", value, "type", type);
            }
        }
        throw new Exception("Element not found");
    }

    public static By returnElement(String element) throws Exception {
        Map<String, String> elementValues = getElement(element);
        String type=elementValues.get("type");

        switch (type) {
            case "id":
                return MobileBy.id(elementValues.get("value"));
            case "xpath":
                return MobileBy.xpath(elementValues.get("value"));
            case "AccessibilityId":
                return MobileBy.AccessibilityId(elementValues.get("value"));
            case "AndroidUIAutomator":
                return MobileBy.AndroidUIAutomator(elementValues.get("value"));
            case "iOSClassChain":
                return MobileBy.iOSClassChain(elementValues.get("value"));
            case "className":
                return MobileBy.className(elementValues.get("value"));
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }
}
