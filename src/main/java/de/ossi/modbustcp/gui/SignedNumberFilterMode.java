package de.ossi.modbustcp.gui;

public class SignedNumberFilterMode extends DocumentFilterMode {

	@Override
	boolean filter(CharSequence cs) {
		if (cs == null || cs.length() == 0) {
			return false;
		}
		final int sz = cs.length();
		for (int i = 0; i < sz; i++) {
			char c = cs.charAt(i);
			if (!isSign(c, i) && !Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * If the first char is a Sign "-" it is allowed
	 */
	private boolean isSign(char c, int index) {
		return index == 0 && (c == '-' || c == '+');
	}
}
