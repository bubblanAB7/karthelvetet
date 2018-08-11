import javax.swing.*;
import java.io.*;

public class FileChooser extends JFileChooser{
	
	private String filePath;
	
	public FileChooser() {
		JFileChooser jfc = new JFileChooser(".");
		int svar = jfc.showOpenDialog(FileChooser.this);
		if (svar == JFileChooser.APPROVE_OPTION) {
			filePath = jfc.getSelectedFile().getAbsolutePath();
		}
	}
	
	public String getFilePath() {
		return filePath;
	}
	

}
