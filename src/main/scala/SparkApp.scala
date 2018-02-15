import org.apache.spark.ml.linalg.{Vector => SparkVector}
import org.apache.spark.sql.streaming.StreamingQuery
import org.apache.spark.sql.{Dataset, SparkSession}

object SparkApp extends App {
  val spark = SparkSession.builder().appName("spark-app").master("local[*]").getOrCreate()
  spark.sparkContext.setLogLevel("WARN")
  import spark.implicits._

  val df = spark
    .readStream
    .format("kafka")
    .option("kafka.bootstrap.servers", "localhost:9092")
    .option("subscribe", "turbine")
    .load()
  private val transformed: Dataset[String] = df.selectExpr("CAST(value AS STRING)")
    .as[String]

  private val query: StreamingQuery = transformed.writeStream.format("console").start()
  query.awaitTermination()
}
