#motor without mixer
#import motor

from socket import *
from time import sleep
import RPi.GPIO as GPIO
from time import sleep

#motor state
STOP  = 0
FORWARD  = 1
BACKWORD = 2

#motor channel 
M1_CH1 = 0
M1_CH2 = 1

M2_CH1 = 2
M2_CH2 = 3

M3_CH1 = 4
M3_CH2 = 5

M4_CH1 = 6
M4_CH2 = 7

#set PIN inout
OUTPUT = 1
INPUT = 0

#PIN setting
HIGH = 1
LOW = 0

#real PIN
#motor driver1

M1_ENA = 26  
M1_ENB = 12

M1_IN1 = 19  
M1_IN2 = 13  
M1_IN3 = 8   
M1_IN4 = 7

#motor driver2
M2_ENA = 18
M2_ENB = 22

M2_IN1 = 17
M2_IN2 = 27
M2_IN3 = 10  
M2_IN4 = 9

#motor driver3
M3_ENA = 25
M3_ENB = 16

M3_IN1 = 23
M3_IN2 = 24
M3_IN3 = 5
M3_IN4 = 6

#motor driver4
M4_ENA = 4
M4_ENB = 11

M4_IN1 = 14
M4_IN2 = 15
M4_IN3 = 20
M4_IN4 = 21

#function for PIN configuraition
def setPinConfig(EN, INA, INB):        
    GPIO.setup(EN, GPIO.OUT)
    GPIO.setup(INA, GPIO.OUT)
    GPIO.setup(INB, GPIO.OUT)
    
    #start pwm with 100khz
    pwm = GPIO.PWM(EN, 1)
    
    #stop pwm.   
    pwm.start(0) 

    return pwm

#function for motor control
def setMotorContorl(pwm, INA, INB, speed, stat):
    #PMW for motor speed control
    pwm.ChangeDutyCycle(speed)  

    if stat == FORWARD:
        GPIO.output(INA, GPIO.HIGH)
        GPIO.output(INB, GPIO.LOW)
    #stop
    elif stat == STOP:
        GPIO.output(INA, LOW)
        GPIO.output(INB, LOW)

#rapping for use motor control function
def setMotor(ch, speed, stat):
    if ch == M1_CH1:
        setMotorContorl(M1_pwmA, M1_IN1, M1_IN2, speed, stat)
    elif ch == M1_CH2:
        setMotorContorl(M1_pwmB, M1_IN3, M1_IN4, speed, stat)
    elif ch == M2_CH1:
        setMotorContorl(M2_pwmA, M2_IN1, M2_IN2, speed, stat)
    elif ch == M2_CH2:
        setMotorContorl(M2_pwmB, M2_IN3, M2_IN4, speed, stat)
    elif ch == M3_CH1:
        setMotorContorl(M3_pwmA, M3_IN1, M3_IN2, speed, stat)
    elif ch == M3_CH2:
        setMotorContorl(M3_pwmB, M3_IN3, M3_IN4, speed, stat)
    elif ch == M4_CH1:
        setMotorContorl(M4_pwmA, M4_IN1, M4_IN2, speed, stat)
    elif ch == M4_CH2:
        setMotorContorl(M4_pwmB, M4_IN3, M4_IN4, speed, stat)

#setting GPIO mode
GPIO.setmode(GPIO.BCM)

#junjaesoo
M1_pwmA = setPinConfig(M1_ENA, M1_IN1, M1_IN2)

#glycerin
M1_pwmB = setPinConfig(M1_ENB, M1_IN3, M1_IN4)

#aloe
M2_pwmA = setPinConfig(M2_ENA, M2_IN1, M2_IN2)

#greentea
M2_pwmB = setPinConfig(M2_ENB, M2_IN3, M2_IN4)

#beyongpool
M3_pwmA = setPinConfig(M3_ENA, M3_IN1, M3_IN2)

#honey
M3_pwmB = setPinConfig(M3_ENB, M3_IN3, M3_IN4)

#snail
M4_pwmA = setPinConfig(M4_ENA, M4_IN1, M4_IN2)

#olive
M4_pwmB = setPinConfig(M4_ENB, M4_IN3, M4_IN4)

 
HOST = '' #all client can communicate with server
PORT = 5001
BUFSIZE = 4096 
ADDR = (HOST, PORT) 

tcpSerSock = socket(AF_INET, SOCK_STREAM)
tcpSerSock.bind(ADDR)
tcpSerSock.listen(5)

#wait for connection
while True:
    print ('Waiting for connection')
    tcpCliSock, addr = tcpSerSock.accept()
    print ('...connected from :', addr)

    try:
        while True:
                data = ''
                data = tcpCliSock.recv(BUFSIZE)
                dt = data.decode('utf-8')
                print(dt)
                
                if not dt:
                    break
                
                if "start" in dt or "tart" in dt or "art" in dt :
                    print ('start is in dt')
                   #start control
                    setMotor(M1_CH1, 0, STOP) 
                    setMotor(M1_CH2, 0, STOP) 
                    setMotor(M2_CH1, 0, STOP) 
                    setMotor(M2_CH2, 0, STOP) 
                    setMotor(M3_CH1, 0, STOP) 
                    setMotor(M3_CH2, 0, STOP) 
                    setMotor(M4_CH1, 0, STOP) 
                    setMotor(M4_CH2, 0, STOP) 
                    #wait 2sec
                    sleep(2)

                if "toner" in dt:
                    print("toner is in dt")
                    setMotor(M1_CH1, 100, FORWARD) #jungjaesoo
                    sleep(3)
                    setMotor(M1_CH1, 0, STOP)
                    setMotor(M1_CH2, 100, FORWARD) #glycerin
                    sleep(1)
                    setMotor(M1_CH2, 0, STOP)

                if "lotion" in dt:
                    print("lotion is in dt")
                    setMotor(M1_CH1, 100, FORWARD) #jungjaesoo
                    sleep(2)
                    setMotor(M1_CH1, 0, STOP)
                    setMotor(M1_CH2, 100, FORWARD) #glycerin
                    sleep(2)
                    setMotor(M1_CH2, 0, STOP)

                if "aloe" in dt:
                    print("aloe is in dt")
                    setMotor(M2_CH1, 100, FORWARD)
                    sleep(3)
                    setMotor(M2_CH1, 0, STOP)

                if "greentea" in dt or "tea" in dt:
                    print("greentea is in dt")
                    setMotor(M2_CH2, 100, FORWARD)
                    sleep(3)
                    setMotor(M2_CH2, 0, STOP)

                if "byeongpul" in dt:
                    print("byeoungpul is in dt")
                    setMotor(M3_CH1, 100, FORWARD)
                    sleep(3)
                    setMotor(M3_CH1, 0, STOP)

                if "honey" in dt:
                    print("hoeny is in dt")
                    setMotor(M3_CH2, 100, FORWARD)
                    sleep(3)
                    setMotor(M3_CH2, 0, STOP)

                if "snail" in dt:
                    print("snail is in dt")
                    setMotor(M4_CH1, 100, FORWARD)
                    sleep(3)
                    setMotor(M4_CH1, 0, STOP)

                if "olive" in dt:
                    print("olive is in dt")    
                    setMotor(M4_CH2, 100, FORWARD)
                    sleep(3)
                    setMotor(M4_CH2, 0, STOP)

    except KeyboardInterrupt:
        motor.close()
        GPIO.cleanup()
        break
        
    #stop 
    setMotor(M1_CH1, 80, STOP)
    setMotor(M1_CH2, 80, STOP)
    setMotor(M2_CH1, 80, STOP)
    setMotor(M2_CH2, 80, STOP)
    setMotor(M3_CH1, 80, STOP)
    setMotor(M3_CH2, 80, STOP)
    setMotor(M4_CH1, 80, STOP)
    setMotor(M4_CH2, 80, STOP)

    #finish
    GPIO.cleanup()
    break

tcpSerSock.close()

HOST2 = '220.69.172.45'
PORT2 = 6000

s = socket(AF_INET, SOCK_STREAM)
print('Socket created')

try :
    s.bind((HOST2, PORT2))
except socket.error:
    print('Bind failed')

s.listen(5)
print('Waiting for second connection')

conn, addr = s.accept()
print('Connected from : ', addr)

while True:
    startmsg = 'start'
    smsg = startmsg.encode('utf-8')
    conn.send(smsg)
    break
    
conn.close()