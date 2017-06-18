package com.sevya.droolsfilter

/*
 * Class to execute the rules
 */
class RulesExecutor (xlsFileName : String)  extends Serializable {
 
  //evaluate all the rules and send the result back to 
  def evalRules (incomingEvents : Iterator[Node]) : Iterator[Node] = {
        val ksession = KieSessionFactory.getKieSession(xlsFileName)
        val nodes = incomingEvents.map(node => {
          ksession.execute(node)
          node
        })
        nodes
  }
}