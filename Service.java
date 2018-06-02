/**
 *
 *  @author Kostyria Vladyslav S14991
 *
 */

package zad1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Currency;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Service {
	private String country,code,currencyCode; 
	private String longitude,latitude;
	private String temp,maxTemp,minTemp;
	private String pressure; 
	private String humidity;
	private String windSpeed; 
	private String cloudCover; 
	
	Service(String country) throws IOException, ParseException {
		this.country = country; 
		currencyCode= getCurrentCountryCurrencyCode();
		
	}
	
public void	setWeatherView(String city)throws IOException, ParseException{
		String infWeather = getWeather(city); 
		
		JSONParser jsParser =new JSONParser();
		Object obj = jsParser.parse(infWeather);
		Map mapWeather = (Map)obj;
		
		setLongitude(((Map)mapWeather.get("coord")).get("lon").toString()+"°");
		setLatitude(((Map)mapWeather.get("coord")).get("lat").toString()+"°");
		setTemp(((Map)mapWeather.get("main")).get("temp").toString()+"°C");
		setMaxTemp(((Map)mapWeather.get("main")).get("temp_max").toString()+"°C");
		setMinTemp(((Map)mapWeather.get("main")).get("temp_min").toString()+"°C");
		setPressure(((Map)mapWeather.get("main")).get("pressure").toString()+"hPa");
		setHumidity(((Map)mapWeather.get("main")).get("humidity").toString()+"%");
		setWindSpeed(((Map)mapWeather.get("wind")).get("speed").toString()+"m/s");
		setCloudCover(((Map)mapWeather.get("clouds")).get("all").toString()+"%");
		
	}
	
	public String getWeather(String city) throws IOException {
		code = getCountryCode(country);

		String ur = "https://api.openweathermap.org/data/2.5/weather?q="+city+","+code+"&units=metric&APPID=e1769be23469c8469283800e9bf17e19";
		
		String infWeather = getStringFromURL(ur);
		
		return infWeather;  
	}
	
	
	public double getRateFor(String currencyCode) throws IOException, ParseException {
		
			String currencyCurrentCountry = this.currencyCode;
		
		String pText  = getStringFromURL("http://data.fixer.io/api/latest?access_key=c083cdd9abf5521dee07ae2ed9c7e7dc&symbols="+currencyCode+","+currencyCurrentCountry); 
		JSONParser jsParser =new JSONParser();
		Object obj = jsParser.parse(pText);
		
		JSONObject jsObj = (JSONObject)obj;
		
		Map rr  = (Map)jsObj.get("rates");
		if(currencyCode.equals("EUR"))
			return Double.parseDouble(rr.get(currencyCurrentCountry).toString());
		
		double curCurrency = Double.parseDouble( rr.get(currencyCurrentCountry).toString());
		double argCurrency = Double.parseDouble (rr.get(currencyCode).toString());
		
		
		return curCurrency<argCurrency?argCurrency/curCurrency:curCurrency/argCurrency;
	}
	
	
	public double getNBPRate() throws ParserConfigurationException, IOException, SAXException, NumberFormatException, DOMException, ParseException {
		if(country.toLowerCase().equals("poland"))
			return 1;
		
		String pTextFirst = getStringFromURL("http://www.nbp.pl/kursy/xml/a061z180327.xml");

		
		Double result = mParse(pTextFirst);
		
		if(result ==-1) {
			String pTextSecond = getStringFromURL("http://www.nbp.pl/kursy/xml/b012z180321.xml");
			result = mParse(pTextSecond);
		}
		return result;
		
	}
	
	
	
	
	private String getCurrentCountryCurrencyCode() throws IOException, ParseException {
		
		    String someInf = getStringFromURL("http://restcountries.eu/rest/v2/name/"+country);
		    
		    JSONParser jsParser =new JSONParser();
			Object obj = jsParser.parse(someInf.toString());
			
			JSONArray array = (JSONArray)obj;
			
		//	System.out.println(array.get());
			
			//JSONObject jsObj = (JSONObject)obj;
			
		Map rr  = (Map)array.get(0);
			array =(JSONArray) rr.get("currencies");
			 rr  = (Map)array.get(0);
				        
			return rr.get("code").toString();
	}
	
	private double mParse(String toParse) throws ParserConfigurationException, SAXException, IOException, NumberFormatException, DOMException, ParseException {
		
		 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        InputSource inputSource = new InputSource(new StringReader(toParse));
	        Document doc = dBuilder.parse(inputSource);
	        doc.getDocumentElement().normalize();
	        
	        NodeList nList = doc.getElementsByTagName("pozycja");
	        
	        for (int temp = 0; temp < nList.getLength(); temp++) {
	            Node nNode = nList.item(temp);
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	           	 Element eElement = (Element) nNode;
	           	 
	           	if( eElement.getElementsByTagName("kod_waluty").item(0).getTextContent().equals(this.currencyCode)) {
	           		return Double.parseDouble(eElement.getElementsByTagName("kurs_sredni").item(0).getTextContent().replace(",",".")); 
	           		}
	            }
	        }        
	        return -1; 
	}
	
	private String getCountryCode(String country) {
		for(Locale l : Locale.getAvailableLocales()) {
	        if (l.getDisplayCountry().equals(country)) {
	            return l.getCountry().toString();
	        }
	    }
		return null; 
	}
	
	
	private String getStringFromURL(String urlStr) throws IOException {
		
		URL url   = new URL(urlStr);
		
		URLConnection myConnect = url.openConnection(); 
		
		 BufferedReader  in =  new BufferedReader(new InputStreamReader(myConnect.getInputStream()));
		 
		 StringBuffer sb = new StringBuffer (); 
		 
		int i ; 
		
		while((i=in.read())!=-1) {
			sb.append((char)i); 
		}
		
		
		return sb.toString();
		
		
	}
	
	public void setCountry(String country) throws IOException, ParseException {
		this.country= country;
		this.currencyCode = this.getCurrentCountryCurrencyCode();
	}

	public String getCloudCover() {
		return cloudCover;
	}

	public void setCloudCover(String cloudCover) {
		this.cloudCover = cloudCover;
	}

	public String getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getPressure() {
		return pressure;
	}

	public void setPressure(String pressure) {
		this.pressure = pressure;
	}

	public String getMinTemp() {
		return minTemp;
	}

	public void setMinTemp(String minTemp) {
		this.minTemp = minTemp;
	}

	public String getMaxTemp() {
		return maxTemp;
	}

	public void setMaxTemp(String maxTemp) {
		this.maxTemp = maxTemp;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public String getCurrency() {
		return currencyCode;
	}
	
}
	
	  
