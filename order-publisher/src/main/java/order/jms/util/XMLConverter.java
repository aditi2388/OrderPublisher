package order.jms.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import order.jms.model.Order;

@Component
public class XMLConverter {
	
	private static final Logger LOG = LoggerFactory.getLogger(XMLConverter.class);
	
	@Value("${file.upload.path}")
    private String UPLOADED_FOLDER; 

	public List<Order> readOrders() throws IOException {
		List<Order> orders = new ArrayList<>();
		File path = new File(UPLOADED_FOLDER);
		File[] files = path.listFiles();
		XmlMapper xmlMapper = new XmlMapper();
		List<String> xmllines = inputStreamToString(new FileInputStream(files[0]));
		for(String line : xmllines) {
		Order order = xmlMapper.readValue(line, Order.class);
		orders.add(order);
		LOG.debug("Oder : {}", order);
		}
		LOG.debug("Orders: {}",orders);
		return orders;
	}
	
	public static List<String> inputStreamToString(InputStream is) throws IOException {
		List<String> xmllines = new ArrayList<>();
	    String line;
	    BufferedReader br = new BufferedReader(new InputStreamReader(is));
	    while ((line = br.readLine()) != null) {
	    	xmllines.add(line);
	    }
	    br.close();
	    xmllines.remove(0); //remove the first line which is not an order object
	    return xmllines;
	}

}
