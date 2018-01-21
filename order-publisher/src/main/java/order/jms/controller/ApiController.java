package order.jms.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import order.jms.model.Order;
import order.jms.model.QueueDetails;
import order.jms.service.OrderService;
import order.jms.util.XMLConverter;

@Controller
public class ApiController {

	private static final Logger LOG = LoggerFactory.getLogger(ApiController.class);

	

	@Autowired
	OrderService orderservice;

	@PostMapping("/processOrder")
	public String processOrder(@RequestParam("file") MultipartFile file, QueueDetails queuedetails,
			RedirectAttributes redirectAttributes) throws IOException {

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:orderStatus";
		} else {
			if (orderservice.processOrder(file, queuedetails)) {
				redirectAttributes.addFlashAttribute("message", "Orders published successfully");
			} else {
				redirectAttributes.addFlashAttribute("message", "Unable to process the request at moment.");
			}

			return "redirect:/orderStatus";
		}
	}

	@GetMapping("/orderStatus")
	public String uploadStatus() {
		return "orderStatus";
	}

}
