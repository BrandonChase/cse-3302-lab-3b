import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class RealEstateMainFunctional {
	static final String INPUT_FILENAME_HOUSE = "House Data.csv";
	static final String INPUT_FILENAME_ZIP = "ZipRateTable.csv";
	static final String OUTPUT_FILENAME_ZIP = "HouseAveragesByZip.txt";
	static final String OUTPUT_FILENAME_RATING = "HouseOrderByRating.txt";
	
	public static void main(String[] args) {
		try {
			NumberFormat formatter = NumberFormat.getCurrencyInstance();
			
			//-----Get Data-----
			ArrayList<RealEstateClass> houses = new ArrayList<>();
			readHouseDataFromFile(houses);
			
			ArrayList<ZipRateTable> zipRates = new ArrayList<>();
			readZipRateDataFromFile(zipRates);
			
			//-----Write to File for House Averages By Zip-----
			final BufferedWriter bWriter = new BufferedWriter(new FileWriter(OUTPUT_FILENAME_ZIP));
			
				//-----Write Headers-----
			bWriter.write("Zip Code\tNo. Homes\tAve Price\tAve Sqft\tAve Beds\tAve Baths\tAve $/sqft\tAve DOM\tAve YrBlt\tAve HOA");
			bWriter.newLine();
				
				//-----Write Data-----
			houses.stream()
					.map(house -> house.Zip) //convert to stream of zip codes
					.distinct()
					.sorted()
					.forEach(zipCode -> { //go through each zipCode in ascending order
						try {
							//-----Zip Code-----
							bWriter.write(String.valueOf(zipCode));
							bWriter.write("\t");
							
							//-----No. Homes-----
							bWriter.write(String.valueOf(houses.stream()
														  	   .filter(house -> house.Zip == zipCode) //filter to stream of houses in current zipCode
														  	   .count()));
							bWriter.write("\t");
							
							//-----Ave Price-----
							bWriter.write("$");
							bWriter.write(NumberFormat.getNumberInstance(Locale.US).format((int) houses.stream()
								  	   						   									 .filter(house -> house.Zip == zipCode) //filter to stream of houses in current zipCode));
								  	   						   									 .mapToInt(house -> house.Price) //convert to stream of prices
								  	   						   									 .average()
								  	   						   									 .getAsDouble())); 
							bWriter.write("\t");
							
							//-----Ave Sqft-----
							bWriter.write(NumberFormat.getNumberInstance(Locale.US).format((int) houses.stream()
								  	   						   									 .filter(house -> house.Zip == zipCode) //filter to stream of houses in current zipCode));
								  	   						   									 .mapToInt(house -> house.Square_Feet) //convert to stream of square feets
								  	   						   									 .average()
								  	   						   									 .getAsDouble()));
							bWriter.write("\t");
							
							//-----Ave Beds-----
							bWriter.write(String.format("%.01f", houses.stream()
								  	   						   		  .filter(house -> house.Zip == zipCode) //filter to stream of houses in current zipCode));
								  	   						   		  .mapToDouble(house -> house.Beds) //convert to stream of bed numbers
								  	   						   		  .average()
								  	   						   		  .getAsDouble())); 
							bWriter.write("\t");
							
							//-----Ave Baths-----
							bWriter.write(String.format("%.01f", houses.stream()
	   						   		  								  .filter(house -> house.Zip == zipCode) //filter to stream of houses in current zipCode));
	   						   		  								  .mapToDouble(house -> house.Baths) //convert to stream of bath numbers
	   						   		  								  .average()
	   						   		  								  .getAsDouble())); 
							bWriter.write("\t");
							
							//-----Ave $/sqft-----
							bWriter.write(formatter.format(houses.stream()
									   			   .filter(house -> house.Zip == zipCode) //filter to stream of houses in current zipCode));
									   			   .mapToInt(house -> house.Dollar_Per_Sq_Ft) //convert to stream of prices
									   			   .average()
									   			   .getAsDouble())); 
							bWriter.write("\t");
							
							//-----Ave DOM-----
							bWriter.write(String.format("%.01f", houses.stream()
								  	   						   		  .filter(house -> house.Zip == zipCode) //filter to stream of houses in current zipCode));
								  	   						   		  .mapToInt(house -> house.Days_On_Market) //convert to stream of prices
								  	   						   		  .average()
								  	   						   		  .getAsDouble())); 
							bWriter.write("\t");
							
							//-----Ave YrBlt-----
							bWriter.write(String.format("%d", (int) houses.stream()
								  	   						   		 .filter(house -> house.Zip == zipCode) //filter to stream of houses in current zipCode));
								  	   						   		 .mapToInt(house -> house.Year_Built) //convert to stream of prices
								  	   						   		 .average()
								  	   						   		 .getAsDouble())); 
							bWriter.write("\t");
							
							//-----Ave HOA-----
							bWriter.write(formatter.format(houses.stream()
								  	   						   	 .filter(house -> house.Zip == zipCode) //filter to stream of houses in current zipCode));
								  	   						   	 .mapToInt(house -> house.HOA_Per_Month) //convert to stream of prices
								  								 .average()
								  	   						   	 .getAsDouble())); 
							
							bWriter.newLine();
						} catch(Exception e) {
							System.out.println("ZIP DATA CALCULATION ERROR: " + e.getMessage());
						}
					});
			bWriter.close();
			
			//-----Write to File for House Order By Rating-----
			final BufferedWriter bWriter2 = new BufferedWriter(new FileWriter(OUTPUT_FILENAME_RATING));
			
				//-----Write Headers-----
			bWriter2.write("Type\tAddress\tCity\tZip\tPrice\tBeds\tBaths\tLocation\tSqft\tLot size\tYrBlt\tDOM\t$/SqFt\tHOA/mth\tRank Grp\tPercnt SqFt");
			bWriter2.newLine();
				
				//-----Write Data-----
			houses.stream()
				  .map(house -> house.Zip) //convert to stream of zip codes
				  .distinct()
				  .sorted()
				  .forEach(zipCode -> { //go through each zipCode in ascending order
					  houses.stream()
					   		.filter(house -> house.Zip == zipCode) //filter to stream of houses in current zipCode
					   		.mapToInt(house -> house.Rating)
					   		.distinct()
					   		.sorted()
					   		.forEach(rating -> { //go through each rating in asending order
					   			houses.stream()
						   			  .filter(house -> house.Zip == zipCode)
						   			  .filter(house -> house.Rating == rating)
						   		  	  .mapToInt(house -> house.Price)
						   			  .distinct()
						   			  .sorted()
						   			  .forEach(price -> { //go through each price in ascending order
						   				  houses.stream()
								   			    .filter(house -> house.Zip == zipCode)
								   			    .filter(house -> house.Rating == rating)
								   			    .filter(house -> house.Price == price)
								   			    .mapToInt(house -> house.Dollar_Per_Sq_Ft)
								   			    .distinct()
								   			    .sorted()
								   			    .forEach(dollar_per_sq_ft -> {
								   			    	houses.stream()
									   				  	  .filter(house -> house.Zip == zipCode)
									   				  	  .filter(house -> house.Rating == rating)
									   				  	  .filter(house -> house.Price == price)
									   				  	  .filter(house -> house.Dollar_Per_Sq_Ft == dollar_per_sq_ft)
									   				  	  .forEach(house -> { //go through each house and write data
									   				  		  try {
										   				  		  //-----Type-----
									   				  			  bWriter2.write(house.Property_Type);
									   				  			  bWriter2.write("\t");
										   				  		  
										   				  		  //-----Address-----
									   				  			  bWriter2.write(house.Address);
									   				  			  bWriter2.write("\t");
										   				  		  
										   				  		  //-----City-----
									   				  			  bWriter2.write(house.City);
									   				  			  bWriter2.write("\t");
										   				  		  
										   				  		  //-----Zip-----
									   				  			  bWriter2.write(String.valueOf(house.Zip));
									   				  			  bWriter2.write("\t");
										   				  		  
										   				  		  //-----Price-----
									   				  			  bWriter2.write("$");
									   				  			  bWriter2.write(NumberFormat.getNumberInstance(Locale.US).format(house.Price));
									   				  			  bWriter2.write("\t");
										   				  		  
									   				  			  //-----Beds-----
									   				  			  bWriter2.write(String.format("%.01f", house.Beds));
									   				  			  bWriter2.write("\t");
									   				  			
									   				  			  //-----Baths-----
									   				  			  bWriter2.write(String.format("%.01f", house.Baths));
									   				  			  bWriter2.write("\t");
									   				  			  
									   				  			  //-----Location-----
									   				  			  bWriter2.write(house.Location);
									   				  			  bWriter2.write("\t");
									   				  			  
									   				  			  //-----Sqft-----
									   				  			  bWriter2.write(NumberFormat.getNumberInstance(Locale.US).format(house.Square_Feet));
									   				  			  bWriter2.write("\t");
									   				  			  
									   				  			  //-----Lot size-----
									   				  			  bWriter2.write(NumberFormat.getNumberInstance(Locale.US).format(house.Lot_Size));
									   				  			  bWriter2.write("\t");
									   				  			  
									   				  			  //-----YrBlt-----
									   				  			  bWriter2.write(String.valueOf(house.Year_Built));
									   				  			  bWriter2.write("\t");
									   				  			  
									   				  			  //-----DOM-----
									   				  			  bWriter2.write(String.valueOf(house.Days_On_Market));
									   				  			  bWriter2.write("\t");
									   				  			  
									   				  			  //-----$/SqFt-----
									   				  			  bWriter2.write("$");
									   				  			  bWriter2.write(NumberFormat.getNumberInstance(Locale.US).format(house.Dollar_Per_Sq_Ft));
									   				  			  bWriter2.write("\t");
									   				  			  
									   				  			  //-----HOA/mth-----
									   				  			  bWriter2.write("$");
									   				  			  bWriter2.write(NumberFormat.getNumberInstance(Locale.US).format(house.HOA_Per_Month));
									   				  			  bWriter2.write("\t");
									   				  			  
									   				  			  //-----Rank Grp-----
									   				  			  bWriter2.write(String.valueOf(house.Rating));
									   				  			  bWriter2.write("\t");
									   				  			  
									   				  			  //-----Percent SqFt-----
									   				  			  bWriter2.write(String.format("%.01f", 100*(house.Dollar_Per_Sq_Ft / houses.stream()
									   				  					  																   .filter(house1 -> house1.Zip == zipCode) 
									   				  					  																   .mapToInt(house1 -> house1.Dollar_Per_Sq_Ft)
									   				  					  																   .average()
									   				  					  																   .getAsDouble())));
									   				  			  bWriter2.newLine();
									   				  		  } catch(Exception e) {
									   							System.out.println("RATING DATA CALCULATION ERROR: " + e.getMessage());
									   				  		  }									   				  	  
									   				  	  });
								   			    });
						   			  });
					   		});
				  });
		
			bWriter2.close();
		} catch(Exception e) {
			System.out.println("FILE ERROR: " + e.getMessage());
		}
	}
	
	public static void readHouseDataFromFile(ArrayList<RealEstateClass> houses) throws NumberFormatException, IOException {
		String[] values;
		String line = "";
		BufferedReader bReader = new BufferedReader(new FileReader(INPUT_FILENAME_HOUSE));
		
		while((line = bReader.readLine()) != null)
		{
			values = line.split(",");
			houses.add(new RealEstateClass(values[0], //Property_Type
										   values[1], //Address
										   values[2], //City
										   Integer.parseInt(values[3]), //Zip
										   Integer.parseInt(values[4]), //Price
										   Double.parseDouble(values[5]), //Beds
										   Double.parseDouble(values[6]), //Baths
										   values[7], //Location
										   Integer.parseInt(values[8]), //Square_Feet
										   Integer.parseInt(values[9]), //Lot_Size
										   Integer.parseInt(values[10]), //Year_Built
										   Integer.parseInt(values[11]), //Days_On_Market
										   Integer.parseInt(values[12]), //Dollar_Per_Sq_Ft
										   Integer.parseInt(values[13]) //HOA_Per_Month
										   ));				  
		}
		
		bReader.close();
	}
	
	public static void readZipRateDataFromFile(ArrayList<ZipRateTable> zipRates) throws NumberFormatException, IOException {
		String[] values;
		String line = "";
		BufferedReader bReader = new BufferedReader(new FileReader(INPUT_FILENAME_ZIP));
		
		while((line = bReader.readLine()) != null)
		{
			values = line.split(",");
			zipRates.add(new ZipRateTable(Integer.parseInt(values[0]), //ZipCode
										  Integer.parseInt(values[1]) //Rating
										  ));				  
		}
		
		bReader.close();
	}

	public static void calculateRatings(ArrayList<RealEstateClass> houses) {
		
	}
}
