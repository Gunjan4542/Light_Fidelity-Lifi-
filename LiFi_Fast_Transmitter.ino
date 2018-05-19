/*This program accepts any character entered into the Serial Monitor of the IDE, and converts the character to ASCII to binary, 
 which is then used to control the glow of an LED */

int c,x,count = 0;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);   //sets up serial transmission between the Serial Port and Arduino to 9600 baud rate
  pinMode(10, OUTPUT);  //pin 10 has the LED output
}

void loop() {
  // put your main code here, to run repeatedly:
    if(Serial.available()) 
    {
      Serial.print("\n");
      int i;
      static int arr[8];
      for(i=0; i<8; i++)
        arr[i] = 0;
      c = Serial.read();  //Reads the actual text entered into Serial Port. Since c is integer, the text is converted to ASCII
       
    //ASCII to binary conversion
      while(c > 0)
      {
        x = c % 2;  //converting ASCII to binary (note that this means that the numbers are represented according to ASCII binary. 9 may not be 1001, 8 may not be 1000....
        c = c / 2;
        arr[count++] = x; 
      }
      count = 8;

      //Reverse the bit array
      digitalWrite(10,HIGH);
      delay(30);
      digitalWrite(10,LOW);
      while(count-1>= 0) //since the result of modular division has to be reversed
      {
        if(arr[count-1] == 0)  //if the bit is 0
          digitalWrite(10, LOW);
        else if(arr[count-1] == 1)  //else if the bit is 1
          digitalWrite(10, HIGH);
        delay(30);  //delay of 500 ms
        count--;
      }
      digitalWrite(10, LOW);
      delay(30);
    }
}
