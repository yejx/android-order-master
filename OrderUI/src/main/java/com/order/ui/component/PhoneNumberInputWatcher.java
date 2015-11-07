package com.order.ui.component;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;

/**
 * 输入格式化手机号码     1XX-XXXX-XXXX
 * @author SeaZhang
 *
 *修改官方系统的PhoneNumberFormattingTextWatcher类及PhoneNumberUtils的formatNanpNumber，使其符合中国特色的社会主义使用
 * 1XX-XXXX-XXXX
 */
public class PhoneNumberInputWatcher implements TextWatcher{

    private boolean mFormatting;
    private boolean mDeletingHyphen;
    private int mHyphenStart;
    private boolean mDeletingBackward;
    
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,int after) {
		 // Check if the user is deleting a hyphen
        if (!mFormatting) {
            // Make sure user is deleting one char, without a selection
            final int selStart = Selection.getSelectionStart(s);
            final int selEnd = Selection.getSelectionEnd(s);
            if (s.length() > 1 // Can delete another character
                    && count == 1 // Deleting only one character
                    && after == 0 // Deleting
                    && s.charAt(start) == ' ' // a hyphen
                    && selStart == selEnd) { // no selection
                mDeletingHyphen = true;
                mHyphenStart = start;
                // Check if the user is deleting forward or backward
                if (selStart == start + 1) {
                    mDeletingBackward = true;
                } else {
                    mDeletingBackward = false;
                }
            } else {
                mDeletingHyphen = false;
            }
        }
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		
		
	}

	@Override
	public void afterTextChanged(Editable text) {
		 // Make sure to ignore calls to afterTextChanged caused by the work done below
        if (!mFormatting) {
            mFormatting = true;

            // If deleting the hyphen, also delete the char before or after that
            if (mDeletingHyphen && mHyphenStart > 0) {
                if (mDeletingBackward) {
                    if (mHyphenStart - 1 < text.length()) {
                        text.delete(mHyphenStart - 1, mHyphenStart);
                    }
                } else if (mHyphenStart < text.length()) {
                    text.delete(mHyphenStart, mHyphenStart + 1);
                }
            }

            formatNanpNumber(text);

            mFormatting = false;
        }
		
	}

	private static final int NANP_STATE_DIGIT = 1;
    private static final int NANP_STATE_PLUS = 2;
    private static final int NANP_STATE_ONE = 3;
    private static final int NANP_STATE_DASH = 4;

    /**
     * Formats a phone number in-place using the NANP formatting rules. Numbers will be formatted
     * as:
     *
     * <p><code>
     * xxx
     * xxx-x
     * xxx-xxxx-xxxx
     * </code></p>
     *
     * @param text the number to be formatted, will be modified with the formatting
     */
    public  void formatNanpNumber(Editable text) {
        int length = text.length();
        if (length > "+1nn-nnnn-nnnn".length()) {
            // The string is too long to be formatted
            return;
        } else if (length <= 3) {
            // The string is either a shortcode or too short to be formatted
            return;
        }

        CharSequence saved = text.subSequence(0, length);

        // Strip the dashes first, as we're going to add them back
        int p = 0;
        while (p < text.length()) {
            if (text.charAt(p) == ' ') {
                text.delete(p, p + 1);
            } else {
                p++;
            }
        }
        length = text.length();

        // When scanning the number we record where dashes need to be added,
        // if they're non-0 at the end of the scan the dashes will be added in
        // the proper places.
        int dashPositions[] = new int[2];
        int numDashes = 0;

        int state = NANP_STATE_DIGIT;
        int numDigits = 0;
        for (int i = 0; i < length; i++) {
            char c = text.charAt(i);
            switch (c) {
                case '1':
//                    if (numDigits == 0 || state == NANP_STATE_PLUS) {
//                        state = NANP_STATE_ONE;
//                        break;
//                    }
                    // fall through
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case '0':
                    if (state == NANP_STATE_PLUS) {
                        // Only NANP number supported for now
                        text.replace(0, length, saved);
                        return;
                    } else if (state == NANP_STATE_ONE) {
                        // Found either +1 or 1, follow it up with a dash
                        dashPositions[numDashes++] = i;
                    } else if (state != NANP_STATE_DASH && (numDigits == 3 || numDigits == 7)) {
                        // Found a digit that should be after a dash that isn't
                        dashPositions[numDashes++] = i;
                    }
                    state = NANP_STATE_DIGIT;
                    numDigits++;
                    break;

                case ' ':
                    state = NANP_STATE_DASH;
                    break;

                case '+':
                    if (i == 0) {
                        // Plus is only allowed as the first character
                        state = NANP_STATE_PLUS;
                        break;
                    }
                    // Fall through
                default:
                    // Unknown character, bail on formatting
                    text.replace(0, length, saved);
                    return;
            }
        }

//        if (numDigits == 7) {
//            // With 8 digits we want xxx-xxxx, not xxx-xxx-x
//            numDashes--;
//        }

        // Actually put the dashes in place
        for (int i = 0; i < numDashes; i++) {
            int pos = dashPositions[i];
            text.replace(pos + i, pos + i, " ");
        }

        // Remove trailing dashes
        int len = text.length();
        while (len > 0) {
            if (text.charAt(len - 1) == ' ') {
                text.delete(len - 1, len);
                len--;
            } else {
                break;
            }
        }
    }
}
