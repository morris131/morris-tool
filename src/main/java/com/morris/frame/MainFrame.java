package com.morris.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import net.sf.json.JSONObject;

import com.morris.util.json.Json2Bean;
import com.morris.util.json.Json2Tree;
import com.morris.util.json.JsonUtil;

import javax.swing.JList;
import javax.swing.JEditorPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JCheckBox;

public class MainFrame extends JFrame {

	private JTextArea textArea;
	private JsonUtil jsonUtil;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField packageNameTxt;
	private JTextField classNameTxt;
	private JTextField savePathTxt;
	private JTextArea jsonArea;
	private JLabel messageLabel;
	private JLabel msgLabel;
	private JTree tree;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
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
	public MainFrame() {
		setTitle("Morris Tool");

		jsonUtil = new JsonUtil();
		setSize(941, 655);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int x = (Toolkit.getDefaultToolkit().getScreenSize().width - getWidth())/2;
		int y = (Toolkit.getDefaultToolkit().getScreenSize().height - getHeight())/2;
		setLocation(x, y);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		JPanel textPanel = new JPanel();
		textPanel.setToolTipText("Text");
		tabbedPane.addTab("Text", null, textPanel, null);
		textPanel.setLayout(null);

		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(0, 0, 827, 29);
		textPanel.add(toolBar);

		JButton pasteBtn = new JButton("Paste");
		pasteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// 从剪切板粘贴字符串
				Transferable t = Toolkit.getDefaultToolkit()
						.getSystemClipboard().getContents(null);

				try {
					if (null != t
							&& t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
						String text = (String) t
								.getTransferData(DataFlavor.stringFlavor);
						textArea.setText(text);
					}
				} catch (UnsupportedFlavorException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		pasteBtn.setToolTipText("\u7C98\u8D34");
		toolBar.add(pasteBtn);

		JButton copyBtn = new JButton("Copy");
		copyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// 设置剪切板
				StringSelection ss = new StringSelection(textArea.getText());
				Toolkit.getDefaultToolkit().getSystemClipboard()
						.setContents(ss, null);
			}
		});
		copyBtn.setToolTipText("\u590D\u5236");
		toolBar.add(copyBtn);

		JButton validateBtn = new JButton("Validate");
		validateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// 校验
				String validate = jsonUtil.validate(textArea.getText());
				JSONObject jsonObject = JSONObject.fromObject(validate);
				String msg = jsonObject.getString("msg");
				msgLabel.setText(msg);
				
			}
		});
		validateBtn.setToolTipText("\u6821\u9A8C");
		toolBar.add(validateBtn);

		JButton formatBtn = new JButton("Format");
		formatBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String formatJson = jsonUtil.formatJson(textArea.getText(), "    ");
				JSONObject jsonObject = JSONObject.fromObject(formatJson);
				String result = "";
				if ("1".equals(jsonObject.getString("code"))) {
					result = jsonObject.getString("msg");
					textArea.setText(jsonObject.getString("remark"));
				} else {
					result = jsonObject.getString("msg");
				}
				msgLabel.setText(result);
			}
		});
		formatBtn.setToolTipText("\u683C\u5F0F\u5316");
		toolBar.add(formatBtn);

		JButton compressBtn = new JButton("Compress");
		compressBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// 压缩json
				String compressionJson = jsonUtil.compressionJson(textArea.getText());
				textArea.setText(compressionJson);
			}
		});
		compressBtn.setToolTipText("\u538B\u7F29");
		toolBar.add(compressBtn);

		JButton escapeBtn = new JButton("Escape");
		escapeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 转义
				String escapeJson = jsonUtil.escapeJson(textArea.getText());
				textArea.setText(escapeJson);
			}
		});
		escapeBtn.setToolTipText("\u8F6C\u4E49");
		toolBar.add(escapeBtn);

		JButton removeEscapeBtn = new JButton("Remove Escape");
		removeEscapeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 去除转义
				String removeEscapeJson = jsonUtil.removeEscapeJson(textArea.getText());
				textArea.setText(removeEscapeJson);
			}
		});
		removeEscapeBtn.setToolTipText("\u53BB\u9664\u8F6C\u4E49");
		toolBar.add(removeEscapeBtn);

		JButton unicode2ChineseBtn = new JButton("Unicode2Chinese");
		unicode2ChineseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Unicode转中文
				String unicode2Chinese = jsonUtil.unicode2Chinese(textArea.getText());
				textArea.setText(unicode2Chinese);
			}
		});
		unicode2ChineseBtn.setToolTipText("Unicode\u8F6C\u4E2D\u6587");
		toolBar.add(unicode2ChineseBtn);

		JButton chinese2UnicodeBtn = new JButton("Chinese2Unicode");
		chinese2UnicodeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 中文转unicode
				String chinese2Unicode = jsonUtil.chinese2Unicode(textArea.getText());
				textArea.setText(chinese2Unicode);
			}
		});
		chinese2UnicodeBtn.setToolTipText("\u4E2D\u6587\u8F6CUnicode");
		toolBar.add(chinese2UnicodeBtn);

		JButton cn2enBtn = new JButton("CN2EN");
		cn2enBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 中文标点字符转英文标点字符
				String cnChar2EnChar = jsonUtil.cnChar2EnChar(textArea.getText());
				textArea.setText(cnChar2EnChar);
			}
		});
		cn2enBtn.setToolTipText("\u4E2D\u6587\u7B26\u53F7\u8F6C\u82F1\u6587\u7B26\u53F7");
		toolBar.add(cn2enBtn);
		
		
		textArea = new JTextArea();
		textArea.setBounds(0, 29, 752, 369);
		textArea.setLineWrap(true);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(textArea);
		scrollPane.setBounds(0, 29, 918, 531);
		textPanel.add(scrollPane);
		
		msgLabel = new JLabel("Welcome");
		msgLabel.setBounds(10, 567, 646, 15);
		textPanel.add(msgLabel);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Tree", null, panel_1, null);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		tree = new JTree();
		panel_1.add(tree);
		
		tabbedPane.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				
				if (((JTabbedPane) e.getSource()).getSelectedIndex() == 1) {
					
					String validate = jsonUtil.validate(textArea.getText());
					JSONObject jsonObject = JSONObject.fromObject(validate);
					int code = jsonObject.getInt("code");
					// 如果json格式正确才在显示tree
					if(1 == code){
						Json2Tree json2Tree = new Json2Tree();
						
						jsonObject = JSONObject.fromObject(textArea.getText());
						DefaultMutableTreeNode root = json2Tree.root(jsonObject , new DefaultMutableTreeNode());
						tree.setModel(new DefaultTreeModel(root));
					}		
			    }
			}
		});
		
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent paramTreeSelectionEvent) {
				
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				
				//Object source = paramTreeSelectionEvent.getSource();
//				System.out.println(node);
			}
		});
		
		JPanel panel = new JPanel();
		panel.setToolTipText("Generate POJO");
		tabbedPane.addTab("Generate POJO", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Package Name");
		lblNewLabel.setToolTipText("\u751F\u6210\u5B9E\u4F53\u7684\u5305\u540D");
		lblNewLabel.setBounds(27, 15, 90, 15);
		panel.add(lblNewLabel);
		
		packageNameTxt = new JTextField();
		packageNameTxt.setBounds(127, 12, 149, 21);
		panel.add(packageNameTxt);
		packageNameTxt.setColumns(10);
		
		JLabel lblClassName = new JLabel("Class Name");
		lblClassName.setToolTipText("\u751F\u6210\u5B9E\u4F53\u7684\u7C7B\u540D");
		lblClassName.setBounds(27, 62, 90, 15);
		panel.add(lblClassName);
		
		classNameTxt = new JTextField();
		classNameTxt.setColumns(10);
		classNameTxt.setBounds(127, 59, 149, 21);
		panel.add(classNameTxt);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(0, 125, 918, 436);
		panel.add(scrollPane_2);
		
		jsonArea = new JTextArea();
		scrollPane_2.setViewportView(jsonArea);
		
		messageLabel = new JLabel("Welcome");
		messageLabel.setBounds(10, 567, 658, 15);
		panel.add(messageLabel);
		
		JLabel lblSavePath = new JLabel("Save Path");
		lblSavePath.setToolTipText("\u751F\u6210\u5B9E\u4F53\u7684\u4FDD\u5B58\u8DEF\u5F84");
		lblSavePath.setBounds(344, 18, 90, 15);
		panel.add(lblSavePath);
		
		savePathTxt = new JTextField();
		savePathTxt.setEditable(false);
		savePathTxt.setColumns(10);
		savePathTxt.setBounds(444, 15, 194, 21);
		panel.add(savePathTxt);
		
		JButton btnNewButton = new JButton("Open");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.setDialogTitle("Please choose a save location");
				fileChooser.showSaveDialog(null);
				if(null != fileChooser.getSelectedFile()){
					String path = fileChooser.getSelectedFile().getPath();
					savePathTxt.setText(path);
				}
				
			}
		});
		btnNewButton.setBounds(648, 10, 95, 25);
		panel.add(btnNewButton);
		
		JButton btnGenerate = new JButton("Generate");
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String packageName = packageNameTxt.getText();
				String className = classNameTxt.getText();
				String filePath = savePathTxt.getText();
				Json2Bean json2Bean = new Json2Bean(packageName, className);
				Map<String, String> map = json2Bean.create(jsonArea.getText());
				Set<Entry<String,String>> entrySet = map.entrySet();
				for (Entry<String, String> entry : entrySet) {
					File file = new File(filePath + File.separator + entry.getKey());
					PrintWriter writer = null;
					try {
						writer = new PrintWriter(file);
						writer.println(entry.getValue());
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}finally{
						if(null != writer){
							writer.close();
						}
					}
					
				}
				messageLabel.setText("Generate POJO success");
			}
		});
		btnGenerate.setBounds(399, 57, 95, 25);
		panel.add(btnGenerate);
		
		JButton btnNewButton_1 = new JButton("Copy");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 设置剪切板
				StringSelection ss = new StringSelection(jsonArea.getText());
				Toolkit.getDefaultToolkit().getSystemClipboard()
						.setContents(ss, null);
			}
		});
		btnNewButton_1.setBounds(10, 100, 95, 25);
		panel.add(btnNewButton_1);
		
		JButton btnPaste = new JButton("Paste");
		btnPaste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 从剪切板粘贴字符串
				Transferable t = Toolkit.getDefaultToolkit()
						.getSystemClipboard().getContents(null);

				try {
					if (null != t
							&& t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
						String text = (String) t
								.getTransferData(DataFlavor.stringFlavor);
						jsonArea.setText(text);
					}
				} catch (UnsupportedFlavorException e1) {
					e1.printStackTrace();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		});
		btnPaste.setBounds(127, 100, 95, 25);
		panel.add(btnPaste);
		
		JButton btnGiveAExample = new JButton("Give a Example");
		btnGiveAExample.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String exampleJson = "{\n" + 
						"    \"animals\": {\n" + 
						"        \"dog\": [\n" + 
						"            {\n" + 
						"                \"name\": \"Rufus\",\n" + 
						"                \"breed\": \"labrador\",\n" + 
						"                \"count\": 1,\n" + 
						"                \"twoFeet\": false\n" + 
						"            },\n" + 
						"            {\n" + 
						"                \"name\": \"Marty\",\n" + 
						"                \"breed\": \"whippet\",\n" + 
						"                \"count\": 1,\n" + 
						"                \"twoFeet\": false\n" + 
						"            }\n" + 
						"        ],\n" + 
						"        \"cat\": {\n" + 
						"            \"name\": \"Matilda\"\n" + 
						"        }\n" + 
						"    }\n" + 
						"}\n";
			
				jsonArea.setText(exampleJson);
			}
		});
		btnGiveAExample.setBounds(247, 100, 129, 25);
		panel.add(btnGiveAExample);
		
		JPanel mybatisPanel = new JPanel();
		tabbedPane.addTab("Mybatis", null, mybatisPanel, null);
		mybatisPanel.setLayout(null);
		
		JLabel lblDatabase = new JLabel("database");
		lblDatabase.setBounds(10, 10, 54, 15);
		mybatisPanel.add(lblDatabase);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_2, null);
		panel_2.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(77, 31, 170, 21);
		panel_2.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Prefix");
		lblNewLabel_1.setBounds(13, 34, 54, 15);
		panel_2.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(338, 31, 170, 21);
		panel_2.add(textField_1);
		
		JLabel lblSubfix = new JLabel("Subfix");
		lblSubfix.setBounds(274, 34, 54, 15);
		panel_2.add(lblSubfix);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 72, 364, 510);
		panel_2.add(scrollPane_1);
		
		JTextArea textArea_1 = new JTextArea();
		scrollPane_1.setViewportView(textArea_1);
		
		JButton btnNewButton_2 = new JButton("Append");
		btnNewButton_2.setBounds(401, 254, 95, 25);
		panel_2.add(btnNewButton_2);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(401, 323, 95, 25);
		panel_2.add(btnRemove);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(524, 72, 364, 510);
		panel_2.add(scrollPane_3);
		
		JTextArea textArea_2 = new JTextArea();
		scrollPane_3.setViewportView(textArea_2);
		
	}
}
