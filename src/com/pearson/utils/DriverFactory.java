package com.pearson.utils;


import org.apache.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.remote.http.W3CHttpCommandCodec;
import org.openqa.selenium.remote.http.W3CHttpResponseCodec;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class DriverFactory 
{
    private Logger log = Logger.getLogger(this.getClass());
    private static String ie_DriverBinary = "IEDriverServer.exe";
    private static String edge_DriverBinary = "MicrosoftWebDriver.exe";
    private static String chrome_DriverBinary_Win = "chromedriver.exe";
    private static String chrome_DriverBinary_Mac = "chromedriver";
    private static final String SAFARI = "safari";
    private static final String FIREFOX = "firefox";
    private static final String CHROME = "chrome";
    private static final String IE = "ie";
    private static final String EDGE = "edge";
    public static RemoteWebDriver webDriver;
    private static String driverRelativePath = "drivers" + File.separator;
    private static String osType;

    public static RemoteWebDriver getWebDriver() throws Exception 
    {
        if(webDriver == null)
        {
            webDriver = createDriver();
            Selenium.switchToWindow();
            webDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            webDriver.manage().window().maximize();
            //webDriver.manage().window().fullscreen();
        }
        return webDriver;
    }

    private static RemoteWebDriver createDriver() throws Exception 
    {
        String browser = PropertyUtil.getproperty("./config/config", "browser");
        osType = getOperatingSystemType();

        if (browser.equalsIgnoreCase(CHROME)) 
        {
            List<String> arguments = new ArrayList<String>();
            arguments.add("--lang=" + "en");
            arguments.add("--allow-file-access-from-files");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments(arguments);

            if (osType.equals("MacOS")) {
                System.setProperty("webdriver.chrome.driver", driverRelativePath + chrome_DriverBinary_Mac);
                webDriver = new ChromeDriver(chromeOptions);
            } else if (osType.equals("Windows")) 
            {
            	WebDriverManager.chromedriver().setup();
            	webDriver = new ChromeDriver();
            } 
            else if (osType.equals("Linux")) 
            {

            } else 
            {
                try {
                    throw new Exception("Please check your operating system");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else if (browser.toLowerCase().equals(FIREFOX)) {
            FirefoxProfile firefoxProfile = new FirefoxProfile();
            firefoxProfile.setPreference("intl.accept_languages", "en");
            firefoxProfile.setPreference("browser.download.dir", "C:/Users/Administrator/Downloads");
            firefoxProfile.setPreference("browser.download.folderList", 2);
            firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
                    "text/plain application/zip application/tar");
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setProfile(firefoxProfile);
            webDriver = new FirefoxDriver(firefoxOptions);
        }
        else if (browser.toLowerCase().equals(IE)){
            if (osType.equals("Windows")) {
                System.setProperty("webdriver.ie.driver", driverRelativePath + ie_DriverBinary);
                InternetExplorerOptions options = new InternetExplorerOptions();
                options.introduceFlakinessByIgnoringSecurityDomains();
                webDriver = new InternetExplorerDriver(options);
            } else {
                try {
                    throw new Exception("You are not running the tests on windows operating system");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else if (browser.toLowerCase().equals(EDGE)){
            if (osType.equals("Windows")) {
                System.setProperty("webdriver.edge.driver", driverRelativePath + edge_DriverBinary);
                EdgeOptions options = new EdgeOptions(); //we can add the options for edge in future as we need
                webDriver = new EdgeDriver(options);
                webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            } else {
                try {
                    throw new Exception("You are not running the tests on windows operating system");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else{

        }
        return webDriver;
    }

    
    public static RemoteWebDriver createDriverFromSession(final SessionId sessionId, URL command_executor)
	{
	    CommandExecutor executor = new HttpCommandExecutor(command_executor) {

	    @Override
	    public Response execute(Command command) throws IOException {
	        Response response = null;
	        if (command.getName() == "newSession") 
	        {
	            response = new Response();
	            response.setSessionId(sessionId.toString());
	            response.setStatus(0);
	            response.setValue(Collections.<String, String>emptyMap());

	            try {
	                Field commandCodec = null;
	                commandCodec = this.getClass().getSuperclass().getDeclaredField("commandCodec");
	                commandCodec.setAccessible(true);
	                commandCodec.set(this, new W3CHttpCommandCodec());

	                Field responseCodec = null;
	                responseCodec = this.getClass().getSuperclass().getDeclaredField("responseCodec");
	                responseCodec.setAccessible(true);
	                responseCodec.set(this, new W3CHttpResponseCodec());
	            } catch (NoSuchFieldException e) {
	                e.printStackTrace();
	            } catch (IllegalAccessException e) {
	                e.printStackTrace();
	            }

	        } else 
	        {
	            response = super.execute(command);
	        }
	        return response;
	    }
	    };

	    return new RemoteWebDriver(executor, new DesiredCapabilities());
	}
    /**
     * This method is used to detect the OS on which tests are running.
     */
    public static String getOperatingSystemType() {
        String OS = System.getProperty("os.name", "generic").toLowerCase();
        String identifiedOS;
        if ((OS.indexOf("mac") >= 0)) {
            identifiedOS = "MacOS";
        } else if (OS.indexOf("win") >= 0) {
            identifiedOS = "Windows";
        } else if (OS.indexOf("nux") >= 0) {
            identifiedOS = "Linux";
        } else {
            identifiedOS = "Other";
        }
        return identifiedOS;
    }
}
