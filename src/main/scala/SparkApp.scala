import org.apache.spark.sql.SparkSession

object SparkApp extends App{
 val spark = SparkSession.builder().appName("spark-app").master("local[*]").getOrCreate()
}
