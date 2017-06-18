package com.sevya.droolsfilter

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.storage.StorageLevel
import org.apache.spark.rdd._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.kie.api.runtime.StatelessKieSession
import java.nio.file.Files
import java.nio.file.Paths

object NodeDataStream {

  def main(args: Array[String]) {
    if (args.length < 3) {
      System.err.println("Usage: BatteryDataStream <xlsFileName>")
      System.exit(1)
    }

    //params
    val xlsFileName = args(0)
    
    //5 seconds interval
    val batchInterval = 5

    //check if the rules file exists
    if (!Files.exists(Paths.get(xlsFileName))) {
      println("Error: Rules file [" + xlsFileName + "] is missing")
      return
    }

    //setup spark. Toggle below two lines for local v/s yarn
    //val sparkConf = new SparkConf().setAppName("Battery Data Streaming Evaluation")
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Node Data Streaming Evaluation")
   
    val sc = new SparkContext(sparkConf)
    val ssc = new StreamingContext(sc, Seconds(batchInterval))
    val sqc = new SQLContext(sc);
    
   
    //setup a queue rdd to simulate streaming rdd
    val nodeDataQueueRDD = scala.collection.mutable.Queue[RDD[Node]]()
    val nodeDataStream = ssc.queueStream(nodeDataQueueRDD)

    //setup rules executor
    val rulesExecutor = new RulesExecutor(xlsFileName)

    // 1000 RDDs of random data
    for (batch <- 1 to 1000) {
      val randomNodeData = NodeDataGenerator.getNodeList(batch)
      val rdd = ssc.sparkContext.parallelize(randomNodeData)
      nodeDataQueueRDD += rdd
    }

    
    //to-add filler logic to create snapshot

    //logic for each dstream
    nodeDataStream.foreachRDD(rdd => {

      //evaluate all the rules for the batch
      val evaluatedNodes = rdd.mapPartitions(incomingEvents => { rulesExecutor.evalRules(incomingEvents) })

      //store the evaluation results in hbase
      //hbaseContext.bulkPut[Patient](evaluatedPatients, patientTable, HBaseUtil.insertEvaluatedDataIntoHBase, true)

      //convert to dataframe
      val nodeDataDf = sqc.applySchema(evaluatedNodes, classOf[Node])

      //print batch details
      println("Total Nodes in batch: " + nodeDataDf.count())
      println("Nodes with faults " + nodeDataDf.filter("voltageFlag > 23").count())

      //compute statistics and print them
      val countMatrix = nodeDataDf.groupBy("voltage").agg(max("voltage"))
      countMatrix.show()

    })

    ssc.start()
    ssc.awaitTermination()
  }
}