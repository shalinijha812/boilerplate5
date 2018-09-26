package com.stackroute.datamunger.query;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Implementation of DataTypeDefinitions class. This class contains a method getDataTypes() 
 * which will contain the logic for getting the datatype for a given field value. This
 * method will be called from QueryProcessors.   
 * In this assignment, we are going to use Regular Expression to find the 
 * appropriate data type of a field. 
 * Integers: should contain only digits without decimal point 
 * Double: should contain digits as well as decimal point 
 * Date: Dates can be written in many formats in the CSV file. 
 * However, in this assignment,we will test for the following date formats('dd/mm/yyyy',
 * 'mm/dd/yyyy','dd-mon-yy','dd-mon-yyyy','dd-month-yy','dd-month-yyyy','yyyy-mm-dd')
 */
public class DataTypeDefinitions {

	//method stub
	public static Object getDataType(String input)
	{




		Boolean checkInt=false,checkFloat=false,checkDate1=false;
		//System.out.println(input);


				// checking for Integer
		checkInt=Pattern.matches("\\d+",input);
		//System.out.println(checkInt);
		if (checkInt==true)
		{

			return "java.lang.Integer";

		}


				// checking for floating point numbers

		checkFloat=Pattern.matches("^([+-]?\\d*\\.?\\d*)$",input);
		if (checkFloat==true)
		{
			return "java.lang.Float";

		}



				// checking for date format dd/mm/yyyy
		checkDate1=Pattern.matches("^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$",input);
		if (checkDate1==true)
		{
			return  "java.lang.Date";

		}

			// checking for date format mm/dd/yyyy
		checkDate1=Pattern.matches("/^(0[1-9]|1[0-2])\\/(0[1-9]|1\\d|2\\d|3[01])\\/(19|20)\\d{4}$/",input);
		if (checkDate1==true)
		{
			return "java.lang.Date";

		}
				// checking for date format dd-mon-yy
		checkDate1=Pattern.matches("^(([0-9])|([0-2][0-9])|([3][0-1]))-(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)-\\d{2}$",input);
			if (checkDate1==true)
			{
				return "java.lang.Date";

			}

		// checking for date format dd-mon-yyyy
			checkDate1=Pattern.matches("^(([0-9])|([0-2][0-9])|([3][0-1]))-(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)-\\\\d{4}$",input);
				if (checkDate1==true)
				{
					return  "java.lang.Date";

				}
				// checking for date format dd-month-yy
		checkDate1=Pattern.matches("^(([0-9])|([0-2][0-9])|([3][0-1]))-(January|February|March|April|May|June|July|August|September|October|November|December)-\\d{2}$",input);
		if (checkDate1==true)
		{
			return "java.lang.Date";

		}
				// checking for date format dd-month-yyyy
		checkDate1=Pattern.matches("^(([0-9])|([0-2][0-9])|([3][0-1]))-(January|February|March|April|May|June|July|August|September|October|November|December)-\\d{4}$",input);
		if (checkDate1==true)
		{
			return "java.lang.Date";

		}

				// checking for date format yyyy-mm-dd
		checkDate1=Pattern.matches("^(\\d{4}$-(((0)[0-9])|((1)[0-2]))-[0-2][0-9]|(3)[0-1])",input);
		if (checkDate1==true)
		{
			return"java.lang.Date";

		}

		
		return "String";
	}
	

	
}
