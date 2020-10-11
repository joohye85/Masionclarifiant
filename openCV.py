import cv2
import pymysql
import datetime
image = cv2.imread("./image/eye1.jpg")
img_hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)

edge1 = cv2.Canny(image, 150, 10)
img_grey = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
######주름 개수를 검출하기 위한 Contours 검출 코드
contours, hierarchy = cv2.findContours(edge1, cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)
print("주름 개수: ", len(contours)-1) #-1은 눈을 가리기 위한 사각형의 개수를 감소시킨 것
wrinkle = len(contours)-1
cv2.drawContours(image, contours, -1, (0,0,255), 1) #세번 째 파라미터가 음수면 모든 Contour 출력

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
sql = "update skinitem set wrinkle = %s where userID = %s order by skinnum desc limit 1"
val = (wrinkle, "wngp0805")
cursor.execute(sql, val)
aws_db.commit()

print(cursor.rowcount, "record inserted")
cv2.waitKey(0)
cv2.destroyAllWindows()
