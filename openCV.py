import cv2
import numpy as np
import pymysql
import datetime
image = cv2.imread("./image/eye1.jpg")
img_hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)
lower_red = (10, 0, 0)
upper_red = (40, 300, 300)
edge1 = cv2.Canny(image, 150, 10)
img_grey = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
ret, img_binary = cv2.threshold(edge1, 127, 255, 0)
contours, hierarchy = cv2.findContours(img_binary, cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)
print("주름 개수: ", len(contours)-1)
cv2.drawContours(image, contours, -1, (0,0,255), 1)

cv2.imshow("original", image)
cv2.imshow("gray", edge1)
cv2.waitKey(0)
cv2.destroyAllWindows()