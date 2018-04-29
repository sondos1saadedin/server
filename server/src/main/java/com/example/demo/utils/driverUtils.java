package com.example.demo.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by sondo on 21/06/2017.
 */
public class driverUtils {


    public static WebElement findElementByAll(WebDriver driver, String identifier) {

        WebElement element = getBy(driver, By.id(identifier));
        if (element == null) {
            element = getBy(driver, By.name(identifier));
        }
        if (element == null) {
            element = getBy(driver, By.className(identifier));
        }
        if (element == null) {
            element = getBy(driver, By.linkText(identifier));
        }
        if (element == null) {
            element = getBy(driver, By.cssSelector(identifier));
        }
        if (element == null) {
            element = getBy(driver, By.cssSelector("a[title=\"" + identifier + "\"]"));
        }

        if (element == null) {
            element = getBy(driver, By.cssSelector("img[alt=\"" + identifier + "\"]"));
        }
        if (element == null) {
            element = getBy(driver, By.cssSelector("img[contains(@src\"" + identifier + "\"]"));
        }
        if (element == null) {
            element = getBy(driver, By.xpath("//input[@alt='" + identifier + "']"));
        }
        if (element == null) {
            element = getBy(driver, By.xpath("//input[@value='" + identifier + "']"));
        }
        if (element == null) {
            element = getBy(driver, By.xpath("//input[@name='" + identifier + "']"));
        }
        if (element == null) {
            element = getBy(driver, By.xpath("//input[@src='" + identifier + "']"));
        }

        if (element == null) {
            element = getBy(driver, By.xpath("//input[@class='" + identifier + "']"));
        }

        if (element == null) {
            element = getBy(driver, By.xpath("//img[contains(@src,'" + identifier + "')]"));
        }
        if (element == null) {
            element = getBy(driver, By.xpath("//a[contains(@href,'" + identifier + "')]"));
        }
        if (element == null) {
            element = getBy(driver, By.xpath("//a[contains(text(),'" + identifier + "')]"));
        }

        if (element == null) {
            element = getBy(driver, By.xpath("//div[contains(text(),\"" + identifier + "\")]"));
        }

        if (element == null) {
            element = getBy(driver, By.xpath("//label[contains(text(), \"" + identifier + "\")]"));
        }
        if (element == null) {
            element = getBy(driver, By.cssSelector("button[title=\"" + identifier + "\"]"));
        }

        if (element == null) {
            element = getBy(driver, By.xpath("//div[@id='" + identifier + "']"));
        }
        if (element == null) {
            element = getBy(driver, By.xpath("//div[contains(@id,'" + identifier + "')]"));
        }
        if (element == null) {
            element = getBy(driver, By.partialLinkText(identifier));
        }

        if (element == null) {
            element = getBy(driver, By.xpath("//img[contains(@alt,'" + identifier + "')]"));
        }

        if (element == null) {

            element = getBy(driver, By.xpath("//span[contains(text(), \"" + identifier + "\")]"));

        }

        if (element == null) {
            element = getBy(driver, By.xpath("//span[@class='" + identifier + "']"));
        }
        if (element == null) {
            element = getBy(driver, By.xpath("//li[contains(text(), \"" + identifier + "\")]"));
        }
        if (element == null) {
            element = getBy(driver, By.xpath(identifier));
        }
        if (element == null) {
            element = getBy(driver, By.cssSelector("'" + identifier + "'"));
        }
        return element;

    }


    public static WebElement getBy(WebDriver driver, By by) {

        try {
            WebElement elem = driver.findElement(by);
            return elem;
        } catch (Throwable ex) {
            // System.out.println("Could not find element : "+by+",
            // err:"+ex.toString());
            return null;
        }

    }


    public static WebElement findElementByClass(WebDriver driver, String value) {
        WebElement element = null;
        element = getBy(driver, By.className(value));

        return element;
    }

    public static boolean verify(WebDriver driver, String word) {
        return driver.getPageSource().contains(word);
    }


}
