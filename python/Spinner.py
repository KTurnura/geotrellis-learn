import cv2
import numpy as np

# 1. 读取图像
img = cv2.imread('iexample.TIF')

# 2. 转换为灰度图像
gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

# 3. 找到最大的轮廓
_, thresh = cv2.threshold(gray, 1, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)
contours, _ = cv2.findContours(thresh, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
contour = max(contours, key=cv2.contourArea)

# 4. 获取最小外接矩形
rect = cv2.minAreaRect(contour)
box = cv2.boxPoints(rect)
box = np.int0(box)

# 5. 旋转原始图像以校正矩形
width = int(rect[1][0])
height = int(rect[1][1])
src_pts = box.astype("float32")
dst_pts = np.array([[0, height-1],
                    [0, 0],
                    [width-1, 0],
                    [width-1, height-1]], dtype="float32")
M = cv2.getPerspectiveTransform(src_pts, dst_pts)
warped = cv2.warpPerspective(img, M, (width, height))

# 6. 显示中间过程（用于可解释性研究）
cv2.drawContours(img, [box], 0, (0, 0, 255), 2)
cv2.imshow('edge', img)
cv2.waitKey(0)
cv2.destroyAllWindows()

# 7. 裁剪并保存结果图像
output = warped[0:height, 0:width]
cv2.imwrite('output.jpg', output)
