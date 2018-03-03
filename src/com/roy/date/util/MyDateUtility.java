package com.roy.date.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyDateUtility {
	
	public static void main(String args[]){

	     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	     int totalWeekendCount = 0;
	     try {
	
	         Date input_startDate = formatter.parse("2017-04-14 20:16:00");	
	         Date input_endDate = getInitialEndDate(input_startDate,3);	
	         System.out.println("Start date : "+ input_startDate);	
	         System.out.println("End date   : "+ input_endDate);	
	         
	         totalWeekendCount =            countSatAndSundays(input_startDate,input_endDate);	
	         System.out.println("Count of Sat & Sundays = "+totalWeekendCount);
	
	         if(totalWeekendCount>0){	
		           Date tempEndDate = incrementDate(totalWeekendCount,input_endDate);
		           System.out.println("\nLast day after calulation(Weekends between dates) : "+ tempEndDate);
		           String endDay = checkSatOrSunDay(tempEndDate);
		           System.out.println("Last day is(SATDAY / SUNDAY / WEEKDAY) : "+endDay);
		
		           if("SUNDAY".equalsIgnoreCase(endDay)){	
		              tempEndDate = incrementDate(1,tempEndDate);
		           }else if("SATURDAY".equalsIgnoreCase(endDay)){
		              tempEndDate = incrementDate(2,tempEndDate);
		           }
		           System.out.println("\nAfter update End date is : "+tempEndDate);
	
		           //fetch the working dates between the start and temp end Date	
		           List<Date> workingDateLst = getWorkingDaysList(input_startDate,tempEndDate);
	               System.out.println("workingDateLst : "+workingDateLst);
	         }
	
	      } catch (ParseException e) {	
	            // TODO Auto-generated catch block
	            e.printStackTrace();
          }
	}

	 

	 /**
	  * @param startDate
	  * @param endDate
	  * @returns the number of Saturday and Sunday between start and end date
	  */
	 private static int countSatAndSundays(Date startDate, Date endDate) {

        Calendar c1 = Calendar.getInstance();
        c1.setTime(startDate);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(endDate);
        int sundays = 0;
        int saturday = 0;
        while (! c1.after(c2)) {
            if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ){
                saturday++; 
            }
            if(c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                sundays++;
            }
            c1.add(Calendar.DATE, 1);
        }
        System.out.println("\nSaturday Count = "+saturday);
        System.out.println("Sunday Count = "+sundays);
        return saturday + sundays;
    }

	      

	 /**
	  * @param noOfWeekends
	  * @param endDate
	  * Will increment the end date if weekends fall between start and end date
	  */

	 private static Date incrementDate(int noOfWeekends, Date endDate){   
		  Calendar c = Calendar.getInstance(); 
		  c.setTime(endDate); 
		  c.add(Calendar.DATE, noOfWeekends);
		  endDate = c.getTime();  
		  return endDate;
	 }

	 

	 /**
	  * @param startDate
	  * @param noOfDays
	  * Will return the initial end date 
	  */

	 private static Date getInitialEndDate(Date startDate, int noOfDays){   
		  Date initialEndDate = null;
		  Calendar c = Calendar.getInstance(); 
		  c.setTime(startDate); 
		  c.add(Calendar.DATE, noOfDays);
		  initialEndDate = c.getTime();  
		  return initialEndDate;
	 }


	 /**
	  * @param tempEndDate
	  * Checks whether its Saturday or Sunday or Weekday
	  * @returns Which day
	  */

	 private static String checkSatOrSunDay(Date tempEndDate){
		  String day = "";
		  Calendar calDate = Calendar.getInstance();
		  calDate.setTime(tempEndDate); 
		  if (calDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
		        day = "SATURDAY";
	      }else if(calDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
		        day = "SUNDAY";
	      }else {
	    	  day = "WEEKDAY";
	      }  
		  return day;
	 }
	 
	 /**
	  * @param startDt
	  * @param endDt
	  * @returns the Number of Working days(Excluding Saturday and Sunday) between date range
	  */

	 public static List<Date> getWorkingDaysList(Date startDt, Date endDt) {
	      List<Date> workingDatesLst = new ArrayList<Date>();
	      Calendar calendar = Calendar.getInstance();
	      calendar.setTime(startDt);
	      while (calendar.getTime().before(incrementDate(1,endDt))) {
	          Date result = calendar.getTime();          
	          if ((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)){
	           //do not add the dates
	          }else{
	           workingDatesLst.add(result);
	          }
	          calendar.add(Calendar.DATE, 1);
	      }      
	      return workingDatesLst;
	 }
}
