import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class ElevatorFactory {
	public static void open() {
		JFileChooser inputChooser = new JFileChooser(".");
		int retval = JFileChooser.ERROR_OPTION;
		while(retval == JFileChooser.ERROR_OPTION) {
			retval = inputChooser.showOpenDialog(null);
			if (retval == JFileChooser.APPROVE_OPTION) {
				File file = inputChooser.getSelectedFile();
				try {
					build(new Scanner(file));
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null,
							"File not found" + file.getName(),
							"IO error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	/**
	 * This builds a building, elevator, and riders according
	 * to the specs in the README.OUTPUTFORMAT. For information
	 * about the input format and assumptions made see the
	 * implementation details in the README under the 
	 * ElevatorFactory section.
	 * @param s Scanner linked to the input file
	 * @return
	 */
	public static void build(Scanner s) {
		String[] parameters = s.nextLine().split(" ");
		int floors = Integer.parseInt(parameters[0]);
		int elevators = Integer.parseInt(parameters[1]);
		int riders = Integer.parseInt(parameters[2]);
		int capacity = Integer.parseInt(parameters[3]);
		SingleBuildingVersion2 sb = new SingleBuildingVersion2(floors, elevators);
		List<Rider> myRiders = new ArrayList<Rider>();
		List<Elevator> myElevators = new ArrayList<Elevator>();
		for(int i = 0; i < elevators; i++) {
			myElevators.add(new Elevator(sb));
		}
		while(s.hasNextLine()) {
			String[] query = s.nextLine().split(" ");
			int riderID = Integer.parseInt(query[0]);
			int startFloor = Integer.parseInt(query[1]);
			int endFloor = Integer.parseInt(query[2]);
			Rider r = new Rider(riderID, startFloor, endFloor, sb);
			r.start();
		}
		for(int i = 0; i < elevators; i++) {
			myElevators.get(i).start();
		}
	}

}
