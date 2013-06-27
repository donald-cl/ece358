package ece358.networks.assignment2;

import java.util.Random;
import java.util.ArrayList;

public class DiscreteEventSimulator {
	
	class Packet {
		public int generationTime;
		public int serviceTime;
	}

	public static int MAX_TICKS = 1000000;

	public static int TEST_RUN_TIMES = 5;
	public static double TICK_DURATION = 0.0001; // one tick = one millisecond
	
	public static Random rand = new Random();

	public static double unifRand() { 
		Random random = new Random();
		return random.nextDouble();

	}
	public static double unifExp(int packetsPerSecond) { 
		return (double) -Math.log(1-unifRand())/(packetsPerSecond * TICK_DURATION);
	}
	
	class Node {
		int waitDuration = 0;
		boolean isTransmitting = false;
		int durationRemaining = 0;
		
		public Node() {}
		
		public int generateNextPacketArrival(int packetsPerSecond) {
			int val = (int)unifExp(packetsPerSecond);
			return val;
		}
	}

	//private final static int LAN_SPEED = 1000000;
	//private static final int PACKET_LENGTH = 100;
	private final static int NODE_DISTANCE = 10;
	
	public static void main(String args[]) {
		
		if (args.length == 1 && args[0].equals("-usage")) {
			debug("------------------------------------------------------------------------");
			debug("Parameter description:");
			debug("N: the number of computers connected to the LAN (variable)");
			debug("A: Data packets arrive at the MAC layer following a Poisson process with an average arrival rate of A packets/second (variable)");
			debug("W: the speed of the LAN (fixed)");
			debug("L: packet length (fixed)");
			debug("P: Persistence parameter for P-persistent CSMA protocols");
			debug("-----------------------------------------------------------------------");
			debug("Command Format: ");
			debug("DiscreteEventSimulator <N> <A> <W> <L> <P>");
			debug("");
 		}
 		else if (args.length == 5) {
 			
 			int compNum = Integer.parseInt(args[0]);
 			int pktArrivalRate = Integer.parseInt(args[1]);
 			int lanSpeed = Integer.parseInt(args[2]);
 			int pktLength = Integer.parseInt(args[3]);
 			int persistanceParam = Integer.parseInt(args[4]);
 			System.out.println("yay");
 			
 			DiscreteEventSimulator des = new DiscreteEventSimulator();
 			
 			ArrayList<Node> nodes = new ArrayList<Node>();
 			for (int i = 0; i < compNum; i++) {
 				Node n = des.new Node();
 				nodes.add(n);
 			}
 			
 		}
 		else {
 			debug("Not enough arguments! Enter DiscreteEventSimulator -usage");
 		}


	}

	public static void debug(String msg) {
		System.out.println(msg);
	}
}