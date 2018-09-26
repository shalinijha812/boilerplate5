package com.stackroute.datamunger.query.parser;

import java.util.ArrayList;
import java.util.List;

public class QueryParser {
	private QueryParameter queryParameter = new QueryParameter();

	
	/*
	 * This method will parse the queryString and will return the object of
	 * QueryParameter class
	 */
	public QueryParameter parseQuery(String queryString)
	{
		takeFileName(queryString);
		takeFields(queryString);
		takeRestrictions(queryString);
		takeAggregateFunctions(queryString);

		List<String> groupByFields=takeGroupByFields(queryString);
		if (groupByFields!=null)
			queryParameter.setGrouprByFields(groupByFields);

		String baseQuery=takeBaseQuery(queryString);
		queryParameter.setBaseQuery(baseQuery);

		List<String> orderByFields=takeOrderByFields(queryString);
		if (orderByFields!=null)
			queryParameter.setOrderByFields(orderByFields);

		takeLogicalOperators(queryString);


		return queryParameter;
	}

		/*
		 * extract the name of the file from the query. File name can be found after the
		 * "from" clause.
		 */
		public void takeFileName(String queryString)
		{
			queryString = queryString.toLowerCase();
			String[] splitFromOrderBy=null;
			String[] splitFromWhere = queryString.split("where");
			String[] splitFromfrom = splitFromWhere[0].split("from");
			if (splitFromfrom[1].contains("order by"))
			{
				splitFromOrderBy=splitFromfrom[1].split("order by")	;
				splitFromOrderBy[0]=splitFromOrderBy[0].trim();
				queryParameter.setFilename((splitFromOrderBy[0]));
			}
			else {
				String theFileName = splitFromfrom[1].trim();
				queryParameter.setFilename(theFileName);
			}
		}
		public String takeBaseQuery(String queryString)
		{
			queryString=queryString.toLowerCase();
			String[] baseQueryString=null;
			String[] baseQueryString1=null;

			if (queryString.contains("where"))
			{
				baseQueryString=queryString.split(" where ");
				return baseQueryString[0];
			}
			else if (queryString.contains("group by")&&(queryString.contains("order by")))
			{
				baseQueryString=queryString.split(" group by ");
				baseQueryString1=baseQueryString[0].split(" order by ");
				return baseQueryString1[1];
			}
			else  if (queryString.contains("group by"))
			{
				baseQueryString=queryString.split(" group by ");
				return baseQueryString[0];
			}
			else
			{
				baseQueryString=queryString.split(" order by ");
				return baseQueryString[0];
			}

		}


		
		/*
		 * extract the order by fields from the query string. Please note that we will
		 * need to extract the field(s) after "order by" clause in the query, if at all
		 * the order by clause exists. For eg: select city,winner,team1,team2 from
		 * data/ipl.csv order by city from the query mentioned above, we need to extract
		 * "city". Please note that we can have more than one order by fields.
		 */
		public List<String>takeOrderByFields(String queryString)
		{
			queryString=queryString.toLowerCase();
			List<String> fieldlist=new ArrayList<String>();
			if (queryString.contains("order by"))
			{
				String[] firstSplit=queryString.split(" order by ");
				String[] finalSplit=firstSplit[1].split(",");
				for (int i=0;i<finalSplit.length;i++)
				{
					fieldlist.add(finalSplit[i]);
				}
				return fieldlist;
			}
			else
				return null;
		}
		
		/*
		 * extract the group by fields from the query string. Please note that we will
		 * need to extract the field(s) after "group by" clause in the query, if at all
		 * the group by clause exists. For eg: select city,max(win_by_runs) from
		 * data/ipl.csv group by city from the query mentioned above, we need to extract
		 * "city". Please note that we can have more than one group by fields.
		 */
		public List<String> takeGroupByFields(String queryString)
		{
			queryString=queryString.toLowerCase();
			List<String> fieldlist=new ArrayList();
			if (queryString.contains("group by"))
			{
				String[] firstSplit=queryString.split(" group by ");
				String[] secondSplit=firstSplit[1].split(" order by ");
				String[] finalSplit=secondSplit[0].split(",");
				for (int i=0;i<finalSplit.length;i++)
				{
					fieldlist.add(finalSplit[i]);

				}
				return fieldlist;
			}
			else
				return null;

		}
		
		/*
		 * extract the selected fields from the query string. Please note that we will
		 * need to extract the field(s) after "select" clause followed by a space from
		 * the query string. For eg: select city,win_by_runs from data/ipl.csv from the
		 * query mentioned above, we need to extract "city" and "win_by_runs". Please
		 * note that we might have a field containing name "from_date" or "from_hrs".
		 * Hence, consider this while parsing.
		 */
		public void takeFields(String queryString)
		{
			queryString=queryString.toLowerCase();
			String[] splittedFromSelect=queryString.split("select");
			String[] splittedFromfrom=splittedFromSelect[1].split("from");
			splittedFromfrom[0]=splittedFromfrom[0].trim();
			String[] finalSplit=splittedFromfrom[0].split(",");
			List<String> fieldList=new ArrayList<>();
			for(int i=0;i<finalSplit.length;i++)
			{
				finalSplit[i]=finalSplit[i].trim();
				fieldList.add(finalSplit[i]);
			}
			queryParameter.setFields(fieldList);
		}
		
		
		
		/*
		 * extract the conditions from the query string(if exists). for each condition,
		 * we need to capture the following: 
		 * 1. Name of field 
		 * 2. condition 
		 * 3. value
		 * 
		 * For eg: select city,winner,team1,team2,player_of_match from data/ipl.csv
		 * where season >= 2008 or toss_decision != bat
		 * 
		 * here, for the first condition, "season>=2008" we need to capture: 
		 * 1. Name of field: season 
		 * 2. condition: >= 
		 * 3. value: 2008
		 * 
		 * the query might contain multiple conditions separated by OR/AND operators.
		 * Please consider this while parsing the conditions.
		 * 
		 */
			public void takeRestrictions(String queryString)
			{
				{
					List<Restriction> restrictionsList = new ArrayList<Restriction>();
					String[] nameConditionValue;
					String[] split2 = null;
					String[] split3 = null;
					if(queryString.contains("where")) {
						String[] split1 = queryString.split("where");
						split1[1] = split1[1].trim();

						String[] splito=split1[1].split(" group by ");
						String[] splitg=splito[0].split(" order by ");

						split2 = splitg[0].split(" and | or ");

						for (int i = 0; i < split2.length; i++) {
							if (split2[i].contains(">")&&!split2[i].contains("=")) {
								split3 = split2[i].split(">");
								split3[0] = split3[0].trim();
								split3[1] = split3[1].trim();
								if (split3[1].contains("'"))
									split3[1] = split3[1].substring(1, split3[1].length() - 1);
								restrictionsList.add(new Restriction(split3[0], split3[1], ">"));
							} else if (split2[i].contains("<")&&!split2[i].contains("=")) {
								split3 = split2[i].split("<");
								split3[0] = split3[0].trim();
								split3[1] = split3[1].trim();
								if (split3[1].contains("'"))
									split3[1] = split3[1].substring(1, split3[1].length() - 1);
								restrictionsList.add(new Restriction(split3[0], split3[1], "<"));
							} else if (split2[i].contains("=")&&!split2[i].contains(">")&&!split2[i].contains("<")&&!split2[i].contains("!")) {
								split3 = split2[i].split("=");
								split3[0] = split3[0].trim();
								split3[1] = split3[1].trim();
								if (split3[1].contains("'"))
									split3[1] = split3[1].substring(1, (split3[1].length() - 1));
								restrictionsList.add(new Restriction(split3[0], split3[1], "="));
							} else if (split2[i].contains("<=")) {
								split3 = split2[i].split("<=");
								split3[0] = split3[0].trim();
								split3[1] = split3[1].trim();
								if (split3[1].contains("'"))
									split3[1] = split3[1].substring(1, split3[1].length() - 1);
								restrictionsList.add(new Restriction(split3[0], split3[1], "<="));
							} else if (split2[i].contains(">=")) {
								split3 = split2[i].split(">=");
								split3[0] = split3[0].trim();
								split3[1] = split3[1].trim();
								if (split3[1].contains("'"))
									split3[1] = split3[1].substring(1, split3[1].length() - 1);
								restrictionsList.add(new Restriction(split3[0], split3[1], ">="));
							} else if (split2[i].contains("!=")) {
								split3 = split2[i].split("!=");
								split3[0] = split3[0].trim();
								split3[1] = split3[1].trim();
								if (split3[1].contains("'"))
									split3[1] = split3[1].substring(1, split3[1].length() - 1);
								restrictionsList.add(new Restriction(split3[0], split3[1], "!="));
							}

						}
						//System.out.println(restrictionsList);
						queryParameter.setRestrictions(restrictionsList);
					}
					else
						queryParameter.setRestrictions(null);

				}

			}
//
		/*
		 * extract the logical operators(AND/OR) from the query, if at all it is
		 * present. For eg: select city,winner,team1,team2,player_of_match from
		 * data/ipl.csv where season >= 2008 or toss_decision != bat and city =
		 * bangalore
		 * 
		 * the query mentioned above in the example should return a List of Strings
		 * containing [or,and]
		 */
		public void takeLogicalOperators(String queryString)
		{
			queryString = queryString.toLowerCase();
			List<String> logicalOperatorsList = new ArrayList<String>();
			if (queryString.contains("where")) {
				String[] split1 = queryString.split(" where ");
				String[] split2 = split1[1].split(" group by ");
				String[] split3 = split2[0].split(" order by ");
				String[] split4 = split3[0].split(" ");

				for (int i = 0; i < split4.length; i++) {
					if (split4[i].equals("and"))
						logicalOperatorsList.add(split4[i]);
					else if (split4[i].equals("or"))
						logicalOperatorsList.add(split4[i]);
					else if (split4[i].equals("not"))
						logicalOperatorsList.add(split4[i]);
				}
				queryParameter.setLogicalOperators(logicalOperatorsList);
			}
			else
				queryParameter.setLogicalOperators(null);

		}
		
		/*
		 * extract the aggregate functions from the query. The presence of the aggregate
		 * functions can determined if we have either "min" or "max" or "sum" or "count"
		 * or "avg" followed by opening braces"(" after "select" clause in the query
		 * string. in case it is present, then we will have to extract the same. For
		 * each aggregate functions, we need to know the following: 
		 * 1. type of aggregate function(min/max/count/sum/avg) 
		 * 2. field on which the aggregate function is being applied
		 * 
		 * Please note that more than one aggregate function can be present in a query
		 * 
		 * 
		 */
		public void takeAggregateFunctions(String queryString)
		{
			queryString=queryString.toLowerCase();
			List<AggregateFunction> list = new ArrayList<AggregateFunction>();
			String[] split4;
			String[] split5;
			int flag=0;
			String[] split1=queryString.split(" from ");
			String[] split2=split1[0].split(" ");
			if (split2[1].equals("*"))
				queryParameter.setAggregateFunctions(null);
			else
			{

				String[] split3 = split2[1].split(",");
				for (int i = 0; i < split3.length; i++)
				{
					if (split3[i].endsWith(")"))
					{
						split4 = split3[i].split("\\)");
						split5 = split4[0].split("\\(");
						list.add(new AggregateFunction(split5[1],split5[0]));
						flag=1;

					}

				}

				if(flag==1)
					queryParameter.setAggregateFunctions(list);
				else

					queryParameter.setAggregateFunctions(null);
			}

		}


//		return null;
//	}
	
	
}
