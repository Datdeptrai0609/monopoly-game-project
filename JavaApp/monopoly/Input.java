/**
 * This is a monopoly game for OOP Project
 * Author: Vo Anh Viet
*/
package monopoly;

import java.io.IOException;
import java.util.Scanner;

class Input {
	private final Scanner scanner;

	public Input() {
		scanner = new Scanner(System.in);
	}

  // Read string input
	public String inputString() {
		return scanner.nextLine();
	}

  // Check input is yes or no return boolean
	public boolean inputBool() {
		return inputDecision(new String[]{"Yes", "No"}) == 0;
	}

  // Check input is integer or not then parse to int
	public int inputInt() {
		while (true) {
			int val;
			try {
				val = Integer.parseInt(inputString());
			} catch (NumberFormatException e) {
				System.out.println("Please enter a valid integer.");
				continue;
			}
			return val;
		}
	}

  // Check yes/no for inputBool()
	public int inputDecision(String[] choices) {
		while (true) {
			String input = inputString();
			for (int i = 0; i < choices.length; i++) {
				if (input.equalsIgnoreCase(choices[i]) || input.equalsIgnoreCase(choices[i].substring(0, 1)))
					return i;
			}
			System.out.println("Please enter a valid decision.");
		}
	}

  // Player input their names and check if duplicated at the beginning of game
	public Player inputPlayer(Monopoly.State state) throws IOException {
    Player player = null;
		do {
			String name = inputString();
      if (state.players.size() == 0)
        return new Player(name);
			for (Player p : state.players) {
        if (name.equals(p.name())) {
          player = null;
          break;
        }
        player = new Player(name);
			}
			if (player == null)
				System.out.println("This name is chosen, please enter another name.");

		} while (player == null);

		return player;
	}
}
