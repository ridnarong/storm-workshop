import java.util.Map;
import java.util.Random;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

public class NumberGeneratorSpout extends BaseRichSpout {
	
	private SpoutOutputCollector collector;
	
	public void nextTuple() {
		Utils.sleep(100);
		final Random rand = new Random();
		int nextNumber = rand.nextInt(100);
		collector.emit(new Values(nextNumber));
	}

	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
		
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("default"));
		
	}

}
