package com.stackroute.datamunger.query.parser;

/*
 * This class is used for storing name of field, condition and value for 
 * each conditions
 * */
public class Restriction {
	public String name;
	public String condition;
	public String value;
	//Constructor
	public Restriction(String name, String value ,String condition)
	{
		setPropertyName(name);
		setPropertyValue(value);
		setCondition(condition);
	}

	public String getPropertyName() {
		// TODO Auto-generated method stub
		return name;
	}

	public String getPropertyValue() {
		// TODO Auto-generated method stub
		return value;
	}

	public String getCondition() {
		// TODO Auto-generated method stub
		return condition;
	}
	public void setPropertyName(String name)
	{
		this.name=name;
	}
	public void setCondition(String condition)
	{
		this.condition=condition;
	}
	public void setPropertyValue(String value)
	{
		this.value=value;
	}
	
	

}
