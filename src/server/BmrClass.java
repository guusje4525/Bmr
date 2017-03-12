package server;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BmrClass {
	private double weight;
	private double height;
	private String gender;
	private int age;
	
	public double generateBmr(double weight, double height, String gender, int age){
		//Formule: https://www.bodybuilding.com/fun/calorie-know-how-get-equation-right-to-get-results.htm
		if("M".contentEquals(gender.toUpperCase()) || "MAN".contentEquals(gender.toUpperCase()) || "MEN".contentEquals(gender.toUpperCase())){
			return 66.47 + (13.75 * weight) + (5.0 * height) - (6.75 * age);
		} else {
			return 665.09 + (9.56 * weight) + (1.84 * height) - (4.67 * age);
		}
		
	}
	
}
