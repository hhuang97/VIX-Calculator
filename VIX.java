

public class VIX {
	private final double minutesInYear = 525600, minutesInMonth = 43200;
	private final double minutesNearTerm = 21600, minutesNextTerm = 61920;
	private double minutes, volatility, index;
	
	public VIX (double m, double v){
		minutes = m;
		volatility = v;
	}
	
	public double calculateNear (){
		double a = minutes / minutesInYear, b, c = minutesInYear / minutesInMonth;
		b = (minutesInMonth - minutesNearTerm) / (minutesNextTerm - minutesInMonth);
		index = a * volatility * b * c;
		return index;
	}
	
	public double calculateNext (){
		double a = minutes / minutesInYear, b, c = minutesInYear / minutesInMonth;
		b = (minutesNextTerm - minutesInMonth) / (minutesInMonth - minutesNearTerm);
		index = a * volatility * b * c;
		return index;
	}
}
