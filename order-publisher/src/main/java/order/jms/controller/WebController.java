package order.jms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {
	
	private static final Logger LOG = LoggerFactory.getLogger(WebController.class);
	
	@RequestMapping({"/","/home"})
	public ModelAndView testMessage(ModelAndView view) {
		view.setViewName("home");
		return view;
	}
	
}
