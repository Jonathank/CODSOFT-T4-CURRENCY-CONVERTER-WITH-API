package currency_Converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Scanner;

public class CurrencyConverter {

	    public static void main(String[] args) {
	        String apiKey = "rDiP9VLWTmD6SMCjomDm0DRwiwIXf8HH"; // API key
	        String baseCurrency;
	        String targetcurrency;
	        OkHttpClient client = new OkHttpClient();
	        ObjectMapper mapper = new ObjectMapper();
	        Scanner scanner = new Scanner(System.in);

	        try {
				System.out.println("\n");
				System.out.println("WELCOME TO JONATHAN CURRENCY CONVERTER");
				System.out.println("\n");
				System.out.print("ENTER YOUR CURRENCY E.g  USA,GBP  :		 ");
				 
			     baseCurrency = scanner.next(); //the base currency
			     baseCurrency = baseCurrency.toUpperCase();
			     System.out.println("\n");
			     System.out.print("ENTER TARGET CURRENCY E.g  USA,GBP  :		 ");
			     //"EUR,GBP,CAD,AUD,USD"
			     targetcurrency = scanner.next(); ; // the currencies you want to fetch
			     
	            // Construct the URL with actual targetcurrency and basecurrency
	            String url = "https://api.apilayer.com/exchangerates_data/latest?symbols=" + targetcurrency + "&base=" + baseCurrency;

	            Request request = new Request.Builder()
	                    .url(url)
	                    .addHeader("apikey", apiKey) // Use the correct header name based on API documentation
	                    .build();

	            Response response = client.newCall(request).execute();

	            if (response.isSuccessful()) {
	                String responseBody = response.body().string();

	                // Parse JSON response
	                JsonNode jsonNode = mapper.readTree(responseBody);
	                JsonNode ratesNode = jsonNode.get("rates");

	                // Prompt user for amount
	                System.out.print("Enter the amount in " + baseCurrency + ": ");
	                double amount = scanner.nextDouble();

	                // Calculate and display converted amounts
	                System.out.println("\n");
	                System.out.println("Converted amounts:");

	                for (String currency : targetcurrency.split(",")) {
	                    double exchangeRate = ratesNode.get(currency).asDouble();
	                    double convertedAmount = amount * exchangeRate;
	                    System.out.println("\n");
	                    System.out.println(amount+" "+baseCurrency +"  =  " + convertedAmount +" "+currency );
	                }
	            } else {
	                System.out.println("Request failed: " + response.code() + " - " + response.message());
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            scanner.close(); // Close the scanner
	        }
	        
	    }
	   
	}

	


