package my.toy.rpi.Controller;

import my.toy.rpi.Service.GPIOService;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GPIOController {
	
	Logger log = Logger.getLogger(GPIOController.class);
	
	@Autowired
	GPIOService gpioservice;
//	@Autowired
//	NetworkService networkservice;
	
	@RequestMapping("/touchme")
	public String controlPage(Model model){
		log.info("touchme");
		
		String[] ips= gpioservice.getIPAddress();
		
		for (String ip : ips){
			log.info("ip:"+ ip);
		}
	
		model.addAttribute("serverip", ips);
		model.addAttribute("circles", "2");
		model.addAttribute("distance", "60");
		return "touchme";
	}
	@RequestMapping("/init/{pinCnt}")
	public void init( @PathVariable int pinCnt){
		log.info("init");
		try {
			gpioservice.init(pinCnt);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping("/pinCnt/{num}/{direction}")
	public void pinCnt(@PathVariable int num , @PathVariable String direction){
		
		log.info("pinCnt:" + num);
		log.info("direction:"+ direction );
		
		try {
			if(direction.equals("UP"))
				gpioservice.pinUp(num);
			else if(direction.equals("DOWN"))
				gpioservice.pinDown(num);
			else
				log.error("wrong diretion");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/Async")
	@ResponseBody
	public String Async(){
		log.info("Async");
		gpioservice.printAsync();
		return "Async";
	}
}
