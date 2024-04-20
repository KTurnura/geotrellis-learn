package learn

import geotrellis.proj4._
import geotrellis.vector._
/**
 * 将EPSG：326525 转换为经纬度坐标系
 */
object EPSGConvert {
  def main(args: Array[String]): Unit = {
    // 定义EPSG:32652投影和经纬度坐标系
    val epsg32652 = CRS.fromEpsgCode(32652)
    val epsg4326 = CRS.fromEpsgCode(4326)

    // 定义一个坐标点
    val point32652 = Point(356385.0, 4833885.0)

    // 将EPSG:32652坐标转换为经纬度坐标
    val transform = Transform(epsg32652, epsg4326)
    val point4326 = point32652.reproject(transform)

    // 打印转换后的经纬度坐标
    println(s"EPSG:4326 Coordinates: ${point4326.getX}, ${point4326.getY}")
  }
}
