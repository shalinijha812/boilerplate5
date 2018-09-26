package com.stackroute.datamunger.reader;

import com.stackroute.datamunger.query.*;
import com.stackroute.datamunger.query.parser.QueryParameter;
import com.stackroute.datamunger.query.parser.Restriction;


import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class CsvQueryProcessor implements QueryProcessingEngine {
	/*
	 * This method will take QueryParameter object as a parameter which contains the
	 * parsed query and will process and populate the ResultSet
	 */
	public DataSet getResultSet(QueryParameter queryParameter) {
		DataSet dataSetObj = new DataSet();
		Header headerObj = new Header();
		RowDataTypeDefinitions rowDataTypeDefinitionsObj = new RowDataTypeDefinitions();

		/*
		 * initialize BufferedReader to read from the file which is mentioned in
		 * QueryParameter. Consider Handling Exception related to file reading.
		 */

		String fileName = queryParameter.getFileName();

		try {
			BufferedReader bufferedReader = new BufferedReader((new FileReader(fileName)));

			/*
			 * read the first line which contains the header. Please note that the headers
			 * can contain spaces in between them. For eg: city, winner
			 */
			String line = bufferedReader.readLine();
			bufferedReader.mark(0);
			String[] headerList = line.split(",");
			for (int i = 0; i < headerList.length; i++)
				headerObj.put(headerList[i].trim(), i);

			/*
			 * read the next line which contains the first row of data. We are reading this
			 * line so that we can determine the data types of all the fields. Please note
			 * that ipl.csv file contains null value in the last column. If you do not
			 * consider this while splitting, this might cause exceptions later
			 */
			line = bufferedReader.readLine();
			String[] splittedFirstRow = line.split("\\s*,\\*", -1);
			/*
			 * populate the header Map object from the header array. header map is having
			 * data type <String,Integer> to contain the header and it's index.
			 */


			/*
			 * We have read the first line of text already and kept it in an array. Now, we
			 * can populate the RowDataTypeDefinition Map object. RowDataTypeDefinition map
			 * is having data type <Integer,String> to contain the index of the field and
			 * it's data type. To find the dataType by the field value, we will use
			 * getDataType() method of DataTypeDefinitions class
			 */
			DataTypeDefinitions dataTypeDefinitions = new DataTypeDefinitions();
			for (int i = 0; i < splittedFirstRow.length; i++) {
				rowDataTypeDefinitionsObj.put(i, (String) dataTypeDefinitions.getDataType(splittedFirstRow[i].trim()));
			}

			/*
			 * once we have the header and dataTypeDefinitions maps populated, we can start
			 * reading from the first line. We will read one line at a time, then check
			 * whether the field values satisfy the conditions mentioned in the query,if
			 * yes, then we will add it to the resultSet. Otherwise, we will continue to
			 * read the next line. We will continue this till we have read till the last
			 * line of the CSV file.
			 */

			/* reset the buffered reader so that it can start reading from the first line */
			bufferedReader.reset();
			/*
			 * skip the first line as it is already read earlier which contained the header
			 */

			/* read one line at a time from the CSV file till we have any lines left */


			/*
			 * once we have read one line, we will split it into a String Array. This array
			 * will continue all the fields of the row. Please note that fields might
			 * contain spaces in between. Also, few fields might be empty.
			 */
			List<String> fieldList = queryParameter.getFields();
			List<Restriction> restrictionList = queryParameter.getRestrictions();
			if (restrictionList == null) {
				if (fieldList.size() == 1) {
					if (fieldList.get(0).equals(("*"))) {
						Long key = new Long(1);

						while ((line = bufferedReader.readLine()) != null) {

							Row rowObj = new Row();
							String[] stringArray = line.split("\\s*,\\s*", -1);
							for (int i = 0; i < stringArray.length; i++) {

//								System.out.println(headerList[i].trim());
//								System.out.println(stringArray[i]);
								rowObj.put(headerList[i].trim(), stringArray[i]);
							}
							dataSetObj.put(key, rowObj);
							key++;
						}
//						System.out.println(dataSetObj);
						return dataSetObj;
					}
				}

				else
				{
					int index=0;
					bufferedReader.reset();
					Long key = new Long(1);
					while ((line = bufferedReader.readLine()) != null) {
						Row rowObj = new Row();
						String[] stringArray = line.split("\\s*,\\s*", -1);

						for (int i = 0; i < stringArray.length; i++) {

							if (fieldList.contains(headerList[i])) {

								rowObj.put(headerList[i], stringArray[i]);

							}
						}
						index=0;

						dataSetObj.put(key, rowObj);
						key++;


					}
					System.out.println(dataSetObj);
					return dataSetObj;
				}
			}
			else if(restrictionList.size()==1)
			{
				Long key = new Long(1);
				String propertyName=restrictionList.get(0).getPropertyName();
				String value=restrictionList.get(0).getPropertyValue();
				String condition=restrictionList.get(0).getCondition();
				System.out.println(condition);
				String dataType=(String)dataTypeDefinitions.getDataType(value);
				while((line=bufferedReader.readLine())!=null)
				{
					String[] stringArray = line.split("\\s*,\\s*", -1);
					for(int i=0;i<stringArray.length;i++)
					{
						if(headerList[i].equalsIgnoreCase(propertyName))
						{
							Filter filterObj=new Filter();
							boolean check=filterObj.evaluate(stringArray[i],value,condition,dataType);
									if (check==true)
									{
										Row rowObj=new Row();
										for(String field:fieldList)
										{
											rowObj.put(field,stringArray[headerObj.get(field)]);
										}
										dataSetObj.put(key,rowObj);
										key++;
									}
						}
					}
				}
				System.out.println(dataSetObj);
				return dataSetObj;
			}
			else if (restrictionList.size()>1)
			{

				Long key = new Long(1);
				List<String> logicalOperators=queryParameter.getLogicalOperators();
				boolean condition=true;
				while((line=bufferedReader.readLine())!=null)
				{
					List<Boolean> conditionList=new ArrayList<>();
					System.out.println(" ");


					String[] stringArray = line.split("\\s*,\\s*", -1);


						for(Restriction restriction:restrictionList)
						{

							String propertyName=restriction.getPropertyName();
							String value=restriction.getPropertyValue();
							String condition1=restriction.getCondition();
							//System.out.println(condition1);
							String dataType=(String)dataTypeDefinitions.getDataType(value);
							Filter filterObj=new Filter();
							//System.out.println(stringArray[headerObj.get(propertyName)]+":"+condition1+":"+value);
							System.out.println("cond:    "+stringArray[headerObj.get(propertyName)]+"                 "+condition1+"         "+value);


							boolean check=filterObj.evaluate(stringArray[headerObj.get(propertyName)],value,condition1,dataType);
							conditionList.add(check);
							//System.out.println(stringArray[headerObj.get(propertyName)]+" "+condition1+" "+value);
							System.out.println(check);
						}
						System.out.println("condList:  "+conditionList);

						if (logicalOperators!=null) {
							String operator;
							condition = conditionList.get(conditionList.size() - 1);

							for (int indexx = logicalOperators.size() - 1; indexx >= 0; indexx--) {
								operator = logicalOperators.get(indexx);
							//	System.out.println(conditionList.get(indexx));
								switch (operator) {
									case "and":

										condition = condition && conditionList.get(indexx);
										//System.out.println(condition+" "+conditionList.get(indexx)+" "+condition);

										break;
									case "or":
										condition = condition || conditionList.get(indexx);
										break;
									case "not":
										condition = condition && !conditionList.get(indexx);
										break;
								}
							}
						}


						if (condition==true)
						{
							Row rowObj=new Row();
							for(String field:fieldList)
							{
								rowObj.put(field,stringArray[headerObj.get(field)]);
								System.out.println(rowObj);
							}
							dataSetObj.put(key,rowObj);
							key++;
						}
					}

					return dataSetObj;
			}

		}

					catch (FileNotFoundException e)
					{
						e.printStackTrace();

					}
					catch (IOException e)
					{
						e.printStackTrace();
					}









			/*
			 * if there are where condition(s) in the query, test the row fields against
			 * those conditions to check whether the selected row satifies the conditions
			 */

			/*
			 * from QueryParameter object, read one condition at a time and evaluate the
			 * same. For evaluating the conditions, we will use evaluateExpressions() method
			 * of Filter class. Please note that evaluation of expression will be done
			 * differently based on the data type of the field. In case the query is having
			 * multiple conditions, you need to evaluate the overall expression i.e. if we
			 * have OR operator between two conditions, then the row will be selected if any
			 * of the condition is satisfied. However, in case of AND operator, the row will
			 * be selected only if both of them are satisfied.
			 */

			/*
			 * check for multiple conditions in where clause for eg: where salary>20000 and
			 * city=Bangalore for eg: where salary>20000 or city=Bangalore and dept!=Sales
			 */

			/*
			 * if the overall condition expression evaluates to true, then we need to check
			 * if all columns are to be selected(select *) or few columns are to be
			 * selected(select col1,col2). In either of the cases, we will have to populate
			 * the row map object. Row Map object is having type <String,String> to contain
			 * field Index and field value for the selected fields. Once the row object is
			 * populated, add it to DataSet Map Object. DataSet Map object is having type
			 * <Long,Row> to hold the rowId (to be manually generated by incrementing a Long
			 * variable) and it's corresponding Row Object.
			 */

			/* return dataset object */


		return null;
		}

	}



