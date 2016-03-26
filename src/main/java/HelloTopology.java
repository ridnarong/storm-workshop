import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.AuthorizationException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;

public class HelloTopology {
	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, AuthorizationException {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("input-number", new NumberGeneratorSpout(), 1);
		builder.setBolt("multiply-by-two", new DoubleBolt(), 1).shuffleGrouping("input-number");
		
		Config conf = new Config();
	    conf.setDebug(true);
	    
	    conf.setNumWorkers(1);
	    // Submit to Storm cluster
	    // StormSubmitter.submitTopologyWithProgressBar(args[0], conf, builder.createTopology());
	    // Submit to local cluster
	    LocalCluster cluster = new LocalCluster();
	    cluster.submitTopology("test", conf, builder.createTopology());
	    Utils.sleep(10000);
	    cluster.killTopology("test");
	    cluster.shutdown();
	}
}
