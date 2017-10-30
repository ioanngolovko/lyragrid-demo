package ru.curs.lyragridtest;

import ru.curs.celesta.CelestaException;
import ru.curs.celesta.dbutils.adaptors.StaticDataAdaptor;
import ru.curs.lyra.grid.VarcharFieldEnumerator;

import java.util.Arrays;
import java.util.List;

public class TestEnum {
	public static void main(String[] args) throws CelestaException {

		StaticDataAdaptor staticDataAdaptor = new StaticDataAdaptor() {
			@Override
			public List<String> selectStaticStrings(List<String> data, String columnName, String orderBy) throws CelestaException {
				return Arrays.asList(
						"-<а<б<в<г<д<е<ж<з<и<й<к<л<м<н<о<п<р<с<т<у<ф<х<ц<ч<ш<щ<ъ<ы<ь<э<ю<я"
								.split("<")
				);
			}

			@Override
			public int compareStrings(String left, String right) throws CelestaException {
				return left.compareTo(right);
			}
		};


		VarcharFieldEnumerator num = new VarcharFieldEnumerator(staticDataAdaptor, 10);
		num.setValue("ижевск");
		System.out.println(num.getOrderValue().toString(10));
		num.setValue("иркутск");
		System.out.println(num.getOrderValue().toString(10));
		num.setValue("йошкар-ола");
		System.out.println(num.getOrderValue().toString(10));
	}
}
