package ru.curs.lyragridtest;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.DefaultBoundedRangeModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ru.curs.celesta.Celesta;
import ru.curs.celesta.CelestaException;
import ru.curs.lyra.BasicGridForm;
import ru.curs.lyra.LyraFieldValue;
import ru.curs.lyra.LyraFormData;

public class GridTestApp extends JFrame {

	private JPanel contentPane;

	private Celesta celesta;
	private BasicGridForm gf;

	private final DefaultTableModel tm;

	private final JTable table;

	private int previousValue;

	public GridTestApp() throws CelestaException {
		setFont(new Font("Dialog", Font.PLAIN, 20));

		initCelesta();

		Vector<String> columns = new Vector<>();
		gf.getFieldsMeta().forEach((k, v) -> {
			columns.add(v.getCaption());
		});
		tm = new DefaultTableModel(columns, 10);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 596, 351);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		table = new JTable(tm);
		JScrollPane scrollPane = new JScrollPane(table);

		scrollPane.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent arg0) {
				int newRowNum = (int) (table.getParent().getSize().getHeight() / table.getRowHeight());
				if (tm.getRowCount() != newRowNum) {
					tm.setRowCount(newRowNum);
					try {
						getRows(gf.getRowsH(newRowNum));
					} catch (CelestaException e) {
						e.printStackTrace();
					}
				}
			}

		});

		JScrollBar scrollBar = new JScrollBar();

		scrollBar.setMaximum(gf.getApproxTotalCount());
		scrollBar.getModel().addChangeListener((changeEvent) -> {
			int newValue = scrollBar.getValue();
			if (newValue == previousValue)
				return;
			if (Math.abs(newValue - previousValue) > 1
					&& !((DefaultBoundedRangeModel) changeEvent.getSource()).getValueIsAdjusting()) {
				previousValue = newValue;
				return;
			}
			try {
				getRows(gf.getRowsH(newValue, tm.getRowCount()));
			} catch (CelestaException e) {
				e.printStackTrace();
			}
			previousValue = newValue;
		});

		gf.setChangeNotifier(() -> {
			EventQueue.invokeLater(() -> {
				scrollBar.setMaximum(gf.getApproxTotalCount());
				int pos = gf.getTopVisiblePosition();
				scrollBar.setValue(pos);
			});
		});

		searchField = new JTextField();
		searchField.setText("31018001000036351");
		searchField.setColumns(10);

		JButton button = new JButton(">>>");
		button.addActionListener((evt) -> {
			try {
				getRows(gf.setPositionH(tm.getRowCount(), searchField.getText()));
				int pos = gf.getTopVisiblePosition();
				scrollBar.setValue(pos);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(searchField, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(button).addGap(297))
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(scrollBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(14)))));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(7)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(searchField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(button))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
								.addComponent(scrollBar, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE))
						.addContainerGap()));
		contentPane.setLayout(gl_contentPane);

	}

	private void initCelesta() throws CelestaException {
		Properties p = Settings.getSettings();
		celesta = Celesta.createInstance(p);
		celesta.login("super", "super");
		gf = (BasicGridForm) celesta.runPython("super", "lyra.lyraplayer.getFormInstance", "kladr.gridform.StreetGrid")
				.__tojava__(BasicGridForm.class);
	}

	private void getRows(List<LyraFormData> rows) throws CelestaException {
		int i = 0;
		for (LyraFormData lfd : rows) {
			int j = 0;
			for (LyraFieldValue lfv : lfd.getFields()) {
				tm.setValueAt(lfv.getValue(), i, j++);
			}
			i++;
		}
	}

	private static final long serialVersionUID = 1L;
	private JTextField searchField;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				GridTestApp frame = new GridTestApp();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
