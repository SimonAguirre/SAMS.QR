package com.sams.samsqr;


import android.util.Log;

public class InputProcessor {
    private  String raw_input;
    private String first_name;
    private String middle_name;
    private String last_name;
    private String name_suffix;
    private String full_name;
    private char middle_initial;
    private String lrn;
    private String raw_text;
    private String grade_level;
    private String section;
    protected int decode_key;
    protected String decode_key_arr;
    protected int cipher_key;

    public InputProcessor(String input) throws StringIndexOutOfBoundsException{
        //initialize input
        this.raw_input = input.substring(1);
        this.decode_key = Integer.parseInt(input.substring(0,1));

        this.decode_key_arr = raw_input.substring(0,decode_key);
        this.raw_text = raw_input.substring(decode_key);

        Log.d("Input Processor: Getting Information","");
        this.lrn = raw_text.substring(0,raw_text.indexOf(decode_key_arr.substring(0,1)));
        raw_text = raw_text.replace(lrn+decode_key_arr.substring(0,1),"");
        decode_key_arr = decode_key_arr.substring(1);
        Log.d("Input Processor: Getting Information",lrn);

        this.first_name = raw_text.substring(0,raw_text.indexOf(decode_key_arr.substring(0,1)));
        raw_text = raw_text.replace(first_name+decode_key_arr.substring(0,1),"");
        decode_key_arr = decode_key_arr.substring(1);
        Log.d("Input Processor: Getting Information",first_name);

        this.middle_name = raw_text.substring(0,raw_text.indexOf(decode_key_arr.substring(0,1)));
        raw_text = raw_text.replace(middle_name+decode_key_arr.substring(0,1),"");
        decode_key_arr = decode_key_arr.substring(1);
        Log.d("Input Processor: Getting Information",middle_name);

        this.last_name = raw_text.substring(0,raw_text.indexOf(decode_key_arr.substring(0,1)));
        raw_text = raw_text.replace(last_name+decode_key_arr.substring(0,1),"");
        decode_key_arr = decode_key_arr.substring(1);
        Log.d("Input Processor: Getting Information",last_name);

        this.name_suffix = raw_text.substring(0,raw_text.indexOf(decode_key_arr.substring(0,1)));
        raw_text = raw_text.replace(name_suffix+decode_key_arr.substring(0,1),"");
        decode_key_arr = decode_key_arr.substring(1);
        Log.d("Input Processor: Getting Information",name_suffix);

        this.grade_level = raw_text.substring(0,raw_text.indexOf(decode_key_arr.substring(0,1)));
        raw_text = raw_text.replace(grade_level+decode_key_arr.substring(0,1),"");
        decode_key_arr = decode_key_arr.substring(1);
        Log.d("Input Processor: Getting Information",grade_level);

        this.section = raw_text.substring(0,raw_text.indexOf(decode_key_arr.substring(0,1)));
        raw_text = raw_text.replace(section+decode_key_arr.substring(0,1),"");
        decode_key_arr = decode_key_arr.substring(1);
        Log.d("Input Processor: Getting Information",section);
        //end initialize info


        //generate info and test nullables
         if (middle_name.equals("null")) {
            middle_name = "";
            this.middle_initial = ' ';
            this.full_name = first_name+" "+last_name+" "+name_suffix;
        } else {
            this.middle_initial = middle_name.toCharArray()[0];
             this.full_name = first_name+" "+middle_initial+". "+last_name+" "+name_suffix;
        }

        if (name_suffix.equals("null")) {
            name_suffix = "";
        }

        //end generate info
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getName_suffix() {
        return name_suffix;
    }

    public String getFull_name() {
        return full_name;
    }

    public char getMiddle_initial() {
        return middle_initial;
    }

    public String getLrn() {
        return lrn;
    }

    public String getGrade_level() {
        return grade_level;
    }

    public String getSection() {
        return section;
    }
}
