package ChatPanel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JMenuBar;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Panel extends JPanel {

	private static final long serialVersionUID = 1L;

	Socket socket = null;
	BufferedReader bf = null;
	DataOutputStream os = null;
	Output t = null;
	String sender;
	String receiver;
	JTextArea TxtMessages;
	
	public Panel( Socket s, String sender, String receiver) {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Message", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(1, 2, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		
		JTextArea TxtMessage = new JTextArea();
		scrollPane.setViewportView(TxtMessage);
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(TxtMessage.getText().trim().length()==0) return;
				try {
					os.writeBytes(TxtMessage.getText());
					os.write(13);os.write(10);
					os.flush();
					TxtMessages.append("\n" +sender+": "+ TxtMessages.getText());
					TxtMessage.setText("");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		panel.add(btnNewButton);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		add(scrollPane_1, BorderLayout.CENTER);
		
	    TxtMessages = new JTextArea();
		scrollPane_1.setViewportView(TxtMessages);

		 socket = s;
		 this.sender=sender;
		 this.receiver=receiver;
		 
		 try {
			 bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			 os = new DataOutputStream(socket.getOutputStream());
			 t= new Output(s, TxtMessages, sender, receiver);
			 t.start();
		 } catch (Exception e) {
			
		}
	}
	public JTextArea getTxtMessages() {
	return	this.TxtMessages;
	}

}
