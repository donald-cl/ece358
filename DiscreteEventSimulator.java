public class DiscreteEventSimulator {

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
			System.out.println("wtf");
 		}
 		else if (args.length == 5) {
 			int compNum = Integer.parseInt(args[0]);
 			int pktArrivalRate = Integer.parseInt(args[1]);
 			int lanSpeed = Integer.parseInt(args[2]);
 			int pktLength = Integer.parseInt(args[3]);
 			int persistanceParam = Integer.parseInt(args[4]);

 		}
 		else {
 			debug("Not enough arguments! Enter DiscreteEventSimulator -usage");
 		}


	}

	public static void debug(String msg) {
		System.out.println(msg);
	}
}