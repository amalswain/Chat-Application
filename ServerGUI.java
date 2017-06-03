package com.chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class ServerGUI extends JFrame implements ActionListener,WindowListener{
	
	private static final long serialVersionUID= 1L;
	private JButton stopStart;
	private JTextArea chat,event;
	private JTextField tPortNumber;
	private Server server;
	
	ServerGUI(int port){
		super("Chat Server");
		server= null;
		
		JPanel north = new JPanel();
		north.add(new JLabel("Port number: "));
		tPortNumber = new JTextField(" "+port);
		stopStart= new JButton("Start");
		stopStart.addActionListener(this);
		north.add(stopStart);
		add(north,BorderLayout.NORTH);
		
		JPanel center= new JPanel(new GridLayout(2,1));
		chat= new JTextArea(80,80);
		chat.setEditable(false);
		appendRoom("Chat Room. /n");
		center.add(new JScrollPane(chat));
		event= new JTextArea(80,80);
		event.setEditable(false);
		appendEvent("Event Lof./n");
		center.add(new JScrollPane(event));
		add(center);
		
		addWindowListener(this);
		setSize(400,600);
		setVisible(true);
		
	}
	
	void appendRoom(String str){
		chat.append(str);
		chat.setCaretPosition(chat.getText().length()-1);
	}
	
	void appendEvent(String str){
		event.append(str);
		event.setCaretPosition(chat.getText().length()-1);
	}
	
	public void actionPerformed(ActionEvent ae){
		if(server != null){
			server.stop();
			server= null;
			tPortNumber.setEditable(false);
			stopStart.setText("Start");
			return;
			
		}
		
		int port;
		try{
			port= Integer.parseInt(tPortNumber.getText().trim());
			
		}catch(Exception ee){
			appendEvent("invalid port number");
			return;
		}
		server = new Server(port, this);
		new ServerRunning().start();
		stopStart.setText("Stop");
		tPortNumber.setEditable(false);
	}
	
	public static void main(String [] args){
		new ServerGUI(1500);
	}
	
	public void windowClosing(WindowEvent e){
		if(server != null){
			try{
				server.stop();
			}catch(Exception ee){
				
			}
			server= null;
		}
		dispose();
		System.exit(0);
	}
	
	public void windowClosed(WindowEvent e){}
	public void windowOpened(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
	
  class ServerRunning extends Thread{
	  public void run(){
		  server.start();
		  stopStart.setText("Start");
		  tPortNumber.setEditable(true);
		  appendEvent("Server Crashed");
		  server= null;
	  }
  }
}
