package de.ossi.modbustcp.gui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

abstract class DocumentFilterMode extends DocumentFilter {

	/**
	 * Filter applied on the CharSequence which evaluates if the CharacterSquence is
	 * allowed or not.
	 */
	abstract boolean filter(CharSequence cs);

	@Override
	public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
		StringBuilder sb = getStringBuilderWithText(fb);
		sb.insert(offset, string);
		if (filter(sb.toString())) {
			super.insertString(fb, offset, string, attr);
		}
	}

	@Override
	public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
		StringBuilder sb = getStringBuilderWithText(fb);
		sb.delete(offset, offset + length);
		if (filter(sb.toString())) {
			super.remove(fb, offset, length);
		}
		super.remove(fb, offset, length);
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
		StringBuilder sb = getStringBuilderWithText(fb);
		sb.replace(offset, offset + length, text);
		if (filter(sb.toString())) {
			super.replace(fb, offset, length, text, attrs);
		}
	}

	private StringBuilder getStringBuilderWithText(FilterBypass fb) throws BadLocationException {
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		return sb;
	}
}