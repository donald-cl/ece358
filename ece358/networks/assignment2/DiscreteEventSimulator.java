package ece358.networks.assignment2;

import java.util.Random;
import java.util.ArrayList;
import java.util.HashSet;
import java.lang.Math;

public class DiscreteEventSimulator {
	
	public boolean isMediumBusy = true;
	public static boolean dbg = false;


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
	
	public static double calculatePropogationTime(int nodeSender, int nodeReceiver)
	{
		debug("CalculatePropogationTime: Sender: " + nodeSender + " Receiver: " + nodeReceiver);
		return (10*(nodeSender - nodeReceiver))/250000000;
	}
	
	public static double calculateTransferTime(int lanSpeed, int packetLength)
	{
		return packetLength/lanSpeed;
	}
	
	class Node {
		int waitDuration = 0;
		boolean isTransmitting = false;
		int durationRemaining = 0;
		int pktGenerationTime = 0;
		int id = -1;
		int collisionCounter = 0;
		
		public Node() {}
		
		public void generateNextPacketArrival(int packetsPerSecond) {
			if (pktGenerationTime == 0) {
				pktGenerationTime = (int)unifExp(packetsPerSecond);				
			}
		}
		public boolean isTransmissionSuccessful() {
			return true;
		}
		public boolean isCollisionDetected() {
			return true;
		}
		public int calculateBEB() {
			int exponentialTime = 1 + (int) (Math.random() * (Math.pow(2,collisionCounter) - 1));	
			return exponentialTime;
		}
	}

	//private final static int LAN_SPEED = 1000000;
	//private static final int PACKET_LENGTH = 100;
	private final static int NODE_DISTANCE = 10;
	private final static double PROPAGATION_SPEED = 2.5E8;
	
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
 			HashSet<Integer> collisionsDetected = new HashSet<Integer>();
 			for (int i = 0; i < compNum; i++) {
 				Node n = des.new Node();
 				n.id = i;
 				nodes.add(n);
 			}
 			Random rand = new Random();
 			for (int currentTick = 0; currentTick < MAX_TICKS; currentTick++) {

 				// node x wants to send data to node y

 				for (int j = 0; j < nodes.size(); j++) {
 					Node currentNode = nodes.get(j);
 					if (currentNode.pktGenerationTime == 0) {
 						currentNode.generateNextPacketArrival(pktArrivalRate);		
 					}
 					if (currentTick == currentNode.pktGenerationTime) {
 						collisionsDetected.add(currentNode.id);		
 					}
 				}
 				
 				if (collisionsDetected.size() > 1) {
 					//calculate backoffs, abort
 				}
 				else if (collisionsDetected.size() == 1) {
 					//send packet
 					Packet p = des.new Packet();
 				}
 				//else no1 wants to send :(
 			}	
 		}
 		else {
 			debug("Not enough arguments! Enter DiscreteEventSimulator -usage");
 		}


	}

	public static void debug(String msg) {
		if(dbg)
		{
			System.out.println("DEBUG:" + msg);
		}
	}
}