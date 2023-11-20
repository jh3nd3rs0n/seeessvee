/**
 * Provides classes for reading and writing CSV files. The classes follows the 
 * standard set out in RFC 4180 with a few additions:
 * 
 * <ul>
 * <li>Line breaks can also be just the line feed character.</li>
 * <li>Header lines are read as CSV records.</li>
 * <li>Fields that contain only characters that are not double quote characters, 
 * comma characters, carriage return characters, or line feed characters do not 
 * need to be enclosed in double quote characters.</li>
 * <li>CSV records within a file do not have to have the same number of fields.
 * </li>
 * <li>Fields in a CSV record that do not have any special characters do not 
 * have to be enclosed in double quote characters.</li>
 * </ul> 
 */
package com.github.jh3nd3rs0n.seeessvee;