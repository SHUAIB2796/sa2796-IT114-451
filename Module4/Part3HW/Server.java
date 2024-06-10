package Module4.Part3HW;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private int port = 3000;
    private final ConcurrentHashMap<Long, ServerThread> connectedClients = new ConcurrentHashMap<>();
    private boolean isRunning = true;

    // Game state variables
    private boolean gameActive = false;
    private int hiddenNumber = 0;
    private Random random = new Random();

    private void start(int port) {
        this.port = port;
        System.out.println("Listening on port " + this.port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (isRunning) {
                System.out.println("Waiting for next client");
                Socket incomingClient = serverSocket.accept();
                System.out.println("Client connected");
                ServerThread sClient = new ServerThread(incomingClient, this, this::onClientInitialized);
                sClient.start();
            }
        } catch (IOException e) {
            System.err.println("Error accepting connection");
            e.printStackTrace();
        } finally {
            System.out.println("Closing server socket");
        }
    }

    private void onClientInitialized(ServerThread sClient) {
        connectedClients.put(sClient.getClientId(), sClient);
        relay(String.format("*User[%s] connected*", sClient.getClientId()), null);
    }

    protected synchronized void disconnect(ServerThread client) {
        long id = client.getClientId();
        client.disconnect();
        connectedClients.remove(id);
        relay("User[" + id + "] disconnected", null);
    }

    protected synchronized void relay(String message, ServerThread sender) {
        if (sender != null && processCommand(message, sender)) {
            return;
        }

        String senderString = sender == null ? "Server" : String.format("User[%s]", sender.getClientId());
        final String formattedMessage = String.format("%s: %s", senderString, message);

        connectedClients.values().removeIf(client -> {
            boolean failedToSend = !client.send(formattedMessage);
            if (failedToSend) {
                System.out.println(String.format("Removing disconnected client[%s] from list", client.getClientId()));
                disconnect(client);
            }
            return failedToSend;
        });
    }

    private boolean processCommand(String message, ServerThread sender) {
        if (sender == null) {
            return false;
        }
        System.out.println("Checking command: " + message);

        if ("/disconnect".equalsIgnoreCase(message)) {
            ServerThread removedClient = connectedClients.get(sender.getClientId());
            if (removedClient != null) {
                disconnect(removedClient);
            }
            return true;

        } else if ("/start".equalsIgnoreCase(message)) {
            startGame();
            relay("The game has started! Start guessing!", null);
            return true;

        } else if ("/stop".equalsIgnoreCase(message)) {
            stopGame();
            relay("The game has been stopped.", null);
            return true;

        } else if (message.startsWith("/guess ")) {
            if (gameActive) {
                try {
                    int guess = Integer.parseInt(message.split(" ")[1]);
                    handleGuess(sender, guess);
                } catch (NumberFormatException e) {
                    sender.send("Wrong guess. Please type a number.");
                }

            } else {
                sender.send("Game not active. Please start a new game to guess.");
            }

            return true;
        }
        else if ("/flip".equalsIgnoreCase(message) || "/toss".equalsIgnoreCase(message) || "/coin".equalsIgnoreCase(message)) {     //UCID: sa2796, Date: 6-10-24
            handleCoinToss(sender);
            return true;
        }


        return false;
    }

    private void startGame() {
        hiddenNumber = random.nextInt(100) + 1;
        gameActive = true;
    }

    private void stopGame() {
        gameActive = false;
        hiddenNumber = 0;
    }

    private void handleGuess(ServerThread sender, int guess) {
        if (guess == hiddenNumber) {
            relay(String.format("Player[%s] guessed %d and it was correct!", sender.getClientId(), guess), null);
            stopGame();
        } else {
            relay(String.format("Player[%s] guessed %d but it was incorrect.", sender.getClientId(), guess), null);
        }
    }

    private void handleCoinToss(ServerThread sender) {
        String result = random.nextBoolean() ? "heads" : "tails";
        relay(String.format("User[%s] flipped a coin and got %s.", sender.getClientId(), result), null);
}

    public static void main(String[] args) {
        System.out.println("Server Starting");
        Server server = new Server();
        int port = 3000;
        try {
            port = Integer.parseInt(args[0]);
        } catch (Exception e) {

        }
        server.start(port);
        System.out.println("Server Stopped");
    }
}
