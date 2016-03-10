package com.example.bbschool.bbremotemobile;

import java.security.Key;
import java.util.HashMap;

/**
 * Created by Braeden on 3/9/2016.
 */
public final class AndroidKeyCodes {

    private static HashMap<Integer, String> CodeToKey = new HashMap<Integer, String>();
    private static HashMap<String, Integer> KeyToCode = new HashMap<String, Integer>();

    static {
        CodeToKey.put(R.integer.key_0, "0");
        CodeToKey.put(R.integer.key_1, "1");
        CodeToKey.put(R.integer.key_2, "2");
        CodeToKey.put(R.integer.key_3, "3");
        CodeToKey.put(R.integer.key_4, "4");
        CodeToKey.put(R.integer.key_5, "5");
        CodeToKey.put(R.integer.key_6, "6");
        CodeToKey.put(R.integer.key_7, "7");
        CodeToKey.put(R.integer.key_8, "8");
        CodeToKey.put(R.integer.key_9, "9");
        CodeToKey.put(R.integer.key_a, "A");
        CodeToKey.put(R.integer.key_b, "B");
        CodeToKey.put(R.integer.key_c, "C");
        CodeToKey.put(R.integer.key_d, "D");
        CodeToKey.put(R.integer.key_e, "E");
        CodeToKey.put(R.integer.key_f, "F");
        CodeToKey.put(R.integer.key_g, "G");
        CodeToKey.put(R.integer.key_h, "H");
        CodeToKey.put(R.integer.key_i, "I");
        CodeToKey.put(R.integer.key_j, "J");
        CodeToKey.put(R.integer.key_k, "K");
        CodeToKey.put(R.integer.key_l, "L");
        CodeToKey.put(R.integer.key_m, "M");
        CodeToKey.put(R.integer.key_n, "N");
        CodeToKey.put(R.integer.key_o, "O");
        CodeToKey.put(R.integer.key_p, "P");
        CodeToKey.put(R.integer.key_q, "Q");
        CodeToKey.put(R.integer.key_r, "R");
        CodeToKey.put(R.integer.key_s, "S");
        CodeToKey.put(R.integer.key_t, "T");
        CodeToKey.put(R.integer.key_u, "U");
        CodeToKey.put(R.integer.key_v, "V");
        CodeToKey.put(R.integer.key_w, "W");
        CodeToKey.put(R.integer.key_x, "X");
        CodeToKey.put(R.integer.key_y, "Y");
        CodeToKey.put(R.integer.key_z, "Z");
        CodeToKey.put(R.integer.key_shift, "SHIFT");
        CodeToKey.put(R.integer.key_ctrl, "CTRL");
        CodeToKey.put(R.integer.key_alt, "ALT");
        CodeToKey.put(R.integer.key_del, "DEL");
        CodeToKey.put(R.integer.key_backspace, "BACKSPACE");
        CodeToKey.put(R.integer.key_win, "WIN");
        CodeToKey.put(R.integer.key_tab, "TAB");
        CodeToKey.put(R.integer.key_space, "SPACE");
        CodeToKey.put(R.integer.key_enter, "ENTER");
        CodeToKey.put(R.integer.key_esc, "ESC");
        CodeToKey.put(R.integer.key_print_screen, "PRINT SCREEN");
        CodeToKey.put(R.integer.key_scroll_lock, "SCROLL LOCK");
        CodeToKey.put(R.integer.key_pause, "PAUSE");
        CodeToKey.put(R.integer.key_insert, "INSERT");
        CodeToKey.put(R.integer.key_home, "HOME");
        CodeToKey.put(R.integer.key_end, "END");
        CodeToKey.put(R.integer.key_page_up, "PAGE UP");
        CodeToKey.put(R.integer.key_page_down, "PAGE DOWN");
        CodeToKey.put(R.integer.key_left_arrow, "LEFT ARROW");
        CodeToKey.put(R.integer.key_right_arrow, "RIGHT ARROW");
        CodeToKey.put(R.integer.key_down_arrow, "DOWN ARROW");
        CodeToKey.put(R.integer.key_up_arrow, "UP ARROW");
        CodeToKey.put(R.integer.key_f1, "F1");
        CodeToKey.put(R.integer.key_f2, "F2");
        CodeToKey.put(R.integer.key_f3, "F3");
        CodeToKey.put(R.integer.key_f4, "F4");
        CodeToKey.put(R.integer.key_f5, "F5");
        CodeToKey.put(R.integer.key_f6, "F6");
        CodeToKey.put(R.integer.key_f7, "F7");
        CodeToKey.put(R.integer.key_f8, "F8");
        CodeToKey.put(R.integer.key_f9, "F9");
        CodeToKey.put(R.integer.key_f10, "F10");
        CodeToKey.put(R.integer.key_f11, "F11");
        CodeToKey.put(R.integer.key_f12, "F12");
        CodeToKey.put(R.integer.key_exclamation, "!");
        CodeToKey.put(R.integer.key_at, "@");
        CodeToKey.put(R.integer.key_number, "#");
        CodeToKey.put(R.integer.key_dollar, "$");
        CodeToKey.put(R.integer.key_forward_slash, "/");
        CodeToKey.put(R.integer.key_exponent, "^");
        CodeToKey.put(R.integer.key_and, "&");
        CodeToKey.put(R.integer.key_times, "*");
        CodeToKey.put(R.integer.key_left_parentheses, "(");
        CodeToKey.put(R.integer.key_right_parentheses, ")");
        CodeToKey.put(R.integer.key_dash, "-");
        CodeToKey.put(R.integer.key_apostrophe, "'");
        CodeToKey.put(R.integer.key_quote, "\"");
        CodeToKey.put(R.integer.key_colon, ":");
        CodeToKey.put(R.integer.key_semicolon, ";");
        CodeToKey.put(R.integer.key_comma, ",");
        CodeToKey.put(R.integer.key_question, "?");
        CodeToKey.put(R.integer.key_period, ".");
        CodeToKey.put(R.integer.key_plus, "+");
        CodeToKey.put(R.integer.key_equal, "=");
        CodeToKey.put(R.integer.key_less_than, "<");
        CodeToKey.put(R.integer.key_greater_than, ">");
        CodeToKey.put(R.integer.key_left_brace, "{");
        CodeToKey.put(R.integer.key_right_brace, "}");
        CodeToKey.put(R.integer.key_left_bracket, "[");
        CodeToKey.put(R.integer.key_right_bracket, "]");
        CodeToKey.put(R.integer.key_percent, "%");
        CodeToKey.put(R.integer.key_tilda, "~");
        CodeToKey.put(R.integer.key_grave, "`");
        CodeToKey.put(R.integer.key_underscore, "_");
        CodeToKey.put(R.integer.key_back_slash, "\\");
        CodeToKey.put(R.integer.key_vertical_line, "|");
        CodeToKey.put(R.integer.key_mode_change, "CHANGE MODE");

        for (Integer i: CodeToKey.keySet()) {
            KeyToCode.put(CodeToKey.get(i), i);
        }
    }

    private AndroidKeyCodes(){}

    public static Integer lookupCode(String key){
        return KeyToCode.get(key);
    }

    public static String lookupKey(Integer code){
        return CodeToKey.get(code);
    }
    
}
