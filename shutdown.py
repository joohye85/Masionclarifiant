import RPi.GPIO as GPIO
from subprocess import call
from datetime import datetime
import time

btnPin = 5

shutdownSeconds = 2
rebootSeconds = 0.5

GPIO.setmode(GPIO.BOARD)
GPIO.setup(btnPin, GPIO.IN)

def getPressTime():
    elapsed = 0
    if pressTime is not None:
        elapsed = (datetime.now() - pressTime).total_seconds()
    return elapsed

while True:
    input = GPIO.input(btnPin)
    if input == 0:
        # btn down
        if prev == -1 or prev == 1:
            pressTime = datetime.now()
        elif prev == 0:
            if getPressTime() > shutdownSeconds:
                call(['shutdown', '-h', 'now'], shell=False)
                break
    
    elif input == 1:
        # btn up
        if prev == 0:
            if getPressTime() > rebootSeconds:
                call(['shutdown', '-r', 'now'], shell=False)
                break
    
    prev = input
    time.sleep(0.5)