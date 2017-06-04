import java.awt.*;
import javax.swing.*;

public class SecondPage extends JFrame {
	public SecondPage(int period){
		super();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("thetaSeat");
        this.setSize(600, 700);
        this.setLocationRelativeTo(null);
        
        ChartDisplay chart = new ChartDisplay(period);
		
		JPanel top = new JPanel(new BorderLayout());
		top.add(chart, BorderLayout.CENTER);
		top.setBorder(MyBorders.borderWithPadding(10, 10, 10, 10));
		
		this.add(top, BorderLayout.NORTH);
		
		SwitchButton switcher = new SwitchButton(chart);
		SaveButton save = new SaveButton(chart);
		UndoButton undo = new UndoButton(chart);
		RedoButton redo = new RedoButton(chart);
		
		chart.setSwitchButton(switcher);
		chart.setSaveButton(save);
		chart.setUndoButton(undo);
		chart.setRedoButton(redo);
		
		JPanel middleRow = new JPanel(new GridLayout(1, 3));
		middleRow.add(switcher);
		middleRow.add(undo);
		middleRow.add(redo);
		middleRow.setBorder(MyBorders.borderWithPadding(0, 10, 10, 10, 10));
		
		this.add(middleRow, BorderLayout.CENTER);
		
		JPanel bottomRow = new JPanel(new BorderLayout());
		bottomRow.add(new BackButton(), BorderLayout.WEST);
		bottomRow.add(new NewChartButton(chart), BorderLayout.CENTER);
		bottomRow.add(save, BorderLayout.EAST);
		
		this.add(bottomRow, BorderLayout.SOUTH);
	}
}