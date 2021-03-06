import java.awt.*;
import javax.swing.*;

public class SecondPage extends JFrame {
	public SecondPage(int period){
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
        setTitle("thetaSeat");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setBackground(Constants.BACKGROUND_COLOR);
        
        ChartDisplay chart = new ChartDisplay(period);
		
		JPanel top = new JPanel(new BorderLayout());
		top.add(chart, BorderLayout.CENTER);
		top.setBorder(MyBorders.borderWithPadding(10, 10, 10, 10));
		top.setBackground(Constants.BACKGROUND_COLOR);
		
		add(top, BorderLayout.NORTH);
		
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
		middleRow.setBackground(Constants.BACKGROUND_COLOR);
		
		this.add(middleRow, BorderLayout.CENTER);
		
		JPanel bottomRow = new JPanel(new BorderLayout());
		bottomRow.add(new BackButton(), BorderLayout.WEST);
		bottomRow.add(new NewChartButton(chart), BorderLayout.CENTER);
		bottomRow.add(save, BorderLayout.EAST);
		bottomRow.setBorder(MyBorders.borderWithPadding(0, 10, 10, 10, 10));
		bottomRow.setBackground(Constants.BACKGROUND_COLOR);
		
		this.add(bottomRow, BorderLayout.SOUTH);
	}
}