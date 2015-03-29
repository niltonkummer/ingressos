/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.table;

/**
 *
 * @author niltonkummer
 */
public class ColumnTable {

    public static final String ALIGN_LEFT = "left";
    public static final String ALIGN_RIGHT = "left";
    private String align;
    private String text;
    private boolean isHeader = false;

    // Build Header f

    public ColumnTable(String s) {
        this.align = align;
        this.text = s;
    }

    public ColumnTable(String align, String s) {
        this.align = align;
        this.text = s;
        this.isHeader = true;
    }

    public String buildColumn() {
        if (!isHeader) {
            return "<td>" + text + "</td>";
        } else {
            return "<th align='" + align + "'>" + text + "</th>";
        }
    }
}
