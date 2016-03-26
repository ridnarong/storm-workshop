import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class TweetSpout extends BaseRichSpout  {

	private SpoutOutputCollector collector;
	/** Set up your blocking queues: Be sure to size these properly based on expected TPS of your stream */
	private BlockingQueue<Status> statusQueue = new LinkedBlockingQueue<Status>(100000);
	private TwitterStream twitterStream;
	
	public void nextTuple() {
			Status status;
			try {
				status = statusQueue.take();
				collector.emit(new Values(status));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				twitterStream.cleanUp();
			}
		
	}

	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
		twitterStream = new TwitterStreamFactory().getInstance();
		StatusListener listener = new StatusListener() {

			public void onException(Exception arg0) {
				// TODO Auto-generated method stub
				
			}

			public void onDeletionNotice(StatusDeletionNotice arg0) {
				// TODO Auto-generated method stub
				
			}

			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub
				
			}

			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub
				
			}

			public void onStatus(Status arg0) {
				statusQueue.add(arg0);	
			}

			public void onTrackLimitationNotice(int arg0) {
				// TODO Auto-generated method stub
				
			}};
			twitterStream.addListener(listener);
			twitterStream.sample();
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("default"));	
	}

}
