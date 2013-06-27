import java.util.Random;
import java.lang.Math;
import java.util.ArrayList;
import java.text.DecimalFormat;

public class NetworkSimulation {

	class Packet {
		public int generationTime;
		public int serviceTime;
	}

	public static int MAX_TICKS = 1000000;
	//public static int MAX_TICKS = 60000000;
	
	public static int TEST_RUN_TIMES = 5;
	public static double TICK_DURATION = 0.0001; // one tick = one millisecond
	//public static double TICK_DURATION = 0.000001; // one tick = one microsecond
	//public static double TICK_DURATION = 0.000000001; // one tick = one nanosecond

	public static Random rand = new Random();

	public static double unifRand() { 
		Random random = new Random();
		return random.nextDouble();

	}
	public static double unifExp(int packetsPerSecond) { 
		return (double) -Math.log(1-unifRand())/(packetsPerSecond * TICK_DURATION);
	}

	public static double calculateAvgExpDistribution(ArrayList<Double> results) {
		double sum = 0;
		int size = results.size();
		for (int i = 0; i < size; i++) {
			sum += results.get(i); 
		}
		return sum / size; 	
	} 

	public static double calculateVarianceExpDisribution(ArrayList<Double> results) {
		double sum = 0;
		int size = results.size();
		double avg = calculateAvgExpDistribution(results); 
		for (int i = 0; i < size; i++) {
			sum += Math.pow((results.get(i) - avg),2); 
		}
		return sum / size; 		
	}
	
	//public static int calculateNextPacketArrival() {
	//	return rand.nextInt(10) + 1;
	//}
	public static int generateNextPacketArrival(int packetsPerSecond) {
		int val = (int)unifExp(packetsPerSecond);
		return val;
	}

	public static void verify(int packetsPerSecond) {
		ArrayList<Double> results = new ArrayList<Double>();
		for (int i = 0; i < TEST_RUN_TIMES; i++) {
			System.out.println("Test #" +(i+1));
			for (int j = 0; j < MAX_TICKS; j++) {
				double val = unifExp(packetsPerSecond);
				results.add(val); 	
			}
			double avgComparison = (double) (1.0 / packetsPerSecond);
			double varianceComparison = (double) (Math.pow((1.0 / packetsPerSecond),2));

			System.out.println("Average: " + calculateAvgExpDistribution(results));
			System.out.println("Average Comparison: " + avgComparison);
			System.out.println();	
			System.out.println("Variance: " + calculateVarianceExpDisribution(results));
			System.out.println("Variance Comparison: " + varianceComparison);
		}
	}

	//need idle server time
	//avg number of packets in queue
	//avg time for queueing + service
	//avg packet loss

	public static double avgPacketTime(ArrayList<Packet> packetsServiced) {
		ArrayList<Integer> times = new ArrayList<Integer>();
		int sum = 0;
		for(Packet p : packetsServiced) {
			times.add(p.serviceTime - p.generationTime);
		}
		for(Integer i : times) {
			sum += i;
		}
		return (double)sum / (double)times.size(); 
	}

	public static double avgPacketsInQueue(ArrayList<Integer> packetsInQueue) {
		int sum = 0;
		//debug("packetsInQueue: "+ packetsInQueue.size());
		for(Integer i : packetsInQueue) {
			sum += i;
			//debug("Sum of packets at iteration # : " + sum);
		}
		//debug("avg packets in queue: " + ((double)sum / (double)packetsInQueue.size()));
		return ((double)sum / (double)packetsInQueue.size());
	}

	public static void main2(String[] args) {
		if (args.length > 1) {
			for (int j = 0; j < 5; j++) {
				debug("Iteration #"+(j+1) + ": ");
			
				int packetsPerSecond = Integer.parseInt(args[0]);
				int serviceTime = Integer.parseInt(args[1]);
				int queueSize = Integer.parseInt(args[2]);

				int idleTime = 0;
				int packetsDropped = 0;
				int packetsGenerated = 0;

				//ArrayList<Integer> packetsInQueue = new ArrayList<Integer>();
				Long totalSoujournTime = 0L;
				Long totalPacketsInQueue = 0L;
				int packetsServiced = 0;
				//ArrayList<Packet> packetsServiced = new ArrayList<Packet>();

				int generationTime = generateNextPacketArrival(packetsPerSecond);

				ArrayList<Packet> networkQueue = new ArrayList<Packet>();

				NetworkSimulation ns = new NetworkSimulation();

				int startedService = 0;
				int finishedTime = 0;

				Packet currentPacket = null;
				for (int i = 0; i < MAX_TICKS; i++) {
					if (i == generationTime) {
						Packet p = ns.new Packet();
						//debug("Packet generated");
						p.generationTime = i;
						generationTime = i + generateNextPacketArrival(packetsPerSecond) + 1;
						//debug("generation time: " + generationTime);
						if (queueSize > 0 && networkQueue.size() == queueSize) {
							packetsDropped++;
						}
						else {
							networkQueue.add(p);
							packetsGenerated++;
						}
						//debug("Queue size: " + networkQueue.size());
					}

					if (networkQueue.size() == 0 && currentPacket == null) {
						idleTime += 1;
					}

					//packetsInQueue.add(networkQueue.size());
					totalPacketsInQueue += networkQueue.size();

					if (networkQueue.size() > 0 && currentPacket == null) {
						currentPacket = networkQueue.get(0);
						startedService = i;
						finishedTime = i+serviceTime;
					}
					if (currentPacket != null && i == finishedTime) {
						networkQueue.remove(0);
						currentPacket.serviceTime = i;
						//packetsServiced.add(currentPacket);
						totalSoujournTime += i - currentPacket.generationTime;
						packetsServiced++;
						currentPacket = null; 
					} 
				} 
				debug("		E[T]    : " + (totalSoujournTime / packetsServiced));
				debug("		E[N]  	: " + (totalPacketsInQueue / MAX_TICKS));
				debug("		PIdle   : " + (((double)idleTime / (double)MAX_TICKS) * 100) + "%");
				if (queueSize > 0) {
					debug("		PLoss   : " + ((double)packetsDropped / (double) packetsGenerated * 100) + "%");
				}
				debug(""); 
			}
		}
		else {
			debug("No arguments specified!");
			// for (int i = 0; i < 100; i++) {
			// 	debug("Random var #"+i+ " :  " + generateNextPacketArrival(100));
			// }
		}
		return;
	}

	public static void debug(String msg) {
		System.out.println(msg);
	}
}