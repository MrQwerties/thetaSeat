import java.awt.*;
import javax.swing.*;

public abstract class ChartButton extends JButton {
	protected ChartDisplay myChart;
	
	public ChartButton(String text, ChartDisplay chart){
		super(text);
		myChart = chart;
	}
}
