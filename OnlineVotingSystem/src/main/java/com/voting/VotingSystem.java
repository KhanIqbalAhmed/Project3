package com.voting;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Scanner;

public class VotingSystem {
	private Database db = new Database();
	private User loggedInUser = null;

	public static void main(String[] args) {
		VotingSystem system = new VotingSystem();
		system.initialize();
		system.run();
	}

	public void initialize() {
		db.addCandidate(new Candidate(0, "Alice"));
		db.addCandidate(new Candidate(0, "Bob"));
	}

	public void run() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("\nMain Menu:");
			System.out.println("1. Register");
			System.out.println("2. Login");
			System.out.println("3. Vote");
			System.out.println("4. View Results");
			System.out.println("5. Exit");
			System.out.print("Select an option: ");
			int option = scanner.nextInt();

			switch (option) {
			case 1:
				register(scanner);
				break;
			case 2:
				login(scanner);
				break;
			case 3:
				vote(scanner);
				break;
			case 4:
				viewResults();
				break;
			case 5:
				System.exit(0);
				break;
			default:
				System.out.println("Invalid option. Try again.");
			}
		}
	}

	private void register(Scanner scanner) {
		System.out.println("\nRegister:");
		System.out.print("Enter username (or type 'back' to return to menu): ");
		String username = scanner.next();
		if (username.equalsIgnoreCase("back")) {
			return;
		}
		System.out.print("Enter password: ");
		String password = scanner.next();
		db.addUser(new User(0, username, password));
		System.out.println("Registration successful.");
	}

	private void login(Scanner scanner) {
		System.out.println("\nLogin:");
		System.out.print("Enter username (or type 'back' to return to menu): ");
		String username = scanner.next();
		if (username.equalsIgnoreCase("back")) {
			return;
		}
		System.out.print("Enter password: ");
		String password = scanner.next();
		User user = db.getUserByUsername(username);
		while (user == null || !user.getPassword().equals(password)) {
			System.out.println("Invalid credentials. Please try again.");
			System.out.print("Enter username: ");
			username = scanner.next();
			if (username.equalsIgnoreCase("back")) {
				return;
			}
			System.out.print("Enter password: ");
			password = scanner.next();
			user = db.getUserByUsername(username);
		}
		loggedInUser = user;
		System.out.println("Login successful.");
	}

	private void vote(Scanner scanner) {
		try {
			if (loggedInUser == null) {
				System.out.println("You must log in first.");
				return;
			}
			System.out.println("\nVote:");
			System.out.println("Candidates:");
			db.getCandidates().forEach((id, candidate) -> {
				System.out.println(id + ". " + candidate.getName());
			});
			System.out.print("Enter candidate name or id to vote (or type 'back' to return to menu): ");
			String input = scanner.next();
			if (input.equalsIgnoreCase("back")) {
				return;
			}
			try {
				int candidateId = Integer.parseInt(input);
				if (db.getCandidates().containsKey(candidateId)) {
					db.addVote(new Vote(0, loggedInUser.getId(), candidateId));
					System.out.println("Vote cast successfully.");
				} else {
					System.out.println("Invalid candidate ID.");
				}
			} catch (NumberFormatException e) {
				// If input is not a valid integer, try to find the candidate by name
				boolean foundCandidate = false;
				for (Candidate candidate : db.getCandidates().values()) {
					if (candidate.getName().equalsIgnoreCase(input)) {
						db.addVote(new Vote(0, loggedInUser.getId(), candidate.getId()));
						System.out.println("Vote cast successfully.");
						foundCandidate = true;
						break;
					}
				}
				if (!foundCandidate) {
					System.out.println("Invalid candidate name.");
				}
			}
		} catch (InputMismatchException e) {
			System.out.println("Invalid input format. Please enter a valid candidate name or ID.");
		} catch (NoSuchElementException e) {
			System.out.println("Input not found. Please provide a valid input.");
		} catch (Exception e) {
			System.out.println("An unexpected error occurred.");
			e.printStackTrace();
		}
	}

	private void viewResults() {
		System.out.println("\nView Results:");
		db.getVotes().forEach((id, vote) -> {
			Candidate candidate = db.getCandidates().get(vote.getCandidateId());
			System.out.println("Vote ID: " + id + ", Candidate: " + candidate.getName());
		});
	}
}
