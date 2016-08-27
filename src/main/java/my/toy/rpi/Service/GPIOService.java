package my.toy.rpi.Service;

import java.io.IOException;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.pi4j.system.NetworkInfo;
import com.pi4j.wiringpi.SoftPwm;

@Service
public class GPIOService {
	
	public void init(int pinCnt) throws InterruptedException {
		// initialize wiringPi library
        com.pi4j.wiringpi.Gpio.wiringPiSetup();
        // create soft-pwm pins (min=0 ; max=100)
//        SoftPwm.softPwmCreate(1, 0, 100);
//        SoftPwm.softPwmCreate(2, 0, 100);
        for(int i=0; i<pinCnt; i++){
        	SoftPwm.softPwmCreate(i, 0, 100);
//        	 fade Servo to fully ON
        	for (int j = 0; j <= 100; j++) {
              SoftPwm.softPwmWrite(i, j);
              Thread.sleep(10);
          }
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

}
