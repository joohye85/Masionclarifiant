import RPi.GPIO as GPIO
import spidev, time
import socket import *
from picamera import PiCamera

spi = spidev.SpiDev()
spi.open(0, 0)
spi.max_speed_hz = 1350000
camera = PiCamera()

def analog_read(channel):
    r = spi.xfer2([1, (8+channel) << 4, 0])
    adc_out = ((r[1]&3) << 8) +r[2]
    return adc_out

HOST = ''
PORT = 9999
BUFSIZE = 4096
ADDR = (HOST, PORT)

tcpSerSock = socket(AF_INET, SOCK_STREAM)
tcpSerSock.bind(ADDR)
tcpSerSock.listen(5)

while True:
    print('Waiting android connection')
    tcpExSock, addr = tcpSerSock.accept()
    print('...connected from : ', addr)

    #유수분값 읽고 전달하는 코드 
    reading = analog_read(0)
    sleep(2)
    print(reading)
    tcpExSock.send(reading) #안드로이드로 전달


    try:
        while Ture:
            data = ''
            data = tcpExSock.recv(BUFSIZE)
            dt = data.decode('utf-8')
            print(dt)
            if not dt:
                break
            if "p" in dt:
                print("success")                            
                #파일 이름 받아오는 코드
                #카메라 동작해서 캡쳐함 
                with picamera.PiCamera() as camera:
                    camera.start_preview()
                    time.sleep(2)
                    camera.captrue('/home/pi/skin.jpg')
                    camera.stop_preview()

                if not exists(filename):
                    self.request.send('no file')

                #이미지 파일 안드로이드로 전송   
                print('파일[skin] 전송 시작...')
                with open('/home/pi/skin.jpg', 'rb') as f:
                    try:
                        data =  f.read(BUFSIZE)
                        while data:
                            data_transgerred += self.request.send(data)
                            data = f.read(BUFSIZE)
                    except Exception as e:
                        print(e)

                print('전송완료[skin]')

            else:
                print("no p")
                

    except KeyboardInterrupt:
        GPIO.cleanup()
        break
    
tcpSerSock.close()
