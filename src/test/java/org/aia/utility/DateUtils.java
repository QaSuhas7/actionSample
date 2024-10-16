
package org.aia.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.aia.pages.fonteva.events.EventConfig;




public final class DateUtils {
	public static final String DEFAULT_DATE_FORMAT = "MM/dd/yyyy";

	/**
	 * Convert date string from one format to another format.
	 * <p>
	 * <b>Example:</b>
	 * <ul>
	 * <li><code>
	 * formatDate("2012-01-11",
				"yyy-MM-dd", "MMM d, yyyy"))
	 * </code> will retrun "Jan 11, 2012"</li>
	 * </ul>
	 * </p>
	 * 
	 * @param dateStr
	 *            : date string to be formated
	 * @param formatFrom
	 *            : format of the given date string
	 * @param formatTo
	 *            : String expected format
	 * @return date string in expected format
	 */
	public static String getFormatedDate(String dateString, String formatFrom, String formatTo) {
		SimpleDateFormat aformat = new SimpleDateFormat(formatFrom);
		SimpleDateFormat eformat = new SimpleDateFormat(formatTo);
		Date d;
		try {
			d = aformat.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage());
		}
		return eformat.format(d);
	}

	public static String getFormatedDate(Date date, String formatTo, String toLocale) {
		SimpleDateFormat eformat = new SimpleDateFormat(formatTo, new Locale(toLocale));
		return eformat.format(date);
	}

	public static String getFormatedDate(String dateString, String formatFrom, String formatTo, String fromLocale,
			String toLocale) {
		SimpleDateFormat aformat = new SimpleDateFormat(formatFrom, new Locale(fromLocale));
		SimpleDateFormat eformat = new SimpleDateFormat(formatTo, new Locale(toLocale));
		Date d;
		try {
			d = aformat.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage());
		}
		return eformat.format(d);
	}

	/**
	 * Get date for today, before/after today as string
	 * 
	 * @param days
	 *            : number of days to be added in current day.
	 * @param format
	 *            : (option) format in which require date string, default is
	 *            "MM/dd/yyyy"
	 * @return date string for expected day. Example:
	 *         <ol>
	 *         <li>getDate(0) will return today's date
	 *         <li>getDate(1) will return tomorrow's date
	 *         <li>getDate(-1) will return yesterday's date
	 *         </ol>
	 */
	public static String getDate(int days, String... format) {
		String outformat = (format == null) || (format.length < 1) ? DEFAULT_DATE_FORMAT : format[0];

		return new SimpleDateFormat(outformat).format(getDate(days));

	}

	/**
	 * Get date for today, before/after today. Example:
	 * <ol>
	 * <li>getDate(0) will return today's date
	 * <li>getDate(1) will return tomorrow's date
	 * <li>getDate(-1) will return yesterday's date
	 * </ol>
	 * 
	 * @param days
	 * @return date
	 */
	public static Date getDate(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}

	/**
	 * Get date after/before base date. Example:
	 * <ol>
	 * <li>getDate(baseDate, 5) will return 5 day's date after baseDate
	 * <li>getDate(baseDate, -5) will return 5 day's date before baseDate
	 * </ol>
	 * 
	 * @param date
	 * @param days
	 * @return date
	 */
	public static Date getDateAfter(Date date, int days) {
		Calendar cal = getCalendarDate(date);
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}

	public static int getMonthsDifference(Date date1, Date date2) {

		Calendar cal1 = getCalendarDate(date1);
		Calendar cal2 = getCalendarDate(date2);

		int m1 = (cal1.get(Calendar.YEAR) * 12) + cal1.get(Calendar.MONTH);
		int m2 = (cal2.get(Calendar.YEAR) * 12) + cal2.get(Calendar.MONTH);
		return m2 - m1;

	}

	/**
	 * Returns number of days between date1 and date2, such that date1 + days =
	 * date2
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getDateDifference(Date date1, Date date2) {
		int oneDay = 1000 * 60 * 60 * 24;
		Calendar cal1 = getCalendarDate(date1);
		Calendar cal2 = getCalendarDate(date2);

		return (int) ((cal1.getTimeInMillis() - cal2.getTimeInMillis()) / oneDay);
	}

	public static Calendar getCalendarDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public static Date parseDate(String dateStr, String format) throws ParseException {
		if (!format.isEmpty()) {
			format = DEFAULT_DATE_FORMAT;
		}
		return new SimpleDateFormat(format).parse(dateStr);
	}

	public static String getFormatedDate(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}
	
	public static String getCurrentDate(){    
		   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
		   LocalDateTime now = LocalDateTime.now();  
		   String todayDate = dtf.format(now);
		   return todayDate;
	}   
	
	public static ArrayList<String> findDifferenceBetweenTwoDates(String start_date,String end_date) throws ParseException
    {
		ArrayList<String> dateValues = new ArrayList<>();
		DateTimeFormatter formatter=  DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
		LocalDateTime endDateTime=LocalDateTime.parse(end_date,formatter);
		LocalDateTime startDateTime=LocalDateTime.parse(start_date, formatter);
		Duration duration=  Duration.between(startDateTime,endDateTime);
	  
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);
		Date d1 = sdf.parse(start_date);
        Date d2 = sdf.parse(end_date);
        
        long difference_In_Time= d2.getTime() - d1.getTime();
 
           // long difference_In_Seconds= (difference_In_Time/ 1000)% 60;
 
            long difference_In_Minutes = (difference_In_Time/ (1000 * 60))% 60;
 
            long difference_In_Hours = (difference_In_Time/ (1000 * 60 * 60))% 24;
            if(!EventConfig.endTimePeriodType.equals(EventConfig.startTimePeriodType) && EventConfig.select24HoursTime ==false) {
            	difference_In_Hours =difference_In_Hours+12;
            }
            
            long difference_In_Years= (difference_In_Time/ (1000l * 60 * 60 * 24 * 365));
            
            //long difference_In_Days = (difference_In_Time/ (1000 * 60 * 60 * 24))% 365;
           
            long difference_In_Days = findDifferenceBetweenDays(start_date,end_date);
            
            long difference_In_Months = findDifferenceBetweenMonths(start_date,end_date);
            
            dateValues.add(difference_In_Years+" years");
            
			dateValues.add(difference_In_Months+" month");
            
            dateValues.add(difference_In_Days+" days");
           
            dateValues.add(difference_In_Hours+" hours");
            
            dateValues.add(difference_In_Minutes+" minute");
            
           // dateValues.add(difference_In_Seconds+" Seconds");
   
         return dateValues;
    }
    
 
    public static long findDifferenceBetweenMonths(String start_date,String end_date){
    	
    	String[] startDateArr = start_date.trim().split(" ");
    	String[] endDateArr = end_date.trim().split(" ");
    	
    	int startYear =Integer.parseInt((startDateArr[0].split("/"))[2]);
    	int startMonth =Integer.parseInt((startDateArr[0].split("/"))[0]);
    	int startDay =Integer.parseInt((startDateArr[0].split("/"))[1]);
    	
    	int endYear =Integer.parseInt((endDateArr[0].split("/"))[2]);
    	int endMonth =Integer.parseInt((endDateArr[0].split("/"))[0]);
    	int endDay =Integer.parseInt((endDateArr[0].split("/"))[1]);
    	
    	LocalDate start_date1 = LocalDate.of(startYear, startMonth, startDay);
    	LocalDate end_date1 = LocalDate.of(endYear, endMonth, endDay);
    	Period diff= Period.between(start_date1,end_date1);
        long months =diff.getMonths();
        
        return months;
    }
     
    public static long findDifferenceBetweenDays(String start_date,String end_date) {
    	int totalDays=0;
    	String[] startDateArr = start_date.trim().split(" ");
    	String[] endDateArr = end_date.split(" ");
    	
    	String startMonth =(startDateArr[0].split("/"))[0];
    	int startDay =Integer.parseInt((startDateArr[0].split("/"))[1]);
    	
    	String endMonth =(endDateArr[0].split("/"))[0];
    	int endDay =Integer.parseInt((endDateArr[0].split("/"))[1]);
    	
    	if(startDay>endDay) {
    			totalDays = EventConfig.monthsdays.get(startMonth)-startDay+endDay;
		
    	}
    	else if(startDay<endDay) {
    		totalDays = endDay-((EventConfig.monthsdays.get(startMonth)%30+startDay));
    	}
    	else {
    		totalDays =0;
    	}	
    	
    	return totalDays;
    	
    }
    
    public static String setFutureDAte() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
 
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
 
        // Calculate future date (e.g., add 3 days)
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        Date futureDate = calendar.getTime();
 
        String formattedFutureDate = sdf.format(futureDate);
 
        System.out.println("Current Date: " + sdf.format(currentDate));
        System.out.println("Future Date (after 7 days): " + formattedFutureDate);
        return formattedFutureDate;
    }
    
    public static String formatDate(String inputDateString) {
    	String outputDateString = null;
    	SimpleDateFormat inputDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("MMMM d, yyyy");

        try {
            // Parse the input string to a Date object
            Date date = inputDateFormat.parse(inputDateString);

            // Format the Date object to the desired output format
             outputDateString = outputDateFormat.format(date);

            // Print the converted date string
            System.out.println("Converted Date: " + outputDateString);
        } catch (ParseException e) {
            // Handle the parsing exception if the format is incorrect
            System.out.println("Error parsing the date: " + e.getMessage());
        }
		return outputDateString;
    
    }
    
    
}

	
	
	
