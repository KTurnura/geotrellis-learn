package learn

import geotrellis.raster.io.geotiff.GeoTiffReader
import geotrellis.raster.io.geotiff.writer.GeoTiffWriter
import geotrellis.raster.{ArrayMultibandTile, MultibandTile, Raster, Tile}

object ImageRotation {
  def main(args: Array[String]): Unit = {
    // 1. Load GeoTIFF image
    val pathToGeoTiff = "src/main/resources/example.TIF"
    val raster: Raster[MultibandTile] = GeoTiffReader.readMultiband(pathToGeoTiff).raster

    // 2. Define rotation parameters
    val rotationAngle = math.Pi / 4 // Rotate 45 degrees
    val centerPoint = raster.extent.center

    

  }
}
