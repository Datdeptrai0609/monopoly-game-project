package monopoly;

import java.io.IOException;
import java.util.Scanner;

class Input {
	private final Scanner scanner;

	public Input() {
		scanner = new Scanner(System.in);
	}

	public String inputString() {
		return scanner.nextLine();
	}

	public boolean inputBool() {
		return inputDecision(new String[]{"Yes", "No"}) == 0;
	}

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
