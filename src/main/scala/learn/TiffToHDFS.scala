package learn

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}

/**
 *使用scala 将遥感Tiff 图像写入HDFS 中，并期望通过指定
    *1. 优先级选定区域的经纬坐标
    *2. 时间戳
    *3. 云量
    *4. 分辨率
    *设计图像存储的文件夹
 */
object TiffToHDFS {
  def main(args: Array[String]): Unit = {
    // 输入参数
    // 从命令行参数获取输入
//    val tiffFilePath = args(0)
//    val regionIndex = args(1)
//    val cloudCover = args(2)
//    val timestamp = args(3)
//    val resolution = args(4)

    val tiffFilePath = "src/main/resources/example.TIF"
    val priorityRegion = "China-Shandong"
    val cloudCover = "10"
    val timestamp = "2023-05-01"
    val resolution = "10"

    val targetDirectory = "hdfs://localhost:9000/user/hadoop/tiff_images"

    // 初始化Hadoop配置
    val conf = new Configuration()
    val fs = FileSystem.get(conf)

    // 创建存储图像的文件夹路径
    val imageDirectory = new Path(s"$targetDirectory/priority_$priorityRegion" +
      s"/timestamp_$timestamp/cloud_$cloudCover/resolution_$resolution")
    fs.mkdirs(imageDirectory)

    // 读取TIFF文件并复制到HDFS
    val localFilePath = new Path(tiffFilePath)
    val hdfsFilePath = new Path(imageDirectory, localFilePath.getName)
    fs.copyFromLocalFile(localFilePath, hdfsFilePath)

    println(s"TIFF file successfully copied to HDFS: $hdfsFilePath")
  }
}
