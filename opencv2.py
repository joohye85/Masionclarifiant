import cv2
import numpy as np
import pymysql
import datetime
img = cv2.imread("./image/eye1.jpg")
img = cv2.resize(img, dsize=(400, 400), interpolation=cv2.INTER_AREA)  # 사이즈 조정
img_hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)

height, width, channel = img.shape

total_blue = []
total_green = []

for y in range(0, height):
    for x in range(0, width):
        b = img.item(y, x, 0)
        g = img.item(y, x, 1)
        r = img.item(y, x, 2)
        total_blue.append(b)
        total_green.append(g)

mean_blue = np.mean(total_blue)
X_blue = np.std(total_blue)
mean_green = np.mean(total_green)
X_green = np.std(total_green)

img_result = img.copy()
total_white = 0
for y in range(0, height):
    for x in range(0, width):
        b = img_result.item(y, x, 0)
        g = img_result.item(y, x, 1)
        r = img_result.item(y, x, 2)
        if g < (mean_green - X_green) and b < (mean_blue - X_blue):
            img_result[y, x] = (255, 255, 255)
            total_white = total_white + 1
print(total_white)
print(height*width)
total = total_white / (height*width)*100
print(total)

aws_db = pymysql.connect(
    user = "admin",
    host = "maison.c22zsfchgrm1.ap-northeast-2.rds.amazonaws.com",
    port = 3306,
    passwd = "sswu1234",
    database = "maison",
    charset = 'utf8'
)
Datetime = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
cursor = aws_db.cursor(pymysql.cursors.DictCursor)
sql = "insert into skinitem(userID, clean, moisture, oil, pimple, skinDate) values (%s, %s, %s, %s, %s, %s)"
val = ("wngp0805", round(total,2), "test", "test", "test", Datetime)
cursor.execute(sql, val)
aws_db.commit()
print(cursor.rowcount, "record inserted")
cannyImage = cv2.Canny(img, 50, 60)

# img_result2 = cv2.bitwise_or(img_result, cannyImage)

cv2.imshow("skin", img)
cv2.imshow("acne", img_result)

cv2.waitKey(0)
cv2.destroyAllWindows()
