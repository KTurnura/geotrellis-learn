package learn

import geotrellis.raster._
import geotrellis.raster.io.geotiff._
import geotrellis.raster.io.geotiff.writer.GeoTiffWriter
import geotrellis.vector.{Extent, Point}

object Spinner2 extends App {
  // 读取GeoTIFF文件
  val path = "src/main/resources/example.TIF"
  val geoTiff: SinglebandGeoTiff = GeoTiffReader.readSingleband(path)


  // 从外部输入中获取倾斜长方形的四个顶点坐标
  val topLeftPoint = (404815.0, 5052915.0)
  val topRightPoint = (356384.0, 4900000.0)
  val bottomLeftPoint = (604815.0, 5000000.0)
  val bottomRightPoint = (504815.0, 4833885.0)

  // 计算旋转的角度
  val rotationAngle: Double = 10.0

  // 获取图像数据和仿射变换信息
  // 宽高，还有中心点
  val extent: Extent = geoTiff.extent
  val width: Double = extent.xmax - extent.xmin
  val height: Double = extent.ymax - extent.ymin
  // 此处中心点是根据GeoTiff 的图像计算出来的，而不是给定的坐标
  // TODO：（经纬度坐标 转变为 墨卡托坐标）
  val center: Point = extent.center


  // 计算旋转后的四个顶点坐标
  val topLeft = rotatePoint(extent.xmin, extent.ymax, center.getX, center.getY, rotationAngle)
  val topRight = rotatePoint(extent.xmax, extent.ymax, center.getX, center.getY, rotationAngle)
  val bottomLeft = rotatePoint(extent.xmin, extent.ymin, center.getX, center.getY, rotationAngle)
  val bottomRight = rotatePoint(extent.xmax, extent.ymin, center.getX, center.getY, rotationAngle)

  // 计算旋转后的Envelope（即包围框），获取四个点的位置
  val rotatedExtent = Extent(topLeft.getX, bottomLeft.getY, bottomRight.getX, topRight.getY)

  // 计算倾斜长方形的四个顶点坐标（假设已知倾斜角度）
  val halfWidth: Double = width / 2
  val halfHeight: Double = height / 2

  val skewExtent = Extent(
    rotatedExtent.center.getX - halfWidth,
    rotatedExtent.center.getY - halfHeight,
    rotatedExtent.center.getX + halfWidth,
    rotatedExtent.center.getY + halfHeight
  )

  // 计算投影后的倾斜长方形的Envelope（即包围框）
  val minX = Seq(skewExtent.xmin, skewExtent.xmax).min
  val maxX = Seq(skewExtent.xmin, skewExtent.xmax).max
  val minY = Seq(skewExtent.ymin, skewExtent.ymax).min
  val maxY = Seq(skewExtent.ymin, skewExtent.ymax).max
  val projectedSkewExtent = Extent(minX, minY, maxX, maxY)

  // 通过投影后的倾斜长方形范围裁剪旋转后的图像
  val croppedRaster: Raster[Tile] = geoTiff.raster.crop(projectedSkewExtent)


  // 创建一个新的GeoTiff对象并保存
  val croppedGeoTiff = SinglebandGeoTiff(croppedRaster.tile, projectedSkewExtent, geoTiff.crs)
  GeoTiffWriter.write(croppedGeoTiff, "output/cropped_image.tif")


  // 根据中心点和旋转角度计算旋转后的坐标
  def rotatePoint(x: Double, y: Double, centerX: Double, centerY: Double, angle: Double): Point = {
    val sinA = Math.sin(angle)
    val cosA = Math.cos(angle)
    val xShifted = x - centerX
    val yShifted = y - centerY
    val rotatedX = xShifted * cosA - yShifted * sinA + centerX
    val rotatedY = xShifted * sinA + yShifted * cosA + centerY
    Point(rotatedX, rotatedY)
  }
}
