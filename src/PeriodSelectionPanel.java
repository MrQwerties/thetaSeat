import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class PeriodSelectionPanel extends JPanel{
	private int periodNum;
	private PeriodsPanel master;
	
	private Color defaultColor;
	
	public PeriodSelectionPanel(int period, PeriodsPanel m){
		super(new BorderLayout());
		this.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.BLACK));
		periodNum = period;
		master = m;
		
		defaultColor = getBackground();
		
		JLabel periodLabel = new JLabel("Period " + periodNum);
		periodLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		periodLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		
		JButton deleteButton = new PeriodDeleteButton(this);
		
		this.add(periodLabel, BorderLayout.WEST);
		this.add(deleteButton, BorderLayout.EAST);
		
		addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
            	choosePeriod(periodNum);
            }
        });
	}
	
	public void highlight(){
		setBackground(Color.YELLOW);
	}
	
	public void unhighlight(){
		setBackground(defaultColor);
	}
	
	public void choosePeriod(int period){
		master.choosePeriod(period);
	}
	
	public void deleteSelf(){
		master.removePeriod(periodNum);
	}
}