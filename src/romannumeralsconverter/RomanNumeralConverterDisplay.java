package romannumeralsconverter;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

public class RomanNumeralConverterDisplay extends JFrame {

	private static final long serialVersionUID = -7274103337349508837L;
	private JPanel contentPane;
	private JButton clearButton;
	private JButton convertButton;
	private JTextField inputBox;
	private JTextField resultBox;
	private JLabel title;
	private JLabel titleBottom;
	
	private static final Font FONT = new Font("Times New Roman", Font.PLAIN, 20);
	private static final Font TITLE_FONT = new Font("Times New Roman", Font.PLAIN, 21);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RomanNumeralConverterDisplay frame = new RomanNumeralConverterDisplay();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RomanNumeralConverterDisplay() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(RomanNumeralConverterDisplay.class.getResource("/romannumeralsconverter/Icon.png")));
		setTitle("Roman Numerals Converter");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 400);
		contentPane = new JPanel();
		
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		convertButton = new JButton("Convert");
		convertButton.setFont(FONT);
		convertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				callRomanMethods();
			}
		});
		setButtonPos(convertButton);
		
		clearButton = new JButton("Clear");
		clearButton.setFont(FONT);
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resultBox.setText("Result here...");
				resultBox.setForeground(Color.LIGHT_GRAY);
				inputBox.setText("Type here...");
				inputBox.setForeground(Color.LIGHT_GRAY);
			}
		});
		setButtonPos(clearButton);
		
		addComponentListener((new ComponentAdapter() { 
			public void componentResized(ComponentEvent e) {
				resetPositions();
			}
		}));
		
		resultBox = new JTextField("Result here...");
		resultBox.setFont(FONT);
		resultBox.setForeground(Color.LIGHT_GRAY);
		resultBox.setEditable(false);
		resultBox.setBackground(Color.WHITE);
		setTextBoxPos(resultBox);
		
		inputBox = new JTextField("Type here...");
		inputBox.setFont(FONT);
		inputBox.setForeground(Color.LIGHT_GRAY);
		setTextBoxPos(inputBox);
		inputBox.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				inputBox.setText("");
				inputBox.setForeground(Color.BLACK);
			}
			
			public void focusLost(FocusEvent e) {
				if(inputBox.getText().equals("")) {
					inputBox.setText("Type here...");
					inputBox.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		
		title = new JLabel("Roman Numerals");
		title.setFont(TITLE_FONT);
		titleBottom = new JLabel("Converter");
		titleBottom.setFont(TITLE_FONT);
		setTitlePos();
		
		contentPane.add(convertButton);
		contentPane.add(clearButton);
		contentPane.add(resultBox);
		contentPane.add(inputBox);
		contentPane.add(title);
		contentPane.add(titleBottom);
	}

	private void callRomanMethods() {
		try {
			int entry = Integer.parseInt(inputBox.getText());
			String result = RomanNumeralConverter.convertToRomanNumeral(entry);
			
			if(result.equals("-3")) {
				if(!inputBox.getForeground().equals(Color.LIGHT_GRAY)) {
					inputBox.setForeground(Color.RED);
				}
				resultBox.setForeground(Color.LIGHT_GRAY);
				resultBox.setText("Result here...");
				return;
			} else {
				resultBox.setText(result);
			}
		} catch (Exception exception) {
			long result = RomanNumeralConverter.convertToNumber(inputBox.getText());
			
			if(result < 0) {
				if(!inputBox.getForeground().equals(Color.LIGHT_GRAY)) {
					inputBox.setForeground(Color.RED);
				}
				resultBox.setForeground(Color.LIGHT_GRAY);
				resultBox.setText("Result here...");
				return;
			} else {
				resultBox.setText(Long.toString(result));
			}
		}
		resultBox.setForeground(Color.BLACK);
	}
	
	private void setTextBoxPos(JTextField textField) {
		if(textField.equals(resultBox)) {
			textField.setBounds(clearButton.getWidth() / 2,
					getHeight() - clearButton.getHeight() * 2 - textField.getHeight() - 30,
				    clearButton.getWidth() + 10,
				    clearButton.getHeight() / 2);
		} else {
			textField.setBounds(clearButton.getWidth() / 2,
					resultBox.getY() - resultBox.getHeight() - 10,
					resultBox.getWidth(),
					resultBox.getHeight());
		}
	}
	
	private void setButtonPos(JButton button) {
		button.setSize(getWidth() / 2 - 20,
							  getHeight() / 6);
		
		if(button.equals(convertButton)) {
			button.setBounds(10,
							getHeight() - (int) (button.getHeight() * 1.5) - 20,
							button.getWidth(),
							button.getHeight());
		} else {
			button.setBounds(getWidth() - (int) (button.getWidth()) - 30,
							getHeight() - (int) (button.getHeight() * 1.5) - 20,
							button.getWidth(),
							button.getHeight());
		}
	}
	
	private void setTitlePos() {
		titleBottom.setBounds(getWidth() / 2 - 50,
				title.getY() + title.getHeight() - 10,
				100,
				35);
		
		title.setBounds(titleBottom.getX() - 30,
				10,
				150,
				35);
	}

	private void resetPositions() {
		setButtonPos(convertButton);
		setButtonPos(clearButton);
		setTextBoxPos(resultBox);
		setTextBoxPos(inputBox);
		setTitlePos();
	}
}