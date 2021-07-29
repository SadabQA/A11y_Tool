package com.pearson.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;




public class Selenium 
{
	private static Logger log = Logger.getLogger(Selenium.class);
	private static int timeInSeconds = 120;
	private static WebDriverWait wait;


	/**r
	 * This method is used to get to the given url.
	 *
	 * @param url
	 * @throws Exception 
	 */
	public static void get(String url) throws Exception {
		getWebDriver().get(url);
		log.info("Opening url:"+url);
	}

	/**
	 * This method is used to close browser.
	 *
	 * @param url
	 * @throws Exception 
	 */
	public static void close() throws Exception {
		getWebDriver().close();
	}


	/**
	 * This method is used to get the page title.
	 *
	 * @return
	 * @throws Exception 
	 */
	public static String getTitle() throws Exception {
		pause(2000);
		return getWebDriver().getTitle().trim();
	}

	/**
	 * This method is used to get the web element
	 *
	 * @param locator
	 * @return
	 * @throws Exception 
	 */
	public static WebElement getWebElement(By locator) throws Exception {
		
		Wait<WebDriver> wait = new FluentWait <WebDriver>(getWebDriver()).withTimeout(Duration.ofSeconds(15))
				.pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		
	}

	/**
	 * This method is used to get all the web elements
	 *
	 * @param locator
	 * @return
	 * @throws Exception 
	 */
	public static List<WebElement> getWebElements(By locator) throws Exception {
		return getWebDriver().findElements(locator);
	}

	/**
	 * This method is used to switch to the iFrame using nameOrId.
	 *
	 * @param nameOrId
	 * @throws Exception 
	 */
	public static void switchToiFrameWithId(String nameOrId) throws Exception {
		waitForElementPresence(By.id(nameOrId));
		waitForElementVisibility(By.id(nameOrId));
		getWebDriver().switchTo().frame(nameOrId);
	}

	/**
	 * This method is used to switch to the immediate parent frame.
	 * @throws Exception 
	 *
	 */
	public static void swicthToParentFrame() throws Exception {
		getWebDriver().switchTo().parentFrame();
	}

	/**
	 * This method is used to switch to the iFrame using nameOrId.
	 *
	 * @param nameOrId
	 * @throws Exception 
	 */
	public static void switchToQuestioniFrameWithId(String nameOrId) throws Exception {
		getWebDriver().switchTo().frame(nameOrId);
	}

	/**
	 * This method is used to switch to the iFrame using web element.
	 *
	 * @param locator
	 * @throws Exception 
	 */
	public static void switchToiFrameWithWebElement(By locator) throws Exception{
		getWebDriver().switchTo().frame(getWebElement(locator));
	}

	/**
	 * This method is used to switch to the most parent frame.
	 * @throws Exception 
	 *
	 */
	public static void switchToMostParentFrame() throws Exception {
		getWebDriver().switchTo().defaultContent();
	}

	/**
	 * This method is used to switch to the immediate parent frame.
	 * @throws Exception 
	 *
	 */
	public static void switchToParentFrame() throws Exception {
		getWebDriver().switchTo().parentFrame();
	}

	/**
	 * This method is used to switch to the most parent frame.
	 * @throws Exception 
	 *
	 */
	public static void switchOutFromFrame() throws Exception {
		getWebDriver().switchTo().defaultContent();
		Selenium.pause(1000);
	}

	/**
	 * This method is used to get the parent window handle.
	 *
	 * @return
	 * @throws Exception 
	 */
	public static String getParentWindowHandle() throws Exception {
		Selenium.pause(1000);
		return getWebDriver().getWindowHandle();
	}

	/**
	 * This method is used to get all the windows handles.
	 *
	 * @return
	 * @throws Exception 
	 */
	public static Set<String> getAllOpenedWindowHandles() throws Exception{
		Selenium.pause(1000);
		return getWebDriver().getWindowHandles();
	}



	public static void checkPageIsReady() throws Exception 
	{

		try {
			JavascriptExecutor js = (JavascriptExecutor) Selenium.getWebDriver();

			// Initially bellow given if condition will check ready state of page.
			if (js.executeScript("return document.readyState").toString().equals("complete")) {
				System.out.println("Page Is loaded.");
				return;
			}

			// This loop will iterate for 25 times to check If page Is ready after
			// every 1 second.
			// If the page loaded successfully, it will terminate the for loop
			for (int i = 0; i < 25; i++) 
			{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}

				// To check page ready state.
				if (js.executeScript("return document.readyState").toString().equals("complete")) {
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to wait for element presence.
	 *
	 * @param locator
	 * @return
	 * @throws Exception 
	 */
	public static WebElement waitForElementPresence(By locator) throws Exception {
		//log.info("Waiting for element to be present - " + locator);
		WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), timeInSeconds);
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * This method is used to wait for element presence.
	 *
	 * @param locator
	 * @return
	 * @throws Exception 
	 */
	public static WebElement waitForElementPresence(By locator, int timeInSeconds) throws Exception {
		//log.info("Waiting for element to be present - " + locator);
		WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), timeInSeconds);
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * This method is used to wait for element visibility.
	 *
	 * @param locator
	 * @return
	 * @throws Exception 
	 */
	public static WebElement waitForElementVisibility(By locator) throws Exception {
		//log.info("Waiting for element visibility - " + locator);
		WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), timeInSeconds);
		return wait.until(ExpectedConditions.visibilityOf(getWebElement(locator)));
	}

	/**
	 * This method is used to wait for element visibility.
	 *
	 * @param locator
	 * @return
	 * @throws Exception 
	 */
	public static WebElement waitForElementVisibility(By locator, int timeInSeconds) throws Exception {
		//log.info("Waiting for element visibility - " + locator);
		WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), timeInSeconds);
		return wait.until(ExpectedConditions.visibilityOf(getWebElement(locator)));
	}

	/**
	 * This method is used to wait for element to be clickable.
	 *
	 * @param locator
	 * @return
	 * @throws Exception 
	 */
	public static WebElement waitForElementClickable(By locator) throws Exception {
		WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), timeInSeconds);
		return wait.until(ExpectedConditions.elementToBeClickable(getWebElement(locator)));
	}

	/**
	 * This method is used to wait for element invisibility.
	 *
	 * @param locator
	 * @return
	 * @throws Exception 
	 */
	public static boolean waitForElementInvisibility(By locator,int time) throws Exception {
		WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), time);
		return wait.until(ExpectedConditions.invisibilityOf(getWebElement(locator)));
	}

	/**
	 * This method is used to wait for all elements to be present.
	 *
	 * @param locator
	 * @return
	 * @throws Exception 
	 */
	public static List<WebElement> waitForAllElementsToBeVisible(By locator) throws Exception {
		WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), timeInSeconds);
		return wait.until(ExpectedConditions.visibilityOfAllElements(getWebElements(locator)));
	}


	/**
	 * This method is used to wait for all elements to be invisible.
	 *
	 * @param locator
	 * @return
	 * @throws Exception 
	 */
	public static boolean waitForAllElementsToBeInvisible(By locator) throws Exception {
		WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), timeInSeconds);
		return wait.until(ExpectedConditions.invisibilityOfAllElements(getWebElements(locator)));
	}

	/**
	 * This method is used to send the keys to the text field.
	 *
	 * @param locator
	 * @param keys
	 * @throws Exception 
	 */
	public static void sendKeys(By locator, String keys) throws Exception 
	{
		WebElement element = waitForElementVisibility(locator);
		try {
			//log.info("Sending keys to " + element);
			element.sendKeys(keys.trim());

		} catch (StaleElementReferenceException e) {
			element.sendKeys(keys.trim());
		}
	}


	/**
	 * This method is used to send the char sequence to the text field.
	 *
	 * @param locator
	 * @param keys
	 * @throws Exception 
	 */
	public static void sendKeys(By locator, CharSequence keys) throws Exception 
	{
		WebElement element = waitForElementPresence(locator);
		try {
			//log.info("Sending keys to " + element);
			element.sendKeys(keys);
		} catch (StaleElementReferenceException e) {
			element.sendKeys(keys);
		}
	}

	/**
	 * This method is used to get text using javascript executor.
	 *
	 * @param driver
	 * @param locator
	 * @return
	 * @throws Exception 
	 */
	public static String getTextJS(By locator) throws Exception 
	{
		JavascriptExecutor js = (JavascriptExecutor) Selenium.getWebDriver();
		String id = getAttribute(locator, "id").trim();
		System.out.println(id);
		try {
			waitForElementVisibility(locator);
			//log.info("Sending keys to " + locator);
			return (String) js.executeScript("return document.getElementById('" + id + "').value;");
		} catch (StaleElementReferenceException e) {
			return (String) js.executeScript("return document.getElementById('" + id + "').value;");
		}
	}

	/**
	 * This method is used to click using javascript.
	 *
	 * @param element
	 * @throws Exception 
	 */
	public static void JSClick(WebElement element) throws Exception 
	{
		JavascriptExecutor executor = (JavascriptExecutor) getWebDriver();
		executor.executeScript("arguments[0].click();", element);
	}

	/**
	 * This method is used to click on by locators using javascript.
	 *
	 * @param locator
	 * @throws Exception 
	 */
	public static void JSClick(By locator) throws Exception 
	{
		JavascriptExecutor executor = (JavascriptExecutor) getWebDriver();
		executor.executeScript("arguments[0].click();", getWebElement(locator));
	}

	/**
	 * This method is used to send keys using javascript.
	 *
	 * @param element
	 * @param keys
	 * @throws Exception 
	 */
	public static void JSSendKeys(WebElement element,CharSequence keys) throws Exception {
		JavascriptExecutor executor = (JavascriptExecutor) getWebDriver();
		executor.executeScript("arguments[0].setAttribute('value','"+keys+"');", element);
	}

	/**
	 * This method used to bring focus using javascript.
	 * @throws Exception 
	 *
	 */
	public static void JSFocus() throws Exception {
		JavascriptExecutor executor = (JavascriptExecutor) getWebDriver();
		executor.executeScript("window.focus();");
	}

	/**
	 * This method is used to click on by locator.
	 *
	 * @param locator
	 * @throws Exception 
	 */
	public static void click(By locator) throws Exception {
		
		try {
			waitForElementPresence(locator);
			waitForElementVisibility(locator);
			waitForElementClickable(locator);
			//log.info("Clicking on element " + locator);
			getWebElement(locator).click();
			Thread.sleep(1500);
		}catch (ElementClickInterceptedException e) {
			Thread.sleep(10000);
			try {
				getWebElement(locator).click();
			} catch (ElementClickInterceptedException e1) {
				Thread.sleep(15000);
				getWebElement(locator).click();
			}
			Selenium.pause(1200);
		} catch (StaleElementReferenceException e) {
			getWebElement(locator).click();
			Selenium.pause(1200);
		}catch (Exception e) {
			getWebElement(locator).click();
			Selenium.pause(1200);
		}
		Selenium.checkPageIsReady();
		//log.info("Clicked on element " + locator);
	}

	/**
	 * This method is used to click on web element.
	 *
	 * @param element
	 */
	public static void click(WebElement element) {
		try {
			//waitForElementToBeClickable(locator);
			//log.info("Clicking on element " + element);
			element.click();
		} catch (StaleElementReferenceException e) {
			element.click();
		}
		//log.info("Clicked on element " + element);
	}

	/**
	 * This method is used to blank click.
	 * @throws Exception 
	 *
	 */
	public static void blankClick() throws Exception 
	{
		Actions actions = new Actions(getWebDriver());
		actions.click();
		actions.perform();
		//log.info("clicking in a page anywhere ");
	}

	/**
	 * This method is used to mouse hover over an element.
	 *
	 * @param locator
	 * @throws Exception 
	 */
	public static void mouseHover(By locator) throws Exception {
		waitForElementVisibility(locator);
		Actions actions = new Actions(getWebDriver());
		actions.moveToElement(getWebElement(locator));
		actions.perform();
		log.info("Hovering on element " + locator);
	}

	/**
	 * This method is used to perform double click on the element.
	 *
	 * @param element
	 * @throws Exception 
	 */
	public static void doubleClick(WebElement element) throws Exception {
		Actions actions = new Actions(getWebDriver());
		actions.click();
		actions.doubleClick(element);
		actions.perform();
		log.info("Double click on element " + element);
	}


	/**
	 * This method is used to perform double click on the element.
	 *
	 * @param element
	 * @throws Exception 
	 */
	public static void actionClick(WebElement element) throws Exception {
		Actions actions = new Actions(getWebDriver());
		actions.click();
		actions.click(element);
		actions.perform();
		log.info("Double click on element " + element);
	}

	/**
	 * This method is used get text of the element.
	 *
	 * @param locator
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings({ "removal", "deprecation" })
	public static String getText(By locator) throws Exception 
	{
		log.info("Getting text value of element " + locator);
		String text="";
		try {
			waitForElementPresence(locator);
			text = getWebElement(locator).getText().trim();
			log.info("Text value is - " + text);
		} catch (Exception e) {
			text=Selenium.getTextJS(locator);
			log.info(e.getMessage());
		}
		return text;
	}



	@SuppressWarnings({ "removal", "deprecation" })
	public static String getTextInput(By locator) throws Exception 
	{
		log.info("Getting text value of element " + locator);
		String text="";

		try {
			text = getWebElement(locator).getText().trim();
		} catch (Exception e) {
			text="";
			log.info(e.getMessage());
		}
		log.info("Text value is - " + text);

		return text;
	}

	/**
	 * This method is used clear the text from the text field.
	 *
	 * @param locator
	 * @throws Exception 
	 */
	public static void clear(By locator) throws Exception 
	{
		log.info("removing text value of element " + locator);
		try {
			waitForElementPresence(locator);
			waitForElementVisibility(locator);
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		getWebElement(locator).sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
		getWebElement(locator).clear();
        Thread.sleep(1200);
		log.info("Value is removed");

	}

	/**
	 * This method is used to get the element attribute.
	 *
	 * @param locator
	 * @param attribute
	 * @return
	 * @throws Exception 
	 */
	public static String getAttribute(By locator, String attribute) throws Exception 
	{
		try {
			waitForElementPresence(locator);
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		log.info("Getting " + attribute + " value of element " + locator);
		String value = getWebElement(locator).getAttribute(attribute);
		log.info(attribute + " attribute value is " + value);
		return value;
	}

	/**
	 * This method is used to check whether is element present in dom.
	 *
	 * @param locator
	 * @return boolean
	 * @throws Exception 
	 */
	public static boolean isElementPresent(By locator) throws Exception {
		try {
			getWebElement(locator);
		} catch (NoSuchElementException e) {
			return false;
		}
		return true;
	}

	/**
	 * This method is used to navigate the browser back.
	 * @throws Exception 
	 *
	 */
	public static void navigateBack() throws Exception {
		log.info("Navigating back...");
		DriverFactory.getWebDriver().navigate().back();
		log.info("Navigated back to last page - " + getWebDriver().getCurrentUrl());
	}

	/**
	 * This method is used to navigate the browser forward.
	 * @throws Exception 
	 *
	 */
	public static void navigateForward() throws Exception {
		log.info("Navigating forward...");
		getWebDriver().navigate().forward();
		log.info("Navigated forward to next page - " + getWebDriver().getCurrentUrl());
	}

	/**
	 * This method is used to wait for alert present.
	 * @throws Exception 
	 *
	 */
	public static void waitForAlertIsPresent() throws Exception {
		WebDriverWait wait = new WebDriverWait(getWebDriver(), 60);
		wait.until(ExpectedConditions.alertIsPresent());
	}

	/**
	 * This method is used get the current opened url.
	 *
	 * @return
	 * @throws Exception 
	 */
	public static String getCurrentUrl() throws Exception {
		return getWebDriver().getCurrentUrl();
	}

	/**
	 * This method is used to get list options.
	 *
	 * @param locator
	 * @return
	 * @throws Exception 
	 */
	public static List<WebElement> getOptions(By locator) throws Exception {
		Select select = new Select(getWebElement(locator));
		return select.getOptions();
	}

	/**
	 * This method is used to get all the selected options.
	 *
	 * @param locator
	 * @return
	 * @throws Exception 
	 */
	public static List<WebElement> getSelectedOptions(By locator) throws Exception {
		Select select = new Select(getWebElement(locator));
		return select.getAllSelectedOptions();
	}

	/**
	 * This method is used to get first selected option of the drop-down list.
	 *
	 * @param locator
	 * @return
	 * @throws Exception 
	 */
	public static WebElement getFirstSelectedOption(By locator) throws Exception {
		Select select = new Select(getWebElement(locator));
		return select.getFirstSelectedOption();
	}

	/**
	 * This method is used to get options of the drop-down list.
	 *
	 * @param element
	 * @return
	 */
	public static List<WebElement> getOptions(WebElement element) {
		Select select = new Select(element);
		return select.getOptions();
	}

	/**
	 * This method is used to select a given option from drop-down values.
	 *
	 * @param locator
	 * @return
	 * @throws Exception 
	 */
	public static void selectByVisibleText(By locator, String value) throws Exception {
		Select select = new Select(getWebElement(locator));
		select.selectByVisibleText(value);
	}

	/**
	 * This method is used to select by value.
	 *
	 * @param locator
	 * @param value
	 * @throws Exception 
	 */
	public static void selectByValue(By locator, String value) throws Exception {
		Select select = new Select(getWebElement(locator));
		select.selectByValue(value);
	}

	/**
	 * This method is used to select by value.
	 *
	 * @param element
	 * @param value
	 */
	public static void selectByValue(WebElement element, String value) {
		Select select = new Select(element);
		select.selectByValue(value);
	}

	/**
	 * This method is used to get all the option values of the drop-down.
	 *
	 * @param locator
	 * @return
	 * @throws Exception 
	 */
	public static List<String> getOptionValues(By locator) throws Exception {
		List<WebElement> list = getOptions(locator);
		List<String> values = new ArrayList<String>();
		log.info("Drop down values are, ");
		for (WebElement webElement : list) {
			String value = webElement.getText();
			log.info(value);
			values.add(value);
		}
		return values;
	}

	/**
	 * This method is used to check is option present under drop-down.
	 *
	 * @param locator
	 * @param value
	 * @return
	 * @throws Exception 
	 */
	public static boolean isOptionPresentUnderDropDown(By locator, String value) throws Exception {
		List<String> values = getOptionValues(locator);
		return values.contains(value);
	}

	/**
	 * Is Option Present Under Drop Down
	 *
	 * @param locator
	 * @param value
	 * @return
	 * @throws Exception 
	 */
	public static void selectValueFromDropDown(By locator, String value) throws Exception {

		Select select = new Select(getWebElement(locator));
		select.selectByVisibleText(value);

	}

	/**
	 * This method is used to refresh the page.
	 *
	 */
	public static void pageRefresh() {
		pause(1000);
		try {
			getWebDriver().navigate().refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param locator
	 * @return
	 * @throws Exception 
	 */
	public static boolean isEnabled(By locator) throws Exception {
		log.info("Checking enable property of element " + locator);
		return getWebElement(locator).isEnabled();

	}

	/**
	 * This method is used to check whether an element is clickable or not.
	 *
	 * @param locator
	 * @return
	 */
	public static boolean checkIfElementIsClickable(By locator) {
		try {
			WebDriverWait wait = new WebDriverWait(getWebDriver(), 6);
			wait.until(ExpectedConditions.elementToBeClickable(getWebElement(locator)));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * This method is used to get the web driver instance.
	 *
	 * @return
	 * @throws Exception 
	 */
	public static WebDriver getWebDriver() throws Exception {
		return DriverFactory.getWebDriver();
	}

	/**
	 * This method is used to get the alert text.
	 *
	 * @return
	 * @throws Exception 
	 */
	public static String getAlertText() throws Exception {
		//waitUntilAlertIsPresent();
		Alert alert = getWebDriver().switchTo().alert();
		return alert.getText();
	}

	/**
	 * This method is used to accept the alert.
	 * @throws Exception 
	 *
	 */
	public static void acceptAlert() throws Exception {
		// waitUntilAlertIsPresent();
		Alert alert = getWebDriver().switchTo().alert();
		alert.accept();
	}

	/**
	 * This method is used to dismiss the alert.
	 * @throws Exception 
	 *
	 */
	public static void dismissAlert() throws Exception {
		// waitUntilAlertIsPresent();
		Alert alert = getWebDriver().switchTo().alert();
		alert.dismiss();
	}

	/**
	 * This method is used to switch to last window opened.
	 * @throws Exception 
	 *
	 */
	public static void switchToWindow() throws Exception {
		// for (String winHandle : getDriver().getWindowHandles()) {
		String win = getWebDriver().getWindowHandle();
		win.length();
		getWebDriver().switchTo().window(win);

	}
	public static void switchToWindowByNumber(int num) throws Exception 
	{
		Set<String> window=getWebDriver().getWindowHandles();
		getWebDriver().switchTo().window(window.toArray()[num].toString());	
	}
	
	
	

	
	
	/**
	 * This method is used to verify that element having given by locator is selected.
	 *
	 * @param locator
	 * @return
	 * @throws Exception 
	 */
	public static boolean isSelected(By locator) throws Exception {
		return getWebElement(locator).isSelected();

	}

	/**
	 * This method is used to check whether the element having the given locator.
	 *
	 * @param locator
	 * @return
	 * @throws Exception 
	 */
	public static boolean isDisplayed(By locator) throws Exception {
		return getWebElement(locator).isDisplayed();
	}

	/**
	 * This method is used to scroll to the top of the page.
	 *
	 * @return
	 * @throws Exception 
	 */

	public static void scrollToTop() throws Exception {
		((JavascriptExecutor) getWebDriver()).executeScript("window.scrollTo(document.body.scrollHeight,0)");
	}

	/**
	 * This method is used to scroll down on the page.
	 * @throws Exception 
	 *
	 */
	public static void scrollDown() throws Exception {
		((JavascriptExecutor) getWebDriver()).executeScript("window.scrollBy(0,200)");
	}

	/**
	 * This method is used to scroll to the bottom of the page.
	 * @throws Exception 
	 *
	 */
	public static void scrollToBottom() throws Exception {
		((JavascriptExecutor) getWebDriver()).executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}

	/**
	 * This method is used to scroll to the element on the page.
	 *
	 * @param locator
	 * @throws Exception 
	 */
	public static void scrollToElement(By locator) throws Exception {
		((JavascriptExecutor) getWebDriver()).executeScript("arguments[0].scrollIntoView(true);", getWebElement(locator));
	}

	/**
	 * This method is used to pause the script for the given second.
	 *
	 * @param seconds
	 */
	public static void pause(int Miliseconds) {
		try {
			Thread.sleep(Miliseconds);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	protected void selectVisibleText(WebElement elem, String text, String elementName) {
		Select sel = new Select(elem);
		sel.selectByVisibleText(text);
	}

	protected void selectValue(WebElement elem, String value, String elementName) {
		Select sel = new Select(elem);
		sel.selectByVisibleText(value);

	}

	protected void clickOnElement(WebElement elem, String elementName) {

		wait.until(ExpectedConditions.elementToBeClickable(elem)).click();
	}

	protected boolean isStringPresentInWebTable(WebElement table, String text) {
		if (table.findElements(By.xpath(".//tr/td[contains(text(), '" + text + "')]")).size() > 0)
			return true;
		else
			return false;
	}

}
