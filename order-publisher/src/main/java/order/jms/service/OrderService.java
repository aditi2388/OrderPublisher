package order.jms.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import order.jms.model.Order;
import order.jms.model.QueueDetails;
import order.jms.util.JMSConstant;
import order.jms.util.XMLConverter;

/**
 * @author Aditi Sinhal
 *
 */
@Service
public class OrderService {

	private static final Logger LOG = LoggerFactory.getLogger(OrderService.class);

	@Value("${activeMQ.default.broker}")
	private String DEFAULT_BROKER;

	@Value("${file.upload.path}")
	private String UPLOADED_FOLDER;

	@Autowired
	XMLConverter converter;

	public boolean processOrder(MultipartFile file, QueueDetails queuedetails) throws IOException {

		if (uploadOrderFile(file)) {
			List<Order> orders = converter.readOrders();
			LOG.info("Orders received to publish: {}", orders);
			if (publishOrder(orders, queuedetails)) {
				return true;
			} else {
				return false;
			}
		}
		return false;

	}

	/**
	 * The method store a Order file uploaded by user
	 * 
	 * @param file
	 * @return
	 */
	public boolean uploadOrderFile(MultipartFile file) {
		try {
			byte[] bytes;
			bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);
			return true;
		} catch (IOException e) {
			LOG.error("Error uploading a file {}", file.getOriginalFilename());
			return false;
		}
	}

	/**
	 * The method is used to publish all the Orders to JMS queue
	 * 
	 * @param orders
	 * @param queuedetails
	 * @return
	 */
	public boolean publishOrder(List<Order> orders, QueueDetails queuedetails) {
		try {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
			if (StringUtils.isEmpty(queuedetails.getBroker())) {
				connectionFactory.setBrokerURL(DEFAULT_BROKER);
			} else {
				connectionFactory.setBrokerURL(queuedetails.getBroker());
			}
			connectionFactory.setTrustedPackages(Arrays.asList("order.jms.model"));

			JmsTemplate template = new JmsTemplate();
			template.setConnectionFactory(connectionFactory);
			template.setDefaultDestinationName(queuedetails.getDestination());
			if (JMSConstant.TOPIC.equalsIgnoreCase(queuedetails.getQueuetype())) {
				template.setPubSubDomain(true);
			}

			for (Order order : orders) {
				template.send(new MessageCreator() {
					@Override
					public Message createMessage(Session session) throws JMSException {
						ObjectMessage objectMessage = session.createObjectMessage(order);
						return objectMessage;
					}
				});
			}
			return true;
		} catch (Exception e) {
			LOG.error("Unable to publish Orders at this moment. Please try again later");
			return false;
		}

	}

}
