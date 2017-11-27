

public class RealEstateClass {
	public RealEstateClass(String Property_Type, String Address, String City, int Zip, int Price, double Beds, 
						   double Baths, String Location, int Square_Feet, int Lot_Size, int Year_Built, int Days_On_Market, 
						   int Dollar_Per_Sq_Ft, int HOA_Per_Month, int Rating) {
		this.Property_Type = Property_Type;
		this.Address = Address;
		this.City = City;
		this.Zip = Zip;
		this.Price = Price;
		this.Beds = Beds;
		this.Baths = Baths;
		this.Location = Location;
		this.Square_Feet = Square_Feet;
		this.Lot_Size = Lot_Size;
		this.Year_Built = Year_Built;
		this.Days_On_Market = Days_On_Market;
		this.Dollar_Per_Sq_Ft = Dollar_Per_Sq_Ft;
		this.HOA_Per_Month = HOA_Per_Month;
		this.Rating = Rating;
	}
	
	public final String Property_Type;
	public final String Address;
	public final String City;
	public final int Zip;
	public final int Price;
	public final double Beds;
	public final double Baths;
	public final String Location;
	public final int Square_Feet;
	public final int Lot_Size;
	public final int Year_Built;
	public final int Days_On_Market;
	public final int Dollar_Per_Sq_Ft;
	public final int HOA_Per_Month;
	public final int Rating;

}
