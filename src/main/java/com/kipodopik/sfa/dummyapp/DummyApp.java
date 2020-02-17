package com.kipodopik.sfa.dummyapp;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JComboBox;

/**
 * This class contains a simple Swing application <br/>
 * With two combo boxes: one with English strings and on with Hebrew strings
 */
public class DummyApp {

	private JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					long startTime = System.currentTimeMillis();
					DummyApp window = new DummyApp();
					window.frame.setVisible(true);
					System.out.println("Loading time: " + (System.currentTimeMillis() - startTime) + " ms");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public DummyApp() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		JComboBox<String> comboBox1 = new JComboBox<>();
		panel.add(comboBox1);
		
		JComboBox<String> comboBox2 = new JComboBox<>();
		panel.add(comboBox2);
		
		for (int i = 0; i < 10_000 ; i++) {
			comboBox1.addItem("item " + i);
			comboBox2.addItem("פריט " + i);
		}
	}

}
