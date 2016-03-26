import java.util.Map;

import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.pubnub.api.PubnubException;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

public class PubnubBolt extends BaseRichBolt {
	private OutputCollector collector;
	
	public void execute(final Tuple input) {
		final Pubnub pubnub = new Pubnub(System.getProperty("pubnub.pubkey"),System.getProperty("pubnub.subkey"));
		try {
			pubnub.subscribe("my_channel", new Callback() {
			    @Override
			    public void connectCallback(String channel, Object message) {
			        pubnub.publish("storm", input.getString(0), new Callback()  {
			        	  public void successCallback(String channel, Object response) {
			        		    System.out.println(response.toString());
			        		    pubnub.shutdown();
			        		  }
			        		  public void errorCallback(String channel, PubnubError error) {
			        		    System.out.println(error.toString());
			        		    pubnub.shutdown();
			        		  }
			        		});
			    }
			    
			    public void errorCallback(String channel, PubnubError error) {
			    	System.out.println(error.toString());
			    }
			});
		} catch (PubnubException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(input.getString(0));
	}

	public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		
		
	}
	
}
