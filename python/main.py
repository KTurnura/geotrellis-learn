import cv2

import os


image_path = '../src/full_image.tif'
output_dir =  'output'
# 分割后的图像大小
image_size = (1111,1111)

image = cv2.imread(image_path)
# 计算分割后的图像数量

num_rows = image.shape[0] / 1111
num_cols = image.shape[1] /1111

# 分割图像

print(str(num_cols) + " "+str(num_rows) + "\n")

for row in range(int(num_rows)):
    for col in range(int(num_cols)):# 计算分割后的图像位置
        x = col * image_size[1]
        y = col * image_size[0]
        # 截取分割后的图像
        sub_image = image[y:y + image_size[0], x : x + image_size[1]]
        # 保存分割后的图像
        output_path = os.path.join(output_dir, f'image_{row}_{col}.tif')
        print(output_path)
        cv2.imwrite(output_path,sub_image)