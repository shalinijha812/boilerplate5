package com.stackroute.datamunger.query.parser;

/* This class is used for storing name of field, aggregate function for 
 * each aggregate function
 * */
public class AggregateFunction {
	public String aggregateField;
	public String aggregateFunction;

	// Write logic for constructor
	public AggregateFunction(String field, String function) {
		setField(field);
		setFunction(function);
	}

	public String getFunction() {
		// TODO Auto-generated method stub
		return aggregateFunction;
	}

	public String getField() {
		// TODO Auto-generated method stub
		return aggregateField;
	}
	//Setter
	public void setField(String aggregateField)
	{
		this.aggregateField=aggregateField;
	}
	public void setFunction(String aggregateFunction)
	{
		this.aggregateFunction=aggregateFunction;
	}
	

}
