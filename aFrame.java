import javax.swing.*;
import java.awt.*;

public class aFrame extends JFrame {
	JLabel Header;
	JTextField NameText;
	
	public aFrame()
	{
		super("Employee Record System");
		JPanel p = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		Header = new JLabel("Enter Employee Information");
		c.insets = new Insets(10,10,10,10);
		c.gridx = 300;
		c.gridy = 10;
		p.add(Header,c);
		this.getContentPane().add(p,BorderLayout.NORTH);
		
		JPanel a = new JPanel(new GridBagLayout());
		NameText = new JTextField();
		NameText.setText("Please Enter your name here");
		c.insets = new Insets(10,10,10,10);
		c.gridx = 100;
		c.gridy = 10;
		c.anchor = GridBagConstraints.NORTH;
		a.add(NameText,c);
		this.getContentPane().add(a);
	}
}
