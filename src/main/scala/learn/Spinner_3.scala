package learn

import geotrellis.proj4._
import geotrellis.raster._
import geotrellis.raster.io.geotiff._
import geotrellis.raster.io.geotiff.writer.GeoTiffWriter
import geotrellis.vector._

object Spinner_3 extends App {
  // 从外部输入中获取倾斜长方形的四个顶点坐标

  val topLeftPoint = (407263.7096439501, 5051316.241219164)
  val topRightPoint = (356384.0, 4900000.0)
  val bottomLeftPoint = (604815.0, 5000000.0)
  val bottomRightPoint = (554810.6187138803, 4836556.7795093)

  // 创建倾斜长方形的Envelope对象
  val envelope = Extent(topLeftPoint._1, bottomRightPoint._2, bottomRightPoint._1, topLeftPoint._2)

  // 从GeoTiff图像中获取相框的信息，包括边界坐标和旋转角度
  val path = "src/main/resources/example.tif"
  val geoTiff: SinglebandGeoTiff = GeoTiffReader.readSingleband(path)
  val extent: Extent = geoTiff.extent
  val crs: CRS = geoTiff.crs
  val transform = Transform(crs, LatLng)
  val rotationAngle: Double = Math.toRadians(transform(0, 0)._1)

  // 裁剪出倾斜长方形
  val croppedRaster: Raster[Tile] = geoTiff.raster.crop(envelope)

  // 从裁剪的Raster中提取Tile
  val croppedTile: Tile = croppedRaster.tile

  // 创建一个新的GeoTiff对象并保存
  val croppedExtent = croppedRaster.extent
  val croppedGeoTiff = SinglebandGeoTiff(croppedTile, croppedExtent, crs, geoTiff.tags, geoTiff.options)
  GeoTiffWriter.write(croppedGeoTiff, "output/cropped_image.tif")
}
