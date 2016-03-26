import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import twitter4j.HashtagEntity;
import twitter4j.Status;

public class HashtagExtractorBolt extends BaseRichBolt {

	private OutputCollector collector;

	public void execute(Tuple input) {
		Status status = (Status) input.getValue(0);
		for(HashtagEntity hashtag : status.getHashtagEntities()) {
			collector.emit(new Values(hashtag.getText()));
		}
		collector.ack(input);
	}

	public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("default"));
		
	}


}
