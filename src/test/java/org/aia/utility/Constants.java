package org.aia.utility;

public class Constants {
	public static final  String USER_DIR = System.getProperty("user.dir");
	public static final  String APPLICATION_PROPERTIES_PATH = USER_DIR+"/config.properties";
	public static final String SCREENSHOT_PATH = USER_DIR+"/ScreenShots/";
	public static final String GENERATE_REPORT_PATH = USER_DIR+"/Reports/extentReport.html";
	public static final String EXTENT_REPORT_FOLDER_PATH = USER_DIR + "/Reports/";
	public static final String EXTENT_REPORT_NAME = "report.html";
	public static final String YES = "yes";
	public static final String NO = "no";
	public static final String OUTPUT_VIDEO_PATH =USER_DIR+ "/Records/";
	
	private static final String PROJECT_NAME = "Fonteva GUI Automation Report";
	public static String getProjectName() {
		return PROJECT_NAME;
	}
}
