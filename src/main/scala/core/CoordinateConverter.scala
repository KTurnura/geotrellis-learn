package learn

import geotrellis.vector._
import geotrellis.proj4._

class CoordinateConverter(sourceEPSG: Int, targetEPSG: Int) {
  // 定义源坐标系的CRS对象
  private val sourceCRS = CRS.fromEpsgCode(sourceEPSG)

  // 定义目标坐标系的CRS对象
  private val targetCRS = CRS.fromEpsgCode(targetEPSG)

  // 创建一个投影转换对象，从源EPSG到目标EPSG坐标系
  private val transform = Transform(sourceCRS, targetCRS)

  // 方法来转换经纬度坐标到目标坐标系中的坐标点
  def convert(lon: Double, lat: Double): Point = {
    // 创建一个Point对象
    val point = Point(lon, lat)

    // 将经纬度坐标转换为目标EPSG坐标系中的点
    point.reproject(transform)
  }

}
object test{
  def main(args: Array[String]): Unit = {
    // 创建一个CoordinateConverter对象，从EPSG:4326到EPSG:32652坐标系
    val converter = new CoordinateConverter(4326, 32652)

    // 经纬度坐标
    val lon = 129.6800
    val lat = 43.6800

    // 使用CoordinateConverter对象将经纬度坐标转换为EPSG:32652坐标
    val targetPoint = converter.convert(lon, lat)

    println(s"目标坐标：(${targetPoint.getX}, ${targetPoint.getY})")
  }
}

