package learn

import geotrellis.raster._
import geotrellis.raster.io.geotiff.writer.GeoTiffWriter
import geotrellis.raster.io.geotiff.{GeoTiffReader, SinglebandGeoTiff}
import geotrellis.raster.resample.NearestNeighbor
import geotrellis.vector._
import org.apache.commons.math3.linear.{Array2DRowRealMatrix, RealMatrix}

object Spinner1 {

  def main(args: Array[String]): Unit = {
    val path = "src/main/resources/example.TIF"

    // 可以看出,创建一个SinglebandGeoTiff对象需要准备一个GeoTiffTile对象和一个GeoTiffInfo对象,而GeoTiffTile对象的创建也需要GeoTiffInfo对象.
    val geoTiff: SinglebandGeoTiff = GeoTiffReader.readSingleband(path)


    // 假设给定的四个点（在原始图像范围内）
    val topLeftPoint = (407263.7096439501, 5051316.241219164)
    val topRightPoint = (356384.0, 4900000.0)
    val bottomLeftPoint = (604815.0, 5000000.0)
    val bottomRightPoint = (554810.6187138803, 4836556.7795093)

    // 将给定的点转换为图像的行列索引坐标
    val topLeftCoord = geoTiff.rasterExtent.mapToGrid(topLeftPoint._1, topLeftPoint._2)
    val topRightCoord = geoTiff.rasterExtent.mapToGrid(topRightPoint._1, topRightPoint._2)
    val bottomLeftCoord = geoTiff.rasterExtent.mapToGrid(bottomLeftPoint._1, bottomLeftPoint._2)
    val bottomRightCoord = geoTiff.rasterExtent.mapToGrid(bottomRightPoint._1, bottomRightPoint._2)

    // 使用 Crop 函数裁剪图像
    val croppedRaster: Raster[Tile] = geoTiff.raster.crop(topLeftCoord._1, topLeftCoord._2, bottomRightCoord._1, bottomRightCoord._2)

    // 构建裁剪图像的新范围
    val croppedExtent = Extent(
      topLeftPoint._1, bottomRightPoint._2,
      bottomRightPoint._1, topLeftPoint._2
    )

    // 计算旋转角度

    // 假设旋转角度为30度（弧度表示）
    val rotationAngle = Math.toRadians(30.0)



    // 对裁剪后的图像进行仿射变换
    val resampledRaster: Raster[Tile] = croppedRaster.resample(RasterExtent(croppedExtent, croppedRaster.cols, croppedRaster.rows), NearestNeighbor)

//    // 创建旋转矩阵
//    val rotationMatrix = createRotationMatrix(rotationAngle)

    // 对旋转后的图像进行重采样
//    val rotatedRaster: Raster[Tile] = resampledRaster.resample(RasterExtent(croppedExtent, croppedRaster.cols, croppedRaster.rows), NearestNeighbor, rotationMatrix)

//    // 创建裁剪并旋转后的新 GeoTIFF 对象并保存
//    val crs = geoTiff.crs
//    val rotatedGeoTiff = SinglebandGeoTiff(resampledRaster, crs, croppedExtent, geoTiff.tags, geoTiff.options)
//    GeoTiffWriter.write(rotatedGeoTiff, "/path/to/cropped_and_rotated_image.tif")
//  }
//  // 定义一个函数来创建旋转矩阵
//  def createRotationMatrix(rotationAngle: Double): RealMatrix = {
//    val cosTheta = Math.cos(rotationAngle)
//    val sinTheta = Math.sin(rotationAngle)
//    new Array2DRowRealMatrix(Array(
//      Array(cosTheta, -sinTheta),
//      Array(sinTheta, cosTheta)
//    ))
  }


}
