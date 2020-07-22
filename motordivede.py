import RPi.GPIO as GPIO
from time import sleep

# 모터 상태
STOP  = 0
FORWARD  = 1
#BACKWORD = 2

# 모터 채널
M1_CH1 = 0
M1_CH2 = 1

M2_CH1 = 2
M2_CH2 = 3

M3_CH1 = 4
M3_CH2 = 5

M4_CH1 = 6
M4_CH2 = 7

# PIN 입출력 설정
OUTPUT = 1
INPUT = 0

# PIN 설정
HIGH = 1
LOW = 0

# 실제 핀 정의
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

# 핀 설정 함수
def setPinConfig(EN, INA, INB):        
    GPIO.setup(EN, GPIO.OUT)
    GPIO.setup(INA, GPIO.OUT)
    GPIO.setup(INB, GPIO.OUT)
    # 100khz 로 PWM 동작 시킴 
    pwm = GPIO.PWM(EN, 1) 
    # 우선 PWM 멈춤.   
    pwm.start(0) 
    return pwm

# 모터 제어 함수
def setMotorContorl(pwm, INA, INB, speed, stat):

    #모터 속도 제어 PWM
    pwm.ChangeDutyCycle(speed)  
    
    if stat == FORWARD:
        GPIO.output(INA, HIGH)
        GPIO.output(INB, LOW)
        
    #뒤로
    #elif stat == BACKWORD:
     #   GPIO.output(INA, LOW)
      #  GPIO.output(INB, HIGH)
        
    #정지
    elif stat == STOP:
        GPIO.output(INA, LOW)
        GPIO.output(INB, LOW)

        
# 모터 제어함수 간단하게 사용하기 위해 한번더 래핑(감쌈)
def setMotor(ch, speed, stat):
    if ch == M1_CH1:
        #pwmA는 핀 설정 후 pwm 핸들을 리턴 받은 값이다.
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

# GPIO 모드 설정 
GPIO.setmode(GPIO.BCM)
      
#모터 핀 설정
#핀 설정후 PWM 핸들 얻어옴 
M1_pwmA = setPinConfig(M1_ENA, M1_IN1, M1_IN2)
M1_pwmB = setPinConfig(M1_ENB, M1_IN3, M1_IN4)

M2_pwmA = setPinConfig(M2_ENA, M2_IN1, M2_IN2)
M2_pwmB = setPinConfig(M2_ENB, M2_IN3, M2_IN4)

M3_pwmA = setPinConfig(M3_ENA, M3_IN1, M3_IN2)
M3_pwmB = setPinConfig(M3_ENB, M3_IN3, M3_IN4)

M4_pwmA = setPinConfig(M4_ENA, M4_IN1, M4_IN2)
M4_pwmB = setPinConfig(M4_ENB, M4_IN3, M4_IN4)

    
#제어 시작

setMotor(M1_CH1, 0, STOP)
setMotor(M1_CH2, 0, STOP)
setMotor(M2_CH1, 0, STOP)
setMotor(M2_CH2, 0, STOP)
setMotor(M3_CH1, 0, STOP)
setMotor(M3_CH2, 0, STOP)
setMotor(M4_CH1, 0, STOP)
setMotor(M4_CH2, 0, STOP)

#2초 대기
sleep(2)

setMotor(M1_CH1, 100, FORWARD)
sleep(3)
setMotor(M1_CH1, 0, STOP)

setMotor(M1_CH2, 100, FORWARD)
sleep(3)
setMotor(M1_CH2, 0, STOP)

setMotor(M2_CH1, 100, FORWARD)
sleep(3)
setMotor(M2_CH1, 0, STOP)

setMotor(M2_CH2, 100, FORWARD)
sleep(3)
setMotor(M2_CH2, 0, STOP)

setMotor(M3_CH1, 100, FORWARD)
sleep(3)
setMotor(M3_CH1, 0, STOP)

setMotor(M3_CH2, 100, FORWARD)
sleep(3)
setMotor(M3_CH2, 0, STOP)

setMotor(M4_CH1, 100, FORWARD)
sleep(3)
setMotor(M4_CH1, 0, STOP)

setMotor(M4_CH2, 100, FORWARD)
sleep(3)
setMotor(M4_CH2, 0, STOP)

# 뒤로 100프로 속도로
#setMotor(CH1, 100, BACKWORD)
#setMotor(CH2, 100, BACKWORD)
#sleep(5)

#정지 
setMotor(M1_CH1, 80, STOP)
setMotor(M1_CH2, 80, STOP)
setMotor(M2_CH1, 80, STOP)
setMotor(M2_CH2, 80, STOP)
setMotor(M3_CH1, 80, STOP)
setMotor(M3_CH2, 80, STOP)
setMotor(M4_CH1, 80, STOP)
setMotor(M4_CH2, 80, STOP)

# 종료
GPIO.cleanup()