package de.ossi.modbustcp.gui;

import java.util.function.Function;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

class DocumentFilterMode extends DocumentFilter {

	private final Function<String, Boolean> filter;

	public DocumentFilterMode(Function<String, Boolean> filter) {
		this.filter = filter;
	}

	public static boolean isNumeric(final CharSequence cs) {
		if (cs == null || cs.length() == 0) {
			return false;
		}
		final int sz = cs.length();
		for (int i = 0; i < sz; i++) {
			if (!Character.isDigit(cs.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.insert(offset, string);
		if (filter.apply(sb.toString())) {
			super.insertString(fb, offset, string, attr);
		}
	}

	@Override
	public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.delete(offset, offset + length);
		if (filter.apply(sb.toString())) {
			super.remove(fb, offset, length);
		}
		super.remove(fb, offset, length);
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.replace(offset, offset + length, text);
		if (filter.apply(sb.toString())) {
			super.replace(fb, offset, length, text, attrs);
		}
	}
}