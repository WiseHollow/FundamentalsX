package com.wisehollow.fundamentalschat;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Created by John on 10/15/2016.
 */
public class EmojiChat {
    private static HashMap<String, String> emojis = new HashMap<>();

    public static boolean Load() {
        emojis.clear();

        emojis.put("<3", "❤");
        emojis.put("(star)", "★");
        emojis.put("o_o", "ಠ_ಠ");
        emojis.put(":)", "(•‿•)");
        emojis.put(";)", "(-‿◦)");
        emojis.put(":D", "(＾▽＾)");
        emojis.put("XD", "(ᗒᗨᗕ)");
        emojis.put("(sad)", "(´･ω･`)");
        emojis.put("(cry)", "(╥﹏╥)");
        emojis.put("(bear)", "ʕ•ᴥ•ʔ");
        emojis.put("(fight)", "(ง'̀-'́)ง");
        emojis.put("(love)", "♥‿♥");
        emojis.put("(quiet)", "ᕙ(⇀‸↼‶)ᕗ");
        emojis.put("(more)", "ლ(´ڡ`ლ)");
        emojis.put("(hi)", "ヾ(＾∇＾)");
        emojis.put("(bye)", "＼(￣O￣)");
        emojis.put("(confused)", "(⊙_◎)");
        emojis.put("(shrug)", "¯\\_(ツ)_/¯");
        emojis.put("(hug)", "⊂（♡⌂♡）⊃");
        emojis.put("(shy)", "(/ω＼)");

        return true;
    }

    public static String Convert(String message) {
        for (String s : message.split(" ")) {
            if (emojis.containsKey(s)) {
                message = message.replaceAll(Pattern.quote(s), emojis.get(s));
            }
        }

        return message;
    }
}
