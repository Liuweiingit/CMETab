package org.wit.rpt.ui;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class ShowImg extends JDialog{
//	protected Shell shell;	
//	public static Display myDisplay;    
//    public static boolean internalCall = false; 
//    
//    /**  
//     * Open the window.  
//     */    
//    public void open() {    
//        Display display = Display.getDefault(); 
//        createContents(myDisplay);    
//        Image img = new Image(display, "E:/CapabilityModel/out.gif");    
//        shell.open();
//        center(display,shell);
//        GC gc = new GC(shell);   
//        gc.drawImage(img, 0, 0);    
//        shell.layout();  
//        while (!shell.isDisposed()) {    
//            if (!display.readAndDispatch()) {    
//                display.sleep();    
//            }    
//        }    
//        img.dispose();    
//        if (internalCall) display.dispose();    
//    }    
//    
//    /**  
//     * Create contents of the window.  
//     */    
//    protected void createContents(Display display) {    
//        myDisplay = display;    
//        shell = new Shell();  
//        shell.setSize(900, 400);    
//        shell.setText("Show Image");  
//    }
//    public static void center(Display display, Shell shell)
//    {
//        Rectangle bounds = display.getPrimaryMonitor().getBounds();
//        Rectangle rect = shell.getBounds();
//        int x = bounds.x + (bounds.width - rect.width) / 2;
//        int y = bounds.y + (bounds.height - rect.height) / 2;
//        shell.setLocation(x, y);
//    }
//    
	
	
	public ShowImg(){ 
		JLabel imgLabel=null;
		try {
			imgLabel = new JLabel(new ImageIcon(ImageIO.read(new File("E:/CapabilityModel/out.gif"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setTitle("ShowImage");
		JPanel cp = (JPanel) this.getContentPane();  
		JPanel imgPanel = new JPanel(); 
		imgPanel.add(imgLabel); 
	
		cp.add(imgPanel, BorderLayout.CENTER);
		this.setSize(950,320); 
		this.setVisible(true); 
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); 
} 

    
    
}
