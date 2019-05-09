package Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.lang.Runnable;
import java.lang.Thread;


public abstract class NetworkConnectionServer{
    private ConnThread connthread = new ConnThread();
    private Consumer<Serializable> callback;
    public ArrayList<ClientThread> clientList = new ArrayList<ClientThread>();
    int i = 0;


    public NetworkConnectionServer(Consumer<Serializable> callback) {
        this.callback = callback;
        connthread.setDaemon(true);

    }


    public void startConn() throws Exception{
        connthread.start();

    }

    public void send(Serializable data) throws Exception{
        connthread.out.writeObject(data);

    }

    public void closeConn() throws Exception{
        connthread.socket.close();

    }

    abstract protected boolean isServer();
    abstract protected String getIP();
    abstract protected int getPort();

    class ConnThread extends Thread {
        private Socket socket;
        private ObjectOutputStream out;


        public void run() {
            try {
                ServerSocket server = new ServerSocket(getPort());

                while(true) {
                    ClientThread t1 = new ClientThread(server.accept());
                    clientList.add(t1);
                    t1.start();
                    i++;
                }


            } catch (Exception e) {
                callback.accept("connection Closed");
            }
        }
    }

    class ClientThread extends Thread {
        private Socket socket;
        private ObjectOutputStream out;

        private String client;
        private String clients;
        private String quit;
        private int score = 0;
        private int id;


        public ClientThread(Socket s) {
            this.socket = s;
        }

        public void setClient(String client) {
            this.client = client;
        }

        public String getClient() {
            return client;
        }

        public void setOutputStream(ObjectOutputStream o2) {
            this.out = o2;
        }

        public ObjectOutputStream getOutputStream() {
            return out;
        }

        public void setScore() {
            score++;
        }

        public int getScore() {
            return score;
        }

        public void resetScore() {
            score = 0;
        }
        //**************************************************
        public String openMessage() {
            String s = "You are Client " + i + "!";
            return s;
        }

        public String clientMessage() {
            String s = "There are " + clientList.size() + " clients connected.";
            return s;
        }

        public void setId() {
            this.id = i;
        }

        public int getid() {
            return id;
        }

        public void run() {

            try {
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                setOutputStream(out);
                setId();
                out.writeObject("Welcome to Java Against Humanity!");

                out.writeObject(openMessage()); // NEW ADDITION

                if (i == 1 || i == 2 || i == 3) {
                    out.writeObject("Waiting on the other Clients to Connect. Please be Patient.");

                }
                if (i == 4) {
                    for (int k = 0; k < clientList.size(); k++) {
                        clientList.get(k).getOutputStream().writeObject("You may now begin the game!!!");
                        clientList.get(k).setClient(" ");
                    } // NEW ADDITION
                }
                for (int k = 0; k < clientList.size(); k++) {
                    clientList.get(k).getOutputStream().writeObject("These are the clients that are currently " +
                            "connected to the server.");
                    clientList.get(k).getOutputStream().writeObject("Clients:");

                    for (int x = 0; x < i; x++) {
                        clients = " " + (x + 1);
                        clientList.get(k).getOutputStream().writeObject(clients);
                    }

                } // NEW ADDITION

                while (true) {
                    Serializable data = (Serializable) in.readObject();
                    callback.accept(data);
                    setClient(data.toString().intern());

                    if(clientList.get(0).getClient() == "Client 2" || clientList.get(0).getClient() == "Client 3"
                            || clientList.get(0).getClient() == "Client 4" || clientList.get(1).getClient() == "Client 1" ||
                            clientList.get(1).getClient() == "Client 3" || clientList.get(1).getClient() == "Client 4" ||
                            clientList.get(2).getClient() == "Client 1" || clientList.get(2).getClient() == "Client 2"
                            || clientList.get(2).getClient() == "Client 4" || clientList.get(3).getClient() == "Client 1" ||
                            clientList.get(3).getClient() == "Client 2" || clientList.get(3).getClient() == "Client 3") {

                        if(clientList.get(1).getClient() == "Client 1" || clientList.get(2).getClient() == "Client 1" ||
                                clientList.get(3).getClient() == "Client 1"  ) {
                            for (int k = 0; k < clientList.size(); k++) {
                                clientList.get(k).getOutputStream().writeObject("Client 1 will ask the next question.");
                                clientList.get(k).setClient(" ");
                            }
                        } else if(clientList.get(0).getClient() == "Client 2" || clientList.get(2).getClient() == "Client 2" ||
                                clientList.get(3).getClient() == "Client 2" ) {
                            for (int k = 0; k < clientList.size(); k++) {
                                clientList.get(k).getOutputStream().writeObject("Client 2 will ask the next question.");
                                clientList.get(k).setClient(" ");
                            }
                        } else if(clientList.get(0).getClient() == "Client 3" || clientList.get(1).getClient() == "Client 3" ||
                                clientList.get(3).getClient() == "Client 3" ) {
                            for (int k = 0; k < clientList.size(); k++) {
                                clientList.get(k).getOutputStream().writeObject("Client 3 will ask the next question.");
                                clientList.get(k).setClient(" ");
                            }
                        } else if(clientList.get(0).getClient() == "Client 4" || clientList.get(1).getClient() == "Client 4" ||
                                clientList.get(2).getClient() == "Client 4" ) {
                            for (int k = 0; k < clientList.size(); k++) {
                                clientList.get(k).getOutputStream().writeObject("Client 4 will ask the next question.");
                                clientList.get(k).setClient(" ");
                            }
                        }
                    } else if(clientList.get(0).getClient() != " " || clientList.get(1).getClient() != " "
                            || clientList.get(2).getClient() != " " || clientList.get(3).getClient() != " ") {
                            for (int k = 0; k < clientList.size(); k++) {
                                clientList.get(k).getOutputStream().writeObject("Client 1");
                                clientList.get(k).getOutputStream().writeObject(clientList.get(0).getClient());
                                clientList.get(k).getOutputStream().writeObject("Client 2");
                                clientList.get(k).getOutputStream().writeObject(clientList.get(1).getClient());
                                clientList.get(k).getOutputStream().writeObject("Client 3");
                                clientList.get(k).getOutputStream().writeObject(clientList.get(2).getClient());
                                clientList.get(k).getOutputStream().writeObject("Client 4");
                                clientList.get(k).getOutputStream().writeObject(clientList.get(3).getClient());
                            }
                        if(clientList.get(0).getClient() != " " && clientList.get(1).getClient() != " "
                                && clientList.get(2).getClient() != " " && clientList.get(3).getClient() != " ") {
                            for (int k = 0; k < clientList.size(); k++) {
                                clientList.get(k).getOutputStream().writeObject("Please choose the next client.");
                                clientList.get(k).setClient(" ");
                            }
                        }

                    }
                }
            } catch (Exception e) {
                callback.accept("Connection closed");
              }
        }
    }
}




