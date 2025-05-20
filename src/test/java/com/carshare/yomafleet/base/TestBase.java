package com.carshare.yomafleet.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class TestBase {
    public static Properties prop;
    public static WebDriver driver;


    public TestBase() throws FileNotFoundException {
        prop = new Properties();

        FileInputStream ip = new FileInputStream("/Users/mac/yomafleet/src/test/java/com/carshare/yomafleet/config/config.properties");
        try {
            prop.load(ip);
        } catch (
                IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void Initialization() {
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().deleteAllCookies();
        driver.get("https://carshare.yomafleet.com/");
    }
}
