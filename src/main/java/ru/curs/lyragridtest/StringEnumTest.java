package ru.curs.lyragridtest;

import java.awt.EventQueue;
import java.math.BigInteger;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;

import ru.curs.celesta.CelestaException;
import ru.curs.celesta.dbutils.adaptors.StaticDataAdaptor;
import ru.curs.lyra.grid.VarcharFieldEnumerator;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;

public class StringEnumTest extends JFrame {

	private static final long serialVersionUID = 1L;

	final VarcharFieldEnumerator num;

	private JPanel contentPane;
	private JSlider slider;

	public StringEnumTest() throws CelestaException {

		StaticDataAdaptor staticDataAdaptor = new StaticDataAdaptor() {
			@Override
			public List<String> selectStaticStrings(List<String> data, String columnName, String orderBy) throws CelestaException {
				return Arrays.asList(
						"<А<Б<В<Г<Д<Е<Ж<З<И<Й<К<Л<М<Н<О<П<Р<С<Т<У<Ф<Х<Ц<Ч<Ш<Щ<Ъ<Ы<Ь<Э<Ю<Я"
								.split("<")
				);
			}

			@Override
			public int compareStrings(String left, String right) throws CelestaException {
				return left.compareTo(right);
			}
		};


		num = new VarcharFieldEnumerator(staticDataAdaptor, "ВАНЯ", "МАША", 4);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 596, 194);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblNum = new JLabel("New label");
		lblNum.setFont(new Font("Tahoma", Font.PLAIN, 24));
		JLabel lblVal = new JLabel("New label");
		lblVal.setFont(new Font("Tahoma", Font.BOLD, 24));

		slider = new JSlider();
		slider.setMajorTickSpacing(1000);
		slider.addChangeListener((e) -> {
			int val = slider.getValue();
			lblNum.setText(Integer.toString(val));
			num.setOrderValue(BigInteger.valueOf(val));
			lblVal.setText(num.getValue());
		});
		slider.setMaximum(num.cardinality().subtract(BigInteger.ONE).intValue());

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addContainerGap()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(lblNum)
												.addGap(181)
												.addComponent(lblVal))
										.addComponent(slider, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE))
								.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addContainerGap()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNum)
										.addComponent(lblVal))
								.addPreferredGap(ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
								.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(28))
		);
		contentPane.setLayout(gl_contentPane);

	}

	public void setOrdinal(int val) {
		slider.setValue(val);
	}

	public static void main(String[] args) throws CelestaException, InterruptedException {
		StringEnumTest frame = new StringEnumTest();

		EventQueue.invokeLater(() -> {
			try {
				frame.setVisible(true);
				// frame.setOrdinal(50000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		String[] vals = { "ВАНЯ", "ВАСЯ", "ГАЛЯ", "ДИМА", "ЖЕНЯ", "ИРА", "КОЛЯ", "МАША" };
		int[] ords = new int[vals.length + 1];
		int i = 0;
		for (String val : vals) {
			frame.num.setValue(val);
			ords[i++] = frame.num.getOrderValue().intValue();
		}

		while (true) {
			frame.setOrdinal(0);
			Thread.sleep(800);
			for (i = 0; i < ords.length - 1; i++) {
				for (int j = ords[i]; j < ords[i + 1]; j += 100) {
					frame.setOrdinal(j);
					Thread.sleep(1);
				}
				frame.setOrdinal(ords[i + 1]);
				Thread.sleep(800);
			}
		}

	}
}
