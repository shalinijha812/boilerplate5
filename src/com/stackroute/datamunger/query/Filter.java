package com.stackroute.datamunger.query;

import com.stackroute.datamunger.query.parser.Restriction;

import java.util.List;

//This class contains methods to evaluate expressions
public class Filter {
	
	/* 
	 * The evaluateExpression() method of this class is responsible for evaluating 
	 * the expressions mentioned in the query. It has to be noted that the process 
	 * of evaluating expressions will be different for different data types. there 
	 * are 6 operators that can exist within a query i.e. >=,<=,<,>,!=,= This method 
	 * should be able to evaluate all of them. 
	 * Note: while evaluating string expressions, please handle uppercase and lowercase 
	 * 
	 */
	
	public boolean evaluate(String value1,String value2,String condition,String dataType)
	{

		if(dataType.equals("java.lang.Integer")){
			if(condition.equals("=")){
				return (Integer.parseInt(value1)==Integer.parseInt(value2));
			}
			if(condition.equals(">")){
				return (Integer.parseInt(value1)>Integer.parseInt(value2));
			}
			if(condition.equals("<")){
				return (Integer.parseInt(value1)<Integer.parseInt(value2));
			}
			if(condition.equals("!=")){
				return (Integer.parseInt(value1)!=Integer.parseInt(value2));
			}
			if(condition.equals(">=")){
				return (Integer.parseInt(value1)>=Integer.parseInt(value2));
			}
			if(condition.equals("<=")){
				return (Integer.parseInt(value1)<=Integer.parseInt(value2));
			}
		}
		if(dataType.equals("java.lang.Float")){
			if(condition.equals("=")){
				return (Double.parseDouble(value1)==Double.parseDouble(value2));
			}
			if(condition.equals(">")){
				return (Double.parseDouble(value1)>Double.parseDouble(value2));
			}
			if(condition.equals("<")){
				return (Double.parseDouble(value1)<Double.parseDouble(value2));
			}
			if(condition.equals("!=")){
				return (Double.parseDouble(value1)!=Double.parseDouble(value2));
			}
			if(condition.equals(">=")){
				return (Double.parseDouble(value1)>=Double.parseDouble(value2));
			}
			if(condition.equals("<=")){
				return (Double.parseDouble(value1)<=Double.parseDouble(value2));
			}
		}
		if(condition.equals("=")){
			return value1.equalsIgnoreCase(value2);
		}
		else if(condition.equals("!="))
			return !value1.equalsIgnoreCase(value2);
		return false;                                                                        // check@TestCases
	}

	
	
	
	
	//Method containing implementation of equalTo operator

	
	
	
	
	
	//Method containing implementation of notEqualTo operator
	
	
	
	
	
	
	
	//Method containing implementation of greaterThan operator
	
	
	
	
	
	
	
	//Method containing implementation of greaterThanOrEqualTo operator
	
	
	
	
	
	
	//Method containing implementation of lessThan operator
	  
	
	
	
	
	//Method containing implementation of lessThanOrEqualTo operator
	
}
