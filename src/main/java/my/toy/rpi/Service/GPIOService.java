package my.toy.rpi.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.pi4j.system.NetworkInfo;
import com.pi4j.wiringpi.SoftPwm;

@Service
public class GPIOService {
	Logger log = Logger.getLogger(GPIOService.class);
	
	public void init(int pinCnt) throws InterruptedException {
		// initialize wiringPi library
        com.pi4j.wiringpi.Gpio.wiringPiSetup();
        // create soft-pwm pins (min=0 ; max=100)
//        SoftPwm.softPwmCreate(1, 0, 100);
//        SoftPwm.softPwmCreate(2, 0, 100);
        for(int i=0; i<pinCnt; i++){
        	SoftPwm.softPwmCreate(i, 0, 100);
        }
	}
	@Async
	public void pinUp(int num) throws InterruptedException {
		// TODO Auto-generated method stub
           
        // fade LED to fully ON
        for (int i = 0; i <= 100; i++) {
            SoftPwm.softPwmWrite(num, i);
            Thread.sleep(10);
        }
	}
	@Async
	public void pinDown(int num) throws InterruptedException {
		// TODO Auto-generated method stub
		// fade LED to fully OFF
        for (int i = 100; i >= 0; i--) {
            SoftPwm.softPwmWrite(num, i);
            Thread.sleep(10);
        }
	}
	@Async
	public String printAsync(){
		return "Async";
		
	} 
	
	/**
	 * 웨이브 파일 얻기
	 * @return
	 */
	public File getWaveFile(String fileName){
		ClassPathResource classPathResource = null;
		File file = null;
		InputStream inputStream = null;
		try {
			classPathResource = new ClassPathResource("META-INF/file/"+fileName+".wav");
			inputStream = classPathResource.getInputStream();
			file = new File("META-INF/file/"+fileName+"_play.wav");
			FileUtils.copyInputStreamToFile(inputStream, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}
	
	@Async
	public void ledToggle(int pinNum) throws InterruptedException {
		// TODO Auto-generated method stub
		for(int i=0; i<5; i++){
            for (int j = 0; j < 2; j++) {
            		log.info(j);
                    SoftPwm.softPwmWrite(pinNum, 100);
                    Thread.sleep(100);
            }
            for (int j = 0; j < 2; j++){
            		log.info(j);
                    SoftPwm.softPwmWrite(pinNum, 90);
                    Thread.sleep(100);
            }
	    }
	    for (int j = 0; j < 3; j++) {
	                    SoftPwm.softPwmWrite(pinNum, 100);
	                    Thread.sleep(100);
	    }
		log.info("toggle service finish");
	}

	public String[] getIPAddress()
	{
		String[] ipadresses = null; 
		try {
			ipadresses = NetworkInfo.getIPAddresses();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ipadresses;
	}

	/**
	 * 웨이브 파일 재생
	 * @param file 재생할 파일
	 */
	@Async
	public void wavePlay(File file) {
		try{
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(file));
			clip.start();
		
			Thread.sleep(clip.getMicrosecondLength()/1000);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
}
