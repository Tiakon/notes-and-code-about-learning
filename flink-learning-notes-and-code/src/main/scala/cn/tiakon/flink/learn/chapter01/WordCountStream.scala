package cn.tiakon.flink.learn.chapter01

import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment, _}

object WordCountStream {

  def main(args: Array[String]): Unit = {

    val environment: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    val inputDataStream: DataStream[String] = environment.socketTextStream("localhost", 7202)

    val result: DataStream[(String, Int)] = inputDataStream.flatMap(_.split("\\W+")).filter(_.nonEmpty)
      .map((_, 1))
      .keyBy(0)
      .sum(1)

    result.print().setParallelism(2)

    environment.execute("WordCountStreamApp")

  }

}
