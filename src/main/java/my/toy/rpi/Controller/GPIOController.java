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

import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

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
	
	/**
	 * LED 점멸 기능.
	 * @param pinNum 핀번호 (29부터 하나씩 줄어듬)
	 */
	@RequestMapping(value="/ledToggle/{pinNum}")
	public void ledToggle(@PathVariable(value="pinNum") String pinNum){
		
		final com.pi4j.io.gpio.GpioController gpio = GpioFactory.getInstance();
		final int toggleCnt = 20;	// 점멸 횟수.
		GpioPinDigitalOutput led = null;
		
		try {
			switch (pinNum) {
			case "29":
				led = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_29, "pin29", PinState.LOW);
				break;
			case "28":
				led = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_28, "pin28", PinState.LOW);
				break;
			case "27":
				led = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27, "pin27", PinState.LOW);
				break;
			case "26":
				led = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_26, "pin26", PinState.LOW);
				break;
			case "25":
				led = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_25, "pin25", PinState.LOW);
				break;
			default:
				break;
			}
			
			if(null != led){
				for (int i = 0; i <toggleCnt; i++) {
					led.toggle();
					Thread.sleep(500);
				}
			}
		} catch (Exception e) {
			e.getMessage();
		} finally {
			led.low();
			led = null;
			gpio.shutdown();
		}
	}
}
