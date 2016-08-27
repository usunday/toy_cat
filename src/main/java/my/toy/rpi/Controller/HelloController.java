package my.toy.rpi.Controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	private static final Logger log = Logger.getLogger(HelloController.class);
	
	@RequestMapping("/Hello")
	@ResponseBody
	public String hello(){
		log.info("Hello");
		return "hello";
	}
}
