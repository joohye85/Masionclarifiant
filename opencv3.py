import cv2
import numpy as np
import pymysql
import datetime
img = cv2.imread("./image/skin6.jpg")
img = cv2.resize(img, dsize=(400, 400), interpolation=cv2.INTER_AREA)  # 사이즈 조정
img_hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)

height, width, channel = img.shape
lower_red = (0, 0, 0)
upper_red = (50, 300, 305)

img_mask = cv2.inRange(img_hsv, lower_red, upper_red)

#mask를 통해 여드름 수 구하기
total_acne = 0
for y in range(0, height):
    for x in range(0, width):
        if img_mask[x, y] == 0:
            total_acne = total_acne + 1

#전체 잡티 추출
total_blue = []
total_green = []

for y in range(0, height):
    for x in range(0, width):
        b = img.item(y, x, 0)
        g = img.item(y, x, 1)
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
        if g < (mean_green - X_green) and b < (mean_blue - X_blue):
            img_result[y, x] = (0, 0, 0)
            total_white = total_white + 1

total_W = total_white / (height*width)*100 #전체 잡티(청결도)
total_R = total_acne / (height*width)*100 #여드름
total_dot = total_W - total_R #기미

aws_db = pymysql.connect(
    user = "admin",
    host = "maison.c1seefdiv0cl.ap-northeast-2.rds.amazonaws.com",
    port = 3306,
    passwd = "sswu1234",
    database = "maison",
    charset = 'utf8'
)
Datetime = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
cursor = aws_db.cursor(pymysql.cursors.DictCursor)
sql = "insert into skinitem(userID, clean, moisture, oil, pimple, liver_spot, skinDate) values (%s, %s, %s, %s, %s, %s, %s)"
val = ("wngp0805", round(total_W,2), "test", "test", round(total_R,2),round(total_dot,2), Datetime)
cursor.execute(sql, val)
aws_db.commit()
print(cursor.rowcount, "record inserted")

cv2.imshow("img_mask", img_mask)
cv2.imshow("skin", img)

cv2.waitKey(0)
cv2.destroyAllWindows()