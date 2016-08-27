package my.toy.rpi.Controller;

import my.toy.rpi.Service.GPIOService;

import java.io.File;

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
	@RequestMapping("/pincntl/{num}/{direction}")
	public void pinCnt(@PathVariable int num , @PathVariable String direction){
		
		log.info("pinCnt:" + num);
		log.info("direction:"+ direction );
		
		try {
			if(direction.equals("UP")){
				gpioservice.pinUp(num);
				
				File file = gpioservice.getWaveFile(Integer.toString(num));
				log.info("file: " + file);
				if(file != null){
					log.info("play start");
					// 웨이브 파일 재생
					gpioservice.wavePlay(file);
					log.info("play end");
				}
			
			}else if(direction.equals("DOWN")){
				gpioservice.pinDown(num);
			}else{
				log.error("wrong diretion");
			}
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
	@RequestMapping(value="/ledtoggle/{pinNum}")
	public String ledToggle(@PathVariable(value="pinNum") int pinNum){
		log.info("ledToggle");
		try {
			gpioservice.ledToggle(pinNum);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("ledTogglefinish");
		return "done";
	}
	

	/**
	 * 웨이브 파일 재생 기능.
	 */
	@RequestMapping(value="/wavePlay/{fileName}")
	public void wavePlay(@PathVariable(value="fileName") String fileName){
		log.info("wavePlay");
		try {
			File file = gpioservice.getWaveFile(fileName);
			log.info("[wave file]  : " + file);
			if(file != null){
				// 웨이브 파일 재생
				gpioservice.wavePlay(file);
				log.info("재생종료");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
