import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class NewChartButton extends ChartButton{
	public NewChartButton(ChartDisplay chart){
		super("NEW CHART", chart);
		
		setBorder(MyBorders.borderWithPadding(0, 10, 10, 10, 10));
		
		this.addActionListener(new ActionListener() { 
			
			  public void actionPerformed(ActionEvent e) {
					setText("asdf");
					revalidate();
					repaint();
					
				    myChart.makeNewChart();
				  } 
				} );
		
		
	}

}
