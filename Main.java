/**
 *
 *  @author Kostyria Vladyslav S14991
 *
 */

package zad1;

import java.io.IOException;

import javax.swing.SwingUtilities;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

public class Main {
  public static void main(String[] args) throws IOException, ParseException, ParserConfigurationException, SAXException {
    Service s = new Service("USA");
    String weatherJson = s.getWeather("New York");
    Double rate1 = s.getRateFor("USD");
    Double rate2 = s.getNBPRate();
   System.out.println(rate2);
   
   SwingUtilities.invokeLater(new Runnable() {

       public void run() {
           ControlView cv;
		try {
			cv = new ControlView(s);
			cv.loadURL("https://www.wikipedia.org");
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }     
  });
   
    
    
    
  }
}
