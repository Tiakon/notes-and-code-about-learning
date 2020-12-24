package cn.tiakon.flink.learn.chapter01

import org.apache.flink.api.scala._
import org.apache.flink.api.scala.{AggregateDataSet, DataSet, ExecutionEnvironment}

object WordCountBatch {
  def main(args: Array[String]): Unit = {
    val environment = ExecutionEnvironment.getExecutionEnvironment

    val inputDataSet: DataSet[String] = environment.readTextFile("data/chapter01.txt")

    inputDataSet.collect().foreach(line => {
      println(line)
    })

    val sumRdd: AggregateDataSet[(String, Int)] = inputDataSet.mapPartition(
      _.flatMap(_.split("\\W+").filter(_.nonEmpty))
        .map((_, 1)))
      .groupBy(0)
      .sum(1)
    sumRdd.collect().foreach(println(_))

  }
}
