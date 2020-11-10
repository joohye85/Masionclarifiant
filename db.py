import pymysql

import datetime

import os

from ftplib import FTP

import picamera

from socket import *

import spidev, time

import RPi.GPIO as GPIO

import re

import paramiko

 

conn = pymysql.connect(host = '',

                       user = '', password = '', db = 'maison', charset = 'utf8')

cursor = conn.cursor(pymysql.cursors.DictCursor)

print("db connect")

 

spi = spidev.SpiDev()

spi.open(0,0)

spi.max_speed_hz = 1350000

 

def analog_read(channel):

    r = spi.xfer2([1, (8+channel) << 4, 0])

    adc_out = ((r[1]&3) << 8) + r[2]

    return adc_out

 

class Camera:

    def __init__(self):

        self.camera = picamera.PiCamera()

    

    def start(self):

        self.camera.start_preview()

    

    def capture(self):

        print("im ready for capture")

        self.camera.capture('/home/pi/skin.jpg')

        print("okay?")

        self.camera.stop_preview()

        print("capture success")

        

        self.camera.close()

     

 

HOST = ''

PORT = 9999

BUFSIZE = 4096

ADDR = (HOST, PORT)

 

tcpSerSock = socket(AF_INET, SOCK_STREAM)

tcpSerSock.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)

tcpSerSock.bind(ADDR)

tcpSerSock.listen(5)

 

datalist = []

try:

    while True:

        print('Waiting for android connection')

        tcpExSock, addr = tcpSerSock.accept()

        print('...connected from : ', addr)

        count = 0

        

        

        try:

            cameraclass = Camera()

            print("below try: ", cameraclass)

            while True:

                data = ''

                data = tcpExSock.recv(BUFSIZE)

                dt = data.decode('utf-8')

                datas = re.sub('[^\w]', '', dt)

      

                if datas:

                    datalist.append(datas)

                    print(datas)

                    print(datalist)

                    

                    if datas == 'moisture':

                        if count < 1:

                            reading = analog_read(0)

                            voltage = reading * 3.3/1024

                            print("Reading = %d/t Voltage = %f" %(reading, voltage))

                            

                            time.sleep(1)

                            measurefin = "measurefin"

                            print(measurefin)

                            tcpExSock.send(measurefin.encode())

                            count = count + 1

                      

                    elif 'eyecam' in datalist:

                        print("im in camera")

                        if len(datalist) == 3:

                            #with picamera.PiCamera() as camera:

                            #camera.resolution = (480, 320)

                                #if len(datalist) == 3:

                            #camera.start_preview()

                            cameraclass.start()

                        else:                          

                            if datas == 'goeyepicture':

                                print("capture: ", cameraclass)

                                cameraclass.capture()

                                

                                host = ''

                                username = ''

                                password = ''

                                ssh = paramiko.SSHClient()

                                ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())

                                ssh.connect(host, username = username, password = password)

                                sftp = ssh.open_sftp()

                                

                                sftp.put('/home/pi/skin.jpg', '/home/project/auto_skin.php')

                                

                                sftp.close()

                                ssh.close()

                                

                                camerafin = "camerafin"

                                print(camerafin)

                                tcpExSock.send(camerafin.encode())

                        

                    elif datas == 'exit':

                        break

                                 

                else:

                    continue

                

                

                

                

        except :

            continue

 

except KeyboardInterrupt:

    GPIO.cleanup()

    tcpSerSock.close()

    tcpExSock.close()        

 

conn.close()