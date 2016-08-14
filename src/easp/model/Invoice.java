package easp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Invoice {
	
	private ArrayList<Position> positions;
	private int customerID;
	private double totalPrice;

	public Invoice(int customerID, ArrayList<Position> positions){
		this.positions = positions;
		this.customerID = customerID;
		this.totalPrice = this.computeTotalPrice();
	}
	
	private double computeTotalPrice(){
		double price = 0.0;
		for (int i = 0; i<this.positions.size(); i++){
			price += positions.get(i).getPrice();
		}
		System.out.println(price);
		return price;
	}
	
	public int getCustomerID(){
		return this.customerID;
	}
	
	public double getPrice(){
		return this.totalPrice;
	}
	
}
