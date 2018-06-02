package zad1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.parser.ParseException;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


public class ControlView {
	private Service ser; 
	private JButton exOkBut;
	private JTextField textFieldEx; 
	private JLabel lbEx;
	private JLabel curLab;  
	private JPanel panelNBP;
	private JLabel lbNBPText;
	private JLabel lbNBP; 
	private JFXPanel jfxPanel;
	  private WebEngine engine;
	
	public ControlView(Service s) throws IOException, ParseException {
		ser = s;
		JFrame frame = new JFrame();
		JPanel panelCityCountry = new JPanel();
		JPanel panelOW = new JPanel();
		JPanel panelExchange = new JPanel();
		JPanel leftContainer = new JPanel();
		GridLayout layoutexp = new GridLayout(0,1);
		panelOW.setLayout(layoutexp);
		JTextField textFieldCity = new JTextField("City");
		JTextField textFieldCountry = new JTextField("Country");
		JButton CCbutton = new JButton("OK");
		
		
		
		panelCityCountry.setPreferredSize(new Dimension(330,55));
		TitledBorder titleCC= BorderFactory.createTitledBorder("Choose City and Country");
		panelCityCountry.setBorder(titleCC);
		panelCityCountry.add(textFieldCity);
		panelCityCountry.add(textFieldCountry);
		panelCityCountry.add(CCbutton);
	
			textFieldCity.setPreferredSize(new Dimension(100,20));
		
		textFieldCountry.setPreferredSize(new Dimension(100,20));
		
		CCbutton.setPreferredSize(new Dimension(50,20));
		CCbutton.setMargin(new Insets(0,0,0,0));
		TitledBorder titleWeather= BorderFactory.createTitledBorder("Weather");
		panelOW.setPreferredSize(new Dimension(320,300));
		panelOW.setBorder(titleWeather);
		
		JLabel lbCity = new JLabel("City: ");
		JLabel lbLonLat = new JLabel();
		JLabel lbTemp = new JLabel();
		JLabel lbTempMax = new JLabel();
		JLabel lbTempMin = new JLabel();
		
		
		JLabel lbPressure = new JLabel();
		JLabel lbHum = new JLabel();
		JLabel lbWindSp = new JLabel();
		JLabel lbClouds = new JLabel();
		
		panelOW.add(lbCity);
		panelOW.add(lbLonLat);
		panelOW.add(lbTemp);
		panelOW.add(lbTempMax);
		panelOW.add(lbTempMin);
		panelOW.add(lbPressure);
		panelOW.add(lbHum);
		panelOW.add(lbWindSp);
		panelOW.add(lbClouds);
	
		
		
		textFieldCity.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(textFieldCity.getText().equals("City"))
				textFieldCity.setText("");
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		textFieldCountry.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(textFieldCountry.getText().equals("Country"))
				textFieldCountry.setText("");
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		
		CCbutton.addActionListener(new ActionListener() {
			
			

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					ser.setCountry(textFieldCountry.getText());
				} catch (IOException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					ser.setWeatherView(textFieldCity.getText());
				} catch (IOException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
					lbCity.setText("City: "+textFieldCity.getText());
					lbLonLat.setText("Longitude: "+ser.getLongitude()+" , Latitude:"+ser.getLatitude());
					lbTemp.setText("Temperature: "+ser.getTemp());
					lbTempMax.setText("Max.temp: "+ser.getMaxTemp());
					lbTempMin.setText("Min.temp: "+ser.getMinTemp());
					lbPressure.setText("Pressure: "+ser.getPressure());
					lbHum.setText("Humidity: "+ser.getHumidity());
					lbWindSp.setText("Wind Speed: "+ser.getWindSpeed());
					lbClouds.setText("Cloud cover: "+ser.getCloudCover());
					
					Double NBPRate;
					try {
						NBPRate = new BigDecimal (ser.getNBPRate()).setScale(5, RoundingMode.UP).doubleValue();
						lbNBP.setText("1.0 "+ser.getCurrency()+" = "+NBPRate+"PLN");
					} catch (NumberFormatException | DOMException | ParserConfigurationException | IOException
							| SAXException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					loadURL("https://en.wikipedia.org/wiki/"+textFieldCity.getText());
					textFieldCountry.setText("Country");
					textFieldCity.setText("City");
						
			}
			
		});
		
		lbEx = new JLabel("Write your currency");
		textFieldEx = new JTextField();
		textFieldEx.setPreferredSize(new Dimension(35,20));
		exOkBut = new JButton("OK");
		curLab = new JLabel();		
		
		
		TitledBorder titleExchange= BorderFactory.createTitledBorder("Exchange");
	panelExchange.setPreferredSize(new Dimension(320,100));
		panelExchange.setBorder(titleExchange);
		
		panelExchange.add(lbEx);
		panelExchange.add(textFieldEx);
		panelExchange.add(exOkBut);
		panelExchange.add(curLab);
		
		
		exOkBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Double rate = new BigDecimal (ser.getRateFor(textFieldEx.getText().toUpperCase())).setScale(3, RoundingMode.UP).doubleValue();
					curLab.setText("1.0 "+textFieldEx.getText().toUpperCase()+" = "+rate+" "+ser.getCurrency().toUpperCase());
					
				} catch (IOException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
			
		});
		
		
		
		panelNBP = new JPanel();
		panelNBP.setLayout(layoutexp);

		TitledBorder titleNBP= BorderFactory.createTitledBorder("NBP");
		panelNBP.setPreferredSize(new Dimension(320,80));

		panelNBP.setBorder(titleNBP);
		lbNBPText = new JLabel("Ratio of the zloty to the currency of the selected country");
		lbNBP = new JLabel();
		panelNBP.add(lbNBPText);
		panelNBP.add(lbNBP); 
		
		leftContainer.setLayout(new BoxLayout(leftContainer,BoxLayout.PAGE_AXIS));
		leftContainer.add(panelCityCountry);
		leftContainer.add(panelOW);
		leftContainer.add(panelExchange);
		leftContainer.add(panelNBP);
		
		jfxPanel = new JFXPanel();
		
		
		jfxPanel.setPreferredSize(new Dimension(600,300));
		frame.add(leftContainer);
		frame.add(jfxPanel,BorderLayout.EAST);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		createScene();
		
		
		
	}
	
	
	
	
	

    private void createScene() {
 
        Platform.runLater(new Runnable() {
            @Override 
            public void run() {
 
                WebView view = new WebView();
                engine = view.getEngine();
 
           
          

                jfxPanel.setScene(new Scene(view));
            }
        });
    }
    
    
    public void loadURL(final String url) {
        Platform.runLater(new Runnable() {
            @Override 
            public void run() {
                      
                engine.load(url);
            }
        });
    }

    private static String toURL(String str) {
        try {
            return new URL(str).toExternalForm();
        } catch (MalformedURLException exception) {
                return null;
        }
    }
    

}
