int ldr=A5;
static int arr[8];
int count=0;
int thres=5;
void setup(){
Serial.begin(9600);
pinMode(10,INPUT);
}
void loop(){
  count=0;
  int val=analogRead(ldr);
   if(val>=thres){
      //Serial.print("1");
      delay(50);
      
      
    int i=0;
    for(i=0;i<8;i++){
      val=analogRead(ldr);
      //Serial.println(val);
      delay(50);
      if(val>=thres){
      //Serial.print("1");
      arr[count++]=1;
      }
      else{
        //Serial.print("0");
        arr[count++]=0;
      }
     
    }
    int a;
    a=convert();
    if(a>=95 ||(a>=48 && a<=63))
    
      Serial.print(char(a+2));
      
      /*else if(a==64)
      Serial.print(char(a)); */  
       else
     Serial.print(char(a+1));
    delay(100);
   }   
   
}
int convert()
{
  int sum=0,i=0;
  for(i=0;i<8;i++){
//    Serial.println(arr[i]);
    sum=sum+(arr[i]*pow(2,7-i));
  }
   
  return sum;
}
