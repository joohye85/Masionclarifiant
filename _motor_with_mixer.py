#motor with mixer 
import RPi.GPIO as GPIO 
from time import sleep 
from socket import * 

#motor state 
STOP = 0 
FORWARD = 1 
BACKWORD = 2 

#set motor channel 
M1_CH1 = 10 
M1_CH2 = 11 
M2_CH1 = 12 
M2_CH2 = 13 
M3_CH1 = 15 
M3_CH2 = 14 

#set PIN inout 
OUTPUT = 1 
INPUT = 0 
HIGH = 1 
LOW = 0 

#motor driver1 
M1_ENA = 26 
M1_ENB = 11 
M1_IN1 = 19 
M1_IN2 = 13 
M1_IN3 = 6 
M1_IN4 = 5

# motor driver2 
M2_ENA = 9 
M2_ENB = 10 
M2_IN1 = 22 
M2_IN2 = 27 
M2_IN3 = 17 
M2_IN4 = 4 

# motor driver3 
M3_ENA = 18 
M3_ENB = 3 
M3_IN1 = 23 #mixer 
M3_IN2 = 24 #drop down motor 
M3_IN3 = 20 
M3_IN4 = 21 

#function for PIN configuration 
def setPinConfig (EN, INA, INB) : 
    GPIO.setup (EN, GPIO.OUT) 
    GPIO.setup (INA, GPIO.OUT) 
    GPIO.setup (INB, GPIO.OUT) 
    pwm = GPIO.PWM (EN, 1) 
    pwm.start (0) 
    return pwm 

#funtion for motor control 
def setMotorContorl (pwm, INA, INB, speed, stat) : 
    pwm.ChangeDutyCycle(speed)
    if stat == FORWARD : 
        GPIO.output ( INA , HIGH ) 
        GPIO.output ( INB , LOW ) 

    elif stat == BACKWORD : 
        GPIO.output ( INA , LOW ) 
        GPIO.output ( INB , HIGH ) 

    elif stat == STOP : 
        GPIO.output ( INA , LOW ) 
        GPIO.output ( INB , LOW )

#rapping for use motor control function 
def setMotor (ch, speed, stat) : 
    if ch == M1_CH1 : 
        setMotorContorl(M1_pwmA, M1_IN1, M1_IN2, speed, stat) 

    elif ch == M1_CH2 :
        setMotorContorl(M1_pwmB, M1_IN3, M3_IN4, speed, stat) 

    elif ch == M2_CH1 : 
        setMotorContorl(M2_pwmA, M2_IN1, M2_IN2, speed, stat)

    elif ch == M2_CH2 : 
        setMotorContorl(M2_pwmB, M2_IN3, M2_IN4, speed, stat)

    elif ch == M3_CH1 :
        setMotorContorl(M3_pwmA, M3_IN1, M3_IN2, speed, stat)

    elif ch == M3_CH2 :
        setMotorContorl(M3_pwmB, M3_IN3, M3_IN4, speed, stat)

#setting GPIO mode
GPIO.setmode(GPIO.BCM)

#final waterpump
M1_pwmA = setPinConfig(M1_ENA, M1_IN1, M1_IN2)

#null
M1_pwmB = setPinConfig(M1_ENB, M1_IN3, M1_IN4)

#null
M2_pwmA = setPinConfig(M2_ENA, M2_IN1, M2_IN2)

#null
M2_pwmB = setPinConfig(M2_ENB, M2_IN3, M2_IN4)

#dropmotor
M3_pwmA = setPinConfig(M3_ENA, M3_IN1, M3_IN2)

#mixer
M3_pwmB = setPinConfig(M3_ENB, M3_IN3, M3_IN4)

setMotor(M1_CH1, 0, STOP)
setMotor(M1_CH2, 0, STOP)
setMotor(M2_CH1, 0, STOP)
setMotor(M2_CH2, 0, STOP)
setMotor(M3_CH1, 0, STOP)
setMotor(M3_CH2, 0, STOP)

try:
    while True:
        try:
            print("in try")
            HOST2 = '220.69.172.142'
            PORT2 = 6000
            BUFSIZE = 4096
            ADDR = (HOST2, PORT2)

            s = socket(AF_INET, SOCK_STREAM)
            s.connect(ADDR)
            print('client socket created')

            data =''
            data = s.recv(BUFSIZE)
            dt = data.decode('utf-8')
            print(dt)

            if not dt:
                break

            if "start" in dt or "tart" in dt or "art" in dt:
                print("start")
                setMotor(M1_CH1, 0, STOP)
                setMotor(M1_CH2, 0, STOP)
                #teatree
                setMotor(M2_CH1, 0, STOP)
                #rosemary
                setMotor(M2_CH2, 0, STOP)
                setMotor(M3_CH1, 0, STOP)
                setMotor(M3_CH2, 0, STOP)
                setMotor(M3_CH1, 100, FORWARD) 
                sleep(3)
                setMotor(M3_CH1, 0, STOP)

            if "teatree" in dt or "tree" in dt:
                print("teatree selected")
                setMotor(M1_CH1, 100, FORWARD)
                sleep(1.8)
                setMotor(M1_CH1, 0, STOP)

            if "rosemary" in dt or "mary" in dt:
                print("rosemary selected")
                setMotor(M1_CH2, 100, FORWARD)
                sleep(1.8)
                setMotor(M1_CH2, 0, STOP)

            #mixing first
            print("Mixing Start")
            setMotor(M3_CH1, 100, FORWARD)
            sleep(3)
            setMotor(M3_CH1, 0, STOP)
            
            #push up mixer by motor drive
            sleep(2)
            setMotor(M3_CH2, 100, FORWARD)
            sleep(3)
            setMotor(M3_CH2, 0, STOP)
            
            #pull result product by waterpump
            setMotor(M2_CH2, 100, FORWARD)
            sleep(3)
            setMotor(M2_CH2, 0, STOP)
           
            #pull down mixer
            sleep(2)
            setMotor(M3_CH2, 10, BACKWORD)

            #stop all motor
            setMotor(M1_CH1, 80, STOP)
            setMotor(M1_CH2, 80, STOP)
            setMotor(M2_CH1, 80, STOP)
            setMotor(M2_CH2, 80, STOP)
            setMotor(M3_CH1, 80, STOP)
            setMotor(M3_CH2, 80, STOP)

            GPIO.cleanup()
            break

        except:
            continue

 

except KeyboardInterrupt:
    GPIO.cleanup()
    s.close()