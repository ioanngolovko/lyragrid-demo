package ru.curs.lyragridtest;

import ru.curs.celesta.CelestaException;
import ru.curs.lyra.grid.Rules;
import ru.curs.lyra.grid.VarcharFieldEnumerator;

public class TestEnum {
	public static void main(String[] args) throws CelestaException {
		final Rules rules = new Rules() {
			@Override
			public String getRules() {
				return "-<а<б<в<г<д<е<ж<з<и<й<к<л<м<н<о<п<р<с<т<у<ф<х<ц<ч<ш<щ<ъ<ы<ь<э<ю<я";
			}

			@Override
			public String getName() {
				return "simple";
			}
		};

		VarcharFieldEnumerator num = new VarcharFieldEnumerator(rules, 10);
		num.setValue("ижевск");
		System.out.println(num.getOrderValue().toString(10));
		num.setValue("иркутск");
		System.out.println(num.getOrderValue().toString(10));
		num.setValue("йошкар-ола");
		System.out.println(num.getOrderValue().toString(10));
	}
}
