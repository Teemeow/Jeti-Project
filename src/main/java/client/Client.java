package client;

import common.Message;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Client {
    private String address;
    private int port;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Acceuil view;

    public Acceuil getView() {
        return view;
    }

    public void setView(Acceuil view) {
        this.view = view;
    }

    public Client(String address, int port, Acceuil view){
        this.address = address;
        this.port = port;
        this.view = view;
        try{
            this.socket = new Socket(address, port);
            out = new ObjectOutputStream(socket.getOutputStream());
        }catch(Exception e){
            System.out.println(e);
        }

        Thread threadClientSend = new Thread(new ClientSend(this.out,this.socket));
        threadClientSend.start();

        Thread threadClientReceive = new Thread(new ClientReceive(this, this.socket));
        threadClientReceive.start();

    }

    public void disconnectedServer(){
        try{
            this.out.close();
            this.socket.close();
            if (this.in != null){
                this.in.close();
            }
            System.exit(0);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public Message messageReceived(Message message){
        System.out.println(message);
        view.printNewMessage(message);
        return message;
    }

    public void sendMessage(Message mess){
        try{
            this.out.writeObject(mess);
            this.out.flush();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
