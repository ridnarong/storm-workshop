import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class DoubleBolt extends BaseRichBolt {
	private OutputCollector collector;

	public void execute(Tuple input) {
		Integer inputNumber = input.getInteger(0);
		collector.emit(input, new Values(inputNumber*2));
	}

	public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	public void declareOutputFields(OutputFieldsDeclarer delarer) {
		delarer.declare(new Fields("double-value"));
		
	}

}
