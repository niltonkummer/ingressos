/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.table;

import java.util.ArrayList;

/**
 *
 * @author niltonkummer
 */
public class RowTable {

    private ArrayList<ColumnTable> columns;

    public RowTable() {
        this.columns = new ArrayList<>();
    }

    public void append(ColumnTable columnTable) {
        columns.add(columnTable);
    }
    public String toString() {
        String tr = new String("<tr>");
        for (ColumnTable column : columns) {
            tr += column.buildColumn();
        }
        tr += "</tr>";
        return tr;
    }
}
