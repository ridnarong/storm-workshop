import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.AuthorizationException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;

public class TwitterTopology {
	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, AuthorizationException {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("input-tweet", new TweetSpout(), 1);
		builder.setBolt("extract-hashtag", new HashtagExtractorBolt(), 1).shuffleGrouping("input-tweet");
		builder.setBolt("count-hashtag", new PubnubBolt(), 1).shuffleGrouping("extract-hashtag");
		
		Config conf = new Config();
	    //conf.setDebug(true);
	    
	    conf.setNumWorkers(1);
	    LocalCluster cluster = new LocalCluster();
	    cluster.submitTopology("test", conf, builder.createTopology());
	    Utils.sleep(100000);
	    cluster.killTopology("test");
	    cluster.shutdown();
	}
}
