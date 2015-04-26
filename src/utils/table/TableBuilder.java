/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.table;

import java.util.List;

/**
 *
 * @author niltonkummer
 */
public class TableBuilder {
    
    private List<RowTable> rows;
    
    public TableBuilder(List rows) {
        this.rows = rows;
    }
    
    public String buildTable(RowTable header) {
         StringBuilder sb = new StringBuilder();
         sb.append("<html><table border='1'>");
         sb.append(header.toString());
         for (RowTable row : rows) {
             sb.append(row.toString());
         }
         sb.append("</table></html>");
         return sb.toString();
    }
}