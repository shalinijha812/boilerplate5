package com.stackroute.datamunger.query.parser;

import java.util.List;
/* 
 * This class will contain the elements of the parsed Query String such as conditions,
 * logical operators,aggregate functions, file name, fields group by fields, order by
 * fields, Query Type
 * */
public class QueryParameter {
	public String fileName;
	public List<String> fields;
	public List<Restriction> restrictions;
	public String baseQuery;
	public List<AggregateFunction> aggregateFunctions;
	public List<String> logicalOperators;
	public List<String> orderByFields;
	public List<String> groupByFields;


	public String getFileName() {
		// TODO Auto-generated method stub
		return fileName;
	}

	public List<String> getFields() {
		// TODO Auto-generated method stub
		return fields;
	}

	public List<Restriction> getRestrictions() {
		// TODO Auto-generated method stub
		return restrictions;
	}

	public String getBaseQuery() {
		// TODO Auto-generated method stub
		return baseQuery;
	}

	public List<AggregateFunction> getAggregateFunctions() {
		// TODO Auto-generated method stub
		return aggregateFunctions;
	}

	public List<String> getLogicalOperators() {
		// TODO Auto-generated method stub
		return logicalOperators;
	}
	//Setter
	public void setFilename(String fileName)
	{
		this.fileName=fileName;
	}
	public void setFields(List<String> fields)
	{
		this.fields=fields;
	}
	public void setRestrictions(List<Restriction> restrictions)
	{
		this.restrictions=restrictions;
	}
	public void setBaseQuery(String baseQuery)
	{
		this.baseQuery=baseQuery;
	}
	public void setAggregateFunctions(List<AggregateFunction> aggregateFunctions)
	{
		this.aggregateFunctions=aggregateFunctions;
	}
	public void setLogicalOperators(List<String> logicalOperators)
	{
		this.logicalOperators=logicalOperators;
	}
	public void setOrderByFields(List<String> orderByFields){this.orderByFields=orderByFields;}
	public void setGrouprByFields(List<String> groupByFields){this.groupByFields=groupByFields;}

	public List<String> getGroupByFields() {
		// TODO Auto-generated method stub
		return groupByFields;
	}

	public List<String> getOrderByFields() {
		// TODO Auto-generated method stub
		return orderByFields;
	}

	public String getQUERY_TYPE() {
		// TODO Auto-generated method stub
		return null;
	}

		

	
}
