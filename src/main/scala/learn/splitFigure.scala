package learn

import geotrellis.raster._
import geotrellis.raster.io.geotiff.writer.GeoTiffWriter
import geotrellis.raster.io.geotiff.{GeoTiffReader, SinglebandGeoTiff}
import geotrellis.vector.Extent

import scala.collection.immutable
object splitFigure {

  def main(args: Array[String]): Unit = {
    val path = "/Users/kturnura/Downloads/LE07_L2SP_116029_20220205_20220303_02_T1/example.TIF"

    // 可以看出,创建一个SinglebandGeoTiff对象需要准备一个GeoTiffTile对象和一个GeoTiffInfo对象,而GeoTiffTile对象的创建也需要GeoTiffInfo对象.
    val geoTiff : SinglebandGeoTiff = GeoTiffReader.readSingleband(path)


    // 2. Split the image into 16 part
    val cols = 2
    val rows = 2


    //Split the image into parts
    val tiled : immutable.IndexedSeq[SinglebandGeoTiff] = (0 until cols).flatMap { col =>
      (0 until rows).map { row =>
        val colMin = col * (geoTiff.tile.cols / cols)
        val colMax = (col + 1) * (geoTiff.tile.cols / cols)
        val rowMin = row * (geoTiff.tile.rows / rows)
        val rowMax = (row + 1) * (geoTiff.tile.rows / rows)
        val gridBounds = GridBounds(colMin, rowMin, colMax, rowMax)
        geoTiff.crop(gridBounds)
      }
    }

    // Save
    val savePath = "/Users/kturnura/Code/scala/FigureSplit/src/output"
    tiled.zipWithIndex.foreach{case (raster, index) =>
      val extent : Extent = raster.extent
      val croppedGeoTiff = SinglebandGeoTiff(raster.tile, extent, geoTiff.crs)
      GeoTiffWriter.write(croppedGeoTiff, s"$savePath/part_$index.tif")
    }
  }
}
