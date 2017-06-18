package com.sevya.droolsfilter

object NodeDataGenerator {
    val rn = scala.util.Random
    
   def getNodeList (a : Int) : scala.collection.mutable.MutableList[Node] = {
       val randomNodeData = scala.collection.mutable.MutableList[Node]()
      for (i <- 1 to rn.nextInt(250)) {

        val node = new Node("192.168.1.2", (rn.nextInt(10)))

        if (i % 5 == 0) {
          node.setNodeIP("192.168.1.2");
          node.setBatteryVoltage(20 + rn.nextInt(90));
        } else if (i % 5 == 1) {
          node.setNodeIP("192.168.1.2");
          node.setBatteryVoltage(20 + rn.nextInt(90));
        } else if (i % 5 == 2) {
          node.setNodeIP("192.168.1.2");
          node.setBatteryVoltage(20 + rn.nextInt(90));
        } else if (i % 5 == 3) {
          node.setNodeIP("192.168.1.2");
          node.setBatteryVoltage(20 + rn.nextInt(90));
        } else if (i % 5 == 4) {
          node.setNodeIP("192.168.1.2");
          node.setBatteryVoltage(20 + rn.nextInt(90));
        }
        randomNodeData += node
      }
       randomNodeData
    }
}