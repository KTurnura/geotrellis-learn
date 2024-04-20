package learn

import geotrellis.raster.io.geotiff.SinglebandGeoTiff
import geotrellis.raster.io.geotiff.reader.GeoTiffReader
import geotrellis.vector.Extent

object ViewGeoTiffMetadata {
  def main(args: Array[String]): Unit = {
    // 读取GeoTIFF文件
//    val path = "src/output/part_0.tif"
    val path = "src/main/resources/example.TIF"
    val geoTiff: SinglebandGeoTiff = GeoTiffReader.readSingleband(path)

    // 获取地理参考信息
    val crs = geoTiff.crs
    val extent: Extent = geoTiff.extent

    // 获取分辨率
    val cellSize = geoTiff.cellSize

    // 打印信息
    println(s"CRS: $crs")
    println(s"Extent: $extent")
    println(s"Cell Size: $cellSize")
  }

}
