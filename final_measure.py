import pymysql

import datetime

import os

from ftplib import FTP

import picamera

from socket import *

import spidev, time

import RPi.GPIO as GPIO

import re

 

conn = pymysql.connect(host = 'maison.c1seefdiv0cl.ap-northeast-2.rds.amazonaws.com',

                       user = 'admin', password = 'sswu1234', db = 'maison', charset = 'utf8')

cursor = conn.cursor(pymysql.cursors.DictCursor)

print("db connect")

 

spi = spidev.SpiDev()

spi.open(0,0)

spi.max_speed_hz = 1350000

 

def analog_read(channel):

    r = spi.xfer2([1, (8+channel) << 4, 0])

    adc_out = ((r[1]&3) << 8) + r[2]

    return adc_out

 

HOST = ''

PORT = 9999

BUFSIZE = 4096

ADDR = (HOST, PORT)

 

tcpSerSock = socket(AF_INET, SOCK_STREAM)

tcpSerSock.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)

tcpSerSock.bind(ADDR)

tcpSerSock.listen(5)

 

datalist = []

 

while True:

    print('Waiting for android connection')

    tcpExSock, addr = tcpSerSock.accept()

    print('...connected from : ', addr)

    

    try:

        while True:

            data = ''

            data = tcpExSock.recv(BUFSIZE)

            dt = data.decode('utf-8')

            datas = re.sub('[^\w]', '', dt)

            if datas:

                datalist.append(datas)

                print(datalist)

                

                if datas == 'moisture':

                    reading = analog_read(0)

                    voltage = reading * 3.3/1024

                    print("Reading = %d/t Voltage = %f" %(reading, voltage))

                    time.sleep(3)

                

                elif datas == 'camera':

                    with picamera.PiCamera() as camera:

                        camera.start_preview()

                        camera.capture('/home/pi/skin.jpg')

                        print("capture success")

                        camera.stop_preview()

                        camera.close()

                        

                        host = '3.35.37.0'

                        username = 'ubuntu'

                        password = 'sswu1234'

                        ssh = paramiko.SSHClient()

                        ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())

                        ssh.connect(host, username = username, password = password)

                        sftp = ssh.open_sftp()

                        

                        sftp.put('/home/pi/skin.jpg', '/home/project/auto_skin.php')

                        

                        sftp.close()

                        ssh.close()

                

                elif datas == 'exit':

                    break

                             

            else:

                continue

            

            

            

            

    except KeyboardInterrupt:

        GPIO.cleanup()

        break

            

            

 

conn.close()

tcpSerSock.close()

tcpExSock.close()