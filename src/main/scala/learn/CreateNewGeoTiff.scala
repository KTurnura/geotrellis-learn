package learn

import geotrellis.raster.Tile
import geotrellis.raster.io.geotiff.SinglebandGeoTiff
import geotrellis.raster.io.geotiff.reader.GeoTiffReader
import geotrellis.raster.io.geotiff.writer.GeoTiffWriter
import geotrellis.vector.Extent

object CreateNewGeoTiff {
  // 读取一个GeoTiff图
  def main(args: Array[String]): Unit = {
    val path = "/Users/kturnura/Downloads/LE07_L2SP_116029_20220205_20220303_02_T1/example.TIF"
    val geoTiff: SinglebandGeoTiff = GeoTiffReader.readSingleband(path)

    // 获取地理参考信息
    val crs = geoTiff.crs
    val extent: Extent = geoTiff.extent
    val tile : Tile = geoTiff.tile

    // 使用构造函数创建一个新的SinglebandGeoTiff 图像
    val newGeoTiff  =  SinglebandGeoTiff(tile, extent, crs)


    val pathToNew = "output/example.TIF"

    GeoTiffWriter.write(newGeoTiff,pathToNew)

  }
}
