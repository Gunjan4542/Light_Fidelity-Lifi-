import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;


public class Lifi_Transmitter {
	
	public void sendFile(File f,JProgressBar progress,JTextArea area) throws Exception{
		SerialPort serialPort = new SerialPort("/dev/ttyACM1");
	    try {
	    	String[] portNames = SerialPortList.getPortNames();
	        for(int i = 0; i < portNames.length; i++){
	            System.out.println(portNames[i]);
	            serialPort = new SerialPort(portNames[i]);
	        }
	        serialPort.openPort();//Open serial port
	        serialPort.setParams(SerialPort.BAUDRATE_9600, 
	                             SerialPort.DATABITS_8,
	                             SerialPort.STOPBITS_1,
	                             SerialPort.PARITY_NONE);//Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);
	        /*serialPort.writeBytes("This is a test string".getBytes());//Write data to port
	        serialPort.closePort();//Close serial port*/
	        
	        FileReader fr=new FileReader(f);
	        BufferedReader br=new BufferedReader(fr);
	        String line;
	        int c=0;
	        boolean flag=true;
	        long count=0;
	        long total  = f.length();
	        long sendSize = 0;
	        double percent = (count/total)*100;
        	progress.setValue((int)percent);
	        while((c=br.read())!=-1){
	        	serialPort.writeInt((int)c);
	        	area.append((char)c+"");
	        	String r=serialPort.readString();
	        	while(r==null){
	        		r=serialPort.readString();
//	        		System.out.println(r);
	        		
	        	}
	        	count++;
	        	percent = (count/(double)total)*100;
	        	progress.setValue((int)percent);
	        	System.out.print((char)c);
	        }
	        serialPort.closePort();
	        System.out.println(count);
	    }
	    catch (SerialPortException ex) {
	        System.out.println(ex);
	    }
	}
	
	public void sendMessage(String s,JProgressBar progress,JTextArea area) throws Exception{
		SerialPort serialPort = new SerialPort("/dev/ttyACM0");
	    try {
	    	String[] portNames = SerialPortList.getPortNames();
	        for(int i = 0; i < portNames.length; i++){
	            System.out.println(portNames[i]);
	            serialPort = new SerialPort(portNames[i]);
	        }
	        serialPort.openPort();//Open serial port
	        serialPort.setParams(SerialPort.BAUDRATE_9600, 
	                             SerialPort.DATABITS_8,
	                             SerialPort.STOPBITS_1,
	                             SerialPort.PARITY_NONE);//Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);
	        /*serialPort.writeBytes("This is a test string".getBytes());//Write data to port
	        serialPort.closePort();//Close serial port*/
	        
	        boolean flag=true;
	        long count=0;
	        long total  = s.length();
	        long sendSize = 0;
	        double percent = (count/total)*100;
        	progress.setValue((int)percent);
	        for(int i=0;i<total;i++){
	        	char c = s.charAt(i);
	        	serialPort.writeInt((int)c);
	        	area.append(c+"");
	        	String r=serialPort.readString();
	        	while(r==null){
	        		r=serialPort.readString();
//	        		System.out.println(r);
	        		
	        	}
	        	count++;
	        	percent = (count/(double)total)*100;
	        	progress.setValue((int)percent);
	        	System.out.print((char)c);
	        }
	        System.out.println(count);
	        serialPort.closePort();
	    }
	    catch (SerialPortException ex) {
	        System.out.println(ex);
	    }
	}
	


}
