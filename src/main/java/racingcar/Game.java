package racingcar;

import static camp.nextstep.edu.missionutils.Console.readLine;
import static camp.nextstep.edu.missionutils.Randoms.pickNumberInRange;

import java.util.ArrayList;

class Game {
	private static String[] userArray;
	private static int count;
	private static int[] moveCount;
	private static ArrayList<Integer> maxIndices = new ArrayList<>();

	Game() {
		this.setGameUsers();
		this.setGameCount();
	}

	private void checkUsername() {
		for (int i = 0; i < userArray.length; i++) {
			if (userArray[i].length() > 5) {
				throw new IllegalArgumentException();
			} else {
				userArray[i] = userArray[i].replaceAll(" ", ""); // 공백제거
			}
		}
	}

	private String[] setGameUsers() {
		System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
		String userInput = readLine();

		userArray = userInput.split(","); // 사용자 등록
		this.checkUsername(); // 유효성 검사

		return userArray;
	}

	private int intValidator(String userInput) {
		try {
			return Integer.parseInt(userInput);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException();
		}

	}

	private int setGameCount() {
		System.out.println("시도할 회수는 몇회인가요?");
		String userInput = readLine();

		count = this.intValidator(userInput);// 이동할 회수 등록

		return count;
	}

	private void forwardOrStop() {
		String forward = "-";
		moveCount = new int[userArray.length];

		System.out.printf("%n실행 결과%n");
		for (int i = 0; i < count; i++) { // 횟수만큼 반복하여 사용자의 전진상태 출력
			for (int k = 0; k < userArray.length; k++) {
				int randomNumber = pickNumberInRange(0, 9);

				if (randomNumber >= 4) {
					moveCount[k]++;
				}

				System.out.printf("%s : %s %n", userArray[k], forward.repeat(moveCount[k]));
			}
			System.out.println("");
		}
	}

	private void findWinnerIndex() {
		int max = moveCount[0];

		maxIndices.add(0);

		for (int i = 1; i < moveCount.length; i++) {
			if (moveCount[i] > max) {
				max = moveCount[i];
				maxIndices.clear();
				maxIndices.add(i);
			} else if (moveCount[i] == max) {
				maxIndices.add(i);
			}
		}

	}

	private String pickWinner() {
		String winner = "";

		this.findWinnerIndex();
		
		for (int i = 0; i < maxIndices.size(); i++) {
			if (i == maxIndices.size() - 1) {
				winner += userArray[maxIndices.get(i)];
			} else {
				winner += userArray[maxIndices.get(i)] + ", ";
			}
		}

		return winner;
	}

	public void processGame() {
		this.forwardOrStop();
		System.out.print("최종 우승자 : " + this.pickWinner());
	}

}