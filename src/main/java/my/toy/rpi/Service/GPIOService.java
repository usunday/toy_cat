package my.toy.rpi.Service;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.pi4j.system.NetworkInfo;
import com.pi4j.wiringpi.SoftPwm;

@Service
public class GPIOService {
	Logger log = Logger.getLogger(GPIOService.class);
	// 재생할 파일이름과 확장자
	private final String FILE_NAME = "Beat.wav";
	
	public void init(int pinCnt) throws InterruptedException {
		// initialize wiringPi library
        com.pi4j.wiringpi.Gpio.wiringPiSetup();
        // create soft-pwm pins (min=0 ; max=100)
//        SoftPwm.softPwmCreate(1, 0, 100);
//        SoftPwm.softPwmCreate(2, 0, 100);
        for(int i=0; i<pinCnt; i++){
        	SoftPwm.softPwmCreate(i, 0, 100);

//        	for (int j = 0; j <= 100; j++) {
//              SoftPwm.softPwmWrite(i, j);
//              Thread.sleep(10);
//        	}
        }
//            // fade Servo to fully ON
//            for (int i = 0; i <= 100; i++) {
//                SoftPwm.softPwmWrite(1, i);
//                SoftPwm.softPwmWrite(2, i);
//                Thread.sleep(10);
//            }
//
//            // fade Servo to fully OFF
//            for (int i = 100; i >= 0; i--) {
//                SoftPwm.softPwmWrite(1, i);
//                SoftPwm.softPwmWrite(2, i);
//                Thread.sleep(10);
//            }
        
	}
	public void pinUp(int num) throws InterruptedException {
		// TODO Auto-generated method stub
           
        // fade LED to fully ON
        for (int i = 0; i <= 100; i++) {
            SoftPwm.softPwmWrite(num, i);
            Thread.sleep(10);
        }
	}

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
		ClassLoader classLoader = getClass().getClassLoader();
		File file = null;
		try {
			file = new File(classLoader.getResource("META-INF/file/"+fileName+".wav").getFile());
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
                    SoftPwm.softPwmWrite(pinNum, 100);
                    Thread.sleep(100);
            }
            for (int j = 0; j < 2; j++){
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
