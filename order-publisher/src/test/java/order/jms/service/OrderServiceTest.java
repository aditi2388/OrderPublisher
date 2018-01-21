package order.jms.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import order.jms.model.Order;
import order.jms.model.QueueDetails;
import order.jms.util.XMLConverter;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

	private static final Logger LOG = LoggerFactory.getLogger(OrderServiceTest.class);

	@Autowired
	OrderService orderservice;

	@Autowired
	XMLConverter xmlconverter;

	@SuppressWarnings("deprecation")
	@Test
	public void testUploadOrderFile() {
		Path path = Paths.get(
				"C:\\Users\\Aditi\\eclipse-workspace\\order-publisher\\src\\test\\java\\interview-test-orders-2.xml");
		String name = "interview-test-orders-2.xml";
		String originalFileName = "interview-test-orders-2.xml";
		String contentType = "text/xml";
		byte[] content = null;
		try {
			content = Files.readAllBytes(path);
		} catch (final IOException e) {
		}
		MultipartFile file = new MockMultipartFile(name, originalFileName, contentType, content);
		Assert.isTrue(orderservice.uploadOrderFile(file));

	}

	@SuppressWarnings("deprecation")
	@Test
	public void testPublishOrder() {
		try {
			QueueDetails queuedetails = new QueueDetails();
			queuedetails.setDestination("queue_req");
			List<Order> orders = xmlconverter.readOrders();
			Assert.isTrue(orderservice.publishOrder(orders, queuedetails));

		} catch (IOException e) {
			LOG.error("Error occured in XMl data read.");
		}
	}

}
