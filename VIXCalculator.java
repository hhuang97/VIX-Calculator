import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class VIXCalculator {
	private static Scanner sc;
	private static boolean running = true;
	private static LinkedList<Double> link = new LinkedList<Double>();
	private static String expire = "12/18/15 08:30:00"; // third Friday of the month, expiration date of options
	
	public static void main (String[] args){
		sc = new Scanner(System.in);
		while (running){
			menu();
			int option = sc.nextInt();
			selection(option);
		}
		sc.close();
	}
	
	private static void menu (){
		System.out.println("\nSelect an option below:");
		System.out.println("1) Add near term option\n2) Add next term option\n3) Remove an option");
		System.out.print("4) Display stored options\n5) Display VIX index\n6) Quit\nSelect an option above: ");
	}
	
	private static void selection (int a){
		switch (a){
		case 1:
			addNearOption();
			break;
		case 2:
			addNextOption();
			break;
		case 3:
			removeOption();
			break;
		case 4:
			displayOption();
			break;
		case 5:
			displayVIX();
			break;
		case 6:
			System.out.print("Are you sure you want to quit? (y/n): ");
			String response = sc.next();
			if (response.equalsIgnoreCase("y")){
				running = false;
			}
			break;
		default:
			System.out.println("invalid selection");
			running = false;
			break;
		}
	}
	
	@SuppressWarnings("deprecation")
	private static void addNearOption (){
		System.out.print("enter volatility index: ");
		double index = sc.nextDouble();
		Date now = new Date();
		now.setHours(8);
		now.setMinutes(30);
		now.setSeconds(0);
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
		Date expiration = null;
		try {
			expiration = format.parse(expire);
		} catch (ParseException e){
			e.printStackTrace();
		}
		long difference = expiration.getTime() - now.getTime();
		double minutes = TimeUnit.MILLISECONDS.toMinutes(difference);
		VIX v = new VIX(minutes, index);
		Double result = new Double(v.calculateNear());
		link.addFirst(result);
	}
	
	@SuppressWarnings("deprecation")
	private static void addNextOption (){
		System.out.print("enter volatility index: ");
		double index = sc.nextDouble();
		Date now = new Date();
		now.setHours(8);
		now.setMinutes(30);
		now.setSeconds(0);
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
		Date expiration = null;
		try {
			expiration = format.parse(expire);
		} catch (ParseException e){
			e.printStackTrace();
		}
		long difference = expiration.getTime() - now.getTime();
		double minutes = TimeUnit.MILLISECONDS.toMinutes(difference);
		VIX v = new VIX(minutes, index);
		Double result = new Double(v.calculateNext());
		link.addFirst(result);
	}
	
	private static void displayOption (){
		for (int i = 0; i < link.size(); i++){
			System.out.println((i+1) + ") variance: " + link.get(i));
		}
	}
	
	private static void removeOption (){
		System.out.println("Options:");
		for (int i = 0; i < link.size(); i++){
			System.out.println((i+1) + ") " + link.get(i));
		}
		System.out.print("Select option number to remove: ");
		int x = sc.nextInt();
		link.remove(x-1);
		System.out.println("Option " + x + " removed.");
	}
	
	private static void displayVIX (){
		double variance = 0.0, ind;
		for (int i = 0; i < link.size(); i++){
			variance+=(double)(link.get(i));
			System.out.println(variance);
		}
		ind = 100 * Math.sqrt(variance);
		System.out.println(ind);
	}
}
